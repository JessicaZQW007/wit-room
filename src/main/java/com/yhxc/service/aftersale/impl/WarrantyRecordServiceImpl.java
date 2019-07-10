package com.yhxc.service.aftersale.impl;

import com.yhxc.entity.aftersale.Repair;
import com.yhxc.entity.aftersale.WarrantyRecord;
import com.yhxc.repository.aftersale.WarrantyRecordRepository;
import com.yhxc.service.aftersale.WarrantyRecordService;
import com.yhxc.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * @Date: 2018/12/19 9:36
 */

@Service
public class WarrantyRecordServiceImpl implements WarrantyRecordService {
    @Resource
    private WarrantyRecordRepository warrantyRecordRepository;


    @Override
    public List<WarrantyRecord> findAll() {
        return null;
    }

    @Override
    public List<WarrantyRecord> findAll(String var) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return warrantyRecordRepository.findAll(new Specification<WarrantyRecord>() {
            @Override
            public Predicate toPredicate(Root<WarrantyRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty(var)) {
                    predicate.getExpressions().add(cb.equal(root.get("uuid"), var));
                }
                return predicate;
            }
        }, sort);
    }

    @Override
    public List<WarrantyRecord> findAll(WarrantyRecord obj) {
        return null;
    }

    @Override
    public WarrantyRecord findById(String id) {
        return warrantyRecordRepository.findOne(id);
    }

    @Override
    public WarrantyRecord findById(Integer id) {
        return null;
    }

    @Override
    public Page<WarrantyRecord> listPage(WarrantyRecord object, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(WarrantyRecord var) {
        warrantyRecordRepository.save(var);
    }
}
