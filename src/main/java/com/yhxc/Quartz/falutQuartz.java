package com.yhxc.Quartz;

import com.yhxc.controller.information.commandController;
import com.yhxc.entity.fault.FaultSet;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.fault.FaultSetService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.SpringContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**硬件上传的故障
 * @Author: 李文光
 * @Date: 2019/4/12 13:04
 */
public class falutQuartz implements Job {
    private static final Logger logger = LoggerFactory.getLogger(falutQuartz.class);

    public falutQuartz() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        //doSomething
        FaultSetService Service= SpringContextHolder.getBean(FaultSetService.class);
        Service.saveFalutReport(DateUtil.getDay());
     }



}
