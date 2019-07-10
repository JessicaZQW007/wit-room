package com.yhxc.controller.project;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.Equipment;

import com.yhxc.entity.project.Air;
import com.yhxc.entity.project.AirBrand;
import com.yhxc.entity.system.InfraredCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;

import com.yhxc.repository.project.AirBrandRepository;
import com.yhxc.repository.project.AirRepository;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.project.AirBrandService;
import com.yhxc.service.project.AirService;
import com.yhxc.service.system.InfraredCodeService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.*;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.List;

/**
 * @Date: 2018/10/31 15:09
 * @Author:
 */

@RequestMapping("/equipmentBrand")
@Controller
public class AirBrandController {
    @Resource
    private AirBrandService ariBrandService;
    @Resource
    private AirService ariService;
    @Resource
    private EquipmentService equipmentService;

    @Resource
    private LogService logService;
    @Resource
    private InfraredCodeService infraredCodeService;





    /**
     * 当pId为空时获取品牌，当pId不为空时获取该父级下的型号
     * @param pId
     * @return
     */
    @RequestMapping("/findBypId")
    @ResponseBody
    public ResultInfo findBypId(String pId,String brand) {
        if(StringUtil.isEmpty(pId)){
            pId = "-1";
        }
        return new ResultInfo(StatusCode.SUCCESS, "success", ariBrandService.findAll(pId,brand));
    }


    /**
     * 品牌名查询 型号
     * @param
     * @return
     */
    @RequestMapping("/findlist")
    @ResponseBody
    public ResultInfo findlist(String brand,String id) {
        JSONArray datas=ariBrandService.findlist(brand,id);

        return new ResultInfo(StatusCode.SUCCESS, "success", datas);
    }


    /**
     * 根据id查询品牌 型号
     * @param
     * @return
     */
    @RequestMapping("/findone")
    @ResponseBody
    public ResultInfo findone(String id) {

        return new ResultInfo(StatusCode.SUCCESS, "success", ariBrandService.findById(id));
    }


    /**
     * 根据id查询品牌 型号
     * @param
     * @return
     */
    @RequestMapping("/findone1")
    @ResponseBody
    public ResultInfo findone1(String brand,String model) {
        JSONArray datasAir=ariBrandService.findOne(brand,model);

        return new ResultInfo(StatusCode.SUCCESS, "成功！",datasAir);
    }



