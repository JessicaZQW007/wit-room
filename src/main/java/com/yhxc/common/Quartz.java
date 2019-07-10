package com.yhxc.common;

import com.yhxc.entity.count.*;
import com.yhxc.entity.send.ReceiveData;
import com.yhxc.entity.system.Log;
import com.yhxc.repository.count.*;
import com.yhxc.service.analyze.RunService;
import com.yhxc.service.count.*;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.DateUtil;
import net.sf.json.JSONArray;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/***
 *
 * Quartz设置项目全局的定时任务
 *
 * @Component注解的意义        泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。一般公共的方法我会用上这个注解
 *
 * 定时做用电量汇总，日 月
 * @author liwg
 *
 */
@Component
public class Quartz {
    @Resource
    private MonthCountElectricRepository monthCountElectricRepository;
    @Resource
    private MonthCountElectricService monthCountElectricService;
    @Resource
    private RunService runService;
    @Resource
    private YearCountElectricService yearCountElectricService;
    @Resource
    private LogService logService;
    @Resource
    private DayCountElectricService dayCountElectricService;
    @Resource
    private YearCountElectricRepository yearCountElectricRepository;


    @Resource
    private DayRunTimeCountRepository dayRunTimeCountRepository;
    @Resource
    private DayRunTimeCountService dayRunTimeCountService;
    @Resource
    private MonthRunTimeCountService monthRunTimeCountService;
    @Resource
    private MonthRunTimeCountRepository monthRunTimeCountRepository;
    @Resource
    private YearRunTimeCountService yearRunTimeCountService;

    @Resource
    private MonthAirCountElectricService monthAirCountElectricService;

    @Resource
    private YearAirCountElectricService yearAirCountElectricService;

    @Resource
    private YearAirCountElectricRepository yearAirCountElectricRepository;
    @Resource
    private  DayAirCountElectricService dayAirCountElectricService ;



    //备份昨天的运行数据
    @Scheduled(cron = "0 2 0 * * ?") // 每天凌晨2点00分执行一次
    @Async
    public void backupsRun() throws Exception {
        String date= DateUtil.getLastDay();
        List<?> datas= dayRunTimeCountRepository.findLastReceiveData(date);
        if(datas!=null){
            for (int i=0;i<datas.size();i++){
                DayRunTimeCount dayRunTimeCount=new DayRunTimeCount();
                Object[] objects = (Object[]) datas.get(i);
                dayRunTimeCount.setDate(objects[0].toString());
                dayRunTimeCount.setCreatetime(DateUtil.getTime());
                dayRunTimeCount.setUuid(objects[1].toString());
                dayRunTimeCount.setAirCurrent1(objects[2].toString());
                dayRunTimeCount.setAirCurrent2(objects[3].toString());
                dayRunTimeCountService.save(dayRunTimeCount);
            }
        }
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份"+ date  + "运作数据"));
    }


    //汇总 昨天 每小时 用电量
    @Scheduled(cron = "0 50 0 * * ?") // 每天凌晨 12点10分执行一次
    @Async
    public void countHour() throws Exception {

        String date= DateUtil.getLastDay();
        //  String date= "2019-03-29";
        dayCountElectricService.sumHourCount(date);
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份每小时用电量"+date+"数据,成功!"));

    }






    //汇总 昨天空调每小时用电量
    @Scheduled(cron = "0 30 1 * * ?") // 每天凌晨1点39分执行一次
    @Async
    public void countAirHour() throws Exception {
      String date= DateUtil.getLastDay();
      //  String date="2019-03-29";
        JSONArray jsonArray1=dayCountElectricService.findOneHour(date);
        JSONArray jsonArray2=dayCountElectricService.findTwoHour(date);
        for (int i = 0; i < jsonArray1.size(); i++) {
            DayAirCountElectric dayAirCountElectric=new DayAirCountElectric();
            if(jsonArray1.getJSONObject(i).getString("uuid").equals(jsonArray2.getJSONObject(i).getString("uuid"))) {
                String uuid=jsonArray1.getJSONObject(i).getString("uuid");
                Float num1=Float.parseFloat(jsonArray1.getJSONObject(i).getString("num"));
                Float num2=Float.parseFloat(jsonArray2.getJSONObject(i).getString("num"));
                dayAirCountElectric.setCreatetime(DateUtil.getTime());
                dayAirCountElectric.setDate(jsonArray1.getJSONObject(i).getString("dateHour"));
                dayAirCountElectric.setUuid(uuid);
                dayAirCountElectric.setElequantity1(num1);
                dayAirCountElectric.setElequantity2(num2);
                dayAirCountElectricService.save(dayAirCountElectric);
            }

        }

        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份用电量"+date+"数据,成功!"));
    }


    /*//汇总 昨天电表 用电量
    @Scheduled(cron = "0 30 2 * * ?") // 每天凌晨0点45分执行一次
    public void countDay() throws Exception {
         String date= DateUtil.getLastDay();
        // String date= "2019-03-29";
        monthCountElectricService.sumDayCount(date);
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份用电量"+date+"数据,成功!"));
    }*/



