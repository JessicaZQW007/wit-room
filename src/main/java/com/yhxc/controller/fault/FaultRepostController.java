package com.yhxc.controller.fault;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.fault.Fault;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.fault.FaultReportService;
import com.yhxc.service.fault.FaultService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/23 9:49
 */

@Controller
@RequestMapping("faultRepost")
public class FaultRepostController {

    @Resource
    private FaultReportService faultReportService;
    @Resource
    private LogService logService;
    @Resource
    private FaultService faultService;

//    查看报警数量
    @ResponseBody
    @RequestMapping("/number")
    public ResultInfo messageNumber(){
        User u= Jurisdiction.getCurrentUser();
        String pId="";
        String unitId="";

        if(u.getUserType()==1){
            //平台用户
            pId=u.getUnitId();

        }else if(u.getUserType()==2){
            //机构用户
            unitId=u.getUnitId();
        }
        return new ResultInfo(StatusCode.SUCCESS,"success",faultReportService.findNumber(pId,unitId));
    }

//    分页条件查询报警信息
    @ResponseBody
    @RequestMapping("/listPageMessage")
    public ResultInfo listPage(String pname,String message,String rank,String address,String allDate,int pageNum,int pageSize){
        User u=Jurisdiction.getCurrentUser();
        String pId="";
        String unitId="";

        if(u.getUserType()==1){
            //平台单位
            pId=u.getUnitId();

        }else if(u.getUserType()==2){
            //项目机构
            unitId=u.getUnitId();

        }
        JSONObject datas= faultReportService.findAllReportMessagePage(pId,unitId,pname,message,rank,address,allDate,pageNum,pageSize);
        return new ResultInfo(StatusCode.SUCCESS,"success",datas);
    }



    //  根据uuid查询所有的未处理故障信息
    @ResponseBody
    @RequestMapping("/findAllReport")
    public ResultInfo findAllReport(String uuid){
        return new ResultInfo(StatusCode.SUCCESS,"success",faultReportService.findAllReport(uuid));
    }





//    删除处理过的报警信息,保存到fault表
    @ResponseBody
    @RequestMapping("/deletesave")
    public ResultInfo delete(String id){
//      把报警信息存入fault表
        JSONArray jsonArray = faultReportService.findColumns(id);
        if (jsonArray.size()>0){
            Object object = jsonArray.get(0);
            JSONObject jsonObject = JSONObject.fromObject(object);
            Fault fault = (Fault) JSONObject.toBean(jsonObject, Fault.class);
            fault.setNdate((String) jsonObject.get("createtime"));//报警开始时间
            fault.setUserName(Jurisdiction.getCurrentRealName());     //设置操作用户真实姓名
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fault.setEndDate(dateFormat.format(new Date()));//报警解决时间
            fault.setFaState("0");//默认处理状态为1
            logService.save(new Log(Log.DELETE_ACTION, "存入已解决的报警信息！"+fault));
            faultService.save(fault);
//          删除报警信息
            faultReportService.deleteByAirIdAndCodeAndUuid(id);
            logService.save(new Log(Log.DELETE_ACTION, "处理报警信息！"+object));

            return new ResultInfo(StatusCode.SUCCESS, "处理成功！");
        }else {
            return new ResultInfo(StatusCode.FAIL, "处理失败！");
        }
    }

    //  批量删除处理过的报警信息,保存到fault表
    @ResponseBody
    @RequestMapping("/deleteAllsave")
    public ResultInfo deleteAll(String ids){
        String[] num = ids.split(",");
        if (StringUtil.isNotEmpty(ids)) {
            String[] idStr = ids.split(",");
            for (String id : idStr) {
                //      把报警信息存入fault表
                JSONArray jsonArray = faultReportService.findColumns(id);
                if (jsonArray.size()>0){
                    Object object = jsonArray.get(0);
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    Fault fault = (Fault) JSONObject.toBean(jsonObject, Fault.class);
                    fault.setNdate((String) jsonObject.get("createtime"));//报警开始时间
                    fault.setUserName(Jurisdiction.getCurrentRealName());     //设置操作用户真实姓名
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    fault.setEndDate(dateFormat.format(new Date()));//报警解决时间
                    fault.setFaState("0");//默认处理状态为0
                    logService.save(new Log(Log.DELETE_ACTION, "存入已解决的报警信息！"+fault));
                    faultService.save(fault);
//                   删除报警信息
                    logService.save(new Log(Log.DELETE_ACTION, "批量处理报警信息！"+object));
                    faultReportService.deleteByAirIdAndCodeAndUuid(id);
                }else {
                    return new ResultInfo(StatusCode.FAIL, "处理失败！");
                }
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "成功处理"+num.length+"条报警信息！");
    }

}
