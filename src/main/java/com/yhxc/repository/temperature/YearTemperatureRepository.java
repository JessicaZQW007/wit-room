package com.yhxc.repository.temperature;


import com.yhxc.entity.temperature.MonthTemperature;
import com.yhxc.entity.temperature.YearTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 每月设备温度湿度记录Repository接口
 * @author 陈霖炎
 *
 */
public interface YearTemperatureRepository extends JpaRepository<YearTemperature, Integer>,JpaSpecificationExecutor<YearTemperature> {




    /**
     *查询每月温度湿度的平均值
     * @return
     */
    @Query(value = "select  substr(date,1,7) ,ROUND(AVG(hjtemp),2) as hjtempAVG,ROUND(AVG(sfktemp1),2) AS sfktemp1AVG,ROUND(AVG(sfktemp2),2) AS sfktemp2AVG,ROUND(AVG(swhumi),2) AS swhumiAVG,ROUND(AVG(swtemp),2) AS swtempAVG from t_month_temperature where uuid=:uuid and date LIKE CONCAT('%',:date,'%') ", nativeQuery = true)
    public List<?> findMonthAVG(@Param("uuid") String uuid, @Param("date") String date);


    /**
     *环境温度报表 室外温湿度 室内温度 回风温度 选择天按小时统计
     * @return  hjtemp
     */
    @Query(value = "select d.date,ROUND(d.swtemp,2),ROUND(d.swhumi,2),ROUND(d.hjtemp,2),ROUND(d.sfktemp1,2),ROUND(d.sfktemp2,2)  " +
            "from p_project p,t_equipment e,t_day_temperature d " +
            "where d.uuid=e.uuid and p.eq_id=e.id " +
            "and if(:pname != '' , p.pname=:pname , 1 = 1) " +
            "and if(:date != '' , d.date LIKE CONCAT('%',:date,'%') , 1 = 1) ", nativeQuery = true)
    public List<?> findSwTempHumiByHour(@Param("pname") String pname, @Param("date") String date);



    /**
     *环境温度报表 室外温湿度 室内温度 回风温度 选择月按天统计
     * @return
     */
    @Query(value = "select d.date,ROUND(d.swtemp,2),ROUND(d.swhumi,2),ROUND(d.hjtemp,2),ROUND(d.sfktemp1,2),ROUND(d.sfktemp2,2)  " +
            "from p_project p,t_equipment e,t_month_temperature d " +
            "where d.uuid=e.uuid and p.eq_id=e.id " +
            "and if(:pname != '' , p.pname=:pname , 1 = 1) " +
            "and if(:date != '' , d.date LIKE CONCAT('%',:date,'%') , 1 = 1) ", nativeQuery = true)
    public List<?> findSwTempHumiByDay(@Param("pname") String pname, @Param("date") String date);



    /**
     *环境温度报表 室外温湿度 室内温度 回风温度 选择年按月统计
     * @return
     */
    @Query(value = "select d.date,ROUND(d.swtemp,2),ROUND(d.swhumi,2),ROUND(d.hjtemp,2),ROUND(d.sfktemp1,2),ROUND(d.sfktemp2,2)  " +
            "from p_project p,t_equipment e,t_year_temperature d " +
            "where d.uuid=e.uuid and p.eq_id=e.id " +
            "and if(:pname != '' , p.pname=:pname , 1 = 1) " +
            "and if(:date != '' , d.date LIKE CONCAT('%',:date,'%') , 1 = 1) ", nativeQuery = true)
    public List<?> findSwTempHumiByMonth(@Param("pname") String pname, @Param("date") String date);


}
