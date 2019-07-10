package com.yhxc.service.system.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yhxc.entity.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yhxc.entity.system.Log;
import com.yhxc.repository.system.LogRepository;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;

/**
 * 系统日志Service实现类
 *
 * @author yhxc 赵贺飞
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource
    private LogRepository logRepository;

    /**
     * 保存log的公用方法，已存入userName、time调用时，只需存入content和type
     *
     * @param log
     */
    @Override
    public void save(Log log) {

        try {
            User user = Jurisdiction.getCurrentUser();
            if (user != null) {
                log.setUserName(user.getUserName());     //设置操作用户名
                log.setRealName(user.getRealName());
                Integer type = user.getType();
                if (type == 1) {
                    log.setUnit("终端用户");
                } else if (type == 2) {
                    log.setUnit("普通用户");
                }
            } else {
                log.setUserName("系统备份");
            }

        } catch (Exception e) {
        }
        log.setTime(new Date()); // 设置操作日期
        logRepository.save(log);
    }

    public Log findById(Integer id) {
        return logRepository.findOne(id);
    }


    @Override
    public Page<Log> list(Log log, Integer page, Integer pageSize, Direction direction, String... properties) {
        Pageable pageable = new PageRequest(page - 1, pageSize, direction, properties);
        Page<Log> listLog = logRepository.findAll(new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (log != null) {
                    if (log.getUserName() != null && StringUtil.isNotEmpty(log.getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("userName"), "%" + log.getUserName() + "%"));
                    }
                    if (log.getType() != null && StringUtil.isNotEmpty(log.getType())) {
                        predicate.getExpressions().add(cb.like(root.get("type"), "%" + log.getType() + "%"));
                    }
                    //这里使用User 的photo属性作为参数查询
                    String p = log.getContent();
                    if (StringUtil.isNotEmpty(p)) {
                        String startDate = p.substring(0, 10);
                        String endDate = p.substring(13, 23);
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("time").as(String.class), startDate));
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("time").as(String.class), endDate));
                    }
                }
                return predicate;
            }
        }, pageable);
        return listLog;
    }

    public void delete(Integer id) {
        logRepository.delete(id);
    }
}
