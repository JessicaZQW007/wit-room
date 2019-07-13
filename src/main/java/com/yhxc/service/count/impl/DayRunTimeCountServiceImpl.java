package com.yhxc.service.count.impl;


import com.yhxc.entity.count.DayRunTimeCount;
import com.yhxc.repository.count.DayRunTimeCountRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.repository.send.ReceiveDataRepository;
import com.yhxc.service.count.DayRunTimeCountService;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日统计表   空调运行情况ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class DayRunTimeCountServiceImpl implements DayRunTimeCountService {

    @Resource
    private DayRunTimeCountRepository dayRunTimeCountRepository;
    @Resource
    private ProjectRepository projectRepository;


    @Resource
    private ReceiveDataRepository receiveDataRepository;



    @PersistenceContext
    private EntityManager em;
    private static Double mixCurrent=0.01;//判断电流大于多少是运行


    @Override
    public void save(DayRunTimeCount dayRunTimeCount) {
           dayRunTimeCountRepository.save(dayRunTimeCount);
    }

    //空调每小时的制冷时间 按小时计算
    @Override
    public JSONObject findAirRunTime(String pname, String date) {

        int  airNum= projectRepository.findProjectAirNum(pname);
        JSONObject jsonObject=new JSONObject();
        if (airNum==1){
            jsonObject.put("air1",disposeDate1(date,pname,1));
        }else if (airNum==2){
            jsonObject.put("air1",disposeDate1(date,pname,1));
            jsonObject.put("air2",disposeDate1(date,pname,2));
        }




        return jsonObject;
    }




    //公共的
    public JSONArray disposeDate1(String date,String pname,int airNum) {
        JSONArray jsonArray = new JSONArray();
        Date time1=null;
        Date time2 = null;
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";//小时
        List<?> datas1=null;


        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            double minute = 0;//分钟
            double minute1 = 0;//分钟
            double minuteSum = 0;//总和分钟
           /* if(airNum==1){*/
            minuteSum = 0;
            minute1 = 0;
            minute = 0;
            String date1 = date + " " + hourArray[b];


            String lastDate1 = "";
            String nextDate1 = "";
            List<?> datasLast = null;
            List<?> datasNext = null;

            if (airNum == 1) {
                datas1 = receiveDataRepository.findCoolTime1(date1, pname);
            } else if (airNum == 2) {
                datas1 = receiveDataRepository.findCoolTime2(date1, pname);
            }

            System.out.println("datas.size="+datas1.size());

            if (datas1.size() != 0){
                if (hourArray[b].equals("00")) {
                    minuteSum = 0;
                    minute1 = 0;
                    minute = 0;
                    DateFormat df_parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //上个小时没有数据

                    //如果本小时的最后一条数据为运行状态那么用下个小时的整点减去本小时的时间

                    Object[] object = (Object[]) datas1.get(datas1.size() - 1);//本小时最后一条数据
                    if (Integer.parseInt(object[2].toString()) == 2) {
                        try {
                            time1 = (Date) df_parseDate.parse(object[1].toString());
                            time2 = (Date) df_parseDate.parse(date + " " + hourArray[b + 1] + ":00:00");
                            minute = (Double.valueOf(time2.getTime() - time1.getTime())) / (1000 * 60);
                            System.out.println("00点的分钟minute=" + minute);
                            minuteSum = minuteSum + minute;
                            System.out.println("00点的minuteSum" + minuteSum);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    minuteSum = 0;
                    minute1 = 0;
                    minute = 0;
                    // 上个小时有数据
                    // 如果上一个小时的最后一条数据为运行中的状态，那么用本小时的第一条数据的时间减去本小时的整点
                    // 如果本小时的最后一条数据为运行状态那么用下个小时的整点减去本小时的时间
                    DateFormat df_parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nextTime = "";
                    String lastTime = date + " " + hourArray[b] + ":00:00";
                    System.out.println("lastTime=" + lastTime);
                    if (hourArray[b].equals("23")) {
                        nextTime = date + " " + "24:00:00";
                    } else {
                        nextTime = date + " " + hourArray[b + 1] + ":00:00";
                    }


                    Object[] object = (Object[]) datas1.get(datas1.size() - 1);//本小时最后一条数据
                    if (Integer.parseInt(object[2].toString()) == 2) {
                        try {
                            time1 = (Date) df_parseDate.parse(object[1].toString());
                            time2 = (Date) df_parseDate.parse(nextTime);
                            minute = (Double.valueOf(time2.getTime() - time1.getTime())) / (1000 * 60);
                            System.out.println("本小时最后一条数据为运行minute=" + minute);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }



                    System.out.println("上小时的时间="+lastDate1);//上小时的数据如果为空就一直往前找知道有数据为止
                    for (int i = 1; -1 != (Integer.parseInt(hourArray[b])-i); i++) {
                        lastDate1=date+" "+hourArray[b-i];
                        datasLast=receiveDataRepository.findCoolTime1(lastDate1,pname);//前面小时的数据
                        if (datasLast.size()!=0){

                            Object[] object1 = (Object[]) datas1.get(0);//本小时第一条数据
                            Object[] object2 = (Object[]) datasLast.get(datasLast.size() - 1);//上小时最后一条数据
                            if (Integer.parseInt(object2[2].toString()) == 2) {
                                try {
                                    time1 = (Date) df_parseDate.parse(lastTime);
                                    System.out.println("上面的time1=" + time1);
                                    time2 = (Date) df_parseDate.parse(object1[1].toString());
                                    System.out.println("上面的上一条数据的时间=" + object1[1].toString());
                                    System.out.println("上面的time2=" + time2);
                                    minute1 = (Double.valueOf(time2.getTime() - time1.getTime())) / (1000 * 60);
                                    System.out.println("上小时最后一条数据为运行minute1=" + minute1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            break;
                        }

                    }




                    /*if (datasLast.size()!=0){



                    Object[] object1 = (Object[]) datas1.get(0);//本小时第一条数据
                    Object[] object2 = (Object[]) datas1.get(datas1.size() - 1);//上小时最后一条数据
                    if (Integer.parseInt(object1[2].toString()) == 2) {
                        try {
                            time1 = (Date) df_parseDate.parse(lastTime);
                            System.out.println("上面的time1=" + time1);
                            time2 = (Date) df_parseDate.parse(object1[1].toString());
                            System.out.println("上面的上一条数据的时间=" + object1[1].toString());
                            System.out.println("上面的time2=" + time2);
                            minute1 = (Double.valueOf(time2.getTime() - time1.getTime())) / (1000 * 60);
                            System.out.println("上小时最后一条数据为运行minute1=" + minute1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    }*/

                    minuteSum = minuteSum + (minute + minute1);


                }


            System.out.println("date1=" + date1);

            System.out.println("datas1.size=" + datas1.size());


            for (int i = 0; i < datas1.size(); i++) {
                System.out.println("循环了=" + i);
                Object[] object = (Object[]) datas1.get(i);

                //Object[] objectLast = (Object[]) datas1.get(i-1);
                Object[] objectNext = null;
                if (datas1.size() != i + 1) {
                    objectNext = (Object[]) datas1.get(i + 1);
                }
                System.out.println("状态：object[2]=" + object[2]);
                if (Integer.parseInt(object[2].toString()) != 0) {
                    //不等0 表示在运行中的状态 用后一条数据的时间减本条数据的时间
                    System.out.println("正常运行状态开始计算时间");
                    try {
                        if (objectNext != null) {
                            DateFormat df_parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            time1 = (Date) df_parseDate.parse(object[1].toString());
                            time2 = (Date) df_parseDate.parse(objectNext[1].toString());
                            System.out.println("得到毫秒数=" + (time2.getTime() - time1.getTime()));//得到毫秒数
                            System.out.println("得到分钟数=" + ((time2.getTime() - time1.getTime()) / (1000 * 60)));//得到分钟数


                            minute = (Double.valueOf(time2.getTime() - time1.getTime())) / (1000 * 60);
                            System.out.println("minute=" + minute);
                            minuteSum = minuteSum + minute;
                            System.out.println("minuteSum=" + minuteSum);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            }


            BigDecimal a = new BigDecimal(minuteSum);
            double f1 = a.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", hourArray[b] + ":00");
            jsonObject.put("sum", f1);
            jsonArray.add(jsonObject);

           /* }*/
        }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("date", hourArray[b] + ":00");
                jsonObject.put("sum", 0);
                jsonArray.add(jsonObject);

            }

        }



        return jsonArray;


    }








    /**     num  1 代表空调1   2代表空调2  0代表没有运行
     * 公共方法
     * @author lwg
     *
     */
    public JSONArray disposeDate( List<?> datas,Integer num) {
        JSONArray jsonArray = new JSONArray();
        if (datas==null){
            for (int i = 0; i < 24; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("length", 0);
                jsonObject.put("date", i+":00");// 时间
                jsonArray.add(jsonObject);
            }
        }else{
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            if(Double.valueOf(objects[0].toString())==0.0){
                jsonObject.put("length", 0);
                jsonObject.put("date", objects[1]);// 时间
                jsonArray.add(jsonObject);
            }else {
                jsonObject.put("length", num);
                jsonObject.put("date", objects[1]);// 时间
                jsonArray.add(jsonObject);
            }

        }
        }
        return jsonArray;
    }

    @Override
    public JSONArray countDayRunTime1(String date) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=dayRunTimeCountRepository.countDayRunTime1(date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid",objects[0]);
            jsonObject.put("date", objects[1]);
            jsonObject.put("electric", objects[2]);
            System.out.println("objects[2]="+objects[2]);


            jsonArray.add(jsonObject);
        }
        return getRundate(jsonArray,date);
    }

    @Override
    public JSONArray countDayRunTime2(String date) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=dayRunTimeCountRepository.countDayRunTime2(date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid",objects[0]);
            jsonObject.put("date", objects[1]);
            jsonObject.put("electric", objects[2]);
            jsonArray.add(jsonObject);
        }
        return getRundate(jsonArray,date);
    }

    @Override
    public JSONArray countDayRuncoolTime1(String date) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=dayRunTimeCountRepository.countDayRuncoolTime1(date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid",objects[0]);
            jsonObject.put("date", objects[1]);
            jsonObject.put("electric", objects[2]);
            jsonArray.add(jsonObject);
        }
        return getRundate(jsonArray,date);
    }

    @Override
    public JSONArray countDayRuncoolTime2(String date) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=dayRunTimeCountRepository.countDayRuncoolTime2(date);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid",objects[0]);
            jsonObject.put("date", objects[1]);
            jsonObject.put("electric", objects[2]);
            jsonArray.add(jsonObject);
        }
        return getRundate(jsonArray,date);
    }

    @Override
    public JSONArray countAirDayRunTime(String date, String uuid,int airNum) {
        JSONArray jsonArray = new JSONArray();
        String maxtime=dayRunTimeCountRepository.maxtime(date,uuid);
        //airnum 第几台空调
        String dataSql="SELECT i.uuid,i.receive_date,( SELECT CASE WHEN(i.kt_switch" +airNum+
                " =\"开机\" ) THEN 2 ELSE 0 END   )AS Gender from  s_receive_data i where  ";

        if(StringUtil.isNotEmpty(date)) {
            dataSql += " substr(i.receive_date,1,10)=:date1 ";
        }
        if(StringUtil.isNotEmpty(uuid)) {
            dataSql += "and uuid=:uuid  ";
        }
        dataSql += " order by i.uuid,i.receive_date";

        // setFirstResults:数据从第几个开始显示（currentPage-1）*PageSize
        // setMaxResults：每页显示的数据数量PageSize
        Query dataQuery = em.createNativeQuery(dataSql);

        if(StringUtil.isNotEmpty(uuid)) {
            dataQuery.setParameter("uuid", uuid);
        }
        if(StringUtil.isNotEmpty(date)) {
            dataQuery.setParameter("date1", date);
        }
        List<?> datas = dataQuery.getResultList();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid",objects[0]);
            jsonObject.put("date", objects[1]);
            jsonObject.put("electric", objects[2]);
            jsonArray.add(jsonObject);
        }
        return getRundateNow(jsonArray,maxtime);
    }


    @Override
    public JSONArray countDayRunTimecool(String date, String uuid,int airNum ) {
        JSONArray jsonArray = new JSONArray();
        String maxtime=dayRunTimeCountRepository.maxtime(date,uuid);
        //airnum 第几台空调
        String dataSql="SELECT i.uuid,i.receive_date, ( SELECT CASE WHEN(i.kt_run_model" +airNum+
                "=\"制冷\" ) THEN 2 ELSE 0 END   )AS Gender from  s_receive_data i where  ";


        if(StringUtil.isNotEmpty(uuid)) {
            dataSql += " uuid=:uuid  ";
        }
        if(StringUtil.isNotEmpty(date)) {
            dataSql += "and substr(i.receive_date,1,10)=:date1  ";
        }
        dataSql += " order by i.uuid,i.receive_date";

        // setFirstResults:数据从第几个开始显示（currentPage-1）*PageSize
        // setMaxResults：每页显示的数据数量PageSize
        Query dataQuery = em.createNativeQuery(dataSql);

        if(StringUtil.isNotEmpty(uuid)) {
            dataQuery.setParameter("uuid", uuid);
        }
        if(StringUtil.isNotEmpty(date)) {
            dataQuery.setParameter("date1", date);
        }

        List<?> datas = dataQuery.getResultList();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid",objects[0]);
            jsonObject.put("date", objects[1]);
            jsonObject.put("electric", objects[2]);
            jsonArray.add(jsonObject);
        }
        return getRundateNow(jsonArray,maxtime);
    }



    public static JSONArray getRundate(JSONArray retValue,String ydate){
        boolean runflag =true;
        boolean stopflag =false;
        boolean thisflag = false;
        double runDateZ = 0 ;
        Date beginDate = null;
        Date stopDate = null;
        Date thisDate =null;
        String uuidt ="";
        int index =0;

        double runDate = 0.00;
        double endDate = 0.00;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < retValue.size(); i++) {
            index++;
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            float electric = Float.parseFloat(retValue.getJSONObject(i).getString("electric"));
            System.out.println("electric="+electric);
            Date date1 = null;
            String  date = retValue.getJSONObject(i).getString("date");
            System.out.println("date="+date);
            String uuid = retValue.getJSONObject(i).getString("uuid");
            System.out.println("uuid="+uuid);
            try {
                date1 = myFmt.parse(date);
                System.out.println("date1="+date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if("".equals(uuidt)){
                uuidt=uuid;
                System.out.println("uuidt="+uuidt);
            }
            if(uuidt.equals(uuid)){
                thisflag= true;

            }else{
                thisflag= false;
            }
            float ele = electric;
            if(thisflag){
                if(runDate>mixCurrent){
                    runDateZ+=runDate;
                    runDate=0;
                    System.out.println("runDateZ="+runDateZ);
                }
            }else{
                if(stopflag){
                    Date stdate=null;
                    ydate=ydate+" "+"23:59:59";
                    System.out.println("ydate="+ydate);
                    try {
                        stdate=myFmt.parse(ydate);
                        System.out.println("stdate="+stdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endDate= ((stdate.getTime()-beginDate.getTime()));
                    System.out.println("stdate.getTime="+stdate.getTime());
                    System.out.println("beginDate.getTime="+beginDate.getTime());

                    System.out.println("第一个endDate="+endDate);

                    endDate= endDate % (1000 * 60 * 60 * 24)/(1000 * 60 * 60);
                    System.out.println("第二个endDate="+endDate);


                    BigDecimal bg = new BigDecimal(endDate);
                    endDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    System.out.println("第三个endDate="+endDate);
                    runDateZ+=endDate;
                    System.out.println("第er个runDateZ="+runDateZ);
                }
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid",uuidt);
                jsonObject.put("runDate",runDateZ);
                jsonArray.add(jsonObject);
                uuidt=uuid;
                runflag =true;
                stopflag =false;
                runDate=0;
                runDateZ= 0;
            }
            if(ele>mixCurrent&&runflag){
                beginDate=date1;
                runflag =false;
                stopflag=true;

            }
            if(ele<=mixCurrent&&stopflag){
                stopDate=date1;
                runflag=true;
                stopflag =false;
                runDate = (stopDate.getTime()-beginDate.getTime());
                runDate= (runDate % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                BigDecimal bg = new BigDecimal(runDate);
                runDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            if(thisflag){
                if(runDate>mixCurrent){
                    runDateZ+=runDate;
                    runDate=0;
                }
            }
            if(retValue.size()==index){
                if(runDate>mixCurrent){
                    runDateZ+=runDate;
                    runDate=0;
                }
                if(stopflag){
                    Date stdate=null;
                    ydate=ydate+" "+"23:59:59";
                    try {
                        stdate=myFmt.parse(ydate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endDate= ((stdate.getTime()-beginDate.getTime()));
                    endDate= endDate % (1000 * 60 * 60 * 24)/(1000 * 60 * 60);
                    BigDecimal bg = new BigDecimal(endDate);
                    endDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    runDateZ+=endDate;
                }

                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid",uuidt);
                jsonObject.put("runDate",runDateZ);
                jsonArray.add(jsonObject);
            }
            thisDate= date1;

        }
        return  jsonArray;
    }


    public static JSONArray getRundateNow(JSONArray retValue,String ydate){
        boolean runflag =true;
        boolean stopflag =false;
        boolean thisflag = false;
        double runDateZ = 0 ;
        Date beginDate = null;
        Date stopDate = null;
        Date thisDate =null;
        String uuidt ="";
        int index =0;

        double runDate = 0.00;
        double endDate = 0.00;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < retValue.size(); i++) {
            index++;
            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            float electric = Float.parseFloat(retValue.getJSONObject(i).getString("electric"));
            System.out.println("electric="+electric);
            Date date1 = null;
            String  date = retValue.getJSONObject(i).getString("date");
            String uuid = retValue.getJSONObject(i).getString("uuid");
            try {
                date1 = myFmt.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if("".equals(uuidt)){
                uuidt=uuid;
            }
            if(uuidt.equals(uuid)){
                thisflag= true;
            }else{
                thisflag= false;
            }
            float ele = electric;
            System.out.println("ele="+ele);
            if(thisflag){
                if(runDate>mixCurrent){
                    runDateZ+=runDate;
                    runDate=0;
                }
            }else{
                if(stopflag){
                    Date stdate=null;

                    ydate=ydate;
                    try {
                        stdate=myFmt.parse(ydate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endDate= ((stdate.getTime()-beginDate.getTime()));
                    endDate= endDate % (1000 * 60 * 60 * 24)/(1000 * 60 * 60);
                    System.out.println("endDate1="+endDate);
                    BigDecimal bg = new BigDecimal(endDate);
                    endDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    System.out.println("endDate1四舍五入后="+endDate);
                    runDateZ+=endDate;
                }
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid",uuidt);
                jsonObject.put("runDate",runDateZ);
                jsonArray.add(jsonObject);
                uuidt=uuid;
                runflag =true;
                stopflag =false;
                runDate=0;
                runDateZ= 0;
            }
            if(ele>mixCurrent&&runflag){
                beginDate=date1;
                runflag =false;
                stopflag=true;

            }
            if(ele<=mixCurrent&&stopflag){
                stopDate=date1;
                runflag=true;
                stopflag =false;
                runDate = (stopDate.getTime()-beginDate.getTime());
                runDate= (runDate % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                System.out.println("rundate1="+runDate);
                BigDecimal bg = new BigDecimal(runDate);
                runDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println("rundate四舍五入后="+runDate);

            }

            if(thisflag){
                if(runDate>mixCurrent){
                    runDateZ+=runDate;
                    runDate=0;
                }
            }
            if(retValue.size()==index){
                if(runDate>mixCurrent){
                    runDateZ+=runDate;
                    runDate=0;
                }
                if(stopflag){
                    Date stdate=null;
                    ydate=ydate;
                    try {
                        stdate=myFmt.parse(ydate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endDate= ((stdate.getTime()-beginDate.getTime()));
                    endDate= endDate % (1000 * 60 * 60 * 24)/(1000 * 60 * 60);
                    System.out.println("endDate2="+endDate);
                    BigDecimal bg = new BigDecimal(endDate);
                    endDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    System.out.println("endDate2四舍五入后="+endDate);
                    runDateZ+=endDate;
                }

                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid",uuidt);
                jsonObject.put("runDate",runDateZ);
                jsonArray.add(jsonObject);
            }
            thisDate= date1;

        }
        return  jsonArray;
    }



}
