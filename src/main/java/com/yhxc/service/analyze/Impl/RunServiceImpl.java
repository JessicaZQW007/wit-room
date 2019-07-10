package com.yhxc.service.analyze.Impl;

import com.yhxc.entity.count.DayRunTimeCount;
import com.yhxc.repository.analyze.RunRepository;
import com.yhxc.service.analyze.RunService;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RunServiceImpl implements RunService {
    @Resource
    private RunRepository runRepository;





    public static JSONArray getRundate(JSONArray retValue,String ydate){
        boolean runflag =true;  //运行状态判断
        boolean stopflag =false;//停机状态判断
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
            float ele = electric;  //当前电流
            if(thisflag){
                if(runDate>0.00){
                    runDateZ+=runDate;
                    runDate=0;
                }
            }else{
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
                uuidt=uuid;
                runflag =true;
                stopflag =false;
                runDate=0;
                runDateZ= 0;
            }
            if(ele>0.00&&runflag){
                beginDate=date1;
                runflag =false;
                stopflag=true;

            }
            if(ele<=0.00&&stopflag){
                stopDate=date1;
                runflag=true;
                stopflag =false;
                runDate = (stopDate.getTime()-beginDate.getTime());//关机时间减去运行时间
                runDate= (runDate % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//转化成小时
                BigDecimal bg = new BigDecimal(runDate);
                runDate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            if(thisflag){
                if(runDate>0.00){
                    runDateZ+=runDate;
                    runDate=0;
                }
            }
            if(retValue.size()==index){
                if(runDate>0.00){
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

}




