package com.yhxc.service.count;


import com.yhxc.entity.count.DayRunTimeCount;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 日统计表   空调运行情况Service接口
 * @author yhxc 李文光
 *
 */
public interface DayRunTimeCountService {

    /**
     * 新增或者修改信息
     * @param dayRunTimeCount
     */
    public void save(DayRunTimeCount dayRunTimeCount);

    /**
     * 查询某天空调的运行情况
     * @param pname
     * @param date
     */
    public JSONObject findAirRunTime(String pname, String date);


    public JSONArray countDayRunTime1(String date);


    public JSONArray countDayRunTime2(String date);


    public JSONArray countDayRuncoolTime1(String date);

    public JSONArray countDayRuncoolTime2(String date);



    /**   date uuid
        统计空调运行时间
     airNum 第几台空调
                 */
    public JSONArray countAirDayRunTime(String date,String uuid,int airNum);




    /**   date uuid
     统计空调制冷运行时间  airNum 第几台空调
     */
    public JSONArray countDayRunTimecool(String date,String uuid,int airNum);





}
