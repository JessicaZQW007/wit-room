package com.yhxc.Quartz;

import com.yhxc.controller.information.commandController;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.utils.SpringContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/4/11 10:07
 */
public class onQuartz implements Job {
    private static final Logger logger = LoggerFactory.getLogger(onQuartz.class);

    public onQuartz() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = format.format(new Date());
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        //doSomething
        EquipmentService Service= SpringContextHolder.getBean(EquipmentService.class);
        List<String> list=Service.finduuidByGroup(jobKey.getGroup());
        commandController comm=new commandController();
        for (int i= 0;i<=list.size();i++){
            try {
                comm.doPostTestFour("01",list.get(i),dataMap.getString("runModel"),dataMap.getString("num"),"","","","",0,"","");
                System.out.println("定时任务启动");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
