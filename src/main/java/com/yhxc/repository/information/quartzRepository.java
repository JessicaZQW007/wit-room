package com.yhxc.repository.information;

import com.yhxc.entity.information.quartz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 10:19
 */
public interface quartzRepository extends JpaRepository<quartz, Integer>, JpaSpecificationExecutor<quartz> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE quartz  SET temp=:temp ,remarks=:remarks,create_date=:create_date WHERE job_key=:jobKey ",nativeQuery = true)
    public void updateTemp(@Param("jobKey") String jobKey,@Param("temp") String temp,@Param("remarks") String remarks,@Param("create_date") String create_date);
    @Modifying
    @Transactional
    @Query(value = "UPDATE quartz  SET run_model=:runModel ,remarks=:remarks,create_date=:create_date WHERE job_key=:jobKey ",nativeQuery = true)
    public void updateRunModel(@Param("jobKey") String jobKey,@Param("runModel") String runModel,@Param("remarks") String remarks,@Param("create_date") String create_date);
    @Modifying
    @Transactional
    @Query(value = "UPDATE quartz  SET corn=:corn ,remarks=:remarks,create_date=:create_date WHERE job_key=:jobKey ",nativeQuery = true)
    public void updateCorn(@Param("jobKey") String jobKey,@Param("corn") String corn,@Param("remarks") String remarks,@Param("create_date") String create_date);
    @Modifying
    @Transactional
    @Query(value = "UPDATE quartz  SET corn_status=:cornStatus WHERE job_key=:jobKey ",nativeQuery = true)
    public void updateCornStatus(@Param("jobKey") String jobKey,@Param("cornStatus") String cornStatus);
    @Query(value = "SELECT  * FROM quartz WHERE JOB_GROUP=:jobGroup ",nativeQuery = true)
    public List<quartz> findquartzByjobGroup(@Param("jobGroup")String jobGroup);
}
