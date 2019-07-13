package com.yhxc.repository.count;

import com.yhxc.entity.count.DayCountElectric;
import com.yhxc.entity.count.DayRunTimeCount;
import com.yhxc.entity.send.ReceiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 运行 日统计表 Repository接口
 * @author yhxc 李文光
 *
 */
public interface DayRunTimeCountRepository extends JpaRepository<DayRunTimeCount, Integer>, JpaSpecificationExecutor<DayCountElectric> {



    /*
          查询空调1的电流 根据项目名 和空调时间
           */
    @Query(value = " SELECT  d.air_current1 ,substr(d.date,12,19)  FROM c_day_run_time_count d  ,p_project p,t_equipment e    where \n" +
            " e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,10)=:date  ORDER BY d.date asc ", nativeQuery = true)
    public List<?> findAirCurrent1(@Param("pname") String pname,@Param("date") String date);


    /*
              查询空调2的电流 根据项目名 和空调时间
               */
    @Query(value = " SELECT  d.air_current2 ,substr(d.date,12,19)  FROM c_day_run_time_count d  ,p_project p,t_equipment e    where \n" +
            " e.uuid=d.uuid and p.eq_id=e.id and p.pname=:pname and substr(d.date,1,10)=:date  ORDER BY d.date asc ", nativeQuery = true)
    public List<?> findAirCurrent2(@Param("pname") String pname,@Param("date") String date);


    /*
            查询某天的分体空调上报的数据
             */
    @Query(value = " SELECT  receive_date,uuid,ktccurrent1,ktccurrent2 from s_receive_data where  substr(receive_date,1,10)=:date ", nativeQuery = true)
    public List<?> findLastReceiveData(@Param("date") String date);





    /**
     *  统计当天的运行时间 空调1
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date, ( SELECT CASE WHEN(i.kt_switch1=\"开机\" ) THEN 2 ELSE 0 END   )AS Gender from  s_receive_data i ,t_equipment e  where i.uuid=e.uuid and   substr(i.receive_date,1,10)=:date order by i.uuid,i.receive_date  ", nativeQuery = true)
    public List<?> countDayRunTime1(@Param("date") String date);



    /**
     *  统计当天的运行时间 空调2
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date,( SELECT CASE WHEN(i.kt_switch2=\"开机\" ) THEN 2 ELSE 0 END   )AS Gender from  s_receive_data i  ,t_equipment e  where i.uuid=e.uuid and substr(i.receive_date,1,10)=:date order by i.uuid,i.receive_date  ", nativeQuery = true)
    public List<?> countDayRunTime2(@Param("date") String date);



    /**
     *  统计当天的运行时间 空调1 uuid date 查询
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date,( SELECT CASE WHEN(i.kt_switch1=\"开机\" ) THEN 2 ELSE 0 END   )AS Gender from  s_receive_data i where substr(i.receive_date,1,10)=:date  and uuid=:uuid  order by i.uuid,i.receive_date", nativeQuery = true)
    public List<?> countAirDayRunTime1(@Param("date") String date,@Param("uuid") String uuid);


    /**
     *  统计当天的运行时间 空调1 uuid date 查询
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date,( SELECT CASE WHEN(i.kt_switch2=\"开机\" ) THEN 2 ELSE 0 END   )AS Gender from  s_receive_data i where substr(i.receive_date,1,10)=:date  and uuid=:uuid  order by i.uuid,i.receive_date", nativeQuery = true)
    public List<?> countAirDayRunTime2(@Param("date") String date,@Param("uuid") String uuid);


    /**
     *  统计当天的运行时间 最大的运行时间
     * @author lwg
     *
     */
    @Query(value = "SELECT MAX(i.receive_date) from  s_receive_data i where substr(i.receive_date,1,10)=:date and uuid=:uuid    ", nativeQuery = true)
    public String maxtime(@Param("date") String date,@Param("uuid") String uuid);






    /**
     *  统计制冷的运行时间 空调1
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date,  ( SELECT CASE WHEN(i.kt_run_model1=\"制冷\"    ) THEN 2 ELSE 0 END   )AS Gender  from s_receive_data i where substr(i.receive_date,1,10)=:date  and uuid=:uuid  order by i.uuid,i.receive_date   ", nativeQuery = true)
    public List<?> countDayRunTimecool1(@Param("date") String date,@Param("uuid") String uuid);



    /**
     *  统计制冷的运行时间 空调2
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date,  ( SELECT CASE WHEN(i.kt_run_model2=\"制冷\"    ) THEN 2 ELSE 0 END   )AS Gender  from s_receive_data i where substr(i.receive_date,1,10)=:date  and uuid=:uuid  order by i.uuid,i.receive_date", nativeQuery = true)
    public List<?> countDayRunTimecool2(@Param("date") String date,@Param("uuid") String uuid);




    /**
     *  统计当天制冷的运行时间 空调1
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date, ( SELECT CASE WHEN(i.kt_run_model1=\"制冷\"  ) THEN 2 ELSE 0 END   )AS Gender  from s_receive_data i where substr(i.receive_date,1,10)=:date  order by i.uuid,i.receive_date ", nativeQuery = true)
    public List<?> countDayRuncoolTime1(@Param("date") String date);



    /**
     *  统计当天制冷的运行时间 空调2
     * @author lwg
     *
     */
    @Query(value = "SELECT i.uuid,i.receive_date, ( SELECT CASE WHEN(i.kt_run_model2=\"制冷\"  ) THEN 2 ELSE 0 END   )AS Gender  from s_receive_data i where substr(i.receive_date,1,10)=:date  order by i.uuid,i.receive_date ", nativeQuery = true)
    public List<?> countDayRuncoolTime2(@Param("date") String date);



}
