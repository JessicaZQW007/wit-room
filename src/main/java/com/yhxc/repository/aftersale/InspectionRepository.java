package com.yhxc.repository.aftersale;

import com.yhxc.entity.aftersale.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/12 11:26
 */
public interface InspectionRepository extends JpaRepository<Inspection, String>, JpaSpecificationExecutor<Inspection> {
}
