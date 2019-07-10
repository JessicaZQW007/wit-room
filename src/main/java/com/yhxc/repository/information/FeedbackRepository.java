package com.yhxc.repository.information;

import com.yhxc.entity.information.Feedback;
import com.yhxc.entity.information.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 *
 */
public interface FeedbackRepository extends JpaRepository<Feedback, String>, JpaSpecificationExecutor<Feedback> {


    /**
     * 用户处理收到的反馈信息，更新处理状态
     *
     * @param dealUserName
     * @param dealRemark
     * @param id
     */
    @Query(value = "UPDATE t_feedback SET deal_user_name = :dealUserName, deal_time = :dealTime, deal_remark =:dealRemark,state = 1 WHERE ID = :id", nativeQuery = true)
    @Transactional
    @Modifying
    public void updDeal(@Param("dealUserName") String dealUserName, @Param("dealTime") String dealTime, @Param("dealRemark") String dealRemark, @Param("id") String id);
}
