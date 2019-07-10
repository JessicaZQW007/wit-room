package com.yhxc.service.fault;


import com.yhxc.entity.fault.Fault;
import com.yhxc.entity.fault.FaultCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 故障统计 Service接口
 * @author lwg
 *
 */
public interface FaultService {

//    报警信息处理记录
    public void save(Fault fault);

//    分页查询报警信息
    public JSONObject findAllFaultMessagePage(String pname, String message, String rank, String address, String allDate, int pageNum, int pageSize);

//    查看某月故障信息
    public JSONArray findMonthCount(String date);

    public JSONArray findFaultCode();

    JSONArray findAllReprot(String uuid,String air_id);
}
