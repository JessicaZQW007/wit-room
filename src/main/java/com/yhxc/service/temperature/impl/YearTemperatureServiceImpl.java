package com.yhxc.service.temperature.impl;



import com.yhxc.entity.temperature.YearTemperature;

import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.repository.project.ProjectRepository;

import com.yhxc.repository.temperature.MonthTemperatureRepository;
import com.yhxc.repository.temperature.YearTemperatureRepository;

import com.yhxc.service.temperature.YearTemperatureService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/25 16:25
 */
@Service
public class YearTemperatureServiceImpl implements YearTemperatureService{

    @Resource
    private YearTemperatureRepository yearTemperatureRepository;

    @Resource
    private MonthTemperatureRepository monthTemperatureRepository;

    @Resource
    private EquipmentRepository equipmentRepository;

    @Resource
    private ProjectRepository projectRepository;


    @Override
    public void save(YearTemperature yearTemperature) {
        yearTemperatureRepository.save(yearTemperature);
    }


    @Override
    public JSONArray findMonthAVG(String lastYear) throws Exception {
        JSONArray jsonArray = new JSONArray();

        List<?> uuidList = equipmentRepository.findUuid();//uuid
        System.out.println("uuidList="+uuidList);
        for (int i = 0; i < uuidList.size(); i++) {
            String uuid = (String) uuidList.get(i);

            int count = monthTemperatureRepository.findMonthCount(uuid, lastYear);
            System.out.println("count="+count);
            if(count!=0){
                String dayStr = "";
                for(int k=1;k<=12;k++){
                    int j = String.valueOf(k).length();
                    if (j == 1) {
                        dayStr = "0" + k;
                    } else {
                        dayStr = String.valueOf(k);
                    }

                    String date = lastYear + "-" + dayStr;
                    System.out.println("date="+date);
                    List<?> datas2 = yearTemperatureRepository.findMonthAVG(uuid, date);
                    Object[] objects = (Object[]) datas2.get(0);
                    JSONObject jsonObject = new JSONObject();
                    if (objects[0] != null) {

                        System.out.println("objects[0]=" + objects[0]);
                        System.out.println("objects[1]=" + objects[1]);
                        System.out.println("objects[2]=" + objects[2]);
                        System.out.println("objects[3]=" + objects[3]);
                        System.out.println("objects[4]=" + objects[4]);
                        System.out.println("objects[5]=" + objects[5]);

                        jsonObject.put("uuid", uuid);
                        jsonObject.put("hjtemp", objects[1]);
                        jsonObject.put("sfktemp1", objects[2]);
                        jsonObject.put("sfktemp2", objects[3]);
                        jsonObject.put("swhumi", objects[4]);
                        jsonObject.put("swtemp", objects[5]);
                        jsonObject.put("date", objects[0]);
                        jsonArray.add(jsonObject);

                    } else {
                        jsonObject.put("uuid", uuid);
                        jsonObject.put("hjtemp", 0);
                        jsonObject.put("sfktemp1", 0);
                        jsonObject.put("sfktemp2", 0);
                        jsonObject.put("swhumi", 0);
                        jsonObject.put("swtemp", 0);
                        jsonObject.put("date", date);
                        jsonArray.add(jsonObject);
                    }



                }

            }
        }

        return  jsonArray;
    }



    /**环境温度统计报表  室外温湿度 室内温度  回风温度
     *
     */
    @Override
    public JSONArray findSwTempHumi(String pname,String date){

        List<?> datas=null;
        int num=date.length();

        System.out.println("num="+num);

        //没选择日期默认选择当月
        if(num==7){
            //每天的温度湿度
            datas=yearTemperatureRepository.findSwTempHumiByDay(pname,date);

        }else if(num==10){
            //每小时的温度湿度
            datas=yearTemperatureRepository.findSwTempHumiByHour(pname,date);

        }else if(num==4){
            //每月的温度湿度
            datas=yearTemperatureRepository.findSwTempHumiByMonth(pname,date);

        }

        int  airNum= projectRepository.findProjectAirNum(pname);//查询项目中的空调数量

        JSONArray jsonArray = new JSONArray();

        if(airNum==2){
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                if(objects[0].toString().length()==13){
                    //小时
                    date=objects[0].toString()+":00";
                    jsonObject.put("date",date);//日期
                }else if(objects[0].toString().length()==10){
                    //每日
                    date=objects[0].toString();
                    String days = date.substring(8, 10);
                    if (days.substring(0, 1).equals("0")) {
                        days = days.substring(1, 2);
                    }

                    jsonObject.put("date",days);//日期
                }else if(objects[0].toString().length()==7){
                    date=objects[0].toString();
                    String months = date.substring(5, 7);
                    if (months.substring(0, 1).equals("0")) {
                        months = months.substring(1, 2);
                    }

                    jsonObject.put("date",months);//日期

                }
                jsonObject.put("swtemp",objects[1]);//室外温度
                jsonObject.put("swhumi",objects[2]);//室外湿度
                jsonObject.put("hjtemp",objects[3]);//室内环境温度
                jsonObject.put("sfktemp1",objects[4]);//空调1送风口温度
                jsonObject.put("sfktemp2",objects[5]);//空调2送风口温度
                jsonArray.add(jsonObject);
            }
        }else if(airNum==1){
            for (int i = 0;i<datas.size();i++){
                Object[] objects = (Object[]) datas.get(i);
                JSONObject jsonObject = new JSONObject();
                if(objects[0].toString().length()==13){
                    date=objects[0].toString()+":00";
                    jsonObject.put("date",date);//日期
                }else if(objects[0].toString().length()==10){
                    //每日
                    date=objects[0].toString();
                    String days = date.substring(8, 10);
                    if (days.substring(0, 1).equals("0")) {
                        days = days.substring(1, 2);
                    }

                    jsonObject.put("date",days);//日期
                }else if(objects[0].toString().length()==7){
                    date=objects[0].toString();
                    String months = date.substring(5, 7);
                    if (months.substring(0, 1).equals("0")) {
                        months = months.substring(1, 2);
                    }

                    jsonObject.put("date",months);//日期
                }
                jsonObject.put("swtemp",objects[1]);//室外温度
                jsonObject.put("swhumi",objects[2]);//室外湿度
                jsonObject.put("hjtemp",objects[3]);//室内环境温度
                jsonObject.put("sfktemp1",objects[4]);//空调1送风口温度
                jsonArray.add(jsonObject);
            }
        }


        return jsonArray;
    }

}
