package com.yhxc.repository.temperature;


import com.yhxc.entity.temperature.DayTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 每小时设备温度湿度记录Repository接口
 * @author 陈霖炎
 *
 */
public interface DayTemperatureRepository extends JpaRepository<DayTemperature, Integer>,JpaSpecificationExecutor<DayTemperature> {






    /**
     *查询每天每小时最接近每个整点的数据
     * @return
     */
    @Query(value = "SELECT swhumi,swtemp,hjtemp,sfktemp1,sfktemp2, abs(UNIX_TIMESTAMP(receive_date)-UNIX_TIMESTAMP(:date)) as min  from s_receive_data  WHERE receive_date LIKE CONCAT('%',:receiveDate,'%') and uuid=:uuid ORDER BY min asc limit 1 ", nativeQuery = true)
    public List<?> findDayHour(@Param("date")String date, @Param("receiveDate")String receiveDate, @Param("uuid")String uuid);



    /**
     *查询当月数据
     * @return
     */
    @Query(value = "select count(*) from t_day_temperature where uuid=:uuid   and date LIKE CONCAT('%',:date,'%') ", nativeQuery = true)
    public int findMonthCount(@Param("uuid")String uuid, @Param("date")String date);

}
