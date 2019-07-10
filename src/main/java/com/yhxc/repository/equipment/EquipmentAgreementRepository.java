package com.yhxc.repository.equipment;

import com.yhxc.entity.equipment.EquipmentAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:51
 */
public interface EquipmentAgreementRepository extends JpaRepository<EquipmentAgreement, String>, JpaSpecificationExecutor<EquipmentAgreement> {

}
