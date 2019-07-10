package com.yhxc.repository.company;

import com.yhxc.entity.company.Company;
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
public interface CompanyRepository extends JpaRepository<Company, String>, JpaSpecificationExecutor<Company> {


    /**
     * 启用
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE t_company SET  status='1'  WHERE id =:id  ", nativeQuery = true)
    public void updateStatus1(@Param("id") String id);

    /**
     * 禁用
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE t_company SET  status='0'  WHERE id =:id  ", nativeQuery = true)
    public void updateStatus0(@Param("id") String id);

}
