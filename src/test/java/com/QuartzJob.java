package com;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: 赵贺飞
 * @Date: 2018/5/25 9:16
 */
public class QuartzJob implements Job {


    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println("定时任务，在" + new Date() + "执行" );
    }
}
