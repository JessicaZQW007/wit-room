package com.yhxc.repository.aftersale;

import com.yhxc.entity.aftersale.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface WarrantyRepository extends JpaRepository<Warranty, String>, JpaSpecificationExecutor<Warranty> {


    /**
     * 续保完成，恢复保修期内
     *
     * @param uuid
     * @return
     */
    @Query(value = "UPDATE t_warranty set state = :state where uuid = :uuid", nativeQuery = true)
    @Transactional
    @Modifying
    public int updState(@Param("state") Integer state, @Param("uuid") String uuid);


}
