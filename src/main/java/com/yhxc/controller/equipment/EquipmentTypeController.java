package com.yhxc.controller.equipment;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.system.Log;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.equipment.EquipmentTypeService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date: 2018/10/31 15:09
 * @Author: 赵贺飞
 */

@RequestMapping("/equipmentType")
@Controller
public class EquipmentTypeController {
    @Resource
    private EquipmentTypeService equipmentTypeService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private LogService logService;

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
        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentTypeService.findAll(pId,brand));
    }


    /**
     * 品牌名查询 型号
     * @param
     * @return
     */
    @RequestMapping("/findlist")
    @ResponseBody
    public ResultInfo findlist(String brand) {

        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentTypeService.findlist(brand));
    }




    /**
     * 查询所有父Pid等于-1的设备名称
     * @param
     * @return
     */
    @RequestMapping("/findEname")
    @ResponseBody
    public ResultInfo findEname(String ename) {

        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentTypeService.findEname(ename));
    }








    /**
     * 根据设备名称查询
     * @param
     * @return
     */
    @RequestMapping("/findByPid")
    @ResponseBody
    public ResultInfo findByEname(String pid) {

        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentTypeService.findByPid(pid));
    }




    /**
     * 下拉联动查询
     * @param
     * @return
     */
    @RequestMapping("/findEnameModelBrand")
    @ResponseBody
    public ResultInfo findEnameModelBrand(String pid) {

        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentTypeService.findEnameModelBrand());
    }








    /**
     * 根据id查询品牌 型号
     * @param
     * @return
     */
    @RequestMapping("/findone")
    @ResponseBody
    public ResultInfo findone(String id) {

        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentTypeService.findById(id));
    }



    /**
     * @param eb
     * @param uploadFile
     * @return
     */
    @RequestMapping("/saveBrand")
    @ResponseBody
    public ResultInfo saveBrand(EquipmentType eb, MultipartFile uploadFile) {

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
          equipmentTypeService.unpdateModel(eb.getBrand(),eb.getId());

            info = "修改成功";
            logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
        }
        equipmentTypeService.save(eb);
        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }

    /**
     * @param eb
     * @param uploadFile
     * @return
     */
    @RequestMapping("/saveModel")
    @ResponseBody
    public ResultInfo saveModel(EquipmentType eb, MultipartFile uploadFile) {

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
            EquipmentType e = equipmentTypeService.findById(eb.getId());
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
        equipmentTypeService.save(eb);
        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }




    /**添加或者修改设备名称
     * @return
     */
    @RequestMapping("/saveOrUpdateEname")
    @ResponseBody
    public ResultInfo saveBrand(EquipmentType eb) {
        String info = "";
        if (StringUtil.isEmpty(eb.getId())) {

            eb.setId(UuidUtil.get32UUID());
            eb.setCreatetime(DateUtil.getTime());
            eb.setpId("-1");
            int num=equipmentTypeService.countEname(eb.getEname());
            if(num>0){
                //已存在
                info = "设备名称已存在";
            }else{
                info = "新增成功";
                logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
                equipmentTypeService.save(eb);
            }



        }
        else {
            int num=equipmentTypeService.countEname(eb.getEname());
            if(num>0){
                //已存在
                info = "设备名称已存在";
            }else{
                info = "修改成功";
                logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
                equipmentTypeService.updateEname(eb.getEname(),eb.getId());
            }
        }





        return new ResultInfo(StatusCode.SUCCESS, "success",info);

    }







    /**
     * @param eb
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(EquipmentType eb,String pid) {
        String info = "";
        if (StringUtil.isEmpty(eb.getId())) {

            eb.setId(UuidUtil.get32UUID());
            eb.setCreatetime(DateUtil.getTime());
            eb.setpId(pid);
            int num=equipmentTypeService.count(eb.getBrand(),eb.getModel());
            if(num>0){
                //已存在
                info = "该型号品牌已存在";
            }else{
                info = "新增成功";
                logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
                equipmentTypeService.save(eb);
            }



        }
        else {
            int num=equipmentTypeService.count(eb.getBrand(),eb.getModel());
            if(num>0){
                //已存在
                info = "该型号品牌已存在";
            }else{
                info = "修改成功";
                logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
                equipmentTypeService.updateBrandAndModel(eb.getBrand(),eb.getModel(),eb.getId());
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
        Equipment equipment = new Equipment();
        equipment.setEqTypeId(id);
        List<Equipment> listE = equipmentService.findAll(equipment);
        if (listE.size() != 0) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！品牌和型号已被使用！");
        }else {
            logService.save(new Log(Log.DELETE_ACTION, "删除了 ："+equipmentTypeService.findById(id).getBrand()+equipmentTypeService.findById(id).getModel()));
            equipmentTypeService.delete(id);
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
              List<EquipmentType>   listE=equipmentTypeService.findmodel(id);
        if (listE.size() != 0) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！请先删除型号！！");
        }else {
            logService.save(new Log(Log.DELETE_ACTION, "删除了 ："+equipmentTypeService.findById(id).getBrand()+equipmentTypeService.findById(id).getModel()));
            equipmentTypeService.delete(id);
            return new ResultInfo(StatusCode.SUCCESS, "删除成功");
        }


    }






}
