package com.yhxc.service.analyze.Impl;

import com.yhxc.repository.analyze.EnergyRepository;
import com.yhxc.repository.count.DayCountElectricRepository;
import com.yhxc.repository.count.MonthAirCountElectricRepository;
import com.yhxc.repository.count.MonthCountElectricRepository;
import com.yhxc.repository.count.YearCountElectricRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.analyze.EnergyService;
import com.yhxc.utils.DateCont;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class EnergyServiceImpl  implements EnergyService {
    @Resource
    private EnergyRepository energyRepository;
    @Resource
    private DayCountElectricRepository dayCountElectricRepository;
    @Resource
    private MonthCountElectricRepository monthCountElectricRepository;
    @Resource
    private YearCountElectricRepository yearCountElectricRepository;
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private MonthAirCountElectricRepository monthAirCountElectricRepository;

    @Override
    public JSONArray findHourList(String pname, String date) {
        List<?> datas=dayCountElectricRepository.findHourList(pname,date);
        return hourDispose(datas);
    }

    @Override
    public JSONArray findHourListAir1(String pname, String date) {
        List<?> datas=dayCountElectricRepository.findHourListAir1(pname,date);
        return hourDispose(datas);
    }

    @Override
    public JSONArray findHourListAir2(String pname, String date) {
        List<?> datas=dayCountElectricRepository.findHourListAir2(pname,date);
        return hourDispose(datas);
    }

    @Override
    public JSONObject findDaycount(String pname, String date) {
         Double  sumEnergy=0.0;
        String  sum=dayCountElectricRepository.findCountList(pname,date);//日用电量
        String  price=projectRepository.findElectricityPrice(pname);//电的费率
        if( StringUtil.isNotEmpty(sum)&&StringUtil.isNotEmpty(price)) {
            sumEnergy=Double.valueOf(sum)*Double.valueOf(price);//费用
        }else {
            sum="0.0";
            sumEnergy=0.0;
        }
        String  air1=monthAirCountElectricRepository.findDayAirSum1(pname,date);//空调1
        String  air2=monthAirCountElectricRepository.findDayAirSum2(pname,date);//空调2
        if(StringUtil.isEmpty(air1)&&StringUtil.isEmpty(air2)){
            air1="0";
            air2="0";
        }

        int  airNum= projectRepository.findProjectAirNum(pname);//项目空调数量
        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
             jsonObject=new JSONObject();
            jsonObject.put("sum",sum);
            jsonObject.put("sumEnergy",sumEnergy);
            jsonObject.put("air1",air1);
            jsonObject.put("air2",air2);
        }else if((airNum==1)){
             jsonObject=new JSONObject();
            jsonObject.put("sum",sum);
            jsonObject.put("sumEnergy",sumEnergy);
            jsonObject.put("air1",air1);
        }

        return jsonObject;
    }
    @Override
    public JSONObject findMonthcount(String pname, String date) {
        Double  sumEnergy=0.0;
        String  sum=monthCountElectricRepository.findCountList(pname,date);//月总用电量
        String  price=projectRepository.findElectricityPrice(pname);//电的费率
      if( StringUtil.isNotEmpty(sum)&&StringUtil.isNotEmpty(price)) {
          sumEnergy=Double.valueOf(sum)*Double.valueOf(price);//费用
      }else {
          sum="0.0";
          sumEnergy=0.0;
      }

        String  air1=monthAirCountElectricRepository.findMonthAirSum1(pname,date);//空调1
        String  air2=monthAirCountElectricRepository.findMonthAirSum2(pname,date);//空调2
        if(StringUtil.isEmpty(air1)&&StringUtil.isEmpty(air2)){
            air1="0";
            air2="0";
        }
        int  airNum= projectRepository.findProjectAirNum(pname);//项目空调数量
        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
            jsonObject=new JSONObject();
            jsonObject.put("sum",sum);
            jsonObject.put("sumEnergy",sumEnergy);
            jsonObject.put("air1",air1);
            jsonObject.put("air2",air2);
        }else if((airNum==1)){
            jsonObject=new JSONObject();
            jsonObject.put("sum",sum);
            jsonObject.put("sumEnergy",sumEnergy);
            jsonObject.put("air1",air1);
        }
        return jsonObject;
    }

    @Override
    public JSONObject findYearcount(String pname, String date) {
        Double  sumEnergy=0.0;
        String  sum=yearCountElectricRepository.findCountList(pname,date);//年总用电量
        String  price=projectRepository.findElectricityPrice(pname);//电的费率
        if( StringUtil.isNotEmpty(sum)&&StringUtil.isNotEmpty(price)) {
            sumEnergy=Double.valueOf(sum)*Double.valueOf(price);//费用
        }else {
            sum="0.0";
            sumEnergy=0.0;
        }
        String  air1=yearCountElectricRepository.findCountListAir1(pname,date);//空调1
        String  air2=yearCountElectricRepository.findCountListAir2(pname,date);//空调2
        if(StringUtil.isEmpty(air1)&&StringUtil.isEmpty(air2)){
            air1="0";
            air2="0";
        }
        int  airNum= projectRepository.findProjectAirNum(pname);//项目空调数量
        JSONObject jsonObject=new JSONObject();
        if(airNum==2){
            jsonObject=new JSONObject();
            jsonObject.put("sum",sum);
            jsonObject.put("sumEnergy",sumEnergy);
            jsonObject.put("air1",air1);
            jsonObject.put("air2",air2);
        }else if((airNum==1)){
            jsonObject=new JSONObject();
            jsonObject.put("sum",sum);
            jsonObject.put("sumEnergy",sumEnergy);
            jsonObject.put("air1",air1);
        }

        return jsonObject;
    }


    @Override
    public JSONArray findDayList(String pname, String date) {
        List<?> datas=monthCountElectricRepository.findDayList(pname,date);
        return dayDispose(datas,date);
    }

    @Override
    public JSONArray findDayListApp(String pname, String date) {
        List<?> datas=monthCountElectricRepository.findDayList(pname,date);
        return dayDisposeApp(datas,date,pname);
    }

    @Override
    public JSONArray findDayListAir1(String pname, String date) {
        List<?> datas=monthCountElectricRepository.findDayListAir1(pname,date);
        return dayDispose(datas,date);
    }

    @Override
    public JSONArray findDayListAir2(String pname, String date) {
        List<?> datas=monthCountElectricRepository.findDayListAir2(pname,date);
        return dayDispose(datas,date);
    }


    @Override
    public JSONArray findMonthList(String pname, String date) {
        List<?> datas=yearCountElectricRepository.findMonthList(pname,date);
        return MonthDispose(datas);
    }

    @Override
    public JSONArray findMonthList1(String pname, String date) {
        List<?> datas=yearCountElectricRepository.findMonthListAir1(pname,date);
        return MonthDispose(datas);
    }

    @Override
    public JSONArray findMonthList2(String pname, String date) {
        List<?> datas=yearCountElectricRepository.findMonthListAir2(pname,date);
        return MonthDispose(datas);
    }


    @Override
    public JSONArray findMonthCount(String date,String pId,String unitId) {
        List<?> datas=monthCountElectricRepository.findMonthCount(date, pId, unitId);
        return dayDispose(datas,date);
    }

    @Override
    public JSONArray findProjectRank(String date,String pId,String unitId) {
        JSONArray jsonArray=new JSONArray();
        List<?> datas=monthCountElectricRepository.findProjectRank(date,pId,unitId);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pname", objects[0]);// 项目名
            jsonObject.put("num", objects[1]);// 数量
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findProjectRankAPP(String date, int userId,String address) {
        JSONArray jsonArray=new JSONArray();
        List<?> datas=monthCountElectricRepository.findProjectRankAPP(date,userId,address);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pname", objects[0]);// 项目名
            jsonObject.put("num", objects[1]);// 数量
            jsonObject.put("address", objects[2]);// 地址
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public JSONArray findtoList(String pnames, String dates, String quarter, String year) {
        boolean status = pnames.contains(",");//判断是不是多个项目
        String[] dateArray=null;
        String[] pnameArray=null;
        String[] quarterArray=null;
        String[] yearArray=null;
        JSONArray datas=new JSONArray();
        if (StringUtil.isEmpty(quarter)&&StringUtil.isEmpty(year)){  //是不是查询季度

            //查询月份
            if(status==true){//选择多个项目
                dateArray = dates.split(",");
                pnameArray=pnames.split(",");
                for (int i=0;i<dateArray.length;i++){
                    JSONObject jsonObject=new JSONObject();
                    JSONObject jsonObject2 = new JSONObject();
                    for (int j=0;j<pnameArray.length;j++) {
                        Float  dataZhi =yearCountElectricRepository.findtoList(pnameArray[j],dateArray[i]);//用电量
                        if (dataZhi==null){
                            jsonObject2.put(pnameArray[j],0);
                        }
                        else {
                            jsonObject2.put(pnameArray[j], dataZhi);
                        }
                    }
                    jsonObject.put("time",dateArray[i]);
                    jsonObject.put("data",jsonObject2);
                    datas.add(jsonObject);
                }

            }else {//选个单个项目

                dateArray = dates.split(",");
                for (int i=0;i<dateArray.length;i++){
                    JSONObject jsonObject=new JSONObject();
                    JSONObject jsonObject2 = new JSONObject();
                    Float  dataZhi =yearCountElectricRepository.findtoList(pnames,dateArray[i]);//用电量
                    if (dataZhi==null){
                        jsonObject2.put(pnames,0);
                    }
                    else {
                        jsonObject2.put(pnames, dataZhi);
                    }
                    jsonObject.put("time",dateArray[i]);
                    jsonObject.put("data",jsonObject2);
                    datas.add(jsonObject);
                }
            }

            //查询季度
        }else{
            if(status==true) {//选择多个项目
                boolean quarterStatus = quarter.contains(",");//判断是不是类比， 是不是有多个季度
                if (quarterStatus == true) {
                    quarterArray = quarter.split(",");
                    pnameArray = pnames.split(",");
                    for (int i = 0; i < quarterArray.length; i++) {
                        JSONObject jsonObject = new JSONObject();
                        JSONObject jsonObject2 = new JSONObject();
                        for (int j = 0; j < pnameArray.length; j++) {
                            Float dataZhi = yearCountElectricRepository.findQuartertoList(pnameArray[j], year, quarterArray[i]);//用电量
                            if (dataZhi == null) {
                                jsonObject2.put(pnameArray[j], 0);
                            } else {
                                jsonObject2.put(pnameArray[j], dataZhi);
                            }
                        }
                        jsonObject.put("time", year+  "第"+quarterArray[i]+"季度");
                        jsonObject.put("data", jsonObject2);
                        datas.add(jsonObject);
                    }

                } else {
                    yearArray = year.split(",");
                    pnameArray = pnames.split(",");
                    for (int i = 0; i < yearArray.length; i++) {
                        JSONObject jsonObject = new JSONObject();
                        JSONObject jsonObject2 = new JSONObject();
                        for (int j = 0; j < pnameArray.length; j++) {
                            Float dataZhi = yearCountElectricRepository.findQuartertoList(pnameArray[j], yearArray[i], quarter);//用电量
                            if (dataZhi == null) {
                                jsonObject2.put(pnameArray[j], 0);
                            } else {
                                jsonObject2.put(pnameArray[j], dataZhi);
                            }
                        }
                        jsonObject.put("time", yearArray[i]+  "第"+quarter+"季度");
                        jsonObject.put("data", jsonObject2);
                        datas.add(jsonObject);
                    }

                }
            }

            else {//选个单个项目
                boolean quarterStatus = quarter.contains(",");//判断是不是类比， 是不是有多个季度
                if (quarterStatus == true) {
                    quarterArray = quarter.split(",");
                    for (int i = 0; i < quarterArray.length; i++) {
                        JSONObject jsonObject = new JSONObject();
                        JSONObject jsonObject2 = new JSONObject();

                        Float dataZhi = yearCountElectricRepository.findQuartertoList(pnames, year, quarterArray[i]);//用电量
                        if (dataZhi == null) {
                            jsonObject2.put(pnames, 0);
                        } else {
                            jsonObject2.put(pnames, dataZhi);
                        }
                            jsonObject.put("time", year + "年第" + quarterArray[i] + "季度");
                            jsonObject.put("data", jsonObject2);
                            datas.add(jsonObject);
                        }

                    } else{
                        yearArray = year.split(",");

                        for (int i = 0; i < yearArray.length; i++) {
                            JSONObject jsonObject = new JSONObject();
                            JSONObject jsonObject2 = new JSONObject();

                            Float dataZhi = yearCountElectricRepository.findQuartertoList(pnames, yearArray[i], quarter);//用电量
                            if (dataZhi == null) {
                                jsonObject2.put(pnames, 0);
                            } else {
                                jsonObject2.put(pnames, dataZhi);
                            }
                                jsonObject.put("time", yearArray[i] + "年第" + quarter + "季度");
                                jsonObject.put("data", jsonObject2);
                                datas.add(jsonObject);
                            }



                    }
                }
            }
        return datas;
    }



    /**
     * 处理小时，补齐小时的数据，为null的数据就等于0
     * @param datas
     * @return
     */
    public JSONArray hourDispose(List<?> datas) {
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
        int dayNum = 24; // 小时
        String dayStr = "";
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i <= dayNum-1; i++) {
            dayStr = String.valueOf(i);
            JSONObject jsonObject1 = new JSONObject();
            if (numDayMap.get(dayStr) != null) {
                jsonObject1.put("sun", (i)+":00");
                jsonObject1.put("num", numDayMap.get(dayStr));
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("sun", (i)+":00");
                jsonObject1.put("num", 0);
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
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




    /**
     * 处理天，补齐天的数据，为null的数据就等于0（app）
     * @param datas
     * @return
     */
    public JSONArray dayDisposeApp(List<?> datas,String date,String pname) {
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
        double sumEnergy=0.0;
        for (int i = 1; i <= dayNum; i++) {
            dayStr = String.valueOf(i);
            JSONObject jsonObject1 = new JSONObject();
            if (numDayMap.get(dayStr) != null) {
                jsonObject1.put("sun", dayStr);
                jsonObject1.put("num", numDayMap.get(dayStr));
                String  price=projectRepository.findElectricityPrice(pname);//电的费率
                if( StringUtil.isNotEmpty(price)) {
                    sumEnergy=Double.valueOf(numDayMap.get(dayStr).toString())*Double.valueOf(price);//费用
                }else {
                    sumEnergy=0.0;
                }
                jsonObject1.put("pricenum", sumEnergy);
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("sun", dayStr);
                jsonObject1.put("num", 0);
                jsonObject1.put("pricenum", 0);
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
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




