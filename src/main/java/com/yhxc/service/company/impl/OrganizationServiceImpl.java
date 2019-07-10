package com.yhxc.service.company.impl;

import com.yhxc.entity.company.Organization;
import com.yhxc.repository.company.OrganizationRepository;
import com.yhxc.service.company.OrganizationService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/23 11:39
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationRepository organizationRepository;


    @Override
    public List<Organization> findAll(Organization organization) {

        return organizationRepository.findAll(new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (organization != null) {
                    if (StringUtil.isNotEmpty(organization.getDealerId())) {
                        predicate.getExpressions().add(cb.equal(root.get("dealerId"), organization.getDealerId()));
                    }
                }
                return predicate;
            }
        });
    }


    @Override
    public Page<Organization> listPage(Integer Page, Integer PageSize, Organization organization) {
        Pageable pageable = new PageRequest(Page - 1, PageSize, Sort.Direction.DESC, "createTime");
        Page<Organization> pageData = organizationRepository.findAll(new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (organization != null) {
                    if (StringUtil.isNotEmpty(organization.getName())) {
                        predicates.add(cb.like(root.get("name"), "%" + organization.getName().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(organization.getDealerId())) {
                        predicates.add(cb.like(root.get("dealerId"), "%" + organization.getDealerId().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(organization.getAddress())) {
                        predicates.add(cb.like(root.get("address"), "%" + organization.getAddress().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(organization.getCreateTime())) {
                        String  allDate=organization.getCreateTime();
                        String startDate = allDate.substring(0, 10);
                        String endDate = allDate.substring(11, 21);
                        predicates.add(cb.between(root.get("createTime"),
                                startDate,
                                endDate));

                    }
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return pageData;
    }

    @Override
    public void save(Organization organization) {
        organizationRepository.save(organization);
    }

    @Override
    public Organization findById(String id) {
        return organizationRepository.findOne(id);
    }


    @Override
    public void delete(String id) {
        organizationRepository.delete(id);
    }




}
