package com.yhxc.controller.project;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.company.Company;
import com.yhxc.entity.project.Air;
import com.yhxc.entity.project.AirBrand;
import com.yhxc.entity.project.Project;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.project.AirBrandService;
import com.yhxc.service.project.AirService;
import com.yhxc.service.project.ProjectService;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.UserProjectService;
import com.yhxc.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**项目管理
 * @Author: 李文光
 * @Date: 2018/11/114:39
 */
@RequestMapping("/project")
@Controller
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Resource
    private LogService logService;
    @Resource
    private AirService airService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private AirBrandService airBrandService;
    @Resource
    private UserProjectService userProjectService;

    /**
     * 新增项目
     * @param projects
     * @param airs
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResultInfo save(String projects, String airs,MultipartFile uploadFile) {
        JSONObject json = JSONObject.fromObject(projects);
        Project project=new Project();
        if (uploadFile != null && uploadFile.getSize() > 10) {
            project.setImg(UploadUtil.uploadFile(uploadFile, "project"));
        }
        String projectId=UuidUtil.get32UUID();
        project.setId(projectId);
        project.setPname((String) json.get("pname"));//项目名称
        project.setAddress((String) json.get("address"));//项目地址
        project.setPuser((String) json.get("puser"));//项目负责人
        project.setPuserPhone((String) json.get("puserPhone"));//联系方式
        project.setStatus((String) json.get("status"));//项目状态（启用停用）
        project.setRoomArea((String) json.get("roomArea"));//机房面积
        project.setRemarks((String) json.get("remarks"));//项目简介
        project.setType((String) json.get("type"));//项目类型（分体空调，精密空调，专用空调）
        project.setEquipmentNum(Integer.parseInt((String) json.get("equipmentNum")));//空调数量
        project.setCreatetime(DateUtil.getTime());//创建日期
        project.setElectricityPrice(Double.parseDouble((String) json.get("electricityPrice")));//每度电的单价
        project.setEqId((String) json.get("eqId"));//设备id

        project.setTransrate((json.getInt("transrate")));//互感器倍率
        project.setVoltage((json.getInt("voltage")));//设备电压
        project.setUnitType((String) json.get("unitType"));//机构类别
        project.setUnitId((String) json.get("unitId"));//机构ID

    String  location=AddressLngLatExchange.getLngLatFromOneAddr((String) json.get("address"));//项目地址转化成经纬度
          if(StringUtil.isNotEmpty(location)){
            project.setLocation(location);
          }

        //如果添加的时候的绑定了设备（设备ID不为空） 修改设备表里的互感器倍率和电压
        String eqid=(String) json.get("eqId");
        Integer transrate=(Integer) json.get("transrate");
        Integer voltage= json.getInt("voltage");
          if(StringUtil.isNotEmpty(eqid)){
                equipmentService.updateTransrate(transrate,voltage,eqid);
          }



        JSONArray jsonArray = JSONArray.fromObject(airs);
        for(int i=0;i<jsonArray.size();i++){
            Air air=new Air();
            JSONObject job = jsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
            air.setId(UuidUtil.get32UUID());
            air.setCreatetime(DateUtil.getTime());
            air.setPower((String) job.get("power"));//空调功率
            air.setAirName(Integer.valueOf((String) job.get("airName")));//空调名称

            air.setCurrent((String) job.get("current"));//额定电流
            air.setProjectId(projectId);//项目ID
            air.setAirType((String) job.get("airType"));//空调类型
            String brand=(String) job.get("brand");
            String model=(String) job.get("model");
            String brandId= airBrandService.findBrandId(brand,model);
            air.setBrandId(brandId);//品牌id
            airService.save(air);

        }
        projectService.save(project);
        logService.save(new Log(Log.ADD_ACTION, "新增 ："+(String) json.get("pname")));

        return new ResultInfo(StatusCode.SUCCESS, "新增成功！");

    }


    /**
     * 修改项目
     * @param projects
     * @param airs
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultInfo update(String projects, String airs,MultipartFile uploadFile) {
        JSONObject json = JSONObject.fromObject(projects);
        Project project=new Project();

        String projectId=(String) json.get("id");
        project.setId(projectId);
        //处理图片
        if (uploadFile != null && uploadFile.getSize() > 10) {
            FileUtil.deleteDir(project.getImg(), Const.fileRoot + "project");
            project.setImg(UploadUtil.uploadFile(uploadFile, "project"));
        } else {
            String img  =projectService.findById(projectId).getImg();
            project.setImg(img);
        }
        project.setEqId((String) json.get("eqId"));//设备id
        project.setPname((String) json.get("pname"));//项目名称
        project.setAddress((String) json.get("address"));//项目地址
        project.setPuser((String) json.get("puser"));//项目负责人
        project.setPuserPhone((String) json.get("puserPhone"));//联系方式
        project.setStatus((String) json.get("status"));//项目状态（1启用0停用）
        project.setRoomArea((String) json.get("roomArea"));//机房面积
        project.setRemarks((String) json.get("remarks"));//项目简介
        project.setType((String) json.get("type"));//项目类型（分体空调，精密空调，专用空调）
        project.setEquipmentNum(Integer.parseInt((String) json.get("equipmentNum")));//空调数量
        project.setCreatetime((String) json.get("createtime"));//创建日期

        project.setTransrate(json.getInt("transrate"));//互感器倍率
        project.setVoltage(json.getInt("voltage"));//设备电压
        project.setUnitType((String) json.get("unitType"));//机构类别
        project.setUnitId((String) json.get("UnitId"));//机构ID

        project.setElectricityPrice(Double.parseDouble((String) json.get("electricityPrice")));//每度电的单价
        String  location=AddressLngLatExchange.getLngLatFromOneAddr((String) json.get("address"));//项目地址转化成经纬度
        if(StringUtil.isNotEmpty(location)){
            project.setLocation(location);
        }


        //绑定设备 修改设备表里的互感器倍率和电压
        String eqid=(String) json.get("eqId");
        int transrate=json.getInt("transrate");
        int voltage=json.getInt("voltage");
        if(StringUtil.isNotEmpty(eqid)){
            equipmentService.updateTransrate(transrate,voltage,eqid);
        }


        JSONArray jsonArray = JSONArray.fromObject(airs);
        for(int i=0;i<jsonArray.size();i++){
            Air air=new Air();
            JSONObject job = jsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象

            if(job.has("id")) {
                air.setId((String) job.get("id"));

                air.setCreatetime((String) job.get("createtime"));
                air.setAirName(Integer.valueOf((String) job.get("airName")));//空调名称
                air.setPower((String) job.get("power"));//空调功率
                air.setCurrent((String) job.get("current"));//额定电流
                air.setProjectId(projectId);//项目ID
                air.setAirType((String) job.get("airType"));//空调类型
                String brand = (String) job.get("brand");
                String model = (String) job.get("model");
                String brandId = airBrandService.findBrandId(brand, model);
                air.setBrandId(brandId);//品牌id
                airService.save(air);
            }else {
                air.setId(UuidUtil.get32UUID());
                air.setCreatetime(DateUtil.getTime());
                air.setPower((String) job.get("power"));//空调功率
                air.setAirName(Integer.valueOf((String) job.get("airName")));//空调名称
                air.setCurrent((String) job.get("current"));//额定电流
                air.setProjectId(projectId);//项目ID
                air.setAirType((String) job.get("airType"));//空调类型
                String brand = (String) job.get("brand");
                String model = (String) job.get("model");
                String brandId = airBrandService.findBrandId(brand, model);
                air.setBrandId(brandId);//品牌id
                airService.save(air);
            }
        }
        projectService.save(project);
        logService.save(new Log(Log.UPDATE_ACTION, "修改 ："+(String) json.get("pname")));

        return new ResultInfo(StatusCode.SUCCESS, "修改成功！");

    }




    /**
     * 根据id查询项目名称
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByid")
    public ResultInfo findByid(String id) {
        Project datas=projectService.findById(id);

        JSONArray datasAir=airService.findById(id);

        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas,datasAir);
    }



    /**
     * 根据项目projectId查询空调信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByprojectId")
    public ResultInfo findByprojectId(String projectId) {
        JSONArray datas=airService.findById(projectId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }





    /**
     * 项目地址树形图
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/addressTree")
    public ResultInfo addressTree() {
        JSONArray datas=projectService.addressTree();
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }



    /**
     * 项目地址树形图（省 市 区）
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/addressTreeOne")
    public ResultInfo addressTreeOne() {
        JSONArray datas=projectService.addressTreeOne();
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }




    /**
     * 分页查询所有项目
     * @param address 地址
     * @param pname  项目名
     * @param allDate 创建时间
     * @param pageNum  当前页
     * @param pageSize 当前数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public ResultInfo pageList(String projectType,String address, String pname, String allDate, int pageNum, int pageSize ) {
        JSONObject datas=projectService.pageList(projectType,address,pname,allDate,pageNum,pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }


    /**
     * 根据id删除项目
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResultInfo delete(String id) {
        Project project = projectService.findById(id);
        String  count= equipmentService.eqcount(id);
        if(StringUtil.isNotEmpty(count)){
            return new ResultInfo(StatusCode.FAIL, "该项目存在设备！不能删除");
        }else
            {
              airService.deleteAir(id);
            logService.save(new Log(Log.DELETE_ACTION, "删除了 ："+project.getPname()));
            FileUtil.deleteDir(project.getImg(), Const.fileRoot + "project");
                userProjectService.deleteByprojectId(id);//删除有关用户项目关系

            projectService.delete(id);
            return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
        }

    }


    /**
     * 根据id删除空调
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteAir")
    public ResultInfo deleteAir(String id) {
            airService.delete(id);
            return new ResultInfo(StatusCode.SUCCESS, "删除成功！");

    }


    /**
     * 获取项目列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/findAll")
    public ResultInfo findAll() {
        return new ResultInfo(StatusCode.SUCCESS, "success", projectService.findAll());
    }




    /**校验输入地址是否正确
     *
     */
    @ResponseBody
    @RequestMapping("/checkAddress")
    public ResultInfo checkAddress(String address) {
          int datas=projectService.checkAddress(address);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);


    }


    /**解绑设备
     *
     */
    @ResponseBody
    @RequestMapping("/untieEq")
    public ResultInfo untieEq(String id) {
       projectService.untieEq(id);
        return new ResultInfo(StatusCode.SUCCESS, "解绑成功！");

    }
    /**查询项目名存不存在
     *
     */
    @ResponseBody
    @RequestMapping("/findpame")
    public ResultInfo findpame(String pname) {
        int num=0;

        num=projectService.findpame(pname) ;
        if(num>0){
            num=projectService.findpame(pname);
        }else {
            num=0;
        }
        return new ResultInfo(StatusCode.SUCCESS, "查询成功",num);


    }



}
