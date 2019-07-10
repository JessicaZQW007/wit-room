package com.yhxc.service.timer.impl;

import com.yhxc.entity.timer.Timer;
import com.yhxc.repository.timer.TimerRepository;
import com.yhxc.service.timer.TimerService;
import com.yhxc.utils.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/5/6 17:02
 */
@Service("timerService")
public class TimerServiceImpl implements TimerService {

    @Resource
    private TimerRepository timerRepository;


    @Override
    public void delete(String id) {
        timerRepository.delete(id);
    }

    @Override
    public void save(Timer timer) {
        timerRepository.save(timer);
    }

    @Override
    public Timer findById(String id) {
        return timerRepository.findOne(id);
    }

    @Override
    public List<Timer> findAll(Timer timer) {
        List<Timer> pageTimer = timerRepository.findAll(new Specification<Timer>() {
            @Override
            public Predicate toPredicate(Root<Timer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (timer != null) {
                    if (StringUtil.isNotEmpty(timer.getUserName())) {
                        predicate.getExpressions().add(cb.equal(root.get("userName"), timer.getUserName().trim()));
                    }
                    if (StringUtil.isNotEmpty(timer.getUuid())) {
                        predicate.getExpressions().add(cb.equal(root.get("uuid"), timer.getUuid().trim()));
                    }

                    if (timer.getWeek() != null) {
                        predicate.getExpressions().add(cb.like(root.get("week"), "%" + timer.getWeek() + "%"));
                    }

                    if (timer.getIsStart() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("isStart"), timer.getIsStart()));
                    }
                }
                return predicate;
            }
        });
        return pageTimer;
    }

    @Override
    public int updIsStart(Integer isStart, String id) {
        return timerRepository.updIsStart(isStart, id);
    }

    @Override
    public int updIsRun(Integer isRun, String uuid) {
        return timerRepository.updIsRun(isRun, uuid);
    }
}
