package com.yhxc.service.count;


import com.yhxc.entity.count.MonthAirCountElectric;
import com.yhxc.entity.count.MonthCountElectric;
import org.springframework.data.repository.query.Param;

/**
 * 每月空调电量汇总Service接口
 * @author yhxc 李文光
 *
 */
public interface MonthAirCountElectricService {

    /**
     * 新增或者修改信息
     * @param monthAirCountElectric
     */
    public void save(MonthAirCountElectric monthAirCountElectric);
    /**
     *  统计空调1总的用电量
     * @param uuid
     */

    public String findAirElequantitySum1( String uuid);
    /**
     *  统计空调2总的用电量
     * @param uuid
     */
    public String findAirElequantitySum2( String uuid);

}
