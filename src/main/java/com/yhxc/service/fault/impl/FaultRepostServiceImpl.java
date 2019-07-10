package com.yhxc.service.fault.impl;

import com.yhxc.entity.fault.Report;
import com.yhxc.repository.fault.ReportRepository;
import com.yhxc.service.fault.FaultReportService;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/22 19:57
 */
@Service("FaultReportService")
public class FaultRepostServiceImpl implements FaultReportService {

    @Resource
    private ReportRepository reportRepository;

    @Override
    public JSONObject findAllReportMessagePage(String pname,String message,String rank,String address,String allDate,int pageNum,int pageSize) {
        List<?> datas=null;
        String	statDate="";
        String	endDate="";
        if (StringUtil.isNotEmpty(allDate)) {
            statDate = allDate.substring(0, 10);
            endDate = allDate.substring(11, 21);
        }
        JSONObject jsonObject2 = new JSONObject();
        pageNum=(pageNum-1)*pageSize;
        datas = reportRepository.findAllReportMessagePage(pname,message,rank,address,statDate,endDate,pageNum,pageSize);
        int number = reportRepository.findAllReportMessage(pname,message,rank,address,statDate,endDate);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("pname",objects[1]);
            jsonObject.put("uuid",objects[2]);
            jsonObject.put("name",objects[3]);
            jsonObject.put("rank",objects[4]);
            jsonObject.put("code",objects[5]);
            jsonObject.put("message",objects[6]);
            jsonObject.put("manage",objects[7]);
            jsonObject.put("createtime",objects[8]);
            jsonObject.put("address",objects[9]);
            jsonObject.put("airId",objects[10]);
            jsonArray.add(jsonObject);
        }
        jsonObject2.put("datas",jsonArray);
        jsonObject2.put("datasCount",number);//总条数
        return jsonObject2;
    }

    @Override
    public int findNumber() {
        return reportRepository.findNumber();
    }

    //    删除处理过的报警信息
    @Override
    public void deleteByAirIdAndCodeAndUuid(String id) {
        reportRepository.deleteByAirIdAndCodeAndUuid(id);
    }

//    删除前查询报警信息
    @Override
    public JSONArray findColumns(String id) {
        List<?> datas = reportRepository.findColumns(id);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pname",objects[0]);
            jsonObject.put("uuid",objects[1]);
            jsonObject.put("name",objects[2]);
            jsonObject.put("rank",objects[3]);
            jsonObject.put("code",objects[4]);
            jsonObject.put("message",objects[5]);
            jsonObject.put("manage",objects[6]);
            jsonObject.put("createtime",objects[7]);
            jsonObject.put("airId",objects[8]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }

    @Override
    public JSONArray findAllReport(String uuid) {
        List<?> datas = reportRepository.findAllReport(uuid);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("pname",objects[1]);
            jsonObject.put("uuid",objects[2]);
            jsonObject.put("name",objects[3]);
            jsonObject.put("rank",objects[4]);
            jsonObject.put("code",objects[5]);
            jsonObject.put("message",objects[6]);
            jsonObject.put("manage",objects[7]);
            jsonObject.put("createtime",objects[8]);
            jsonObject.put("address",objects[9]);
            jsonObject.put("airId",objects[10]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findAllReportAir(String uuid, String air_id) {
        List<?> datas = reportRepository.findAllReportAir(uuid,air_id);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("pname",objects[1]);
            jsonObject.put("uuid",objects[2]);
            jsonObject.put("name",objects[3]);
            jsonObject.put("rank",objects[4]);
            jsonObject.put("code",objects[5]);
            jsonObject.put("message",objects[6]);
            jsonObject.put("manage",objects[7]);
            jsonObject.put("createtime",objects[8]);
            jsonObject.put("address",objects[9]);
            jsonObject.put("airId",objects[10]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
