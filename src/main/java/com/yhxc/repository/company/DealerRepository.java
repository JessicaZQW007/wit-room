package com.yhxc.repository.company;

import com.yhxc.entity.company.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 企业 Repository
 * @author lwg
 *
 */
public interface DealerRepository extends JpaRepository<Dealer, String>,JpaSpecificationExecutor<Dealer>{


    /*
         根据公司id查询经销商的地址（省）
        */
    @Modifying
    @Transactional
    @Query(value = "select    substring_index(d.address,',',1)   from t_dealer d   where d.company_id=:companyId    GROUP BY substring_index(d.address,',',1) ", nativeQuery = true)

    public List<String> findAddress(@Param("companyId") String companyId);

    /*
       根据公司id,地址（省）查询经销商
           */
    @Modifying
    @Transactional
    @Query(value = "select  d.`name`  from t_dealer d   where d.company_id=:companyId  and  substring_index(d.address,',',1) =:address  ", nativeQuery = true)

    public List<String> findDealerName(@Param("companyId") String companyId,@Param("address") String address);

}
