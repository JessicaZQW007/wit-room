package com.yhxc.repository.count;

import com.yhxc.entity.count.DayCountElectric;
import com.yhxc.entity.count.MonthCountElectric;
import com.yhxc.entity.send.ReceiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 每天电量汇总Repository接口
 * @author yhxc 李文光
 *
 */
public interface DayCountElectricRepository extends JpaRepository<DayCountElectric, Integer>, JpaSpecificationExecutor<DayCountElectric> {


    /**
     * 查询电表的数据
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.totalpower,c.receive_date from s_receive_data c  where substr(c.receive_date ,1,10)=:date and uuid=:uuid ORDER BY c.receive_date asc;",nativeQuery = true)
    public List<?> sumHourCount(@Param("date")String date,@Param("uuid")String uuid);



    /**
     * 查询空调1的数据
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.ktccurrent1,c.receive_date from s_receive_data c  where substr(c.receive_date ,1,10)=:date and uuid=:uuid ORDER BY c.receive_date asc;",nativeQuery = true)
    public List<?> sumHourCountair1(@Param("date")String date,@Param("uuid")String uuid);

    /**
     * 查询空调2的数据
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.ktccurrent2,c.receive_date from s_receive_data c  where substr(c.receive_date ,1,10)=:date and uuid=:uuid ORDER BY c.receive_date asc;",nativeQuery = true)
    public List<?> sumHourCountair2(@Param("date")String date,@Param("uuid")String uuid);



    /*
     查询某天每小时最大的值
    */
    @Query(value = " SELECT a.uuid ,a.totalpower ,  substr(a.receive_date,1,13) from(SELECT r.uuid ,r.totalpower ,r.receive_date from  t_equipment e,s_receive_data r where\n" +
            "    e.uuid =r.uuid   and  substr(r.receive_date,1,10)=:date  ORDER BY r.receive_date DESC LIMIT 100000000000) a GROUP BY a.uuid ,substr(a.receive_date,1,13)\n", nativeQuery = true)
    public List<?> sumHourMax(@Param("date") String date);




    /*
         查询某天每小时的用电量
          */
    @Query(value = "select de.sun,de.num from (SELECT substr(substr(c.date,1,13),12,13) sun,sum(elequantity) num  from c_day_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "   and p.pname=:pname     and substr(c.date,1,10)=:date  GROUP BY c.date ) de ORDER BY de.sun asc ", nativeQuery = true)
    public List<?> findHourList(@Param("pname") String pname,@Param("date") String date);





    /*
         查询某天每小时的用电量空调1
          */
    @Query(value = "select de.sun,de.num from (SELECT substr(substr(c.date,1,13),12,13) sun,sum(elequantity1) num  from c_day_air_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id \n" +
            "             and p.pname=:pname     and substr(c.date,1,10)=:date  GROUP BY c.date ) de ORDER BY de.sun asc ", nativeQuery = true)
    public List<?> findHourListAir1(@Param("pname") String pname,@Param("date") String date);




    /*
         查询某天每小时的用电量空调2
          */
    @Query(value = "select de.sun,de.num from (SELECT substr(substr(c.date,1,13),12,13) sun,sum(elequantity2) num  from c_day_air_count_electric c,t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id " +
            "              and p.pname=:pname     and substr(c.date,1,10)=:date  GROUP BY c.date ) de ORDER BY de.sun asc  ", nativeQuery = true)
    public List<?> findHourListAir2(@Param("pname") String pname,@Param("date") String date);







    /*
       查询某天总的用电量
        */
    @Query(value = " SELECT sum(c.elequantity) from c_day_count_electric c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and  substr(c.date,1,10)=:date and p.pname=:pname     ", nativeQuery = true)
    public String findCountList(@Param("pname") String pname,@Param("date") String date);




    /**
     * 查询所有的uuid
     * @return
     */
    @Query(value="SELECT c.uuid,c.air_current1 from c_day_run_time_count c,t_equipment e   where e.uuid=c.uuid and  substr(c.date,1,10)=:date GROUP BY c.uuid ",nativeQuery = true)
    public List<?> finduuid(@Param("date")String date);




