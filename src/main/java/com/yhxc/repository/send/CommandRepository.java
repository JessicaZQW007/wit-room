package com.yhxc.repository.send;

import com.yhxc.entity.send.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/27 14:42
 */
public interface CommandRepository extends JpaRepository<Command,Integer>,JpaSpecificationExecutor<Command> {

    @Query(value = "SELECT co.commstatus FROM command co WHERE co.messageid=:messageid AND co.uuid=:uuid",nativeQuery = true)
    public List<String> findByMessageidAndUuid(@Param("messageid")String messageid,@Param("uuid")String uuid);

}
