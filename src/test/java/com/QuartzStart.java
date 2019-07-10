package com;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 赵贺飞
 * @Date: 2018/5/25 9:16
 */
public class QuartzStart {

  /*  public static void main(String[] args) throws SchedulerException, ParseException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = new JobDetail("QuartzJob", Scheduler.DEFAULT_GROUP, QuartzJob.class);
        String time = "2018-05-25 09:44:20";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cron = getCron(sdf.parse(time));
        String cronExpression = cron; // 每分钟的30s起，每5s触发任务
        CronTrigger cronTrigger = new CronTrigger("cronTrigger", Scheduler.DEFAULT_GROUP, cronExpression);
        scheduler.scheduleJob(jobDetail, cronTrigger);
        scheduler.start();
    }

    //停止任务，删除任务
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
            scheduler.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
            scheduler.interrupt(jobName, jobGroupName);
            scheduler.deleteJob(jobName, jobGroupName);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/
    /***
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016"
     * "30 * * * * ?" 每半分钟触发任务
     "30 10 * * * ?" 每小时的10分30秒触发任务
     "30 10 1 * * ?" 每天1点10分30秒触发任务
     "30 10 1 20 * ?" 每月20号1点10分30秒触发任务
     "30 10 1 20 10 ? *" 每年10月20号1点10分30秒触发任务
     "30 10 1 20 10 ? 2011" 2011年10月20号1点10分30秒触发任务
     "30 10 1 ? 10 * 2011" 2011年10月每天1点10分30秒触发任务
     "30 10 1 ? 10 SUN 2011" 2011年10月每周日1点10分30秒触发任务
     "15,30,45 * * * * ?" 每15秒，30秒，45秒时触发任务
     "15-45 * * * * ?" 15到45秒内，每秒都触发任务
     "15/5 * * * * ?" 每分钟的每15秒开始触发，每隔5秒触发一次
     "15-30/5 * * * * ?" 每分钟的15秒到30秒之间开始触发，每隔5秒触发一次
     "0 0/3 * * * ?" 每小时的第0分0秒开始，每三分钟触发一次
     "0 15 10 ? * MON-FRI" 星期一到星期五的10点15分0秒触发任务
     "0 15 10 L * ?" 每个月最后一天的10点15分0秒触发任务
     "0 15 10 LW * ?" 每个月最后一个工作日的10点15分0秒触发任务
     "0 15 10 ? * 5L" 每个月最后一个星期四的10点15分0秒触发任务
     "0 15 10 ? * 5#3" 每个月第三周的星期四的10点15分0秒触发任务
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? *";
        return formatDateByPattern(date, dateFormat);
    }


    /***
     *  功能描述：日期转换cron表达式
     * @param date
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }


}
