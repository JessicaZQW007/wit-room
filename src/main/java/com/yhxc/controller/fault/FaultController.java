package com.yhxc.controller.fault;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.fault.FaultCode;
import com.yhxc.entity.system.User;
import com.yhxc.service.fault.FaultService;
import com.yhxc.utils.Jurisdiction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/23 17:38
 */
@Controller
@RequestMapping("fault")
public class FaultController {

    @Resource
    private FaultService faultService;

//    查询全部故障（处理，未处理）
    @ResponseBody
    @RequestMapping("/listPageFault")
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

        JSONObject datas= faultService.findAllFaultMessagePage(pId,unitId,pname,message,rank,address,allDate,pageNum,pageSize);
        return new ResultInfo(StatusCode.SUCCESS,"success",datas);
    }

//    查看某月故障数量
    @ResponseBody
    @RequestMapping("/findMonthCount")
    public ResultInfo  findMonthCount(String date) {
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

        JSONArray datas=  faultService.findMonthCount(date,pId,unitId);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

//    查看某个设备的故障
    @ResponseBody
    @RequestMapping("/findAllReprot")
    public ResultInfo  findAllReprot(String uuid,String air_id) {
        JSONArray datas=  faultService.findAllReprot(uuid,air_id);
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }

    //    查看所有的故障代码信息
    @ResponseBody
    @RequestMapping("/findFaultCode")
    public ResultInfo  findFaultCode() {
        JSONArray datas=  faultService.findFaultCode();
        return new ResultInfo(StatusCode.SUCCESS, "成功！",datas);
    }


}
