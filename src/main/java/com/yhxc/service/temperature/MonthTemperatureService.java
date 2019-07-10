package com.yhxc.service.temperature;


import com.yhxc.entity.temperature.MonthTemperature;
import net.sf.json.JSONArray;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/24 17:05
 */
public interface MonthTemperatureService {

    //添加记录设备的温度湿度信息
    public void save(MonthTemperature monthTemperature);

    //查询每天温度湿度的平均值
    public JSONArray findDayAVG(String lastDate) throws Exception;


}
