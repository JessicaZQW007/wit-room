package com.yhxc.repository.count;

import com.yhxc.entity.count.MonthRunTimeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * 每日空调的运行时间Repository接口
 * @author yhxc 李文光
 *
 */
public interface MonthRunTimeCountRepository extends JpaRepository<MonthRunTimeCount, Integer>, JpaSpecificationExecutor<MonthRunTimeCount> {



    /*
            查询空调1的运行 根据项目名 和空调时间
             */
    @Query(value = " SELECT  substr(substr(d.date,1,10),9,10), d.air_run_time1   FROM c_month_run_time_count d  ,p_project p,t_equipment e    where \n" +
            "           e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,7)=:date  ORDER BY d.date asc  ", nativeQuery = true)
    public List<?> findMonthAirRunTime1(@Param("pname") String pname,@Param("date") String date);


    /*
              查询空调2的运行 根据项目名 和空调时间
               */
    @Query(value = "SELECT  substr(substr(d.date,1,10),9,10), d.air_run_time2  FROM c_month_run_time_count d  ,p_project p,t_equipment e    where " +
            "                   e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,7)=:date  ORDER BY d.date asc ", nativeQuery = true)
    public List<?> findMonthAirRunTime2(@Param("pname") String pname,@Param("date") String date);



    /*
            查询空调1的制冷运行 根据项目名 和空调时间
             */
    @Query(value = " SELECT  substr(substr(d.date,1,10),9,10), d.air_run_cool_time1   FROM c_month_run_time_count d  ,p_project p,t_equipment e    where \n" +
            "           e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,7)=:date  ORDER BY d.date asc  ", nativeQuery = true)
    public List<?> findMonthAirRunTime1Cool(@Param("pname") String pname,@Param("date") String date);


    /*
              查询空调2的制冷运行 根据项目名 和空调时间
               */
    @Query(value = "SELECT  substr(substr(d.date,1,10),9,10), d.air_run_cool_time2  FROM c_month_run_time_count d  ,p_project p,t_equipment e    where " +
            "                   e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,7)=:date  ORDER BY d.date asc ", nativeQuery = true)
    public List<?> findMonthAirRunTime2Cool(@Param("pname") String pname,@Param("date") String date);




    /*
         统计空调1某个月的运行
                  */
    @Query(value = " SELECT e.uuid,substr(e.date,1,7), sum(e.air_run_time1) from c_month_run_time_count e  where substr(e.date,1,7)=:date GROUP BY e.uuid", nativeQuery = true)
    public List<?> sumMonthAirRunTime1(@Param("date") String date);



    /*
         统计空调2某个月的运行
                  */
    @Query(value = " SELECT e.uuid,substr(e.date,1,7), sum(e.air_run_time2) from c_month_run_time_count e  where substr(e.date,1,7)=:date GROUP BY e.uuid", nativeQuery = true)
    public List<?> sumMonthAirRunTime2(@Param("date") String date);

    /*
       统计空调1制冷某个月的运行
                    */
    @Query(value = " SELECT e.uuid,substr(e.date,1,7), sum(e.air_run_cool_time1) from c_month_run_time_count e  where substr(e.date,1,7)=:date GROUP BY e.uuid", nativeQuery = true)
    public List<?> sumMonthAirRunTimeCool1(@Param("date") String date);



    /*
         统计空调2制冷某个月的运行
                  */
    @Query(value = " SELECT e.uuid,substr(e.date,1,7), sum(e.air_run_cool_time2) from c_month_run_time_count e  where substr(e.date,1,7)=:date GROUP BY e.uuid", nativeQuery = true)
    public List<?> sumMonthAirRunTimeCool2(@Param("date") String date);



    /*
       统计空调1某天的运行时间
                */
    @Query(value = "SELECT c.air_run_time1 from c_month_run_time_count c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,10)=:date        ", nativeQuery = true)
    public String finddayAirRunTime1(@Param("pname") String pname,@Param("date") String date);

    /*
          统计空调2某天的运行时间
                   */
    @Query(value = " SELECT c.air_run_time2 from c_month_run_time_count c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,10)=:date ", nativeQuery = true)
    public String finddayAirRunTime2(@Param("pname") String pname,@Param("date") String date);


    /*
         统计空调1某月的总运行时间
                  */
    @Query(value = "SELECT  sum(c.air_run_time1)  from c_month_run_time_count c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,7)=:date        ", nativeQuery = true)
    public String findMonthAirRun1(@Param("pname") String pname,@Param("date") String date);

    /*
          统计空调2某月的总运行时间
                   */
    @Query(value = " SELECT sum(c.air_run_time2) from c_month_run_time_count c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,7)=:date ", nativeQuery = true)
    public String findMonthAirRun2(@Param("pname") String pname,@Param("date") String date);



    /*
       统计空调1总的运行时间
                */
    @Query(value = "SELECT sum(c.air_run_time1) from c_month_run_time_count c where  c.uuid=:uuid   ", nativeQuery = true)
    public String findAirSum1(@Param("uuid") String uuid);


    /*
       统计空调2总的运行时间
                */
    @Query(value = "SELECT sum(c.air_run_time2) from c_month_run_time_count c where  c.uuid=:uuid   ", nativeQuery = true)
    public String findAirSum2(@Param("uuid") String uuid);
}
