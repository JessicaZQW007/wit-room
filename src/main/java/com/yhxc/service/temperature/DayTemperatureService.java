package com.yhxc.service.temperature;


import com.yhxc.entity.temperature.DayTemperature;
import net.sf.json.JSONArray;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/17 17:31
 */
public interface DayTemperatureService {

    //添加记录设备的温度湿度信息
    public void save(DayTemperature dayTemperature);

    //查询前一天的温度湿度信息
    public JSONArray findHour(String lastDate) throws Exception;


}