    //汇总 上个月 每个设备的 用电量
    @Scheduled(cron = "0 30 2 * * ?") // 每月的1号1点30分执行 用电量
    @Async
    public void countDay() throws Exception {
        String date= DateUtil.getLastDay();
        List<?> datas=monthCountElectricRepository.sumDay(date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            MonthCountElectric monthCountElectric=new MonthCountElectric();
            monthCountElectric.setUuid(String.valueOf(objects[0]));
            monthCountElectric.setDate(String.valueOf(objects[1]));
            monthCountElectric.setElequantity(Float.parseFloat(objects[2].toString()));
            monthCountElectric.setCreatetime(DateUtil.getTime());
            monthCountElectricService.save(monthCountElectric);
        }
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份空调用电量"+date+"数据,成功!"));
    }




    /*//汇总 上个月 每个设备的 用电量
    @Scheduled(cron = "0 30 2 * * ?") // 每月的1号1点30分执行 用电量
    @Async
    public void countDay() throws Exception {
        for(int y=1;y<49;y++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -y);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
            System.out.println("减天数 date=" + date);

            List<?> datas = monthCountElectricRepository.sumDay(date);
            for (int i = 0; i < datas.size(); i++) {
                Object[] objects = (Object[]) datas.get(i);
                MonthCountElectric monthCountElectric = new MonthCountElectric();
                monthCountElectric.setUuid(String.valueOf(objects[0]));
                monthCountElectric.setDate(String.valueOf(objects[1]));
                monthCountElectric.setElequantity(Float.parseFloat(objects[2].toString()));
                monthCountElectric.setCreatetime(DateUtil.getTime());
                monthCountElectricService.save(monthCountElectric);
            }
            logService.save(new Log(Log.ADD_ACTION, "在" + DateUtil.getTime() + "的时候:备份空调用电量" + date + "数据,成功!"));
        }
    }*/







    //汇总 昨天空调用电量
    @Scheduled(cron = "0 0 4 * * ?") // 每天凌晨0点45分执行一次
    @Async
    public void countAirDay() throws Exception {
        String date= DateUtil.getLastDay();
        JSONArray jsonArray1=dayCountElectricService.findOne(date);
        JSONArray jsonArray2=dayCountElectricService.findTwo(date);
        for (int i = 0; i < jsonArray1.size(); i++) {
            MonthAirCountElectric monthAirCountElectric=new MonthAirCountElectric();
            if(jsonArray1.getJSONObject(i).getString("uuid").equals(jsonArray2.getJSONObject(i).getString("uuid"))) {
                String uuid=jsonArray1.getJSONObject(i).getString("uuid");
                Float num1=Float.parseFloat(jsonArray1.getJSONObject(i).getString("num"));
                Float num2=Float.parseFloat(jsonArray2.getJSONObject(i).getString("num"));
                monthAirCountElectric.setCreatetime(DateUtil.getTime());
                monthAirCountElectric.setDate(date);
                monthAirCountElectric.setUuid(uuid);
                monthAirCountElectric.setElequantity1(num1);
                monthAirCountElectric.setElequantity2(num2);
                monthAirCountElectricService.save(monthAirCountElectric);
            }

        }

        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份用电量"+date+"数据,成功!"));
    }


    //汇总 上个月 每个空调的 用电量
    @Scheduled(cron = "0 1 5 1 * ?") // 每月的1号1点30分执行 用电量
    @Async
    public void backupAirCurrentMonth() throws Exception {
       String date= DateUtil.getLastMonth();
      //  String date= "2019-03";
        List<?> datas1=yearAirCountElectricRepository.sumAirMonth1(date);
        List<?> datas2=yearAirCountElectricRepository.sumAirMonth2(date);
        for (int i = 0; i < datas1.size(); i++) {
            Object[] objects1 = (Object[]) datas1.get(i);
            Object[] objects2 = (Object[]) datas2.get(i);
            YearAirCountElectric yearAirCountElectric=new YearAirCountElectric();
            yearAirCountElectric.setCreatetime(DateUtil.getTime());
            yearAirCountElectric.setDate(objects1[1].toString());
            yearAirCountElectric.setUuid(objects1[0].toString());
            yearAirCountElectric.setElequantity1(Float.parseFloat(objects1[2].toString()));
            yearAirCountElectric.setElequantity2(Float.parseFloat(objects2[2].toString()));
            yearAirCountElectricService.save(yearAirCountElectric);

        }
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份设备用电量"+date+"数据,成功!"));
    }



