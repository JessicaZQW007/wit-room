package com.yhxc.repository.system;

import com.yhxc.entity.send.SetMessageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/25 16:55
 */
public interface SetMessageRecordRepository extends JpaRepository<SetMessageRecord,String>,JpaSpecificationExecutor<SetMessageRecord> {
//    分页查询全部空调操作日志信息，条件uuid
    @Query(value = "SELECT * FROM set_message_record WHERE 1=1 " +
            "AND if(:uuid !='', uuid=:uuid, 1 = 1)" +
            "AND if(:num !='', num=:num, 1 = 1)" +
            "AND if(:realName !='', real_name like CONCAT('%',:realName,'%'), 1 = 1)" +
            "AND if(:startDate !='', operation_date BETWEEN :startDate  and :endDate, 1 = 1)" +
            "ORDER BY operation_date desc limit :pageNum,:pageSize", nativeQuery = true)
    List<?> findAllByUuidPage(@Param("uuid")String uuid,@Param("num")String num,@Param("realName")String realName,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("pageNum")int pageNum,@Param("pageSize") int pageSize);

    //    查询分页全部空调操作日志信息（用于统计按当前条件查询的条数）
    @Query(value = "SELECT count(id) FROM set_message_record WHERE 1=1 " +
            "AND if(:uuid !='', uuid=:uuid, 1 = 1)" +
            "AND if(:num !='', num=:num, 1 = 1)" +
            "AND if(:realName !='', real_name like CONCAT('%',:realName,'%'), 1 = 1)" +
            "AND if(:startDate !='', operation_date BETWEEN :startDate  and :endDate, 1 = 1)" +
            "ORDER BY operation_date desc", nativeQuery = true)
    int findAllByUuid(@Param("uuid")String uuid,@Param("num")String num,@Param("realName")String realName,@Param("startDate")String startDate,@Param("endDate")String endDate);
}
