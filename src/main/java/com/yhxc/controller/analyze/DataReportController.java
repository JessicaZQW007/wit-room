package com.yhxc.controller.analyze;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.service.analyze.DataReportService;
import com.yhxc.service.count.DayCountElectricService;
import com.yhxc.service.count.MonthCountElectricService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**数据报表 Controller
 * @Author:李文光
 */
@RequestMapping("/dataReport")
@Controller
public class DataReportController {

    @Resource
    private DataReportService dataReportService;


    @Resource
    private DayCountElectricService dayCountElectricService;
    @Resource
    private MonthCountElectricService monthCountElectricService;



    /**
     * 数据报表中 日报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    @ResponseBody
    @RequestMapping("/findDayReport")
    public ResultInfo findDayReport(String projectType,String address,String date) {
      JSONArray datas=  dataReportService.findDayReport(projectType,address,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }






    /**
     * 数据报表中 月报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    @ResponseBody
    @RequestMapping("/findMonthReport")
    public ResultInfo findMonthReport(String projectType,String address,String date) {
        JSONArray datas=  dataReportService.findMonthReport(projectType,address,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }


    /**
     * 数据报表 时间段报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    @ResponseBody
    @RequestMapping("/findTimeReport")
    public ResultInfo findTimeReport(String projectType,String address,String allDate) {
        JSONArray datas=  dataReportService.findTimeReport(projectType,address,allDate);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }



    /**
     * 数据报表中 年报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    @ResponseBody
    @RequestMapping("/findYearReport")
    public ResultInfo findYearReport(String projectType,String address,String date) {
        JSONArray datas=  dataReportService.findYearReport(projectType,address,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    /**
     * 数据报表中 季度报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    @ResponseBody
    @RequestMapping("/findQuarterReport")
    public ResultInfo findQuarterReport(String projectType,String address,String date) {
        JSONArray datas=  dataReportService.findQuarterReport(projectType,address,date);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }










}
