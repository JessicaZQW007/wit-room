package com.yhxc.controller.timer;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.timer.Timer;
import com.yhxc.service.system.LogService;
import com.yhxc.service.timer.TimerService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/5/6 17:20
 */
@Controller
@RequestMapping("/timer")
@Component
public class TimerController {
    @Resource
    private TimerService timerService;
    @Resource
    private LogService logService;


    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public ResultInfo saveOrUpdate(Timer timer) {
        String info = "";

        String startT = timer.getStartTime();
        String stopT = timer.getStopTime();
        if (timer.getId() != null) { // 写入日志
            info = "更新信息成功！";
        } else {
            String id = UuidUtil.get32UUID();
            timer.setId(id);
            timer.setUserName(Jurisdiction.getUserName());
            timer.setIsStart(0);        //新增定时器默认不启用
            timer.setIsRun(0);
            info = "添加信息成功！";
        }
        if (startT.equals(stopT)) {
            info = "开始时间不能等于结束时间！";
            return new ResultInfo(StatusCode.FAIL, info);
        }
        List<Timer> listT = timerService.findAll(new Timer(timer.getUuid()));
        if (listT.size() >= 3) {
            info = "运行规则不能超过三条！";
            return new ResultInfo(StatusCode.FAIL, info);
        }
        int x = 0;
        for (Timer t : listT) {
            long hisStart = DateUtil.getTime(t.getStartTime());
            long hisStop = DateUtil.getTime(t.getStopTime());

            long nowStart = DateUtil.getTime(startT);
            long nowStop = DateUtil.getTime(stopT);

            if ((hisStart <= nowStart && nowStart <= hisStop) || (hisStart <= nowStop && nowStop <= hisStop)) {
                //新增定时规则不能在之前规则的时间段内,如果在时间段内就x++
                x++;
            }
        }
        if (x == 1) {
            info = "不能嵌套时间段！";
        } else {
            timerService.save(timer);
        }
        logService.save(new Log(Log.ADD_ACTION, "新增定时设置！"));
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    @ResponseBody
    @RequestMapping("/list")
    public ResultInfo list(Timer timer) {
        String userName = Jurisdiction.getUserName();
        timer.setUserName(userName);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", timerService.findAll(timer));
    }

    @ResponseBody
    @RequestMapping("/del")
    public ResultInfo del(String id) {
        timerService.delete(id);
        logService.save(new Log(Log.DELETE_ACTION, "删除定时设置！"));
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }


    @ResponseBody
    @RequestMapping("/findTimerRole")
    public ResultInfo findTimerRole() {
        Timer timer = new Timer();
        timer.setUserName(Jurisdiction.getUserName());
        return new ResultInfo(StatusCode.SUCCESS, "success", timerService.findAll(timer));
    }


    /**
     * 这里需要前端发送，是否启用定时器
     *
     * @param timer
     * @return
     */
    @ResponseBody
    @RequestMapping("/updIsStart")
    public ResultInfo updIsStart(Timer timer) {
        Integer isStart = timer.getIsStart();
        String info = "启用失败！";
        //查询启动几个定时器
        /*List<Timer> listT = timerService.findAll(new Timer(timer.getUuid()));
        int a = 0;
        for (Timer timer1 : listT) {
            Integer is = timer1.getIsStart();
            if (is == 1) {
                a++;
            }
        }
        if (a >= 1) {
            info = "启用失败！不能同时启用多个定时器！";
        } else {

        }*/
        timerService.updIsStart(isStart, timer.getId());
        if (isStart == 1) {
            info = "启用成功！";
        } else if (isStart == 0) {
            info = "已关闭！";
        }
        logService.save(new Log(Log.UPDATE_ACTION, "定时器" + info));
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 手机端定时方法，
     */
    //@Scheduled(cron = "0 0,30 * * * ?")//每个小时的00分和30分执行
    //@Async
    //@ResponseBody
    //@RequestMapping("/timer")
    public void timer() {
        System.err.println("定时器启动！");
        Timer t = new Timer();
        t.setIsStart(1);//查询所有已启用的定时数据

        Calendar c = Calendar.getInstance();
        Integer weekNow = c.get(Calendar.DAY_OF_WEEK) - 1;//获取当前星期几
        if (0 == weekNow) {
            weekNow = 7;
        }
        t.setWeek(weekNow+"");
        List<Timer> listTimer = timerService.findAll(t);

        String hoursAndMinutes = DateUtil.getHoursAndMinutes();//获取当前小时和分钟

        for (Timer timer : listTimer) {
            String uuid = timer.getUuid();
            String temp = timer.getTemp();              //定时温度
            String startTime = timer.getStartTime();    //定时开始时间
            String stopTime = timer.getStopTime();      //定时结束时间

                //如果当前时间和开机时间相同，开机
                if(hoursAndMinutes.equals(startTime)){
                    String paranum = "2";
                    String command = "runmodel,开机;hstemp,"+ temp +";";
                    String sendResult =""; //commandBoard(uuid, paranum, command);
                    String res = check(sendResult, uuid);
                    System.err.println("开机"+res);
                    //开机后，更新状态为1
                    if(StringUtil.isNotEmpty(res)){
                        timerService.updIsRun(1, uuid);
                    }
                }

                //如果当前时间和关机时间相同，关机
                if(hoursAndMinutes.equals(stopTime)){
                    String paranum = "1";
                    String command = "runmodel,关机";
                    String sendResult = ""; //commandBoard(uuid, paranum, command);
                    String res = check(sendResult, uuid);
                    System.err.println("关机"+res);
                    //关机后，更新状态为0
                    if(StringUtil.isNotEmpty(res)){
                        timerService.updIsRun(0, uuid);
                    }
                }

        }
    }


    /**
     * 校验命令是否发送成功
     * @param sendResult
     * @return
     */
    public String check(String sendResult, String uuid){
        String[] result = sendResult.split(",");
        String messageId = result[1];
        String res = "";
        int i = 0;
        while (StringUtil.isEmpty(res)){
            i++;        //防止请求失败造成死循环
            //res = getCommandStatus(uuid, messageId);
            if(i > 60){
                break;
            }
        }
        return res;
    }


    /**
     * 发送命令
     * @param uuid
     * @param paranum
     * @param command
     * @return
     */
    /*public String commandBoard(String uuid, String paranum, String command){
        System.out.println("***************机型KJR-51 编码测试*****************");
        Encode c2 = new Encode();
        //数据存入数据库的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = format.format(new Date());
        RandomChar rand = new RandomChar();
        Integer unm= Integer.parseInt(paranum);
        String messageid =rand.getRandomChar(4);
        String[][] ccc=new String[unm][2];
        String[] aaa=command.split(";");
        for (int i=0;i<aaa.length;i++){
            String[] bbb=aaa[i].split(",");
            ccc[i][0]=bbb[0];
            ccc[i][1]=bbb[1];
        }
        String cmdstr2 = c2.boardCmdEncode( messageid, paranum, ccc);
        System.out.println(cmdstr2);
        String deviceId=equidMentService.finddeviceId(uuid);
        String message = null;
        try {
            message = Command.SendCommadn(deviceId,cmdstr2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        commandForMeidi com = new commandForMeidi();
        com.setCommand(cmdstr2);
        com.setMessageid(messageid);
        com.setSendDate(date);
        com.setUuid(uuid);
        comm.saveCommand(com);
        System.out.println(cmdstr2);
        return message+","+messageid+","+uuid;
    }*/


    /**
     * 监测是否发送成功
     * @param uuid
     * @param messageid
     * @return
     */
    /*public String getCommandStatus(String uuid, String messageid){
        return commandmeidiservice.findstatusByCommd(uuid,messageid);
    }*/
}