    /**
     * 查询空调1 的电流
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.air_current1,c.date from c_day_run_time_count c  where substr(c.date ,1,10)=:date and uuid=:uuid ORDER BY c.date desc;",nativeQuery = true)
    public List<?> byUuidDate1(@Param("date")String date,@Param("uuid")String uuid);

    /**
     * 查询空调2 的电流
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.air_current2,c.date from c_day_run_time_count c  where substr(c.date ,1,10)=:date and uuid=:uuid ORDER BY c.date desc;",nativeQuery = true)
    public List<?> byUuidDate2(@Param("date")String date,@Param("uuid")String uuid);





    /**
     * 查询所有的uuid 某个小时
     * @return
     */
    @Query(value="SELECT c.uuid,c.air_current1 from c_day_run_time_count c, t_equipment e  where substr(c.date,1,13)=:date  and c.uuid=e.uuid   GROUP BY c.uuid ",nativeQuery = true)
    public List<?> finduuidHour(@Param("date")String date);



    /**
     * 查询空调1 的电流某个小时(c_day_run_time_count)
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.air_current1,c.date from c_day_run_time_count c  where substr(c.date,1,13)=:date  and uuid=:uuid ORDER BY c.date desc;",nativeQuery = true)
    public List<?> byUuidDate1Hour(@Param("date")String date,@Param("uuid")String uuid);

    /**
     * 查询空调2 的电流某个小时(c_day_run_time_count)
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.air_current2,c.date from c_day_run_time_count c  where substr(c.date,1,13)=:date  and uuid=:uuid ORDER BY c.date desc;",nativeQuery = true)
    public List<?> byUuidDate2Hour(@Param("date")String date,@Param("uuid")String uuid);



    /**
     * 查询空调1 的电流某个小时(s_receive_data)
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.ktccurrent1,c.receive_date from s_receive_data c  where substr(c.receive_date,1,13)=:date  and uuid=:uuid ORDER BY c.receive_date desc;",nativeQuery = true)
    public List<?> byUuidDate1Hours(@Param("date")String date,@Param("uuid")String uuid);

    /**
     * 查询空调2 的电流某个小时(s_receive_data)
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.ktccurrent2,c.receive_date from s_receive_data c  where substr(c.receive_date,1,13)=:date  and uuid=:uuid ORDER BY c.receive_date desc;",nativeQuery = true)
    public List<?> byUuidDate2Hours(@Param("date")String date,@Param("uuid")String uuid);





    /*
 查询某天每小时最大的值（电表）
*/
    @Query(value = "SELECT r.totalpower ,  substr(r.receive_date,1,13) from  t_equipment e,s_receive_data r where\n" +
            "     e.uuid =r.uuid    and r.id in (SELECT MAX(id) from s_receive_data  where   substr(receive_date,1,10)=:date and uuid=:uuid and totalpower>0.0       GROUP BY substr(receive_date,1,13)  ) ; ", nativeQuery = true)
    public List<?> sumHourMaxbiao(@Param("date") String date,@Param("uuid") String uuid);


    /*
        查询某天每小时最小的值（电表）
       */

    @Query(value = "SELECT r.totalpower ,  substr(r.receive_date,1,13) from  t_equipment e,s_receive_data r where " +
            "   e.uuid =r.uuid    and r.id in (SELECT MIN(id) from s_receive_data  where   substr(receive_date,1,10)=:date and uuid=:uuid and totalpower>0.0 GROUP BY substr(receive_date,1,13)  )  ", nativeQuery = true)
    public List<?> sumHourMinbiao(@Param("date") String date,@Param("uuid") String uuid);

 /*
        查询某天每小时最小的值
       */

    @Query(value = " SELECT a.uuid ,a.totalpower ,  substr(a.receive_date,1,13) from(SELECT r.uuid ,r.totalpower ,r.receive_date from  t_equipment e,s_receive_data r where " +
            "    e.uuid =r.uuid   and  substr(r.receive_date,1,10)=:date and r.uuid=:uuid  ORDER BY r.receive_date ASC LIMIT 1000000000000) a GROUP BY a.uuid ,substr(a.receive_date,1,13) ", nativeQuery = true)
    public List<?> sumHourMin(@Param("date") String date,@Param("uuid") String uuid);


}
