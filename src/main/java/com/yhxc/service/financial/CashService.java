package com.yhxc.service.financial;

import com.yhxc.entity.financial.Cash;
import com.yhxc.service.BaseService;


/**
 * @Author: 赵贺飞
 * @Date: 2018/12/13 17:42
 */
public interface CashService extends BaseService<Cash> {
    Cash findCurrent();

}
