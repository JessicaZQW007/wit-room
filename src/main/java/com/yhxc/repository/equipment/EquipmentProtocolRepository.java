package com.yhxc.repository.equipment;

import com.yhxc.entity.equipment.EquipmentProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/27 15:53
 */
public interface EquipmentProtocolRepository extends JpaRepository<EquipmentProtocol, String>, JpaSpecificationExecutor<EquipmentProtocol> {

}