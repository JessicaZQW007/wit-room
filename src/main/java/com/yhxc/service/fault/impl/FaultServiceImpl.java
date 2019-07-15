package com.yhxc.service.fault.impl;

import com.yhxc.entity.fault.Fault;
import com.yhxc.entity.fault.FaultCode;
import com.yhxc.repository.fault.FaultRepository;
import com.yhxc.service.fault.FaultService;
import com.yhxc.utils.DateCont;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/23 10:51
 */
@Service("FaultService")
public class FaultServiceImpl implements FaultService {

    @Resource
    FaultRepository faultRepository;

    @Override
    public void save(Fault fault) {
        faultRepository.save(fault);
    }

    @Override
    public JSONObject findAllFaultMessagePage(String pname, String message, String rank, String address, String allDate, int pageNum, int pageSize) {
        List<?> datas=null;
        String	statDate="";
        String	endDate="";
        if (StringUtil.isNotEmpty(allDate)) {
            statDate = allDate.substring(0, 10);
            endDate = allDate.substring(11, 21);
        }
        JSONObject jsonObject2 = new JSONObject();
        pageNum=(pageNum-1)*pageSize;
        datas = faultRepository.findAllFaultMessagePage(pname,message,rank,address,statDate,endDate,pageNum,pageSize);
        int number = faultRepository.findAllFaultMessage(pname,message,rank,address,statDate,endDate);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message",objects[0]);
            jsonObject.put("code",objects[1]);
            jsonObject.put("rank",objects[2]);
            jsonObject.put("uuid",objects[3]);
            jsonObject.put("pname",objects[4]);
            jsonObject.put("name",objects[5]);
            jsonObject.put("address",objects[6]);
            jsonObject.put("createtime",objects[7]);
            jsonObject.put("endtime",objects[8]);
            jsonObject.put("state",objects[9]);
            jsonObject.put("airid",objects[10]);
            jsonObject.put("user_name",objects[11]);
            jsonArray.add(jsonObject);
        }
        jsonObject2.put("datas",jsonArray);
        jsonObject2.put("datasCount",number);//总条数
        return jsonObject2;
    }

    @Override
    public JSONArray findMonthCount(String date,String pId,String unitId) {
        List<?> datas = faultRepository.findMonthCount(date,pId,unitId);
        return dayDispose(datas,date);
    }

    @Override
    public JSONArray findFaultCode() {
        JSONArray jsonArray = new JSONArray();
       List<?> datas= faultRepository.findFaultCode();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("message",objects[1]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findAllReprot(String uuid,String air_id) {
        JSONArray jsonArray = new JSONArray();
      List<?>  datas=faultRepository.findAllReprot(uuid,air_id);
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
            jsonObject.put("address",objects[8]);
            jsonObject.put("airId",objects[9]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    /**
     * 处理天，补齐天的数据，为null的数据就等于0
     * @param datas
     * @return
     */
    public JSONArray dayDispose(List<?> datas,String date) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            String day = (String) objects[0];
            if (day.substring(0, 1).equals("0")) {
                day = day.substring(1, 2);
            }
            jsonObject.put("sun", day);// 天数
            jsonObject.put("num", objects[1]);// 数量
            jsonArray.add(jsonObject);
        }
        // 首先把字符串转成 JSONArray 对象
        JSONArray json = JSONArray.fromObject(jsonArray);
        HashMap<String, Object> numDayMap = new HashMap<String, Object>();
        if (json.size() > 0) {
            for (int i = 0; i < json.size(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject job = json.getJSONObject(i);
                // 得到 每个对象中的属性值
                numDayMap.put((String) (job.get("sun")), job.get("num"));
            }
        }
        DateCont dateCont = new DateCont();
        date.substring(1, 4);

        int year = Integer.parseInt(date.substring(0, 4));

        String months = date.substring(5, 7);
        if (months.substring(0, 1).equals("0")) {
            months = months.substring(1, 2);
        }
        int month = Integer.parseInt(months);
        int dayNum = dateCont.getDaysByYearMonth(year, month);
        String dayStr = "";
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 1; i <= dayNum; i++) {
            dayStr = String.valueOf(i);
            JSONObject jsonObject1 = new JSONObject();
            if (numDayMap.get(dayStr) != null) {
                jsonObject1.put("sun", dayStr);
                jsonObject1.put("num", numDayMap.get(dayStr));
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("sun", dayStr);
                jsonObject1.put("num", 0);
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
    }


}
