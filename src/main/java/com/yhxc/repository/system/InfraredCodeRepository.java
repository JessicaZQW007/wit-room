package com.yhxc.repository.system;

import com.yhxc.entity.system.InfraredCode;
import com.yhxc.entity.system.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 红外代码Repository接口
 * @author yhxc lwg
 *
 */
public interface InfraredCodeRepository extends JpaRepository<InfraredCode, Integer>,JpaSpecificationExecutor<InfraredCode>{








    /**查询品牌查询红外码
     * @param
     */
    @Query(value = "SELECT * from   t_infrared_code where brand=:brand  ORDER BY code asc ", nativeQuery = true)
    public List<InfraredCode> findByBrand(@Param("brand") String brand);





}
