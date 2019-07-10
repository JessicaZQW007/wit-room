package com.yhxc.repository.analyze;


import com.yhxc.entity.count.DayRunTimeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 运行统计 Repository接口
 * @author lwg
 *
 */

public interface RunRepository extends JpaRepository<DayRunTimeCount, Integer>,JpaSpecificationExecutor<DayRunTimeCount> {





}
