package com.yhxc.service.analyze;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 能耗统计 Service接口
 * @author lwg
 *
 */
public interface EnergyService {
    /**
     *
     查询某天每小时的用电量

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findHourList(String pname,String date);



    /**
     *
     查询某天每小时的用电量(空调1)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findHourListAir1(String pname,String date);



    /**
     *
     查询某天每小时的用电量(空调2)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findHourListAir2(String pname,String date);







    /**
     *
     查询某天总的用电量，总费用，空调1 用电量 ，空调2 用电量
     * @param date
     * @return
     */
    public JSONObject findDaycount(String pname,String date);



    /**
     *
     查询某月每天的用电量

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findDayList(String pname,String date);



    /**
     *
     查询某月每天的用电量(app)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findDayListApp(String pname,String date);



    /**
     *
     查询某月每天的用电量(空调1)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findDayListAir1(String pname,String date);


    /**
     *
     查询某月每天的用电量(空调2)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findDayListAir2(String pname,String date);








    /**
     *
     查询某月总的用电量，总费用，空调1 用电量 ，空调2 用电量
     * @param date
     * @return
     */
    public JSONObject findMonthcount(String pname,String date);



    /**
     *
     查询某年每月的用电量

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findMonthList(String pname,String date);





    /**
     *
     查询某年每月的用电量(空调1)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findMonthList1(String pname,String date);


    /**
     *
     查询某年每月的用电量(空调2)

     * @param pname
     * @param date
     * @return
     */
    public JSONArray findMonthList2(String pname,String date);



    /**
     *
     查询某月总的用电量，总费用，空调1 用电量 ，空调2 用电量
     * @param date
     * @return
     */
    public JSONObject findYearcount(String pname,String date);


    /**
     *
     * @param pnames 项目名
     * @param dates  时间
     * @param quarter  季度
     * @param year  年
     * @return
     */
    public JSONArray findtoList(String pnames,String dates, String quarter, String year );


    /**
     *
     查询某月全部项目的能耗
     * @param date
     * @return
     */
    public JSONArray findMonthCount(String date,String pId,String unitId);

    /**
     *
     查询本月项目能耗前十排名
     * @param date
     * @return
     */
    public JSONArray findProjectRank(String date);


    /**
     *
     查询本月项目能耗前十排名
     * @param date
     * @return
     */
    public JSONArray findProjectRankAPP(String date, int userId,String address);




}