    /**新增或者修改品牌
     * @param eb
     * @param uploadFile
     * @return
     */
    @RequestMapping("/saveBrand")
    @ResponseBody
    public ResultInfo saveBrand(AirBrand eb, MultipartFile uploadFile) {

        String info = "";
        if (StringUtil.isEmpty(eb.getId())) {
            if (uploadFile != null && uploadFile.getSize() > 10) {
                eb.setImg(UploadUtil.uploadFile(uploadFile, "model"));
            }
            eb.setId(UuidUtil.get32UUID());
          eb.setCreatetime(DateUtil.getTime());
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, info + "," + eb));
        } else {
            ariBrandService.unpdateModel(eb.getBrand(),eb.getId());
            info = "修改成功";
            logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
        }
        ariBrandService.save(eb);
        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }


    /**新增或者修改型号
     * @param eb
     * @param uploadFile
     * @return
     *//*
    @RequestMapping("/saveModel")
    @ResponseBody
    public ResultInfo saveModel(AirBrand eb, MultipartFile uploadFile) {

        String info = "";
        if (StringUtil.isEmpty(eb.getId())) {
            if (uploadFile != null && uploadFile.getSize() > 10) {
                eb.setImg(UploadUtil.uploadFile(uploadFile, "model"));
            }
            eb.setId(UuidUtil.get32UUID());
            eb.setCreatetime(DateUtil.getTime());
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, info + "," + eb));
        } else {
            AirBrand e = ariBrandService.findById(eb.getId());
            if (uploadFile != null && uploadFile.getSize() > 10) {
                FileUtil.deleteDir(e.getImg(), Const.fileRoot + "model");
                eb.setImg(UploadUtil.uploadFile(uploadFile, "model"));
            } else {
                String img = e.getImg();
                eb.setImg(img);
            }


            info = "修改成功";
            logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
        }
        ariBrandService.save(eb);



        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }*/



    /**新增或者修改型号
     * @param eb
     * @param uploadFile
     * @return
     */
    @RequestMapping("/saveModel")
    @ResponseBody
    public ResultInfo saveModel(AirBrand eb, Air air, MultipartFile uploadFile,String airBrandID,String airID) {

        String info = "";
        if (StringUtil.isEmpty(airBrandID)) {
            //空调品牌型号
            AirBrand airBrand=ariBrandService.findByBrandModel(eb.getBrand(),eb.getModel());

            if(airBrand!=null){
                //存在
                info = "型号已存在";

            }else{
                if (uploadFile != null && uploadFile.getSize() > 10) {
                    eb.setImg(UploadUtil.uploadFile(uploadFile, "model"));
                }
                eb.setId(UuidUtil.get32UUID());
                eb.setCreatetime(DateUtil.getTime());
                info = "新增成功";
                logService.save(new Log(Log.ADD_ACTION, info + "," + eb));
                ariBrandService.save(eb);

                //空调
                air.setCreatetime(DateUtil.getTime());
                air.setId(UuidUtil.get32UUID());
                air.setBrandId(eb.getId());
                logService.save(new Log(Log.ADD_ACTION, info + "," + air));
                ariService.save(air);
            }





        } else {

            AirBrand airBrand=ariBrandService.findByBrandModel(eb.getBrand(),eb.getModel());

            if(airBrand!=null){
                //存在
                info = "型号已存在";

            }else{
                //空调品牌型号
                AirBrand e = ariBrandService.findById(airBrandID);
                if (uploadFile != null && uploadFile.getSize() > 10) {
                    FileUtil.deleteDir(e.getImg(), Const.fileRoot + "model");
                    eb.setImg(UploadUtil.uploadFile(uploadFile, "model"));
                } else {
                    String img = e.getImg();
                    eb.setImg(img);
                }


                info = "修改成功";
                logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
                ariBrandService.updateModelAndImg(eb.getModel(),eb.getImg(),airBrandID);

                if (StringUtil.isNotEmpty(airID)) {
                    //空调
                    System.out.println("airID="+airID);
                    logService.save(new Log(Log.UPDATE_ACTION, info + "," + air));
                    ariService.updatePowerCuurrent(air.getPower(), air.getCurrent(),air.getAirType(), airID.toString());

                }
            }

        }



        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }

    /**
     * 删除型号
     *
     * @return
     */
    @RequestMapping("/deleteModel")
    @ResponseBody
    public ResultInfo deleteModel(String id) {
         Integer num=  ariService.findBybrand_id(id);
        if (num>=2) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！品牌和型号已被使用！");
        }else {
            logService.save(new Log(Log.DELETE_ACTION, "删除了 ："+ariBrandService.findById(id).getBrand()+ariBrandService.findById(id).getModel()));
            FileUtil.deleteDir(ariBrandService.findById(id).getImg(), Const.fileRoot + "model");
            ariBrandService.delete(id);
            ariService.deleteByBrandId(id);

            return new ResultInfo(StatusCode.SUCCESS, "删除成功");
        }

    }


    /**
     * 删除品牌
     *
     * @return
     */
    @RequestMapping("/deleteBrand")
    @ResponseBody
    public ResultInfo deleteBrand(String id) {
        List<AirBrand> num=ariBrandService.findModel(id);
        if (num.size()>0) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！请先删除型号！！");
        }else {
            logService.save(new Log(Log.DELETE_ACTION, "删除了 ："+ariBrandService.findById(id).getBrand()+ariBrandService.findById(id).getModel()));
            ariBrandService.delete(id);
            return new ResultInfo(StatusCode.SUCCESS, "删除成功");
        }

    }

    /**
     * 品牌名查询 红外代码
     * @param
     * @return
     */
    @RequestMapping("/findByBrand")
    @ResponseBody
    public ResultInfo findByBrand(String brand) {
      List<InfraredCode> datas=infraredCodeService.findByBrand(brand);
        return new ResultInfo(StatusCode.SUCCESS, "success", datas);
    }


    /**
     * 品牌名,型号 查询 红外代码
     * @param
     * @return
     */
    @RequestMapping("/findByBrandModel")
    @ResponseBody
    public ResultInfo findByBrandModel(String brand,String model) {
        AirBrand datas=ariBrandService.findByBrandModel(brand,model);
        return new ResultInfo(StatusCode.SUCCESS, "success", datas);
    }




    /**
     * 新增 红外代码 绑定型号
     * @param codeId 红外码id
     *  @param   id 型号id
     * @return
     */
    @RequestMapping("/updateCodeId")
    @ResponseBody
    public ResultInfo updateCodeId(String codeId,String id) {
         ariBrandService.updateCodeId(codeId,id);
        logService.save(new Log(Log.ADD_ACTION, "绑定"+ariBrandService.findById(id).getBrand()+ariBrandService.findById(id).getModel()+"的红外代码"));
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }




}
