package com.yhxc.repository.send;

import com.yhxc.entity.send.ReceiveDataA011;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/6/17 9:13
 */
public interface receiveDataA011Repository extends JpaRepository<ReceiveDataA011,Integer>,JpaSpecificationExecutor<ReceiveDataA011> {
    /**
     *功能描述 查询设备的机组数量和对应的机组地址
            * @author 张权威
            * @date 2019/6/17
            * @param [uuid]
            * @return
            */
    @Query(value = "SELECT unit_addr FROM`s_receive_dataa011` where uuid=:uuid GROUP BY  unit_addr",nativeQuery = true)
    public List findUnitAddr(@Param("uuid")String uuid);
    /**
     *功能描述 根据UUID和unitaddr查询设备数据
            * @author 张权威
            * @date 2019/6/17
            * @param [uuid, unitaddr]
            * @return
            */
    @Query(value = "SELECT * FROM `s_receive_dataa011` WHERE id=(select MAX(id) from s_receive_dataa011 where UUID=:uuid AND unit_addr=:unitaddr)",nativeQuery = true)
    public List<ReceiveDataA011> findMessageByUuid(@Param("uuid")String uuid,@Param("unitaddr")String unitaddr);
    /**
     *功能描述 曲线图数据
            * @author 张权威
            * @date 2019/6/17
            * @param [uuid, time, unitaddr]
            * @return
            */
    @Query(value = "SELECT sn_humidity,sn_temp,sw_temp,receive_date  FROM  `s_receive_dataa011` WHERE uuid=:uuid AND DATE_FORMAT(receive_date,'%Y-%m-%d')=:time AND unit_addr=:unitaddr",nativeQuery = true)
    public List findTempByDate(@Param("uuid")String uuid,@Param("time")String time,@Param("unitaddr")String unitaddr);
    /**
     *功能描述  三相电流
            * @author 张权威
            * @date 2019/6/18
            * @param [uuid, time, unitaddr]
            * @return
            */
    @Query(value = "SELECT currentA,currentB,currentC,receive_date  FROM  `s_receive_dataa011` WHERE uuid=:uuid AND DATE_FORMAT(receive_date,'%Y-%m-%d')=:time AND unit_addr=:unitaddr",nativeQuery = true)
    public List findCurrentByDate(@Param("uuid")String uuid,@Param("time")String time,@Param("unitaddr")String unitaddr);

    @Query(value = "SELECT  MAX(f.totalpower) - MIN(f.totalpower) totalpower FROM (SELECT totalpower FROM  `s_receive_dataa011` WHERE DATE_FORMAT(receive_date, '%Y-%m-%d %H') = :hourTotpower  AND UUID=:uuid AND unit_addr=:unitaddr) f ",nativeQuery = true)
    public String findTotPowerByTime(@Param("uuid")String uuid,@Param("unitaddr")String unitaddr,@Param("hourTotpower")String hourTotpower);


    @Query(value = "SELECT MAX(totalpower) - MIN(totalpower) totalpower FROM s_receive_dataa011 WHERE DATE_FORMAT(receive_date, '%Y-%m-%d')=:time and uuid=:uuid and unit_addr=:unitaddr ",nativeQuery = true)
    public String tadayTotalpower(@Param("uuid")String uuid,@Param("time")String time,@Param("unitaddr")String unitaddr);

}
