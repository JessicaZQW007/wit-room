package com.yhxc.service.count;


import com.yhxc.entity.count.YearCountElectric;

/**
 * 每年电量汇总Service接口
 * @author yhxc 李文光
 *
 */
public interface YearCountElectricService {

    /**
     * 新增或者修改信息
     * @param yearCountElectric
     */
    public void save(YearCountElectric yearCountElectric);
}
