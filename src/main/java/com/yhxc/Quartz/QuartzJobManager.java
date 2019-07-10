package com.yhxc.Quartz;

import com.yhxc.entity.information.quartz;
import com.yhxc.service.information.quartzService;
import com.yhxc.utils.SpringContextHolder;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 张权威
 * @Date: 2019/4/11 9:35
 */
@Component
public class QuartzJobManager {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJobManager.class);

    private static QuartzJobManager jobUtil;

    @Autowired
    private Scheduler scheduler;






    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String date = format.format(new Date());


    public QuartzJobManager() {
        logger.info("init jobUtil");
        jobUtil = this;
    }

    public static QuartzJobManager getInstance() {
        logger.info("return  JobCreateUtil");
        return QuartzJobManager.jobUtil;
    }

    /**
     * 创建job
     *
     * @param clazz          任务类
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     */
    public void addJob(Class clazz, String jobName, String jobGroupName, String cronExpression,String remarks) {
        addJob(clazz, jobName, jobGroupName, cronExpression, remarks);

    }


    /**
     * 创建job，可传参
     *
     * @param clazz          任务类
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     * @param argMap         map形式参数
     */
    public void addJob(Class clazz, String jobName, String jobGroupName, String cronExpression, Map<String, Object> argMap,String remarks) {
        try {
            // 启动调度器
            scheduler.start();
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(((Job) clazz.newInstance()).getClass()).withIdentity(jobName, jobGroupName).build();
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName).withSchedule(scheduleBuilder).build();
            //获得JobDataMap，写入数据
            if (argMap != null) {
                trigger.getJobDataMap().putAll(argMap);
            }
            scheduler.scheduleJob(jobDetail, trigger);
            String jobKey=jobGroupName+"."+jobName;
            quartz qq=new quartz();
            qq.setJobKey(jobKey);
            qq.setCorn(cronExpression);
            qq.setCreate_date(date);
            qq.setRemarks(remarks);
            qq.setJobGroup(jobGroupName);
            qq.setTemp(trigger.getJobDataMap().getString("temp"));
            qq.setRunModel(trigger.getJobDataMap().getString("runModel"));
            qq.setRunDate(trigger.getJobDataMap().getString("runDate"));
            qq.setNum(trigger.getJobDataMap().getString("num"));
            quartzService Service= SpringContextHolder.getBean(quartzService.class);
            Service.save(qq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAll();
    }

    /**
     * 暂停job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        getAll();
    }

    /**
     * 恢复job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        getAll();
    }


    /**
     * job 更新,只更新频率
     *
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     */
    public void updateJob(String jobName, String jobGroupName, String cronExpression,String temp,String runModel,String remarks) {
        updateJob(jobName, jobGroupName, cronExpression, null);
        quartzService Service= SpringContextHolder.getBean(quartzService.class);
        String jobKey=jobGroupName+"."+jobName;
        if(temp!=null){
            Service.updateTemp(jobKey,temp,remarks,date);
        }
        if(runModel!=null){
            Service.updateRunModel(jobKey,runModel,remarks,date);
        }
        if(cronExpression!=null){
            Service.updateCorn(jobKey,cronExpression,remarks,date);
        }

    }


    /**
     * job 更新,更新频率和参数
     *
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     * @param argMap         参数
     */
    public void updateJob(String jobName, String jobGroupName, String cronExpression, Map<String, Object> argMap) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //修改map
            if (argMap != null) {
                trigger.getJobDataMap().putAll(argMap);
            }
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * job 更新,只更新更新参数
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     * @param argMap       参数
     */
    public void updateJob(String jobName, String jobGroupName, Map<String, Object> argMap) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //修改map
            trigger.getJobDataMap().putAll(argMap);
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * job 删除
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     */
    public void deleteJob(String jobName, String jobGroupName,Integer id) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            quartzService Service= SpringContextHolder.getBean(quartzService.class);
            Service.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 启动所有定时任务
     */
    public void startAllJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getAll();
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownAllJobs() {
        try {
            if (!scheduler.isInStandbyMode()) {
                scheduler.standby();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getAll();
    }

    /**
     * 获取所有任务列表
     *
     * @return
     */
    public List<quartz> getAllJob() {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<Map<String, Object>> jobList = new ArrayList<>();
        quartzService Service= SpringContextHolder.getBean(quartzService.class);
        List<quartz> list=Service.findAll();
        Set<JobKey> jobKeys = null;
        try {
            jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> job = new HashMap<>();
                    job.put("jobName", jobKey.getName());
                    job.put("jobGroupName", jobKey.getGroup());
                    job.put("trigger", trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.put("jobStatus", triggerState.name());
                    System.out.println(jobKey.toString());
                    Service.updateCornStatus(jobKey.toString(),triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.put("cronExpression", cronExpression);
                    }
                    jobList.add(job);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void getAll() {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<Map<String, Object>> jobList = new ArrayList<>();
        quartzService Service= SpringContextHolder.getBean(quartzService.class);
        List<quartz> list=Service.findAll();
        Set<JobKey> jobKeys = null;
        try {
            jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> job = new HashMap<>();
                    job.put("jobName", jobKey.getName());
                    job.put("jobGroupName", jobKey.getGroup());
                    job.put("trigger", trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.put("jobStatus", triggerState.name());
                    System.out.println(jobKey.toString());
                    Service.updateCornStatus(jobKey.toString(),triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.put("cronExpression", cronExpression);
                    }
                    jobList.add(job);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

