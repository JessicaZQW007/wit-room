package com.yhxc.repository.count;

import com.yhxc.entity.count.DayAirCountElectric;
import com.yhxc.entity.count.MonthAirCountElectric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * 每日空调电量汇总Repository接口
 * @author yhxc 李文光
 *
 */
public interface DayAirCountElectricRepository extends JpaRepository<DayAirCountElectric, Integer>, JpaSpecificationExecutor<DayAirCountElectric> {




    /**
     * 查询空调1 的电流(s_receive_data)
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.ktccurrent1,c.receive_date from s_receive_data c  where substr(c.receive_date,1,10)=:date and c.uuid=:uuid ORDER BY c.receive_date desc;",nativeQuery = true)
    public List<?> byUuidDate1(@Param("date")String date,@Param("uuid")String uuid);

    /**
     * 查询空调2 的电流(s_receive_data)
     * @param uuid
     * @param date
     * @return
     */
    @Query(value="SELECT c.ktccurrent2,c.receive_date from s_receive_data c  where substr(c.receive_date,1,10)=:date and c.uuid=:uuid ORDER BY c.receive_date desc;",nativeQuery = true)
    public List<?> byUuidDate2(@Param("date")String date,@Param("uuid")String uuid);



}
