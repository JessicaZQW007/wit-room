package com.yhxc.controller.analyze;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.User;
import com.yhxc.service.analyze.EnergyService;
import com.yhxc.service.count.DayCountElectricService;
import com.yhxc.service.count.DayRunTimeCountService;
import com.yhxc.service.count.MonthCountElectricService;
import com.yhxc.utils.Jurisdiction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**能耗统计 Controller
 * @Author:李文光
 */
@RequestMapping("/energy")
@Controller
public class EnergyController {

    @Resource
    private EnergyService energyService;
    @Resource
    private DayCountElectricService dayCountElectricService;
    @Resource
    private MonthCountElectricService monthCountElectricService;
    @Resource
    private DayRunTimeCountService dayRunTimeCountService;


    /**
     * 统计某天每个小时的用电量 datas 电表 datas1 空调1 datas2 空调2
     *pname 项目名
     * @param date  时间
     *   airNum 空调数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/findHourList")
    public ResultInfo findHourList(String pname,String date,int airNum) {

        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
            JSONArray datas=  energyService.findHourList(pname,date);
            JSONArray datas1 =energyService.findHourListAir1(pname,date);
            JSONArray datas2 =energyService.findHourListAir2(pname,date);
            jsonObject.put("datas",datas);
            jsonObject.put("datas1",datas1);
            jsonObject.put("datas2",datas2);
        }else if(airNum==1){
            JSONArray datas=  energyService.findHourList(pname,date);
            JSONArray datas1 =energyService.findHourListAir1(pname,date);
            jsonObject.put("datas",datas);
            jsonObject.put("datas1",datas1);
        }
        JSONObject  dataCount= energyService.findDaycount(pname,date);


        return new ResultInfo(StatusCode.SUCCESS, "成功！",jsonObject,dataCount);
    }



    /**
     * 统计某月每天的用电量datas 电表 datas1 空调1 datas2 空调2
     *pname 项目名
     * @param date  时间
     *   airNum 空调数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDayList")
    public ResultInfo findDayList(String pname,String date,int airNum) {
        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
            JSONArray datas = energyService.findDayList(pname,date);
            JSONArray datas1 =energyService.findDayListAir1(pname,date);
            JSONArray datas2 =energyService.findDayListAir2(pname,date);
            jsonObject.put("datas",datas);
            jsonObject.put("datas1",datas1);
            jsonObject.put("datas2",datas2);
        }else if(airNum==1){
            JSONArray datas = energyService.findDayList(pname,date);
            JSONArray datas1 =energyService.findDayListAir1(pname,date);
            jsonObject.put("datas",datas);
            jsonObject.put("datas1",datas1);
        }
        JSONObject  dataCount= energyService.findMonthcount(pname,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",jsonObject,dataCount);
    }





    /**
     * 统计某年每个月的用电量datas 电表 datas1 空调1 datas2 空调2
     *pname 项目名
     * @param date  时间
     *   airNum 空调数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/findMonthList")
    public ResultInfo findMonthList(String pname,String date,int airNum) {
        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
            JSONArray datas=  energyService.findMonthList(pname,date);
            JSONArray datas1 =energyService.findMonthList1(pname,date);
            JSONArray datas2 =energyService.findMonthList2(pname,date);
            jsonObject.put("datas",datas);
            jsonObject.put("datas1",datas1);
            jsonObject.put("datas2",datas2);
        }else if(airNum==1){
            JSONArray datas= energyService.findMonthList(pname,date);
            JSONArray datas1 =energyService.findMonthList1(pname,date);
            jsonObject.put("datas",datas);
            jsonObject.put("datas1",datas1);
        }
        JSONObject  dataCount= energyService.findYearcount(pname,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",jsonObject,dataCount);
    }


    /**同比 类比
     *
     * @param pnames 项目名
     * @param dates  年月
     * @param quarter 季度
     * @param year 年
     * @return
     */
    @ResponseBody
    @RequestMapping("/findtoList")
    public ResultInfo findtoList(String pnames,String dates,String quarter, String year) {
        JSONArray datas=  energyService.findtoList(pnames,dates,quarter,year);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }
    /**
     * 统计某个月全部项目的能耗
     *
     * @param date  时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/findMonthCount")
    public ResultInfo  findMonthCount(String date) {
        User u = Jurisdiction.getCurrentUser();

        String pId="";
        String unitId="";
        if(u.getUserType()==1){
            //平台用户
            pId=u.getUnitId();

        }else if(u.getUserType()==2){
            //机构用户
            unitId=u.getUnitId();
        }



        System.out.println("userType="+u.getUserType());

        JSONArray datas=  energyService.findMonthCount(date,pId,unitId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    /**
     *  查询本月项目能耗前十排名
     *
     * @param date  时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/findProjectRank")
    public ResultInfo  findProjectRank(String date) {
        JSONArray datas=  energyService.findProjectRank(date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    /**
     *  ce1
     *
     * @param date  时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/countDayRunTime1")
    public ResultInfo  countDayRunTime1(String date) {
        JSONArray datas1=dayRunTimeCountService.countDayRunTime1(date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas1);
    }



    /**
     *  ces1
     *
     * @param date  时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/findOne")
    public ResultInfo  findOne(String date)throws Exception {
        JSONArray datas1=dayCountElectricService.findOne(date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas1);
    }




}
