package com.yhxc.controller.send;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.service.send.ReceiveDataA011Service;
import org.dom4j.io.STAXEventReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/6/17 9:48
 */
@Controller
@RequestMapping("/receiveDataA011")
public class ReceiveDataA011Controller {
    @Resource
    private ReceiveDataA011Service A011;

    @ResponseBody
    @RequestMapping("/findUnitAddr")
    public ResultInfo findUnitAddr(String uuid) {
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", A011.findUnitAddr(uuid));

    }
/**
 *功能描述 根据uuid和机组地址查询所有数据
        * @author 张权威
        * @date 2019/6/18
        * @param [uuid, unitaddr]
        * @return
        */
    @ResponseBody
    @RequestMapping("/findMessageByUuid")
    public ResultInfo findMessageByUuid(String uuid, String unitaddr) {
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", A011.findMessageByUuid(uuid, unitaddr));
    }

    @ResponseBody
    @RequestMapping("/findTempByDate")
    public ResultInfo findTempByDate(String uuid, String time, String unitaddr) {
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",A011.findTempByDate(uuid, time, unitaddr));
    }
    @ResponseBody
    @RequestMapping("/todayPower")
    public ResultInfo todayPower(String uuid, String time, String unitaddr) {
        String todayPower=A011.tadayTotalpower(uuid, time, unitaddr);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",todayPower);
    }
    /**
     *功能描述 三相电流
            * @author 张权威
            * @date 2019/6/18
            * @param [uuid, time, unitaddr]
            * @return
            */
    @ResponseBody
    @RequestMapping("/findCurrentByDate")
    public ResultInfo findCurrentByDate(String uuid, String time, String unitaddr) {
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", A011.findCurrentByDate(uuid, time, unitaddr));
    }
    /**
     *功能描述 小时统计能耗
            * @author 张权威
            * @date 2019/6/18
            * @param [uuid, time, unitaddr]
            * @return
            */
    @ResponseBody
    @RequestMapping("/findTotPowerByTime")
    public ResultInfo findTotPowerByTime(String uuid, String time, String unitaddr, String data) {
        String[] hour={
                "00",
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",

        };
        List hourTotPower= new ArrayList();
        for (int i=0;i<hour.length;i++){
            String power=A011.findTotPowerByTime(uuid, unitaddr,data+" "+hour[i]);
            hourTotPower.add(power);
        }
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", hourTotPower);
    }
}
