package com.yhxc.service.company.impl;

import com.yhxc.entity.company.Company;
import com.yhxc.repository.company.CompanyRepository;
import com.yhxc.service.company.CompanyService;
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
 * 企业信息ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyRepository companyRepository;

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void delete(String id) {
        companyRepository.delete(id);

    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }


    @Override
    public void updateStatus(String id) {
        Company company = companyRepository.findOne(id);
        if (company.getStatus().equals("1")) {
            companyRepository.updateStatus0(id);
        } else {
            companyRepository.updateStatus1(id);
        }
    }

    @Override
    public Company findById(String id) {
        return companyRepository.findOne(id);
    }

    @Override
    public Page<Company> listPage(Integer Page, Integer PageSize, Company company) {
        Pageable pageable = new PageRequest(Page - 1, PageSize, Sort.Direction.DESC, "createtime");
        Page<Company> pageData = companyRepository.findAll(new Specification<Company>() {
            @Override
            public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (company != null) {
                    if (StringUtil.isNotEmpty(company.getCompanyName())) {
                        predicates.add(cb.like(root.get("companyName"), "%" + company.getCompanyName().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(company.getId())) {
                        predicates.add(cb.like(root.get("id"), "%" + company.getId().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(company.getAddress())) {
                        predicates.add(cb.like(root.get("address"), "%" + company.getAddress().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(company.getCompanyPhone())) {
                        predicates.add(cb.like(root.get("companyPhone"), "%" + company.getCompanyPhone().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(company.getStatus())) {
                        predicates.add(cb.like(root.get("status"), "%" + company.getStatus().trim() + "%"));
                    }
                    if (StringUtil.isNotEmpty(company.getCreatetime())) {
                        String  allDate=company.getCreatetime();
                        String startDate = allDate.substring(0, 10);
                        String endDate = allDate.substring(11, 21);
                        predicates.add(cb.between(root.get("createtime"),
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


}
