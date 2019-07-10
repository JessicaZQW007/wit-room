package com.yhxc.service.count.impl;


import com.yhxc.entity.count.MonthCountElectric;
import com.yhxc.entity.count.YearCountElectric;
import com.yhxc.repository.count.MonthCountElectricRepository;
import com.yhxc.repository.count.YearCountElectricRepository;
import com.yhxc.service.count.MonthCountElectricService;
import com.yhxc.service.count.YearCountElectricService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 每天电量汇总ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class YearCountElectricServiceImpl implements YearCountElectricService {

    @Resource
    private YearCountElectricRepository yearCountElectricRepository;

    @Override
    public void save(YearCountElectric yearCountElectric) {
        yearCountElectricRepository.save(yearCountElectric);
    }
}
