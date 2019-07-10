package com.yhxc.service.aftersale;

import com.yhxc.entity.aftersale.Warranty;
import com.yhxc.service.BaseService;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/11 16:04
 */
public interface WarrantyService extends BaseService<Warranty> {

    List<Warranty> list(Warranty warranty, Integer pageNum, Integer pageSize);

    List<Warranty> list(Warranty warranty);

    /**
     * 查询过期的保修记录
     * @return
     */
    List<Warranty> listOverdue();

    int updState(Integer state, String uuid);
}
