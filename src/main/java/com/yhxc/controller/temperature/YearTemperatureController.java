package com.yhxc.controller.temperature;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;

import com.yhxc.service.temperature.YearTemperatureService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/26 14:17
 */


@Controller
@RequestMapping("/yearTemperature")
@Component
public class YearTemperatureController {

    @Resource
    private YearTemperatureService yearTemperatureService;



    /**环境温度统计报表  室外温湿度 室内温度 回风温度
     *
     */
    @ResponseBody
    @RequestMapping("/findSwTempHumi")
    public ResultInfo findSwTempHumi(String pname,String date) {

        JSONArray datas=yearTemperatureService.findSwTempHumi(pname,date);
        return new ResultInfo(StatusCode.SUCCESS,"success",datas);

    }



}
