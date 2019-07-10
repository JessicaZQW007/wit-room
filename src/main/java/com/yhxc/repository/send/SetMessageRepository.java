package com.yhxc.repository.send;

import com.yhxc.entity.send.SetMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 15:42
 */
public interface SetMessageRepository extends JpaRepository<SetMessage,String>,JpaSpecificationExecutor<SetMessage> {
    @Query(value = "SELECT * FROM set_message WHERE id = (SELECT MAX(id) FROM set_message WHERE uuid=?1 and num=?2)", nativeQuery = true)
    public SetMessage findByUuidAndNum(String uuid,String num);
}
