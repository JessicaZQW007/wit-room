package com.yhxc.service.fault;

import com.yhxc.entity.fault.Fault;
import com.yhxc.entity.fault.Report;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 刘俊涛
 * 故障列表Service接口
 * @Date: 2019/3/22 18:52
 */
public interface FaultReportService {

    public JSONObject findAllReportMessagePage(String pId,String unitId,String pname, String message, String rank, String address, String allDate, int pageNum, int pageSize);

//    查看报警数量
    public int findNumber(String pId,String unitId);

    //    根据airid,code,uuid删除报警信息
    public void deleteByAirIdAndCodeAndUuid(String id);

//    根据airid,code,uuid查看报警信息
    public JSONArray findColumns(String id);



    //    报警信息处理记录
    public void save(Report report);



    public JSONArray findAllReport(String uuid);


    public JSONArray findAllReportAir(String uuid,String air_id);

}
