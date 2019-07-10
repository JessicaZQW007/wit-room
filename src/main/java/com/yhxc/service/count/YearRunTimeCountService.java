package com.yhxc.service.count;


import com.yhxc.entity.count.DayRunTimeCount;
import com.yhxc.entity.count.YearRunTimeCount;
import net.sf.json.JSONObject;

/**
 * 年统计表   空调运行时间Service接口
 * @author yhxc 李文光
 *
 */
public interface YearRunTimeCountService {

    /**
     * 新增或者修改信息
     * @param yearRunTimeCount
     */
    public void save(YearRunTimeCount yearRunTimeCount);

    /**
     * 查询某年空调的运行情况
     * @param pname
     * @param date
     */
    public JSONObject findYearAirRunTime(String pname, String date);

}
