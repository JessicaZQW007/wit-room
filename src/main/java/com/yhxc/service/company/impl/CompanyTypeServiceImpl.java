package com.yhxc.service.company.impl;

import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.CompanyType;
import com.yhxc.repository.company.CompanyRepository;
import com.yhxc.repository.company.CompanyTypeRepository;
import com.yhxc.service.company.CompanyService;
import com.yhxc.service.company.CompanyTypeService;
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
public class CompanyTypeServiceImpl implements CompanyTypeService {

    @Resource
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public void save(CompanyType companyType) {
        companyTypeRepository.save(companyType);
    }



    @Override
    public List<CompanyType> findAll() {
        return companyTypeRepository.findAll();
    }

    @Override
    public void delete(String id) {
        companyTypeRepository.delete(id);
    }


}
