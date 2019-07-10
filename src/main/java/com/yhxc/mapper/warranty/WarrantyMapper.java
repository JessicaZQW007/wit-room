package com.yhxc.mapper.warranty;

import com.yhxc.entity.aftersale.Warranty;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/12 11:40
 */
public interface WarrantyMapper {

    List<Warranty> listWarranty(Warranty u);

    List<Warranty> listOverdue();
}
