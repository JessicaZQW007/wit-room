package com.yhxc.controller.analyze;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.service.analyze.RunService;
import com.yhxc.service.count.DayRunTimeCountService;
import com.yhxc.service.count.MonthRunTimeCountService;
import com.yhxc.service.count.YearRunTimeCountService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**能耗统计 Controller
 * @Author:李文光
 */
@RequestMapping("/run")
@Controller
public class RunController {

    @Resource
    private RunService runService;
    @Resource
    private DayRunTimeCountService dayRunTimeCountervice;
    @Resource
    private MonthRunTimeCountService monthRunTimeCountService;
    @Resource
    private YearRunTimeCountService yearRunTimeCountService;//

    /**
     * 统计某天 空调的运行情况
     * @param pname  项目名
     * @param date  时间（2019-01-11）
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDayRunTime")
    public ResultInfo findDayRunTime(String pname,String date) {
        JSONObject datas=dayRunTimeCountervice.findAirRunTime(pname,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    /**
     * 统计某月 空调的运行时间
     * @param pname  项目名
     * @param date  时间（2019-01）
     * @return
     */
    @ResponseBody
    @RequestMapping("/findMonthAirRunTime")
    public ResultInfo findMonthAirRunTime(String pname,String date) {
        JSONObject datas=monthRunTimeCountService.findMonthAirRunTime(pname,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }
    /**
     * 统计某年 空调的运行时间
     * @param pname  项目名
     * @param date  时间（2019）
     * @return
     */
    @ResponseBody
    @RequestMapping("/findYearAirRunTime")
    public ResultInfo findYearAirRunTime(String pname,String date) {
        JSONObject datas=yearRunTimeCountService.findYearAirRunTime(pname,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }
    /**
     * 统计某年 空调的运行时间
     * @param date  时间（2019）
     * @return
     */
    @ResponseBody
    @RequestMapping("/countDayRunTime2")
    public ResultInfo countDayRunTime1(String date) {
        JSONArray datas=dayRunTimeCountervice.countDayRunTime2(date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }




}
