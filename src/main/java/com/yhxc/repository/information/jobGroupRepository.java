package com.yhxc.repository.information;

import com.yhxc.entity.information.jobGroup;
import com.yhxc.entity.information.quartz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 10:19
 */
public interface jobGroupRepository extends JpaRepository<jobGroup, Integer>, JpaSpecificationExecutor<jobGroup> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE job_group  SET remakes=:remakes WHERE id=:id ",nativeQuery = true)
    public void updateRemakes(@Param("remakes") String remakes,@Param("id") Integer id);

    @Query(value = "SELECT COUNT(*) FROM quartz WHERE job_group=:jobGroup",nativeQuery = true)
    public Integer findCount(@Param("jobGroup")String jobGroup);

}
