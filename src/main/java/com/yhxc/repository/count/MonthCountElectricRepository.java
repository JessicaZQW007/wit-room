package com.yhxc.repository.count;

import com.yhxc.entity.company.Build;
import com.yhxc.entity.count.MonthCountElectric;
import com.yhxc.entity.information.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 每月电量汇总Repository接口
 * @author yhxc 李文光
 *
 */
public interface MonthCountElectricRepository extends JpaRepository<MonthCountElectric, Integer>, JpaSpecificationExecutor<MonthCountElectric> {




    /*
       根据天统计 用电量
      */

    @Query(value = "SELECT e.uuid,substr(e.date,1,10), sum(e.elequantity)from c_day_count_electric e  where substr(e.date,1,10)=:date GROUP BY e.uuid", nativeQuery = true)
    public List<?> sumDay(@Param("date") String date);





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
     查询某天最大的用电量
    */
    @Query(value = " SELECT a.totalpower  from (SELECT r.uuid ,r.totalpower ,r.receive_date from s_receive_data r where \n" +
            "              substr(r.receive_date,1,10)=:date AND r.uuid=:uuid   ORDER BY r.receive_date DESC LIMIT 1) a ", nativeQuery = true)
    public String sumDayMaxtotalpower(@Param("date") String date,@Param("uuid") String uuid);


    /*
  查询某天最大的用电量 date ,uuid查询
 */
    @Query(value = " SELECT  a.totalpower from (SELECT r.uuid ,r.totalpower ,r.receive_date from  t_equipment e,s_receive_data r where " +
            "   e.uuid =r.uuid   and  substr(r.receive_date,1,10)=:date and r.uuid=:uuid  ORDER BY r.receive_date DESC LIMIT 10000000000) a  LIMIT 1  ", nativeQuery = true)
    public String sumDayMaxuuid(@Param("date") String date,@Param("uuid") String uuid);



    /*
查询某天最小的用电量date ,uuid查询
*/
    @Query(value = " SELECT a.totalpower  from (SELECT r.uuid ,r.totalpower ,r.receive_date from  t_equipment e,s_receive_data r where " +
            "   e.uuid =r.uuid   and  substr(r.receive_date,1,10)=:date and r.uuid=:uuid and r.totalpower!=0  ORDER BY r.receive_date asc LIMIT 10000000000) a LIMIT 1 ", nativeQuery = true)
    public String sumDayMinuuid(@Param("date") String date,@Param("uuid") String uuid);









    /*
           查询某月每天的用电量
            */

    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "          and p.pname=:pname     and substr(c.date,1,7)=:date   GROUP BY c.date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findDayList(@Param("pname") String pname,@Param("date") String date);





    /*
           查询某段时间每天的用电量
            */

    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(c.date,1,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "          and p.pname=:pname     and (substr(c.date,1,10) BETWEEN :startDate  and :endDate)   GROUP BY c.date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findTimeList(@Param("pname") String pname,@Param("startDate") String startDate,@Param("endDate") String endDate);


    /*
           查询某月某天的用电量
            */
    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "          and p.pname=:pname     and substr(c.date,1,10)=:date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findByDay(@Param("pname") String pname,@Param("date") String date);


       /*
           查询某月每天的用电量
            */

    @Query(value = "SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity1) num from c_month_air_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "     and p.pname=:pname     and substr(c.date,1,7)=:date   GROUP BY c.date  )  de ORDER BY de.sun asc ", nativeQuery = true)
    public List<?> findDayListAir1(@Param("pname") String pname,@Param("date") String date);



     /*
           查询某月每天的用电量
            */

    @Query(value = "SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity2) num from c_month_air_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "     and p.pname=:pname     and substr(c.date,1,7)=:date   GROUP BY c.date  )  de ORDER BY de.sun asc ", nativeQuery = true)
    public List<?> findDayListAir2(@Param("pname") String pname,@Param("date") String date);











    /*
           查询某月总的用电量
            */
    @Query(value = " SELECT sum(c.elequantity) from c_month_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,7)=:date   ", nativeQuery = true)
    public String findCountList(@Param("pname") String pname,@Param("date") String date);






     /*
           查询某月全部项目的能耗
            *//*

    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "        and substr(c.date,1,7)=:date   GROUP BY c.date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findMonthCount(@Param("date") String date);*/


    /*
           查询某月全部项目的能耗
            */

    @Query(value = " SELECT de.sun,de.num  from (  SELECT substr(substr(c.date,1,10),9,10) sun,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p,t_unit u where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "        and substr(c.date,1,7)=:date and e.unit_id=u.id and p.unit_id=u.id " +
            "and  if(:pId !='', u.p_id=:pId, 1 = 1) " +
            "and  if(:unitId !='', e.unit_id=:unitId, 1 = 1)   GROUP BY c.date  )  de ORDER BY de.sun asc", nativeQuery = true)
    public List<?> findMonthCount(@Param("date") String date,@Param("pId") String pId,@Param("unitId") String unitId);




     /*
           查询项目能耗前十排名
            */

    @Query(value = " SELECT  ve.pname,ve.num    from   ( SELECT p.pname,sum(elequantity) num from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id  \n" +
            " and substr(c.date,1,7)=:date   GROUP BY p.pname   ) ve ORDER BY ve.num  desc  LIMIT 10 ", nativeQuery = true)
    public List<?> findProjectRank(@Param("date") String date);


 /*
        查询终端用户的项目能耗排名
            */

    @Query(value = " SELECT  ve.pname,ve.num,ve.address    from   ( SELECT p.pname,sum(elequantity) num,p.address from c_month_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "  and substr(c.date,1,7)=:date  " +
            "  and  if(:address !='', p.address like CONCAT('%',:address,'%'), 1 = 1) " +
            " and  p.id in (  SELECT  project_id from   t_user_project where user_id=:userId)  GROUP BY p.pname   ) ve ORDER BY ve.num  desc  ", nativeQuery = true)
    public List<?> findProjectRankAPP(@Param("date") String date,@Param("userId") int userId,@Param("address") String address);



}
