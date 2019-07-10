package com.yhxc.controller.app;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.entity.project.Air;
import com.yhxc.entity.project.Project;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.UserProject;
import com.yhxc.service.analyze.EnergyService;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.fault.FaultReportService;
import com.yhxc.service.project.AirBrandService;
import com.yhxc.service.project.AirService;
import com.yhxc.service.project.ProjectService;
import com.yhxc.service.send.ReceiveDataService;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.UserProjectService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**app首页 Controller
 * @Author:李文光
 */

@RequestMapping("/app")
@Controller
public class AppIndexController {

    @Resource
    private ReceiveDataService receiveDataService;
    @Resource
    private FaultReportService faultReportService;

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
    @Resource
    private UserService userService;
    @Resource
    private EnergyService energyService;


    /**app新增项目
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
        String projectId= UuidUtil.get32UUID();
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
      //绑定设备

      String uuid= (String) json.get("uuid");
        Equipment equipment=equipmentService.findByUUID(uuid);
        if (equipment==null){
            return new ResultInfo(StatusCode.FAIL, "该设备不存在！");
        }else {
            String eqId=equipment.getId();
            Project projectdatas=projectService.findByEqId(eqId);
            if(projectdatas!=null){
                return new ResultInfo(StatusCode.FAIL, "该设备已经绑定项目！不可以重复绑定！绑定项目为:"+projectdatas.getPname());
            }else{
                project.setEqId(eqId);//设备id
            }
        }



        String  location= AddressLngLatExchange.getLngLatFromOneAddr((String) json.get("address"));//项目地址转化成经纬度
        if(StringUtil.isNotEmpty(location)){
            project.setLocation(location);
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

  /*     UserProject userProject = new UserProject();
        userProject.setUser(userService.findById(Jurisdiction.getCurrentUser().getId()));
        userProject.setProject(projectService.findById(projectId));
        userProjectService.save(userProject);*/

        projectService.save(project);
        logService.save(new Log(Log.ADD_ACTION, "新增 ："+(String) json.get("pname")));

        return new ResultInfo(StatusCode.SUCCESS, "新增项目成功！");

    }
    /**app修改项目
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

        String uuid= (String) json.get("uuid");
        Equipment equipment=equipmentService.findByUUID(uuid);
        if (equipment==null){
            return new ResultInfo(StatusCode.FAIL, "该设备不存在！");
        }else {
            String eqId=equipment.getId();
            Project projectdatas=projectService.findByEqId(eqId);
            if(projectdatas.getEqId().equals("eqId")){//判断修改时有没有重新扫描
                project.setEqId(eqId);//设备id
            }else{
                Project proje=projectService.findByEqId(eqId);//判断重新扫描的设备有没有绑定过项目
                if(proje!=null){
                    return new ResultInfo(StatusCode.FAIL, "该设备已经绑定项目！不可以重复绑定！绑定项目为:"+projectdatas.getPname());
                }else{
                    project.setEqId(eqId);//设备id
                }
            }
        }

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
        project.setElectricityPrice(Double.parseDouble((String) json.get("electricityPrice")));//每度电的单价
        String  location=AddressLngLatExchange.getLngLatFromOneAddr((String) json.get("address"));//项目地址转化成经纬度
        if(StringUtil.isNotEmpty(location)){
            project.setLocation(location);
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
        UserProject userProject = new UserProject();
        userProject.setUser(userService.findById(Jurisdiction.getCurrentUser().getId()));
        userProject.setProject(projectService.findById(projectId));
        userProjectService.save(userProject);
        projectService.save(project);
        logService.save(new Log(Log.UPDATE_ACTION, "修改 ："+(String) json.get("pname")));
        return new ResultInfo(StatusCode.SUCCESS, "修改项目成功！！");

    }


    /**
     *查询app登录用户负责的项目
     *
     */
    @ResponseBody
    @RequestMapping("/findprojectMessages")
    public ResultInfo findprojectMessages(int userId) {
        JSONArray datas=projectService.findprojectMessages(userId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    /**
     *查询app空调信息(airNum)空调数量
     *
     */
    @ResponseBody
    @RequestMapping("/findAirApp")
    public ResultInfo findAirApp(String uuid,String project_id,int airNum) {
        JSONArray datas1=null;
        JSONObject jsonObject=new JSONObject();
            for(int i=1;i<=airNum;i++){
                datas1=receiveDataService.findAirApp(uuid,project_id,i);
                jsonObject.put("datas"+i,datas1);
           }
        return new ResultInfo(StatusCode.SUCCESS, "成功！",jsonObject);
    }


 /**
     *查询app空调信息输入第几台空调
     **
     */
    @ResponseBody
    @RequestMapping("/findAirApp1")
    public ResultInfo findAirApp1(String uuid,int airNum,String project_id) {
        JSONArray  datas=receiveDataService.findAirApp(uuid,project_id,airNum);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }


    /**查询项目的排名（APP）
     *date时间(必选)
     * address 地址（可以为空）
     **userId 用户的id
     */
    @ResponseBody
    @RequestMapping("/findProjectRankAPP")
    public ResultInfo findProjectRankAPP(String date,int userId,String address) {
        JSONArray datas=energyService.findProjectRankAPP(date,userId,address);
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

    /**
     * 保存单个项目终端用户关系(app) 扫码绑定
     *
     * @param uuid
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveProjectSetOne")
    @ResponseBody
    public ResultInfo saveProjectSetOne(String uuid, Integer userId) throws Exception {
        Equipment equipment=equipmentService.findByUUID(uuid);
        Project projectdatas=null;
        if (equipment==null){
            return new ResultInfo(StatusCode.FAIL, "该设备不存在！");
        }else {

            String eqId=equipment.getId();
            projectdatas=projectService.findByEqId(eqId);
            projectService.findById(projectdatas.getId());
            if(projectdatas!=null){
                return new ResultInfo(StatusCode.FAIL, "该项目你已经绑定过！请勿重复绑定！");
            }else {
            UserProject userProject = new UserProject();
            userProject.setUser(userService.findById(userId));
            userProject.setProject(projectService.findById(projectdatas.getId()));
            userProjectService.save(userProject);
        }
        }
        logService.save(new Log(Log.ADD_ACTION, Jurisdiction.getUserName()+"app端绑定项目:"+projectdatas.getPname()));  // 写入日志
        return new ResultInfo(StatusCode.SUCCESS, "绑定'"+projectdatas.getPname()+"'项目成功！");
    }

    /**
     * 解绑项目(app)
     *
     * @param uuid
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteone")
    @ResponseBody
    public ResultInfo deleteone(String uuid, Integer userId) throws Exception {
        Equipment equipment = equipmentService.findByUUID(uuid);
        Project projectdatas = null;
        if (equipment == null) {
            return new ResultInfo(StatusCode.FAIL, "该设备不存在！");
        } else {
            String eqId = equipment.getId();
            projectdatas = projectService.findByEqId(eqId);
            logService.save(new Log(Log.DELETE_ACTION, Jurisdiction.getUserName() + "app端解绑项目:" + projectdatas.getPname()));  // 写入日志
            userProjectService.deleteByuserIdProjectId(userId, projectdatas.getId());
            return new ResultInfo(StatusCode.SUCCESS, "解绑项目成功！");

        }

    }

    /**
     * app统计某月每天的用电量
     *pname 项目名
     * @param date  时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDayListApp")
    public ResultInfo findDayListApp(String pname,String date) {
            JSONArray datas = energyService.findDayListApp(pname,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }




}
