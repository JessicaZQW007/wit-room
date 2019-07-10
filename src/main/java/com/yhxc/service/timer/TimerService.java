package com.yhxc.service.timer;


import com.yhxc.entity.timer.Timer;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/5/6 17:01
 */
public interface TimerService {

    public void delete(String id);

    public void save(Timer timer);

    public Timer findById(String id);

    public List<Timer> findAll(Timer timer);

    public int updIsStart(Integer isStart, String id);

    public int updIsRun(Integer isRun, String id);
}
