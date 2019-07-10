package com.yhxc.repository.count;

import com.yhxc.entity.company.Build;
import com.yhxc.entity.count.YearCountElectric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 年电量汇总Repository接口
 * @author yhxc 李文光
 *
 */
public interface YearCountElectricRepository extends JpaRepository<YearCountElectric, Integer>, JpaSpecificationExecutor<YearCountElectric> {



    /*
       根据月统计 月的用电量
      */

    @Query(value = "SELECT e.uuid,substr(e.date,1,7), sum(e.elequantity)from c_month_count_electric e  where substr(e.date,1,7)=:date GROUP BY e.uuid ", nativeQuery = true)

    public List<?> sumMonth(@Param("date") String date);



    /*
         查询某年每月的用电量
          */
    @Query(value = "SELECT * from (  SELECT substr(substr(c.date,1,7),6,7) dates,sum(elequantity) from c_year_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "                 and p.pname=:pname     and substr(c.date,1,4)=:date   GROUP BY c.date  )  de ORDER BY de.dates asc ", nativeQuery = true)
    public List<?> findMonthList(@Param("pname") String pname,@Param("date") String date);



    /*
           查询某年每月的用电量
            */
    @Query(value = "SELECT * from (  SELECT substr(substr(c.date,1,7),6,7) dates,sum(elequantity1) from c_year_air_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "           and p.pname=:pname    and substr(c.date,1,4)=:date    GROUP BY c.date  )  de ORDER BY de.dates asc ", nativeQuery = true)
    public List<?> findMonthListAir1(@Param("pname") String pname,@Param("date") String date);



    /*
          查询某年每月的用电量
           */
    @Query(value = "SELECT * from (  SELECT substr(substr(c.date,1,7),6,7) dates,sum(elequantity2) from c_year_air_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "           and p.pname=:pname    and substr(c.date,1,4)=:date    GROUP BY c.date  )  de ORDER BY de.dates asc ", nativeQuery = true)
    public List<?> findMonthListAir2(@Param("pname") String pname,@Param("date") String date);



    /*
        查询某年总的用电量
         */
    @Query(value = " SELECT sum(c.elequantity) from c_year_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,4)=:date   ", nativeQuery = true)
    public String findCountList(@Param("pname") String pname,@Param("date") String date);




    /*
         查询某年总的用电量(空调1)
          */
    @Query(value = "  SELECT sum(c.elequantity1) from c_year_air_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,4)=:date   ", nativeQuery = true)
    public String findCountListAir1(@Param("pname") String pname,@Param("date") String date);




    /*
            查询某年总的用电量(空调2)
             */
    @Query(value = "  SELECT sum(c.elequantity2) from c_year_air_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,4)=:date   ", nativeQuery = true)
    public String findCountListAir2(@Param("pname") String pname,@Param("date") String date);









    /*
           查询某年每季度的用电量
            */
    @Query(value = "SELECT * from (  SELECT quarter(CONCAT(c.date,\"-01\") ) dates,sum(elequantity) from c_year_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "                    and p.pname=:pname     and substr(c.date,1,4)=:date   GROUP BY dates )  de ORDER BY de.dates asc", nativeQuery = true)
    public List<?> findQuarterList(@Param("pname") String pname,@Param("date") String date);




    /*
       根据项目名 ，时间查询 项目的用电量
         */
    @Query(value = " SELECT sum(c.elequantity) from c_year_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "                      and p.pname=:pname     and c.date=:date  ", nativeQuery = true)
    public Float findtoList(@Param("pname") String pname,@Param("date") String date);

    /*
       查询某年  , 季度的用电量
        */
    @Query(value = " SELECT sum(elequantity) from c_year_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "                      and p.pname=:pname     and substr(c.date,1,4)=:year   and  quarter(CONCAT(c.date,\"-01\") )=:quarter   ", nativeQuery = true)
    public Float findQuartertoList(@Param("pname") String pname,@Param("year") String year,@Param("quarter") String quarter);



}
