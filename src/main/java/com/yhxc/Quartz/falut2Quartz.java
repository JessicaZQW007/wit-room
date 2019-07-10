package com.yhxc.Quartz;

import com.yhxc.service.fault.FaultSetService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.SpringContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**d电流判断故障
 * @Author: 李文光
 * @Date: 2019/4/12 13:04
 */
public class falut2Quartz implements Job {
    private static final Logger logger = LoggerFactory.getLogger(falut2Quartz.class);

    public falut2Quartz() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        //doSomething
        FaultSetService Service= SpringContextHolder.getBean(FaultSetService.class);
        Service.saveReport(DateUtil.getDay());

     }



}
