package com.yhxc.service.equipment.Impl;

import com.yhxc.entity.equipment.EquipmentAgreement;
import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.system.User;
import com.yhxc.repository.equipment.EquipmentAgreementRepository;
import com.yhxc.service.equipment.EquipmentAgreementService;
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
 * @Date: 2018/12/13 16:54
 */

@Service
public class EquipmentAgreementServiceImpl implements EquipmentAgreementService {
    @Resource
    private EquipmentAgreementRepository equipmentAgreementRepository;

    @Override
    public List<EquipmentAgreement> findAll() {
        return null;
    }

    @Override
    public List<EquipmentAgreement> findAll(String var) {
        return null;
    }

    @Override
    public List<EquipmentAgreement> findAll(EquipmentAgreement obj) {
        return equipmentAgreementRepository.findAll(new Specification<EquipmentAgreement>() {
            @Override
            public Predicate toPredicate(Root<EquipmentAgreement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(obj, cb, root);
            }
        });
    }


    public Predicate serach(EquipmentAgreement et, CriteriaBuilder cb, Root<EquipmentAgreement> root) {
        Predicate predicate = cb.conjunction();
        User user = Jurisdiction.getCurrentUser();
        predicate.getExpressions().add(cb.equal(root.get("companyId"), user.getCompanyId().trim()));
        if (et != null) {
            if (StringUtil.isNotEmpty(et.getCompanyId())) {

            }

            /*if (StringUtil.isNotEmpty(et.getDealerId())) {
                predicate.getExpressions().add(cb.equal(root.get("dealerId"), et.getDealerId().trim()));
            }

            if (StringUtil.isNotEmpty(et.getOrganizationId())) {
                predicate.getExpressions().add(cb.equal(root.get("organizationId"), et.getOrganizationId().trim()));
            }*/

        }
        return predicate;
    }


    @Override
    public EquipmentAgreement findById(String id) {
        return equipmentAgreementRepository.findOne(id);
    }

    @Override
    public EquipmentAgreement findById(Integer id) {
        return null;
    }

    @Override
    public Page<EquipmentAgreement> listPage(EquipmentAgreement object, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum - 1, pageSize);
        return equipmentAgreementRepository.findAll(new Specification<EquipmentAgreement>() {
            @Override
            public Predicate toPredicate(Root<EquipmentAgreement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(object, cb, root);
            }
        }, pageable);
    }

    @Override
    public void delete(String id) {
        equipmentAgreementRepository.delete(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(EquipmentAgreement var) {
        equipmentAgreementRepository.save(var);
    }
}
