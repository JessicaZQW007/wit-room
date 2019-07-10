package com.yhxc.service.temperature.impl;



import com.yhxc.entity.temperature.DayTemperature;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.repository.send.ReceiveDataRepository;
import com.yhxc.repository.temperature.DayTemperatureRepository;
import com.yhxc.service.temperature.DayTemperatureService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/18 09:05
 */
@Service
public class DayTemperatureServiceImpl implements DayTemperatureService{


    @Resource
    private DayTemperatureRepository dayTemperatureRepository;
    @Resource
    private EquipmentRepository equipmentRepository;

    @Resource
    private ReceiveDataRepository receiveDataRepository;

    @Override
    public void save(DayTemperature dayTemperature) {
        dayTemperatureRepository.save(dayTemperature);
    }


    @Override
    public JSONArray findHour(String lastDate) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";//小时
        List<?> uuidList =equipmentRepository.findUuid();//uuid
        System.out.println("uuidList="+uuidList);
        System.out.println("size="+uuidList.size());

        for (int i = 0; i < uuidList.size(); i++) {
            JSONObject jsonObject = new JSONObject();

            System.out.println("uuidList.get(i)=" + uuidList.get(i));
            String uuid = (String) uuidList.get(i);

            List<?> datas1 = receiveDataRepository.findHour(uuid, lastDate);
            System.out.println("datas1.size="+datas1.size());
            if (datas1.size() != 0) {
                String[] hourArray = hours.split(",");
                for (int b = 0; b < hourArray.length; b++) {
                    String dateHour = lastDate + " " + hourArray[b] + ":00:00";
                    String date = lastDate + " " + hourArray[b];
                    //根据UUID和时间 查询设备每个小时的温度湿度信息
                    System.out.println("dateHour=" + dateHour);
                    System.out.println("lastDate=" + lastDate);
                    System.out.println("uuid=" + uuid);
                    List<?> datas = dayTemperatureRepository.findDayHour(dateHour, lastDate, uuid);
                    Object[] object1 = (Object[]) datas.get(0);
                    if (object1[0] != null) {

                        if (object1[0] != null) {
                            jsonObject.put("uuid", uuid);
                            jsonObject.put("swhumi", object1[0]);
                            jsonObject.put("swtemp", object1[1]);
                            jsonObject.put("hjtemp", object1[2]);
                            jsonObject.put("sfktemp1", object1[3]);
                            jsonObject.put("sfktemp2", object1[4]);
                            jsonObject.put("date", date);
                            jsonArray.add(jsonObject);
                        }else{
                            jsonObject.put("uuid", uuid);
                            jsonObject.put("swhumi", 0);
                            jsonObject.put("swtemp", 0);
                            jsonObject.put("hjtemp", 0);
                            jsonObject.put("sfktemp1", 0);
                            jsonObject.put("sfktemp2", 0);
                            jsonObject.put("date", date);
                            jsonArray.add(jsonObject);
                        }
                    }
                }

            }
        }

        return jsonArray;
    }











}
