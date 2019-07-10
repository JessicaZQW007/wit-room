package com.yhxc.repository.aftersale;

import com.yhxc.entity.aftersale.InspectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/12 11:26
 */
public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, String>, JpaSpecificationExecutor<InspectionRecord> {


    @Query(value = "UPDATE t_inspection_record set problem = :problem, create_time = :createTime where id = :id", nativeQuery = true)
    @Transactional
    @Modifying
    public int updProblem(@Param("problem") String problem, @Param("createTime") String createTime, @Param("id") String id);


    @Query(value = "delete from t_inspection_record where inspection_id=?1", nativeQuery = true)
    @Transactional
    @Modifying
    public void delete(String inspectionId);
}
