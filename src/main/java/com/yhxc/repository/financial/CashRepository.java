package com.yhxc.repository.financial;

import com.yhxc.entity.financial.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:51
 */
public interface CashRepository extends JpaRepository<Cash, String>, JpaSpecificationExecutor<Cash> {

}
