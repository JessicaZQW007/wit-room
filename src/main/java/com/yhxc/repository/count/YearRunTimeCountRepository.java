package com.yhxc.repository.count;

import com.yhxc.entity.count.MonthRunTimeCount;
import com.yhxc.entity.count.YearRunTimeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * 每月空调的运行时间Repository接口
 * @author yhxc 李文光
 *
 */
public interface YearRunTimeCountRepository extends JpaRepository<YearRunTimeCount, Integer>, JpaSpecificationExecutor<YearRunTimeCount> {



    /*
            查询空调1的用电量 根据项目名 和空调时间
             */
    @Query(value = "  SELECT  substr(substr(d.date,1,7),6,7), d.air_run_time1   FROM c_year_run_time_count d  ,p_project p,t_equipment e    where \n" +
            "   e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,4)=:date ORDER BY d.date asc   ", nativeQuery = true)
    public List<?> findYearAirRunTime1(@Param("pname") String pname, @Param("date") String date);


    /*
              查询空调2的用电量 根据项目名 和空调时间
               */
    @Query(value = "  SELECT  substr(substr(d.date,1,7),6,7), d.air_run_time2   FROM c_year_run_time_count d  ,p_project p,t_equipment e    where \n" +
            "   e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,4)=:date ORDER BY d.date asc   ", nativeQuery = true)
    public List<?> findYearAirRunTime2(@Param("pname") String pname, @Param("date") String date);

    /*
               查询空调1的制冷运行时间 根据项目名 和空调时间
                */
    @Query(value = "  SELECT  substr(substr(d.date,1,7),6,7), d.air_run_cool_time1   FROM c_year_run_time_count d  ,p_project p,t_equipment e    where \n" +
            "   e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,4)=:date ORDER BY d.date asc   ", nativeQuery = true)
    public List<?> findYearAirRunTime1Cool(@Param("pname") String pname, @Param("date") String date);


    /*
              查询空调2的制冷运行时间 根据项目名 和空调时间
               */
    @Query(value = "  SELECT  substr(substr(d.date,1,7),6,7), d.air_run_cool_time2   FROM c_year_run_time_count d  ,p_project p,t_equipment e    where \n" +
            "   e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,4)=:date ORDER BY d.date asc   ", nativeQuery = true)
    public List<?> findYearAirRunTime2Cool(@Param("pname") String pname, @Param("date") String date);




    /*
           统计空调1某月的总运行时间
                    */
    @Query(value = "SELECT  sum(c.air_run_time1)  from c_year_run_time_count c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,4)=:date        ", nativeQuery = true)
    public List<?> findMonthAirRun1(@Param("pname") String pname,@Param("date") String date);

    /*
              统计空调2某月的总运行时间
                       */
    @Query(value = "SELECT  sum(c.air_run_time2)  from c_year_run_time_count c , t_equipment e ,p_project p where  c.uuid=e.uuid and p.eq_id=e.id   and p.pname=:pname    and  substr(c.date,1,4)=:date        ", nativeQuery = true)
    public List<?> findMonthAirRun2(@Param("pname") String pname,@Param("date") String date);


}
