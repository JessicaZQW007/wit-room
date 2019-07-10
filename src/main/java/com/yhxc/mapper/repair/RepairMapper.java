package com.yhxc.mapper.repair;

import com.yhxc.entity.aftersale.Repair;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/30 18:27
 */
public interface RepairMapper {
    List<Repair> list(Repair repair);
}
