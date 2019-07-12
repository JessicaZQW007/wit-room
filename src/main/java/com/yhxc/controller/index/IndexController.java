package com.yhxc.controller.index;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.count.MonthRunTimeCount;
import com.yhxc.entity.system.User;
import com.yhxc.service.count.*;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.fault.FaultSetService;
import com.yhxc.service.project.ProjectService;
import com.yhxc.service.send.ReceiveDataService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**首页 Controller
 * @Author:李文光
 */
@RequestMapping("/index")
@Controller
public class IndexController {

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private ProjectService projectService;
    @Resource
    private ReceiveDataService receiveDataService;
    @Resource
    private MonthCountElectricService monthCountElectricService;

    @Resource
    private DayRunTimeCountService dayRunTimeCountService;

    @Resource
    private MonthRunTimeCountService monthRunTimeCountService;
    @Resource
    private DayAirCountElectricService dayAirCountElectricService;
    @Resource
    private MonthAirCountElectricService monthAirCountElectricService;
    @Resource
    private DayCountElectricService dayCountElectricService;
    @Resource
    private FaultSetService faultSetService;

    /**
     * 查询首页中 ，项目数量 projectNum，设备数量 eqNum，空调数量    airNum online 在线 ， offline离线
     *
     */
    @ResponseBody
    @RequestMapping("/findIndexData")
    public ResultInfo findIndexData() {
        String pId="";
        String unitId="";
        User u = Jurisdiction.getCurrentUser();
        if(u.getUserType()==1){
            //平台用户
            pId=u.getUnitId();

        }else if(u.getUserType()==2){
            //机构用户
            unitId=u.getUnitId();
        }
        JSONObject datas=  equipmentService.findIndexData(pId,unitId);
        JSONObject datas1=  equipmentService.findEqStatus(pId,unitId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas,datas1);
    }


    /**
     * 查询监控页面 总用电量
     *
     *
     */
    @ResponseBody
    @RequestMapping("/findTotalpower")
    public ResultInfo findTotalpower(String uuid) {
       Double datas=  receiveDataService.findTotalpower(uuid);

        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    /**
     *
     * 查询监控页面 某天 电表总用电量
     */
    @ResponseBody
    @RequestMapping("/sumDayCountBiao")
    public ResultInfo sumDayCountBiao(String date,String uuid) {
        Double datas=  monthCountElectricService.sumDayCountBiao(date,uuid);

        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }



    /**
     *  空调的本日运行时间
     *
     * @param date  时间 airNum 第几台空调
     * @return
     */
    @ResponseBody
    @RequestMapping("/runcount")
    public ResultInfo  runcount(String date,String uuid,int airNum)throws Exception {
         Double runNow=0.0;//空调的运行时间
        Double runCoolNow=0.0;//空调的制冷运行时间

        JSONObject jsonObject=new JSONObject();
            JSONArray jsonArray=null;
            JSONArray datas1=dayRunTimeCountService.countAirDayRunTime(date,uuid,airNum);//本日运行时间
            String nowAirSum1;
            for (int i = 0; i < datas1.size(); i++) {
                nowAirSum1=datas1.getJSONObject(i).getString("runDate");
                runNow=Double.valueOf(nowAirSum1);
            }
            jsonArray=  dayRunTimeCountService.countDayRunTimecool(date,uuid,airNum);
            for (int k = 0; k < jsonArray.size(); k++) {
                String runCoolNow1;
                runCoolNow1=jsonArray.getJSONObject(k).getString("runDate");
                runCoolNow=Double.valueOf(runCoolNow1);
            }
            jsonObject.put("runNow",runNow);//本日的运行时间
            jsonObject.put("runCoolNow",runCoolNow);//制冷运行时间

        return new ResultInfo(StatusCode.SUCCESS, "成功！",jsonObject);
    }



    /**
     *  电表本日用电量，本日用电量，
     *
     * @param date  时间
     */
    @ResponseBody
    @RequestMapping("/powerCount")
    public ResultInfo  powerCount(String date,String uuid)throws Exception {

        Double powercount=   monthCountElectricService.sumDayCountBiao(date,uuid);

        return new ResultInfo(StatusCode.SUCCESS, "成功！",powercount);
    }





    /**
     *查询某天的每小时 电表，空调 的用电量
     *   airNum 空调数量
     */
  /*  @ResponseBody
    @RequestMapping("/sumHourCountbiao")
    public ResultInfo sumHourCountbiao(String date,String uuid,int airNum)throws Exception {
        JSONObject jsonObject=new JSONObject();
            if (airNum == 1) {
                JSONArray datas = dayCountElectricService.sumHourCountbiao(date, uuid);
                JSONArray datas1 = dayCountElectricService.findOneHouruuid(date, uuid);
                jsonObject.put("datas", datas);//电表
                jsonObject.put("datas1", datas1);//空调1
            } else if (airNum == 2) {
                JSONArray datas = dayCountElectricService.sumHourCountbiao(date, uuid);
                JSONArray datas2 = dayCountElectricService.findTwoHouruuid(date, uuid);
                jsonObject.put("datas", datas);//电表
                jsonObject.put("datas1", datas2);//空调2

            }

        return new ResultInfo(StatusCode.SUCCESS, "成功！",jsonObject);
    }*/



    /**
     *查询某天电表的用电量
     *   airNum 空调数量
     */
    @ResponseBody
    @RequestMapping("/sumHourbiao")
    public ResultInfo sumHourbiao(String date,String uuid)throws Exception {
        JSONArray datas = dayCountElectricService.sumHourCountbiao1(date, uuid);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }



    /**
     *查询某天电表的三项电流
     *   airNum 空调数量
     */
    @ResponseBody
    @RequestMapping("/findcurrent")
    public ResultInfo findcurrent(String uuid,String date)throws Exception {
        JSONArray datas = receiveDataService.findcurrent(uuid, date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }




    /**
     *查询某天的电表 的用电量（监控界面）
     *   airNum 空调数量
     */
/*
    @ResponseBody
    @RequestMapping("/sumHourCount")
    public ResultInfo sumHourCount(String date,String uuid)throws Exception {
            JSONArray datas = dayCountElectricService.sumHourCount(date, uuid);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }
*/








}