    //汇总 上个月 每个设备的 用电量
    @Scheduled(cron = "0 0 3 1 * ?") // 每月的1号1点30分执行 用电量
    @Async
    public void backupCurrentMonth() throws Exception {
        String date= DateUtil.getLastMonth();
        List<?> datas=yearCountElectricRepository.sumMonth(date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            YearCountElectric yearCountElectric=new YearCountElectric();
            yearCountElectric.setUuid(String.valueOf(objects[0]));
            yearCountElectric.setDate(String.valueOf(objects[1]));
            yearCountElectric.setElequantity(Float.parseFloat(objects[2].toString()));
            yearCountElectric.setCreatetime(DateUtil.getTime());
            yearCountElectricService.save(yearCountElectric);
        }
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份空调用电量"+date+"数据,成功!"));
    }



/*

    //统计昨天每小时每台空调运行时间
    @Scheduled(cron = "0 47 4 * * ?") // 每天凌晨0点48分执行一次
    @Async
    public void dayRunSum() throws Exception {
        String date= DateUtil.getLastDay();
        System.out.println("运行了");
        JSONArray datas1=dayRunTimeCountService.countDayRunTime1(date);//空调1运行时间
        JSONArray datas2=dayRunTimeCountService.countDayRunTime2(date);//空调2运行时间
        JSONArray datas3=dayRunTimeCountService.countDayRuncoolTime1(date);//空调1制冷运行时间
        JSONArray datas4=dayRunTimeCountService.countDayRuncoolTime2(date);//空调2制冷运行时间

        if (datas1!=null){
            for (int i = 0; i < datas1.size(); i++) {
                MonthRunTimeCount monthRunTimeCount=new MonthRunTimeCount();
                monthRunTimeCount.setDate(date);
                monthRunTimeCount.setUuid(datas1.getJSONObject(i).getString("uuid"));
                monthRunTimeCount.setAirRunTime2(datas2.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setAirRunTime1(datas1.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setAirRunCoolTime1(datas3.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setAirRunCoolTime2(datas4.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setCreatetime(DateUtil.getTime());
                monthRunTimeCountService.save(monthRunTimeCount);
            }
        }

        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份运行时间"+date+"数据,成功!"));
    }
*/



    //统计昨天的每台空调运行时间
    @Scheduled(cron = "0 20 4 * * ?") // 每天凌晨0点48分执行一次
    @Async
    public void countDayRunTime() throws Exception {
     String date= DateUtil.getLastDay();
      // String date="2019-03-29";
        System.out.println("运行了");
        JSONArray datas1=dayRunTimeCountService.countDayRunTime1(date);//空调1运行时间
        JSONArray datas2=dayRunTimeCountService.countDayRunTime2(date);//空调2运行时间
        JSONArray datas3=dayRunTimeCountService.countDayRuncoolTime1(date);//空调1制冷运行时间
        JSONArray datas4=dayRunTimeCountService.countDayRuncoolTime2(date);//空调2制冷运行时间

        if (datas1!=null){
            for (int i = 0; i < datas1.size(); i++) {
                MonthRunTimeCount monthRunTimeCount=new MonthRunTimeCount();
                monthRunTimeCount.setDate(date);
                monthRunTimeCount.setUuid(datas1.getJSONObject(i).getString("uuid"));
                monthRunTimeCount.setAirRunTime2(datas2.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setAirRunTime1(datas1.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setAirRunCoolTime1(datas3.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setAirRunCoolTime2(datas4.getJSONObject(i).getString("runDate"));
                monthRunTimeCount.setCreatetime(DateUtil.getTime());
                monthRunTimeCountService.save(monthRunTimeCount);
            }
        }

        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份运行时间"+date+"数据,成功!"));
    }

    //汇总 上个月 每个空调的运行时间
    @Scheduled(cron = "0 35 3 1 * ?") // 每月的1号1点30分执行 用电量
    @Async
    public void backupRunMonth() throws Exception {
       String date= DateUtil.getLastMonth();
       // String date="2019-03";
        List<?> datas1=monthRunTimeCountRepository.sumMonthAirRunTime1(date);//空调1运行时间
        List<?> datas2=monthRunTimeCountRepository.sumMonthAirRunTime2(date);//空调2运行时间
        List<?> datas3=monthRunTimeCountRepository.sumMonthAirRunTimeCool1(date);//空调1制冷运行时间
        List<?> datas4=monthRunTimeCountRepository.sumMonthAirRunTimeCool2(date);//空调2制冷运行时间
        for (int i = 0; i < datas1.size(); i++) {
            Object[] objects1 = (Object[]) datas1.get(i);
            Object[] objects2 = (Object[]) datas2.get(i);
            Object[] objects3 = (Object[]) datas3.get(i);
            Object[] objects4 = (Object[]) datas4.get(i);
            YearRunTimeCount yearRunTimeCount=new YearRunTimeCount();
            yearRunTimeCount.setDate(String.valueOf(objects1[1]));
            yearRunTimeCount.setUuid(String.valueOf(objects1[0]));
            yearRunTimeCount.setCreatetime(DateUtil.getTime());
            yearRunTimeCount.setAirRunTime1(String.valueOf(objects1[2]));
            yearRunTimeCount.setAirRunTime2(String.valueOf(objects2[2]));
            yearRunTimeCount.setAirRunCoolTime1(String.valueOf(objects3[2]));
            yearRunTimeCount.setAirRunCoolTime2(String.valueOf(objects4[2]));
            yearRunTimeCountService.save(yearRunTimeCount);

        }
        logService.save(new Log(Log.ADD_ACTION, "在"+DateUtil.getTime()+"的时候:备份运行时间"+date+"数据,成功!"));
    }




}
