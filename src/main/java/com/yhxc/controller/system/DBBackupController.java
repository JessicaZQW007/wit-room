package com.yhxc.controller.system;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.Log;
import com.yhxc.utils.DbFH;
import com.yhxc.utils.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Author: 赵贺飞
 * @Date: 2018/3/26 11:41
 * 数据库备份、还原
 */
@Controller
@RequestMapping("/db")
public class DBBackupController {


    /**
     * 列出数据库所有表
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listAllTable")
    @ResponseBody
    public ResultInfo listAllTable() throws Exception {
        Object[] arrOb = DbFH.getTables();
        List<String> tblist = (List<String>)arrOb[1];
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tblist", tblist);      //数据库表列表
        map.put("dbtype", arrOb[2]);
        map.put("databaseName", arrOb[0]);
        return new ResultInfo(StatusCode.SUCCESS, "获取数据表成功！", map);
    }




    /**
     * 备份单表
     * @param tableName
     * @return
     */
    @ResponseBody
    @RequestMapping("/backupTable")
    public ResultInfo backupTable(String tableName){
         String info = "";
        try {
            String kackupPath = DbFH.getDbFH().backup(tableName).toString();	//调用数据库备份
            if(Tools.notEmpty(kackupPath) && !"errer".equals(kackupPath)){
                info = "备份成功！目录：C:/mysql_backup/";
            } else {
                info = "备份失败！";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            info = "备份失败！";
        } catch (ExecutionException e) {
            e.printStackTrace();
            info = "备份失败！";
        }
        return new ResultInfo(StatusCode.SUCCESS, info);
    }

    /**备份全库
     * @param
     * @throws Exception
     */
    @RequestMapping(value="/backupAll")
    @ResponseBody
    public ResultInfo backupAll(){
        String info = "";
        try {
            String kackupPath = DbFH.getDbFH().backup("").toString();	//调用数据库备份
            if(Tools.notEmpty(kackupPath) && !"errer".equals(kackupPath)){
                info = "备份成功！目录：C:/mysql_backup/";
            } else {
                info = "备份失败！";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            info = "备份失败！";
        } catch (ExecutionException e) {
            e.printStackTrace();
            info = "备份失败！";
        }
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 数据库还原操作
     * @param tableName  页面ajax传过来的表名或数据库名
     * @param sqlPath    页面ajax传过来的备份文件完整路径
     * @return
     */
    @RequestMapping(value="/dbRecover")
    @ResponseBody
    public ResultInfo dbRecover(String tableName, String sqlPath){
        String info = "";
        try {
            String returnStr = DbFH.getDbFH().recover(tableName, sqlPath).toString();
            if("ok".equals(returnStr)){
                info = "还原成功!";
            }else{
                info = "还原失败!";
            }
        } catch (InterruptedException e) {
            info = "还原失败!";
            e.printStackTrace();
        } catch (ExecutionException e) {
            info = "还原失败!";
            e.printStackTrace();
        }
        return new ResultInfo(StatusCode.SUCCESS,info);
    }
}
