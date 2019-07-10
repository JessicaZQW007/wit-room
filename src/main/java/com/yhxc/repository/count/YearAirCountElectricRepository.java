package com.yhxc.repository.count;

import com.yhxc.entity.count.YearAirCountElectric;
import com.yhxc.entity.count.YearCountElectric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * 年电量汇总Repository接口
 * @author yhxc 李文光
 *
 */
public interface YearAirCountElectricRepository extends JpaRepository<YearAirCountElectric, Integer>, JpaSpecificationExecutor<YearAirCountElectric> {



    /*
       根据月统计 空调1 的总用电量
      */

    @Query(value = "SELECT e.uuid,substr(e.date,1,7), sum(e.elequantity1)from c_month_air_count_electric e  where substr(e.date,1,7)=:date GROUP BY e.uuid  ", nativeQuery = true)

    public List<?> sumAirMonth1(@Param("date") String date);


 /*
       根据月统计 空调1 的总用电量
      */

    @Query(value = "SELECT e.uuid,substr(e.date,1,7), sum(e.elequantity2)from c_month_air_count_electric e  where substr(e.date,1,7)=:date GROUP BY e.uuid  ", nativeQuery = true)

    public List<?> sumAirMonth2(@Param("date") String date);





}
