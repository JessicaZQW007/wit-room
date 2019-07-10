package com.yhxc.service.financial.impl;

import com.yhxc.entity.financial.Cash;
import com.yhxc.entity.system.User;
import com.yhxc.repository.financial.CashRepository;
import com.yhxc.service.financial.CashService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.criteria.Predicate;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/13 17:43
 */

@Service
public class CashServiceImpl implements CashService {
    @Resource
    private CashRepository cashRepository;

    @Override
    public List<Cash> findAll() {
        return null;
    }

    @Override
    public List<Cash> findAll(String var) {
        return null;
    }

    @Override
    public List<Cash> findAll(Cash obj) {
        return null;
    }

    @Override
    public Cash findById(String id) {
        return cashRepository.findOne(id);
    }

    @Override
    public Cash findById(Integer id) {
        return null;
    }

    @Override
    public Page<Cash> listPage(Cash object, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(Cash var) {
        cashRepository.save(var);
    }


    @Override
    public Cash findCurrent() {
        return cashRepository.findOne(new Specification<Cash>() {
            @Override
            public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                User user = Jurisdiction.getCurrentUser();
                predicate.getExpressions().add(cb.equal(root.get("companyId"), user.getCompanyId()));
                return predicate;
            }
        });
    }
}
