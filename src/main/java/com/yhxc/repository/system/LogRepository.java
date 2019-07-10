package com.yhxc.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yhxc.entity.system.Log;

/**
 * 系统日志Repository接口
 * @author yhxc 赵贺飞
 *
 */
public interface LogRepository extends JpaRepository<Log, Integer>,JpaSpecificationExecutor<Log>{
}
