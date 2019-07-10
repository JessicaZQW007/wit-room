package com.yhxc.common;



import com.yhxc.entity.system.Log;
import com.yhxc.entity.temperature.DayTemperature;

import com.yhxc.entity.temperature.MonthTemperature;
import com.yhxc.entity.temperature.YearTemperature;
import com.yhxc.service.system.LogService;
import com.yhxc.service.temperature.DayTemperatureService;

import com.yhxc.service.temperature.MonthTemperatureService;
import com.yhxc.service.temperature.YearTemperatureService;
import com.yhxc.utils.DateUtil;
import net.sf.json.JSONArray;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/***
 *
 * 设置项目全局的定时任务
 *
 * @Component注解的意义        泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。一般公共的方法我会用上这个注解
 *
 * 定时记录设备的湿度和温度，日 月 年
 * @Author: 陈霖炎
 * @Date: 2019/06/17 17:24
 *
 */
@Component
public class TemperatureQuartz {
    @Resource
    private DayTemperatureService dayTemperatureService;

    @Resource
    private MonthTemperatureService monthTemperatureService;


    @Resource
    private YearTemperatureService yearTemperatureService;

    @Resource
    private LogService logService;


    //记录昨天的设备温度湿度数据
    @Scheduled(cron = "0 30 03 * * ?") // 每天凌晨3点30分执行一次
    @Async
    public void dayTemperature() throws Exception {
        String lastDate= DateUtil.getLastDay();
        JSONArray jsonArray1=dayTemperatureService.findHour(lastDate);
        for (int i = 0; i < jsonArray1.size(); i++) {
            DayTemperature dayTemperature=new DayTemperature();
            dayTemperature.setUuid(jsonArray1.getJSONObject(i).getString("uuid"));
            dayTemperature.setSwhumi(jsonArray1.getJSONObject(i).getString("swhumi"));
            dayTemperature.setSwtemp(jsonArray1.getJSONObject(i).getString("swtemp"));
            dayTemperature.setHjtemp(jsonArray1.getJSONObject(i).getString("hjtemp"));
            dayTemperature.setSfktemp1(jsonArray1.getJSONObject(i).getString("sfktemp1"));
            dayTemperature.setSfktemp2(jsonArray1.getJSONObject(i).getString("sfktemp2"));
            dayTemperature.setDate(jsonArray1.getJSONObject(i).getString("date"));
            dayTemperature.setCreatetime(DateUtil.getTime());
            dayTemperatureService.save(dayTemperature);
        }


        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份当天每小时的设备温度湿度数据,成功!"));

    }

    //记录上个月的设备温度湿度数据
    @Scheduled(cron = "0 50 03 1 * ?") // 每个月1号的凌晨3点50分执行一次
    @Async
    public void monthTemperature() throws Exception{
        String lastMonth= DateUtil.getLastMonth();
        JSONArray jsonArray1=monthTemperatureService.findDayAVG(lastMonth);
        for (int i = 0; i < jsonArray1.size(); i++) {
            MonthTemperature monthTemperature=new MonthTemperature();
            monthTemperature.setUuid(jsonArray1.getJSONObject(i).getString("uuid"));
            monthTemperature.setSwhumi(jsonArray1.getJSONObject(i).getString("swhumi"));
            monthTemperature.setSwtemp(jsonArray1.getJSONObject(i).getString("swtemp"));
            monthTemperature.setHjtemp(jsonArray1.getJSONObject(i).getString("hjtemp"));
            monthTemperature.setSfktemp1(jsonArray1.getJSONObject(i).getString("sfktemp1"));
            monthTemperature.setSfktemp2(jsonArray1.getJSONObject(i).getString("sfktemp2"));
            monthTemperature.setDate(jsonArray1.getJSONObject(i).getString("date"));
            monthTemperature.setCreatetime(DateUtil.getTime());
            monthTemperatureService.save(monthTemperature);
        }


        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份当月每天的设备温度湿度平均值,成功!"));
    }



    //记录上一年的设备温度湿度数据
    @Scheduled(cron = "0 10 04 1 * ?") // 每年的1月1号的凌晨4点10分执行一次
    @Async
    public void yearTemperature() throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        String lastYear= year;
        System.out.println("lastYear="+lastYear);
        JSONArray jsonArray1=yearTemperatureService.findMonthAVG(lastYear);
        for (int i = 0; i < jsonArray1.size(); i++) {
            YearTemperature yearTemperature=new YearTemperature();
            yearTemperature.setUuid(jsonArray1.getJSONObject(i).getString("uuid"));
            yearTemperature.setSwhumi(jsonArray1.getJSONObject(i).getString("swhumi"));
            yearTemperature.setSwtemp(jsonArray1.getJSONObject(i).getString("swtemp"));
            yearTemperature.setHjtemp(jsonArray1.getJSONObject(i).getString("hjtemp"));
            yearTemperature.setSfktemp1(jsonArray1.getJSONObject(i).getString("sfktemp1"));
            yearTemperature.setSfktemp2(jsonArray1.getJSONObject(i).getString("sfktemp2"));
            yearTemperature.setDate(jsonArray1.getJSONObject(i).getString("date"));
            yearTemperature.setCreatetime(DateUtil.getTime());
            yearTemperatureService.save(yearTemperature);
        }


        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份当年每月的设备温度湿度平均值,成功!"));
    }



}
