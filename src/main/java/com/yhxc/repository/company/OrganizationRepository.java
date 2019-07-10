package com.yhxc.repository.company;

import com.yhxc.entity.company.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 企业 Repository
 * @author lwg
 *
 */
public interface OrganizationRepository extends JpaRepository<Organization, String>,JpaSpecificationExecutor<Organization>{


}
