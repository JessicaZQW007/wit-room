package com.yhxc.controller.send;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.send.SetMessage;
import com.yhxc.service.send.SetMessageService;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.SetMessageRecordService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 16:07
 */
@Controller
@RequestMapping("/setMessage")
public class SetMessageController {

    @Resource
    private LogService logService;
    @Resource
    private SetMessageService messageService;
    @Resource
    private SetMessageRecordService messageRecordService;
    /**
     * 根据条件查看空调信息
     * uuid 设备编号，num空调编号
     */
    @ResponseBody
    @RequestMapping("/findByUuidAndNum")
    public ResultInfo findByUuidAndNum(String uuid,String num){
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！",messageService.findByUuidAndNum(uuid,num));
    }

    /**
     * 添加空调操作信息
     */
    @RequestMapping("/savemessage")
    @ResponseBody
    public ResultInfo savemessage(SetMessage setMessage) throws Exception{
        messageRecordService.save(setMessage);   //纪录操作命令日志
        messageService.save(setMessage);            //添加操作命令操作
        return new ResultInfo(StatusCode.SUCCESS, "新增成功！");
    }

    /**
     * 分页查询空调操作信息
     */
    @RequestMapping("/findMessageRecord")
    @ResponseBody
    public ResultInfo savemessage(String uuid,String num,String realName,String allDate, int pageNum, int pageSize){
        JSONObject datas = messageRecordService.findAllByUuidPage(uuid,num,realName,allDate, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS,"success",datas);
    }
}
