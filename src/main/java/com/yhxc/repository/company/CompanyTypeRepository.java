package com.yhxc.repository.company;

import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * 企业 Repository
 *
 * @author lwg
 */
public interface CompanyTypeRepository extends JpaRepository<CompanyType, String>, JpaSpecificationExecutor<CompanyType> {




}
