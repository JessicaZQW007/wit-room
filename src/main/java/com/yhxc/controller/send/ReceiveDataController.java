package com.yhxc.controller.send;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.send.ReceiveData;
import com.yhxc.entity.send.SetMessage;
import com.yhxc.service.send.ReceiveDataService;
import com.yhxc.service.send.SetMessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 14:42
 */
@Controller
@RequestMapping("/receiveData")
public class ReceiveDataController {

    @Resource
    ReceiveDataService dataService;

    /**
     * 查看空调
     */
    @ResponseBody
    @RequestMapping("/findAllList")
    public ResultInfo findAllList(String uuid){
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",dataService.findAllList(uuid));
    }

    /**
     * 根据条件查看空调 1 信息
     * uuid 设备编号，num空调编号
     */
    @ResponseBody
    @RequestMapping("/findNnm1")
    public ResultInfo findNnm1(String uuid){
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",dataService.findNnm1(uuid));
    }

    /**
     * 根据条件查看空调 2 信息
     * uuid 设备编号，num空调编号
     */
    @ResponseBody
    @RequestMapping("/findNnm2")
    public ResultInfo findNnm2(String uuid){
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",dataService.findNnm2(uuid));
    }

    /**
     * 查看室内温度，室外湿度，室外温度
     * @param someDate 根据时间查询
     * @return
     */
   /* @ResponseBody
    @RequestMapping("/findTemps")
    public ResultInfo findTemps(String someDate,String uuid,int num){
        JSONArray datas = dataService.findTemps(someDate,uuid,num);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",datas);
    }*/

    /**
     * 查看室内温度，空调送风口温度
     * @param someDate 根据时间查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/findIndoorTemps")
    public ResultInfo findIndoorTemps(String someDate,String uuid,int num){
        JSONArray datas = dataService.findIndoorTemps(someDate,uuid,num);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",datas);
    }

    /**
     * 查看室外温湿度
     * @param someDate 根据时间查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/findOutdoorTempAndHumi")
    public ResultInfo findOutdoorTempAndHumidity(String someDate,String uuid){
        JSONArray datas = dataService.findOutdoorTempAndHumidity(someDate, uuid);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",datas);
    }

    /**
     * 查看空调送风口温度
     * @param someDate 根据时间查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/findTempsNum")
    public ResultInfo findTempsNum(String someDate,String uuid,int num){
        JSONArray datas = dataService.findTempsNum(someDate,uuid,num);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",datas);
    }

    /**
     * 查看室外温湿度，室内温度
     * @param someDate 根据时间查询
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDoorTempAndHumidity")
    public ResultInfo findDoorTempAndHumidity(String someDate,String uuid){
        JSONArray datas = dataService.findDoorTempAndHumidity(someDate, uuid);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",datas);
    }

}
