package com.yhxc.repository.count;

import com.yhxc.entity.count.MonthAirCountElectric;
import com.yhxc.entity.count.MonthCountElectric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * 每月电量汇总Repository接口
 * @author yhxc 李文光
 *
 */
public interface MonthAirCountElectricRepository extends JpaRepository<MonthAirCountElectric, Integer>, JpaSpecificationExecutor<MonthAirCountElectric> {




    /*
   查询某天最大的用电量
  */
    @Query(value = " SELECT  a.uuid  ,a.totalpower ,substr(a.receive_date,1,10) from (SELECT r.uuid ,r.totalpower ,r.receive_date from  t_equipment e,s_receive_data r where " +
            "   e.uuid =r.uuid   and  substr(r.receive_date,1,10)=:date  ORDER BY r.receive_date DESC LIMIT 10000000000) a GROUP BY a.uuid ", nativeQuery = true)
    public List<?> sumDayMax(@Param("date") String date);



    /*
     查询某天最小的用电量
    */

    @Query(value = " SELECT  a.uuid  ,a.totalpower ,substr(a.receive_date,1,10) from (SELECT r.uuid ,r.totalpower ,r.receive_date from  t_equipment e,s_receive_data r where " +
            "   e.uuid =r.uuid   and  substr(r.receive_date,1,10)=:date  ORDER BY r.receive_date ASC LIMIT 10000000000) a GROUP BY a.uuid ", nativeQuery = true)
    public List<?> sumDayMin(@Param("date") String date);



    /*
           查询某月每天的用电量
            */

    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "          and p.pname=:pname     and substr(c.date,1,7)=:date   GROUP BY c.date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findDayList(@Param("pname") String pname, @Param("date") String date);



    /*
           查询某月总的用电量
            */
    @Query(value = " SELECT sum(c.elequantity) from c_month_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,7)=:date   ", nativeQuery = true)
    public String findCountList(@Param("pname") String pname, @Param("date") String date);






     /*
           查询某月全部项目的能耗
            */

    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "        and substr(c.date,1,7)=:date   GROUP BY c.date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findMonthCount(@Param("date") String date);




     /*
           查询项目能耗前十排名
            */

    @Query(value = " SELECT  ve.pname,ve.num    from   ( SELECT p.pname,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id  \n" +
            " and substr(c.date,1,7)=:date   GROUP BY p.pname   ) ve ORDER BY ve.num  desc  LIMIT 10 ", nativeQuery = true)
    public List<?> findProjectRank(@Param("date") String date);


    /*
         统计空调1总的用电量
                  */
    @Query(value = " SELECT sum(c.elequantity1) from c_month_air_count_electric c where  c.uuid=:uuid  ", nativeQuery = true)
    public String findAirElequantitySum1(@Param("uuid") String uuid);

    /*
         统计空调2总的用电量
                  */
    @Query(value = " SELECT sum(c.elequantity2) from c_month_air_count_electric c where  c.uuid=:uuid  ", nativeQuery = true)
    public String findAirElequantitySum2(@Param("uuid") String uuid);



    /*
         查询某日空调1的用电量
          */
    @Query(value = " SELECT c.elequantity1 from c_month_air_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,10)=:date   ", nativeQuery = true)
    public String findDayAirSum1(@Param("pname") String pname, @Param("date") String date);


    /*
             查询某日空调2的用电量
             */
    @Query(value = " SELECT c.elequantity2 from c_month_air_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,10)=:date   ", nativeQuery = true)
    public String findDayAirSum2(@Param("pname") String pname, @Param("date") String date);



    /*
       查询某月空调1的用电量
        */
    @Query(value = " SELECT sum(c.elequantity1) from c_month_air_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,7)=:date   ", nativeQuery = true)
    public String findMonthAirSum1(@Param("pname") String pname, @Param("date") String date);


    /*
             查询某月空调2的用电量
             */
    @Query(value = " SELECT sum(c.elequantity2) from c_month_air_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,7)=:date   ", nativeQuery = true)
    public String findMonthAirSum2(@Param("pname") String pname, @Param("date") String date);


}
