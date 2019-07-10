package com.yhxc.Quartz;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 张权威
 * @Date: 2019/4/26 14:58
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
   /* //3.添加定时任务
    @Scheduled(cron = "0 0/1 * * * ? ")*/
    private void configureTasks() throws IllegalStateException{
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String currSun = dateFm.format(date);
        System.out.println(currSun);
        System.out.println("执行定时任务》》》" + new Date());
        String filePath="C:\\数据库文件\\";
        String dbName="wiit-room";//备份的数据库名
        String username="root";//用户名
        String password="123456";//密码
        File uploadDir = new File(filePath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        String cmd =  "mysqldump -u"+ username +"  -p"+password +" "+ dbName + " -r "
                + filePath + "/" + dbName+currSun+ ".sql";
        try {
            Process process = Runtime.getRuntime().exec(cmd);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("执行失败！");
        }
        System.out.println("执行成功！");
    }

}