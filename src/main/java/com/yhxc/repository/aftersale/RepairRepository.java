package com.yhxc.repository.aftersale;

import com.yhxc.entity.aftersale.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface RepairRepository extends JpaRepository<Repair, String>, JpaSpecificationExecutor<Repair> {


    /**
     * 已分派
     *
     * @param acceptName
     * @param acceptPhone
     * @param acceptNum
     * @param acceptTime
     * @param id
     */
    @Query(value = "update t_repair set state = 1, accept_name = :acceptName, accept_phone = :acceptPhone,accept_num = :acceptNum,accept_time = :acceptTime  WHERE id = :id", nativeQuery = true)
    @Transactional
    @Modifying
    public void accept(@Param("acceptName") String acceptName, @Param("acceptPhone") String acceptPhone, @Param("acceptNum") String acceptNum, @Param("acceptTime") String acceptTime, @Param("id") String id);


    /**
     * 已处理
     *
     * @param signature
     * @param finishTime
     * @param remark
     * @param id
     */
    @Query(value = "update t_repair set state = 2, signature = :signature, finish_time = :finishTime,remark = :remark  WHERE id = :id", nativeQuery = true)
    @Transactional
    @Modifying
    public void deal(@Param("signature") String signature, @Param("finishTime") String finishTime, @Param("remark") String remark, @Param("id") String id);

}
