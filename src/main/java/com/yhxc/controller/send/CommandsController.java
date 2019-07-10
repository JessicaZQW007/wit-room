package com.yhxc.controller.send;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.service.count.DayAirCountElectricService;
import com.yhxc.service.count.DayCountElectricService;
import com.yhxc.service.send.CommandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/27 15:02
 */
@Controller
@RequestMapping("/commands")
public class CommandsController {

    @Resource
    private CommandService commandService;
    @Resource
    private DayCountElectricService dayaircountelectricservice;

//    查看指令送达状态
    @ResponseBody
    @RequestMapping("/findCommand")
    public ResultInfo findAllList(String messageid,String uuid){
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",commandService.findByMessageidAndUuid(messageid, uuid));
    }
    @ResponseBody
    @RequestMapping("/test")
    public void test(String date){
        dayaircountelectricservice.sumHourCount(date);
    }


}
