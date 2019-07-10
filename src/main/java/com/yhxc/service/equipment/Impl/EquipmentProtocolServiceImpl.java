package com.yhxc.service.equipment.Impl;

import com.yhxc.entity.equipment.EquipmentAgreement;
import com.yhxc.entity.equipment.EquipmentProtocol;
import com.yhxc.entity.system.User;
import com.yhxc.repository.equipment.EquipmentProtocolRepository;
import com.yhxc.service.equipment.EquipmentProtocolService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * @Date: 2018/12/27 15:55
 */
@Service
public class EquipmentProtocolServiceImpl implements EquipmentProtocolService {
    @Resource
    private EquipmentProtocolRepository equipmentProtocolRepository;


    @Override
    public List<EquipmentProtocol> findAll() {
        return equipmentProtocolRepository.findAll();
    }

    @Override
    public List<EquipmentProtocol> findAll(String var) {
        return null;
    }

    @Override
    public List<EquipmentProtocol> findAll(EquipmentProtocol obj) {
        return equipmentProtocolRepository.findAll(new Specification<EquipmentProtocol>() {
            @Override
            public Predicate toPredicate(Root<EquipmentProtocol> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(obj, cb, root);
            }
        });
    }

    @Override
    public EquipmentProtocol findById(String id) {
        return equipmentProtocolRepository.findOne(id);
    }

    @Override
    public EquipmentProtocol findById(Integer id) {
        return null;
    }

    @Override
    public Page<EquipmentProtocol> listPage(EquipmentProtocol object, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize);
        return equipmentProtocolRepository.findAll(new Specification<EquipmentProtocol>() {
            @Override
            public Predicate toPredicate(Root<EquipmentProtocol> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(object, cb, root);
            }
        }, pageable);
    }


    public Predicate serach(EquipmentProtocol ep, CriteriaBuilder cb, Root<EquipmentProtocol> root) {
        Predicate predicate = cb.conjunction();
        User user = Jurisdiction.getCurrentUser();
        predicate.getExpressions().add(cb.equal(root.get("companyId"), user.getCompanyId().trim()));
        if (ep != null) {
            if (StringUtil.isNotEmpty(ep.getCompanyId())) {

            }
        }
        return predicate;
    }

    @Override
    public void delete(String id) {
        equipmentProtocolRepository.delete(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(EquipmentProtocol var) {
        equipmentProtocolRepository.save(var);
    }
}
