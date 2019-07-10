package com.yhxc.service.count.impl;


import com.yhxc.entity.count.YearRunTimeCount;
import com.yhxc.repository.count.YearRunTimeCountRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.count.YearRunTimeCountService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 年统计表   空调运行情况ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class YearRunTimeCountServiceImpl implements YearRunTimeCountService {

    @Resource
    private YearRunTimeCountRepository yearRunTimeCountRepository;
    @Resource
    private ProjectRepository projectRepository;


    @Override
    public void save(YearRunTimeCount yearRunTimeCount) {
        yearRunTimeCountRepository.save(yearRunTimeCount);
    }

    @Override
    public JSONObject findYearAirRunTime(String pname, String date) {
        int  airNum= projectRepository.findProjectAirNum(pname);
        List<?> datas1=yearRunTimeCountRepository.findYearAirRunTime1Cool(pname,date);
        List<?> datas2=yearRunTimeCountRepository.findYearAirRunTime2Cool(pname,date);
        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
            jsonObject.put("air1",MonthDispose(datas1));
            jsonObject.put("air2",MonthDispose(datas2));
        } else if(airNum==1){
            jsonObject.put("air1",MonthDispose(datas1));
        }
        return jsonObject;
    }


    /**
     * 处理月，补齐月的数据，为null的数据就等于0
     * @param datas
     * @return
     */
    public JSONArray MonthDispose(List<?> datas) {
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
        int dayNum = 12; // 个月
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
