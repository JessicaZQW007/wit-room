package com.yhxc.service.analyze;


import net.sf.json.JSONArray;

/**
 * 能耗统计 Service接口
 * @author lwg
 *
 */
public interface DataReportService {

    /**
     * 数据报表中日 报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */

    public JSONArray findDayReport(String projectType, String address, String date);


    /**
     * 数据报表中月 报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */

    public JSONArray findMonthReport(String projectType, String address, String date);
    /**
     * 数据报表中年 报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */

    public JSONArray findYearReport(String projectType, String address, String date);

    /**
     * 数据报表中  季度报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    public JSONArray findQuarterReport(String projectType, String address, String date);


    /**
     * 数据报表中  季度报表
     * @param projectType 项目类别
     *  @param address 项目地址
     */
    public JSONArray findTimeReport(String projectType, String address, String allDate);

}
