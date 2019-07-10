package com.yhxc.service.count;


import com.yhxc.entity.count.DayRunTimeCount;
import com.yhxc.entity.count.MonthRunTimeCount;
import net.sf.json.JSONObject;
import org.springframework.data.repository.query.Param;

/**
 * 月统计表   空调运行时间Service接口
 * @author yhxc 李文光
 *
 */
public interface MonthRunTimeCountService {

    /**
     * 新增或者修改信息
     * @param monthRunTimeCount
     */
    public void save(MonthRunTimeCount monthRunTimeCount);

    /**
     * 查询某月空调的运行情况
     * @param pname
     * @param date
     */
    public JSONObject findMonthAirRunTime(String pname, String date);

    /*
        统计空调1总的运行时间
                 */
    public String findAirSum1( String uuid);
    /*
    统计空调2总的运行时间
             */
    public String findAirSum2( String uuid);
}
