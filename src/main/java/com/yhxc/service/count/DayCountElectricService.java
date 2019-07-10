package com.yhxc.service.count;


import com.yhxc.entity.count.DayCountElectric;
import net.sf.json.JSONArray;

/**
 * 每天电量汇总Service接口
 * @author yhxc 李文光
 *
 */
public interface DayCountElectricService {

    /**
     * 新增或者修改信息
     * @param dayCountElectric
     */
    public void save(DayCountElectric dayCountElectric);


    /**
     * 统计某天每小时的汇总(电表)
     * @param date
     */
    public void sumHourCount(String date);

    /**
     * 统计某天每小时的汇总（电表）
     * @param date
     */
    public JSONArray sumHourCountbiao(String date,String uuid);



    /**
     * 统计某天电表的汇总（电表）
     * @param date
     */
    public JSONArray sumHourCount(String date,String uuid) throws Exception ;


    /**
     * 统计某天空调1的汇总（电表）
     * @param date
     */
    public JSONArray sumHourCountair1(String date,String uuid) throws Exception ;


    /**
     * 统计某天电表的汇总（电表）
     * @param date
     */
    public JSONArray sumHourCountair2(String date,String uuid) throws Exception ;





    /**
     * 处理电流转化成用电量空调1(查询 c_day_run_time_count 表)
     *
     */
    public JSONArray findOne(String date) throws Exception;

    /**
     * 处理电流转化成用电量空调2(查询 c_day_run_time_count 表)
     *
     */
    public JSONArray findTwo(String date) throws Exception;



    /**
     * 处理电流转化成用电量空调1 每小时
     *
     */
    public JSONArray findOneHour(String date) throws Exception;

    /**
     * 处理电流转化成用电量空调2 每小时
     *
     */
    public JSONArray findTwoHour(String date) throws Exception;



    /**
     * 处理电流转化成用电量空调1 每小时 uuid查询(查询 c_day_run_time_count 表)
     *
     */
    public JSONArray findOneHouruuidDay(String date,String uuid) throws Exception;

    /**
     * 处理电流转化成用电量空调2 每小时  (查询 c_day_run_time_count 表)
     *
     */
    public JSONArray findTwoHouruuidDay(String date,String uuid) throws Exception;







    /**
     * 处理电流转化成用电量空调1 每小时 uuid查询(查询 s_receive_data 表)
     *
     */
    public JSONArray findOneHouruuid(String date,String uuid) throws Exception;

    /**
     * 处理电流转化成用电量空调2 每小时  (查询 s_receive_data 表)
     *
     */
    public JSONArray findTwoHouruuid(String date,String uuid) throws Exception;

}
