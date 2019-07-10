package com.yhxc.service.count;


import com.yhxc.entity.count.DayAirCountElectric;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 每日空调电量汇总Service接口
 * @author yhxc 李文光
 *
 */
public interface DayAirCountElectricService {

    /**
     * 新增或者修改信息
     * @param dayAirCountElectric
     */
    public void save(DayAirCountElectric dayAirCountElectric);


    /**
     * 处理电流转化成用电量空调1(查询 s_receive_data 表)
     *
     */
    public Double findOne(String date, String uuid) throws Exception;

/**
 * 处理电流转化成用电量空调2(查询 s_receive_data 表)
 *
 */
    public Double findTwo(String date,String uuid) throws Exception;


}
