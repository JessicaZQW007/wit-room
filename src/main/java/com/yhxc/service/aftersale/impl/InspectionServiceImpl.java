package com.yhxc.service.aftersale.impl;

import com.yhxc.entity.aftersale.Inspection;
import com.yhxc.repository.aftersale.InspectionRepository;
import com.yhxc.service.aftersale.InspectionService;
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
 * @Date: 2018/12/12 11:28
 */
@Service
public class InspectionServiceImpl implements InspectionService {
    @Resource
    private InspectionRepository inspectionRepository;

    @Override
    public List<Inspection> findAll() {
        return inspectionRepository.findAll();
    }

    @Override
    public List<Inspection> findAll(String var) {
        return inspectionRepository.findAll(new Specification<Inspection>() {
            @Override
            public Predicate toPredicate(Root<Inspection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty(var)) {
                    predicate.getExpressions().add(cb.equal(root.get("name"), var));
                }
                return predicate;
            }
        });
    }


    @Override
    public List<Inspection> findAll(Inspection obj) {
        return null;
    }

    @Override
    public Inspection findById(String id) {
        return inspectionRepository.findOne(id);
    }

    @Override
    public Inspection findById(Integer id) {
        return null;
    }

    @Override
    public Page<Inspection> listPage(Inspection object, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "createTime");
        return inspectionRepository.findAll(new Specification<Inspection>() {
            @Override
            public Predicate toPredicate(Root<Inspection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(object, cb, root);
            }
        }, pageable);
    }

    public Predicate serach(Inspection object, CriteriaBuilder cb, Root<Inspection> root) {
        Predicate predicate = cb.conjunction();
        if (object != null) {
            if (StringUtil.isNotEmpty(object.getName())) {
                predicate.getExpressions().add(cb.equal(root.get("name"), object.getName()));
            }
            if(object.getState() != null){
                predicate.getExpressions().add(cb.equal(root.get("state"), object.getState()));
            }
        }
        return predicate;
    }

    @Override
    public void delete(String id) {
        inspectionRepository.delete(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(Inspection var) {
        inspectionRepository.save(var);
    }
}
