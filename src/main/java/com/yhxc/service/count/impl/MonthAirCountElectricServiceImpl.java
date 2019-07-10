package com.yhxc.service.count.impl;


import com.yhxc.entity.count.MonthAirCountElectric;
import com.yhxc.entity.count.MonthCountElectric;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.repository.count.MonthAirCountElectricRepository;
import com.yhxc.repository.count.MonthCountElectricRepository;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.service.count.MonthAirCountElectricService;
import com.yhxc.service.count.MonthCountElectricService;
import com.yhxc.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 每月电量汇总ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class MonthAirCountElectricServiceImpl implements MonthAirCountElectricService {

    @Resource
    private MonthAirCountElectricRepository monthAirCountElectricRepository;
    @Resource
    EquipmentRepository equipmentRepository;

    @Override
    public void save(MonthAirCountElectric monthAirCountElectric) {
        monthAirCountElectricRepository.save(monthAirCountElectric);
    }

    @Override
    public String findAirElequantitySum1(String uuid) {
        return monthAirCountElectricRepository.findAirElequantitySum1(uuid);
    }

    @Override
    public String findAirElequantitySum2(String uuid) {
        return monthAirCountElectricRepository.findAirElequantitySum2(uuid);
    }
}
