package com.yhxc.repository.temperature;


import com.yhxc.entity.temperature.DayTemperature;
import com.yhxc.entity.temperature.MonthTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.util.List;

/**
 * 每天设备温度湿度记录Repository接口
 * @author 陈霖炎
 *
 */
public interface MonthTemperatureRepository extends JpaRepository<MonthTemperature, Integer>,JpaSpecificationExecutor<MonthTemperature> {






    /**
     *查询每天温度湿度的平均值
     * @return
     */
    @Query(value = "select  substr(date,1,10) ,ROUND(AVG(hjtemp),2) as hjtempAVG,ROUND(AVG(sfktemp1),2) AS sfktemp1AVG,ROUND(AVG(sfktemp2),2) AS sfktemp2AVG,ROUND(AVG(swhumi),2) AS swhumiAVG,ROUND(AVG(swtemp),2) AS swtempAVG from t_day_temperature where uuid=:uuid and date LIKE CONCAT('%',:date,'%') ", nativeQuery = true)
    public List<?> findDayAVG(@Param("uuid") String uuid, @Param("date") String date);


    /**
     *查询当年数据
     * @return
     */
    @Query(value = "select count(*) from t_month_temperature where uuid=:uuid   and date LIKE CONCAT('%',:date,'%') ", nativeQuery = true)
    public int findMonthCount(@Param("uuid")String uuid, @Param("date")String date);

}
