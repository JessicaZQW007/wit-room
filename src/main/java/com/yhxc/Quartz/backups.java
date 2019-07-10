package com.yhxc.Quartz;

import com.yhxc.controller.information.commandController;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.utils.SpringContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: 张权威
 * @Date: 2019/4/25 16:29
 */
public class backups implements Job {
    private static final Logger logger = LoggerFactory.getLogger(backups.class);

    public backups() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行定时任务》》》" + new Date());
        String filePath="G:\\数据库文件\\";
        String dbName="wit-room";//备份的数据库名
        String username="root";//用户名
        String password="123456";//密码
        File uploadDir = new File(filePath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        String cmd =  "mysqldump -u"+ username +"  -p"+password +" "+ dbName + " -r "
                + filePath + "/" + dbName+new java.util.Date().getTime()+ ".sql";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            System.out.println(cmd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
}
