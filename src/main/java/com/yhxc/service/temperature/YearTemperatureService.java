package com.yhxc.service.temperature;



import com.yhxc.entity.temperature.YearTemperature;

import net.sf.json.JSONArray;

/**
 * @Author: 陈霖炎
 * @Date: 2019/6/25 16:24
 */
public interface YearTemperatureService {

    //添加记录设备的温度湿度信息
    public void save(YearTemperature yearTemperature);


    //查询每月温度湿度的平均值
    public JSONArray findMonthAVG(String lastYear) throws Exception;

    //环境温度统计报表  室外温湿度 室内温度 回风温度
    public JSONArray findSwTempHumi(String Pname,String date);


}
