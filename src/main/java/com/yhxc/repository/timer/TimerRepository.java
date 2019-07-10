package com.yhxc.repository.timer;

import com.yhxc.entity.timer.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TimerRepository extends JpaRepository<Timer, String>,JpaSpecificationExecutor<Timer> {

    /**
     * 更新定时器是否启用状态
     * @param isStart
     * @param id
     * @return
     */
    @Query(value = "update app_timer set is_start = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public int updIsStart(Integer isStart, String id);

    /**
     * 更新设备是否在运行中的状态
     * @param isRun
     * @param uuid  设备编号
     * @return
     */
    @Query(value = "update app_timer set is_run = ?1 WHERE uuid = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public int updIsRun(Integer isRun, String uuid);
}