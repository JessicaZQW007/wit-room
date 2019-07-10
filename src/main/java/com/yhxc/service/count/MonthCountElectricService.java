package com.yhxc.service.count;


import com.yhxc.entity.count.MonthCountElectric;
import net.sf.json.JSONObject;

/**
 * 每月电量汇总Service接口
 * @author yhxc 李文光
 *
 */
public interface MonthCountElectricService {

    /**
     * 新增或者修改信息
     * @param monthCountElectric
     */
    public void save(MonthCountElectric monthCountElectric);

    /**
     * 统计某天的汇总(电表)
     * @param date
     */
    public void sumDayCount(String date);


    /**
     * 统计某天的总用电量(电表)
     * @param date
     */
    public Double sumDayCountBiao(String date,String uuid);

}
