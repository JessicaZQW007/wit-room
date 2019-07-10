package com.yhxc.service.temperature.impl;




import com.yhxc.entity.temperature.MonthTemperature;
import com.yhxc.repository.equipment.EquipmentRepository;

import com.yhxc.repository.send.ReceiveDataRepository;
import com.yhxc.repository.temperature.DayTemperatureRepository;
import com.yhxc.repository.temperature.MonthTemperatureRepository;

import com.yhxc.service.temperature.MonthTemperatureService;
import com.yhxc.utils.DateCont;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/18 09:05
 */
@Service
public class MonthTemperatureServiceImpl implements MonthTemperatureService{


    @Resource
    private MonthTemperatureRepository monthTemperatureRepository;
    @Resource
    private EquipmentRepository equipmentRepository;

    @Resource
    private ReceiveDataRepository receiveDataRepository;

    @Resource
    private DayTemperatureRepository dayTemperatureRepository;


    @Override
    public void save(MonthTemperature monthTemperature) {
        monthTemperatureRepository.save(monthTemperature);
    }

    @Override
    public JSONArray findDayAVG(String lastDate) throws Exception {
        JSONArray jsonArray = new JSONArray();
        int year = Integer.parseInt(lastDate.substring(0, 4));//年
        String months = lastDate.substring(5, 7);
        if (months.substring(0, 1).equals("0")) {
            months = months.substring(1, 2);
        }
        int month = Integer.parseInt(months);//月
        DateCont dateCont = new DateCont();
        int dayNum = dateCont.getDaysByYearMonth(year, month);//当月天数

        List<?> uuidList = equipmentRepository.findUuid();//uuid
        System.out.println("uuidList="+uuidList);

        for (int i = 0; i < uuidList.size(); i++) {
            String uuid = (String) uuidList.get(i);

            int count = dayTemperatureRepository.findMonthCount(uuid, lastDate);
            if(count!=0) {
                String dayStr = "";
                for (int k = 1; k <= dayNum; k++) {
                    int j = String.valueOf(k).length();
                    if (j == 1) {
                        dayStr = "0" + k;
                    } else {
                        dayStr = String.valueOf(k);
                    }
                    String date = lastDate + "-" + dayStr;
                    System.out.println("uuid=" + uuid);
                    System.out.println("date=" + date);

                    List<?> datas2 = monthTemperatureRepository.findDayAVG(uuid, date);
                    System.out.println("datas2.size=" + datas2.size());

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

        return jsonArray;

    }









}
