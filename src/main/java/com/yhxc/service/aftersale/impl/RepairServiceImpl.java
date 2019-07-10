package com.yhxc.service.aftersale.impl;

import com.yhxc.entity.aftersale.Repair;
import com.yhxc.mapper.repair.RepairMapper;
import com.yhxc.repository.aftersale.RepairRepository;
import com.yhxc.service.aftersale.RepairService;
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

@Service
public class RepairServiceImpl implements RepairService {

    @Resource
    private RepairRepository repairRepository;
    @Resource
    private RepairMapper repairMapper;

    @Override
    public Repair findById(String id) {
        return repairRepository.findOne(id);
    }

    @Override
    public Repair findById(Integer id) {
        return null;
    }

    @Override
    public Page<Repair> listPage(Repair object, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "createTime");
        return repairRepository.findAll(new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                return serach(object, predicate, cb, root);
            }
        }, pageable);
    }

    @Override
    public void delete(String id) {
        repairRepository.delete(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(Repair var) {
        repairRepository.save(var);
    }


    @Override
    public List<Repair> findAll() {
        return null;
    }

    @Override
    public List<Repair> findAll(String var) {
        return null;
    }

    @Override
    public List<Repair> findAll(Repair repair) {
        return repairRepository.findAll(new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                return serach(repair, predicate, cb, root);
            }
        });
    }

    /**
     * 封装查询条件
     *
     * @param repair
     * @param predicate
     * @param cb
     * @param root
     * @return
     */
    public Predicate serach(Repair repair, Predicate predicate, CriteriaBuilder cb, Root<Repair> root) {
        if (repair != null) {
            if (StringUtil.isNotEmpty(repair.getUserName())) {
                predicate.getExpressions().add(cb.like(root.get("userName"), "%" + repair.getUserName().trim() + "%"));
            }
            if (StringUtil.isNotEmpty(repair.getPhone())) {
                predicate.getExpressions().add(cb.like(root.get("phone"), "%" + repair.getPhone().trim() + "%"));
            }
            if(repair.getState() != null){
                predicate.getExpressions().add(cb.equal(root.get("state"), repair.getState()));
            }
            if(StringUtil.isNotEmpty(repair.getCreateTime())){
                predicate.getExpressions().add(cb.like(root.get("createTime"), "%" + repair.getCreateTime()+ "%"));
            }
        }
        return predicate;
    }

    @Override
    public List<Repair> list(Repair repair, Integer pageNum, Integer pageSize) {
        return repairMapper.list(repair);
    }

    /**
     * 不分页
     * @param repair
     * @return
     */
    @Override
    public List<Repair> list(Repair repair) {
        return repairMapper.list(repair);
    }

    @Override
    public long count(Repair repair) {
        return repairRepository.count(new Specification<Repair>() {
            @Override
            public Predicate toPredicate(Root<Repair> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                return serach(repair, predicate, cb, root);
            }
        });
    }
}
