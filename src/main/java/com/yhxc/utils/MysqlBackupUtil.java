package com.yhxc.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 备份数据库mysql
 */

public class MysqlBackupUtil {
    private static Log log = LogFactory.getLog(DbFH.class);
    public static boolean  backupData(String host,String dbName,String account,String pass,String savaPath){
        Long starttime=System.currentTimeMillis();
        Runtime rt=Runtime.getRuntime();
        Process process;
        try {
            String command="mysqldump -h "+host+" -u "+account+" -p"+pass+" "+dbName+" --result-file "+savaPath+DateUtil.getTime()+".sql";
            log.info("mysqldump running this comman-->"+command);
            process = rt.exec(command);
            if(process.waitFor()==0){
                log.info("mysqldump backup successfully");
                Long endtime=System.currentTimeMillis();
                Long distance=starttime-endtime;
                log.info("mysqldump备份花"+distance/1000+"秒");
                return true;

            }else{
                log.info("process.waitFor()!=0");
                InputStream is=process.getErrorStream();
                if(is!=null){
                    BufferedReader in=new BufferedReader(new  InputStreamReader(is,"utf-8"));
                    String line;
                    while((line=in.readLine())!=null){
                        log.error("mysqldump错误日志>"+line);
                    }
                }
            }
        }catch (Exception e) {
            log.info("mysqldump backup failed");
            e.printStackTrace();
            return false;
        }
        return false;

    }

}
