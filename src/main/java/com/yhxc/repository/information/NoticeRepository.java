package com.yhxc.repository.information;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yhxc.entity.information.Notice;


/**
 * 公告Repository接口
 *
 * @author 李文光
 */
public interface NoticeRepository extends JpaRepository<Notice, String>, JpaSpecificationExecutor<Notice> {

    /**
     * 更新公告的flag
     */
    @Query(value = "update t_notice set flag=?1 where id=?2", nativeQuery = true)
    @Transactional
    @Modifying
    public void editFlag(String flag, String id);


    /**
     * 发布公告
     *
     * @param state
     * @param id
     */
    @Query(value = "update t_notice set state=?1 where id=?2", nativeQuery = true)
    @Transactional
    @Modifying
    public void editState(Integer state, String id);

    /**
     * -- 公告弹出--
     * 查询最新公告
     */
    @Query(value = "SELECT * FROM t_notice where state = 1 and stop_time >= now() ORDER BY stop_time DESC", nativeQuery = true)
    @Transactional
    @Modifying
    public List<Notice> findNewList();

}
