package com.yhxc.service.count.impl;


import com.yhxc.entity.count.YearAirCountElectric;
import com.yhxc.entity.count.YearCountElectric;
import com.yhxc.repository.count.YearAirCountElectricRepository;
import com.yhxc.repository.count.YearCountElectricRepository;
import com.yhxc.service.count.YearAirCountElectricService;
import com.yhxc.service.count.YearCountElectricService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 每月空调电量汇总ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class YearAirCountElectricServiceImpl implements YearAirCountElectricService {

    @Resource
    private YearAirCountElectricRepository yearAirCountElectricRepository;


    @Override
    public void save(YearAirCountElectric yearAirCountElectric) {
        yearAirCountElectricRepository.save(yearAirCountElectric) ;
    }
}
