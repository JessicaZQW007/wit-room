package com.yhxc.controller.information;

import com.yhxc.Quartz.*;
import com.yhxc.entity.information.quartz;
import com.yhxc.service.information.quartzService;
import com.yhxc.utils.CronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 张权威
 * @Date: 2019/4/11 9:37
 */
@Controller
@RequestMapping("/job")
public class JobController {


    @Autowired
    QuartzJobManager quartzJobManager;

    @Autowired
    private quartzService quartzservice;

    @RequestMapping("/add")
    @ResponseBody
    public String add(String name,String jobGroup,String createdate,String remarks,String num,String runModel,String temp,String runDate)throws ParseException {
        Map<String, Object> map = new HashMap<>();
        CronUtil cronutil= new CronUtil();
        String cron="";
        if(createdate==null){
            cron="0/10 * * * * ? *";
        }else {
            cron=cronutil.getCron("day",createdate);
        }

        map.put("num", num);
        map.put("runModel", runModel);
        map.put("runDate",runDate);
        map.put("temp",temp);
       if("开机".equals(runModel)){
           quartzJobManager.addJob(onQuartz.class, name, jobGroup, cron, map,remarks);
       }else if("关机".equals(runModel)){
           quartzJobManager.addJob(offQuartz.class, name, jobGroup, cron, map,remarks);
       }
        return "已新增定时任务 任务名："+name+"任务组："+jobGroup;
    }

    @RequestMapping("/del")
    @ResponseBody
    public String del(String name,String groupName,Integer id) {
        quartzJobManager.deleteJob(name, groupName,id);
        return "已删除定时任务 任务名："+name+"任务组："+groupName;
    }
    @RequestMapping("/list")
    @ResponseBody
    public List<quartz> getAllJob() {
        List<quartz> list=quartzJobManager.getAllJob();
        return list;
    }
    @RequestMapping("/findByjobGroup")
    @ResponseBody
    public List<quartz> findByjobGroup(String jobGroup) {
        List<quartz> list=quartzservice.findByjobGroup(jobGroup);
        return list;
    }
    @RequestMapping("/update")
    @ResponseBody
    public void update(String name,String groupName,String cron,String temp,String runModel,String remarks) {
        quartzJobManager.updateJob(name, groupName, cron,temp,runModel,remarks);
    }
    @RequestMapping("/startAllJobs")
    @ResponseBody
    public String startAllJobs() {
        quartzJobManager.startAllJobs();
        return "已开启所有定时任务";
    }
    @RequestMapping("/shutdownAllJobs")
    @ResponseBody
    public String shutdownAllJobs() {
        quartzJobManager.shutdownAllJobs();
        return "已停止所有定时任务";
    }
    @RequestMapping("/pauseJob")
    @ResponseBody
    public String pauseJob(String name,String groupName) {
        quartzJobManager.pauseJob(name, groupName);
        return "已暂停定时任务 任务名："+name+"任务组："+groupName;
    }
    @RequestMapping("/resumeJob")
    @ResponseBody
    public String resumeJob(String name,String groupName) {
        quartzJobManager.resumeJob(name, groupName);
        return "已新恢复时任务 任务名："+name+"任务组："+groupName;
    }


    @RequestMapping("/addfalut")
    @ResponseBody
    public String addfalut( String  cron, String name,String jobGroup,String remarks,String num,String runModel,String temp,String runDate)throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("num", num);
        map.put("runModel", runModel);
        map.put("runDate",runDate);
        map.put("temp",temp);
            quartzJobManager.addJob(falutQuartz.class, name, jobGroup, cron, map,remarks);
        return "已新增定时任务 任务名："+name+"任务组："+jobGroup;
    }



    @RequestMapping("/addfalut2")
    @ResponseBody
    public String addfalut2( String cron, String name,String jobGroup,String remarks,String num,String runModel,String temp,String runDate)throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("num", num);
        map.put("runModel", runModel);
        map.put("runDate",runDate);
        map.put("temp",temp);
        quartzJobManager.addJob(falut2Quartz.class, name, jobGroup, cron, map,remarks);
        return "已新增定时任务 任务名："+name+"任务组："+jobGroup;
    }






}

