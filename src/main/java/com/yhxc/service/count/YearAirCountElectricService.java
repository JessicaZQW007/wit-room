package com.yhxc.service.count;


import com.yhxc.entity.count.YearAirCountElectric;
import com.yhxc.entity.count.YearCountElectric;

/**
 * 每年空调电量汇总Service接口
 * @author yhxc 李文光
 *
 */
public interface YearAirCountElectricService {

    /**
     * 新增或者修改信息
     * @param yearAirCountElectric
     */
    public void save(YearAirCountElectric yearAirCountElectric);
}
