package com.yhxc.service.analyze.Impl;

import com.yhxc.repository.analyze.EnergyRepository;
import com.yhxc.repository.count.DayCountElectricRepository;
import com.yhxc.repository.count.MonthCountElectricRepository;
import com.yhxc.repository.count.YearCountElectricRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.analyze.DataReportService;
import com.yhxc.service.analyze.EnergyService;
import com.yhxc.utils.DateCont;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class DataReportServiceImpl implements DataReportService {
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




    @Override
    public JSONArray findDayReport(String projectType, String address,String date,String pId,String unitId) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=projectRepository.findProjectName(projectType,address,pId,unitId);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();
            List<?> hourDate=dayCountElectricRepository.findHourList(objects[1].toString(),date);
            jsonObject.put("pname",objects[1].toString());
            jsonObject.put("data", hourDispose(hourDate));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findMonthReport(String projectType, String address, String date,String pId,String unitId) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=projectRepository.findProjectName(projectType,address,pId,unitId);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();

            List<?> dayDate=monthCountElectricRepository.findDayList( objects[1].toString(),date);
            jsonObject.put("pname",objects[1].toString());
            jsonObject.put("data",dayDispose(dayDate,date));
            jsonArray.add(jsonObject);
        }
        return jsonArray;

    }


    //查询某段时间的用电量
    @Override
    public JSONArray findTimeReport(String projectType, String address, String allDate,String pId,String unitId) {
        String	statDate="";
        String	endDate="";
        if (StringUtil.isNotEmpty(allDate)) {
            statDate = allDate.substring(0, 10);
            endDate = allDate.substring(11, 21);
        }


        JSONArray jsonArray = new JSONArray();

        List<?> datas=projectRepository.findProjectName(projectType,address,pId,unitId);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();

            List<?> dayDate=monthCountElectricRepository.findTimeList( objects[1].toString(),statDate,endDate);


            jsonObject.put("pname",objects[1].toString());
            jsonObject.put("data",timeDispose(dayDate,allDate));
            jsonArray.add(jsonObject);
        }
        return jsonArray;

    }

    @Override
    public JSONArray findYearReport(String projectType, String address, String date,String pId,String unitId) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=projectRepository.findProjectName(projectType,address,pId,unitId);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();
            List<?> monthDate=yearCountElectricRepository.findMonthList(objects[1].toString(),date);
            jsonObject.put("pname",objects[1].toString());
            jsonObject.put("data", monthDispose(monthDate));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findQuarterReport(String projectType, String address, String date,String pId,String unitId) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas=projectRepository.findProjectName(projectType,address,pId,unitId);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();
            List<?> quarterDate=yearCountElectricRepository.findQuarterList(objects[1].toString(),date);
            jsonObject.put("pname",objects[1].toString());
            jsonObject.put("data", quarterDispose(quarterDate));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
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
        for (int i = 0; i <dayNum; i++) {
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
                jsonObject1.put("sun", dayStr+"日");
                jsonObject1.put("num", numDayMap.get(dayStr));
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("sun", dayStr+"日");
                jsonObject1.put("num", 0);
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
    }



    /**
     * 处理天，补齐天的数据，为null的数据就等于0  一个范围的日期 不止一个月
     * @param datas
     * @return
     */
    public JSONArray timeDispose(List<?> datas,String allDate) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            String day = (String) objects[0];
            System.out.println("day="+day);
            System.out.println("num="+objects[1]);
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
                System.out.println("job.get(sun)"+(String) (job.get("sun")));
                System.out.println("job.get(num)"+job.get("num"));
                // 得到 每个对象中的属性值
                numDayMap.put((String) (job.get("sun")), job.get("num"));
            }
        }
        DateCont dateCont = new DateCont();

        String	statDate="";
        String	endDate="";
        if (StringUtil.isNotEmpty(allDate)) {
            statDate = allDate.substring(0, 10);
            endDate = allDate.substring(11, 21);
        }

        int year = Integer.parseInt(statDate.substring(0, 4));//年

        int statYear = Integer.parseInt(statDate.substring(0, 4));//开始年

        int endYear = Integer.parseInt(endDate.substring(0, 4));//结束年

        String statMonths = statDate.substring(5, 7);
        System.out.println("statMonths="+statMonths);
        String statDays = statDate.substring(8, 10);
        String endMonths = endDate.substring(5,7);
        String endDays = endDate.substring(8,10);


        //把日期中多余的0去掉 如06月03日改为6月3日
        if (statMonths.substring(0, 1).equals("0")) {
            statMonths = statMonths.substring(1, 2);
        }
        if (statDays.substring(0, 1).equals("0")) {
            statDays = statDays.substring(1, 2);
        }
        if (endMonths.substring(0, 1).equals("0")) {
            endMonths = endMonths.substring(1, 2);
        }
        if (endDays.substring(0, 1).equals("0")) {
            endDays = endDays.substring(1, 2);
        }


        int statMonth = Integer.parseInt(statMonths);//开始月
        int statDay = Integer.parseInt(statDays);//开始天
        int endMonth = Integer.parseInt(endMonths);//结束月
        int endDay = Integer.parseInt(endDays);//结束天


        JSONArray jsonArray1 = new JSONArray();

        //不跨年
        if(endYear-statYear==0) {
            //同月
            if (endMonth - statMonth == 0) {
                String dayStr = " ";
                for (int x = statDay; x <= endDay; x++) {
                    String xx = "";
                    if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                        xx = String.valueOf(x);

                    } else {
                        xx = "0" + x;
                    }

                    if (statMonth != 10 && statMonth != 11 && statMonth != 12) {
                        dayStr = statYear + "-0" + statMonth + "-" + xx;
                    } else {
                        dayStr = statYear +"-"+statMonth + "-" + xx;
                    }
                    JSONObject jsonObject1 = new JSONObject();


                    if (numDayMap.get(dayStr) != null) {
                        jsonObject1.put("sun", statYear+"年"+statMonth + "月" + x + "日");
                        jsonObject1.put("num", numDayMap.get(dayStr));
                        jsonArray1.add(jsonObject1);

                    } else {
                        jsonObject1.put("sun", statYear+"年"+statMonth + "月" + x + "日");
                        jsonObject1.put("num", 0);
                        jsonArray1.add(jsonObject1);
                    }
                }
            } else {
                //月循环 循环开始月到结束月
                for (int k = statMonth; k <= endMonth; k++) {
                    int dayNum = dateCont.getDaysByYearMonth(year, k);
                    System.out.println("dayNum=" + dayNum);
                    //开始
                    if (k == statMonth) {
                        String dayStr = " ";
                        for (int x = statDay; x <= dayNum; x++) {
                            String xx = "";
                            if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                                xx = String.valueOf(x);
                            } else {
                                xx = "0" + x;
                            }

                            if (k != 10 && k != 11 && k != 12) {
                                dayStr =  statYear + "-0" + k + "-" + xx;
                            } else {
                                dayStr =  statYear + "-"+ k + "-" + xx;
                            }

                            JSONObject jsonObject1 = new JSONObject();

                            System.out.println("起始daySrt=" + dayStr);
                            if (numDayMap.get(dayStr) != null) {
                                jsonObject1.put("sun", statYear+"年"+k + "月" + x + "日");
                                jsonObject1.put("num", numDayMap.get(dayStr));
                                jsonArray1.add(jsonObject1);

                            } else {
                                jsonObject1.put("sun", statYear+"年"+k + "月" + x + "日");
                                jsonObject1.put("num", 0);
                                jsonArray1.add(jsonObject1);
                            }
                        }

                    } else if (k == endMonth) {
                        String dayStr = " ";
                        for (int x = 1; x <= endDay; x++) {
                            String xx = "";
                            if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                                xx = String.valueOf(x);
                            } else {
                                xx = "0" + x;
                            }

                            if (k != 10 && k != 11 && k != 12) {
                                dayStr = statYear + "-0" + k + "-" + xx;
                            } else {
                                dayStr = statYear + "-"+ k + "-" + xx;
                            }

                            JSONObject jsonObject1 = new JSONObject();
                            System.out.println("结束daySrt=" + dayStr);
                            if (numDayMap.get(dayStr) != null) {
                                jsonObject1.put("sun", statYear+"年"+k + "月" + x + "日");
                                jsonObject1.put("num", numDayMap.get(dayStr));
                                jsonArray1.add(jsonObject1);

                            } else {
                                jsonObject1.put("sun", statYear+"年"+k + "月" + x + "日");
                                jsonObject1.put("num", 0);
                                jsonArray1.add(jsonObject1);
                            }
                        }

                    } else {
                        String dayStr = " ";
                        for (int x = 1; x <= dayNum; x++) {
                            String xx = "";
                            if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                                xx = String.valueOf(x);
                            } else {
                                xx = "0" + x;
                            }

                            if (k != 10 && k != 11 && k != 12) {
                                dayStr = statYear + "-0" + k + "-" + xx;
                            } else {
                                dayStr = statYear + "-"+ k + "-" + xx;
                            }
                            JSONObject jsonObject1 = new JSONObject();
                            if (numDayMap.get(dayStr) != null) {
                                jsonObject1.put("sun", statYear+"年"+ k + "月" + x + "日");
                                jsonObject1.put("num", numDayMap.get(dayStr));
                                jsonArray1.add(jsonObject1);

                            } else {
                                jsonObject1.put("sun", statYear+"年"+ k + "月" + x + "日");
                                jsonObject1.put("num", 0);
                                jsonArray1.add(jsonObject1);
                            }
                        }

                    }


                }

            }
        }else{
            //跨年

            //开始年
            //月循环 循环开始年月到本年12月
            for (int k = statMonth; k <= 12; k++) {
                int dayNum = dateCont.getDaysByYearMonth(statYear, k);
                System.out.println("dayNum=" + dayNum);
                //开始
                if (k == statMonth) {
                    String dayStr = " ";
                    for (int x = statDay; x <= dayNum; x++) {
                        String xx = "";
                        if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                            xx = String.valueOf(x);
                        } else {
                            xx = "0" + x;
                        }

                        if (k != 10 && k != 11 && k != 12) {
                            dayStr =  statYear + "-0" + k + "-" + xx;
                        } else {
                            dayStr =  statYear + "-"+ k + "-" + xx;
                        }

                        JSONObject jsonObject1 = new JSONObject();

                        System.out.println("起始daySrt=" + dayStr);
                        if (numDayMap.get(dayStr) != null) {
                            jsonObject1.put("sun", statYear+"年"+k + "月" + x + "日");
                            jsonObject1.put("num", numDayMap.get(dayStr));
                            jsonArray1.add(jsonObject1);

                        } else {
                            jsonObject1.put("sun", statYear+"年"+k + "月" + x + "日");
                            jsonObject1.put("num", 0);
                            jsonArray1.add(jsonObject1);
                        }
                    }

                }  else {
                    String dayStr = " ";
                    for (int x = 1; x <= dayNum; x++) {
                        String xx = "";
                        if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                            xx = String.valueOf(x);
                        } else {
                            xx = "0" + x;
                        }

                        if (k != 10 && k != 11 && k != 12) {
                            dayStr = statYear + "-0" + k + "-" + xx;
                        } else {
                            dayStr = statYear + "-"+ k + "-" + xx;
                        }
                        JSONObject jsonObject1 = new JSONObject();
                        if (numDayMap.get(dayStr) != null) {
                            jsonObject1.put("sun", statYear+"年"+ k + "月" + x + "日");
                            jsonObject1.put("num", numDayMap.get(dayStr));
                            jsonArray1.add(jsonObject1);

                        } else {
                            jsonObject1.put("sun", statYear+"年"+ k + "月" + x + "日");
                            jsonObject1.put("num", 0);
                            jsonArray1.add(jsonObject1);
                        }
                    }

                }


            }


            //结束年
            //月循环 循环1月份到结束年月
            for (int k = 1; k <= endMonth; k++) {
                int dayNum = dateCont.getDaysByYearMonth(endYear, k);
                System.out.println("dayNum=" + dayNum);
                //开始
                if (k == endMonth) {
                    String dayStr = " ";
                    for (int x = 1; x <= endDay; x++) {
                        String xx = "";
                        if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                            xx = String.valueOf(x);
                        } else {
                            xx = "0" + x;
                        }

                        if (k != 10 && k != 11 && k != 12) {
                            dayStr = endYear + "-0" + k + "-" + xx;
                        } else {
                            dayStr = endYear + "-"+ k + "-" + xx;
                        }

                        JSONObject jsonObject1 = new JSONObject();
                        System.out.println("结束daySrt=" + dayStr);
                        if (numDayMap.get(dayStr) != null) {
                            jsonObject1.put("sun", endYear+"年"+k + "月" + x + "日");
                            jsonObject1.put("num", numDayMap.get(dayStr));
                            jsonArray1.add(jsonObject1);

                        } else {
                            jsonObject1.put("sun", endYear+"年"+k + "月" + x + "日");
                            jsonObject1.put("num", 0);
                            jsonArray1.add(jsonObject1);
                        }
                    }

                } else {
                    String dayStr = " ";
                    for (int x = 1; x <= dayNum; x++) {
                        String xx = "";
                        if (x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7 && x != 8 && x != 9) {

                            xx = String.valueOf(x);
                        } else {
                            xx = "0" + x;
                        }

                        if (k != 10 && k != 11 && k != 12) {
                            dayStr = endYear + "-0" + k + "-" + xx;
                        } else {
                            dayStr = endYear + "-"+ k + "-" + xx;
                        }
                        JSONObject jsonObject1 = new JSONObject();
                        if (numDayMap.get(dayStr) != null) {
                            jsonObject1.put("sun", endYear+"年"+ k + "月" + x + "日");
                            jsonObject1.put("num", numDayMap.get(dayStr));
                            jsonArray1.add(jsonObject1);

                        } else {
                            jsonObject1.put("sun", endYear+"年"+ k + "月" + x + "日");
                            jsonObject1.put("num", 0);
                            jsonArray1.add(jsonObject1);
                        }
                    }

                }


            }




        }




        return jsonArray1;
    }










    /**
     * 处理月，补齐月的数据，为null的数据就等于0
     * @param datas
     * @return
     */
    public JSONArray monthDispose(List<?> datas) {
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
                jsonObject1.put("sun", dayStr+"月");
                jsonObject1.put("num", numDayMap.get(dayStr));
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("sun", dayStr+"月");
                jsonObject1.put("num", 0);
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
    }

    /**
     * 处理季度，补齐季度的数据，为null的数据就等于0
     * @param datas
     * @return
     */
    public JSONArray quarterDispose(List<?> datas) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            String day =objects[0].toString();
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
        int dayNum = 4; // 个月
        String dayStr = "";
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 1; i <= dayNum; i++) {
            dayStr = String.valueOf(i);
            JSONObject jsonObject1 = new JSONObject();
            if (numDayMap.get(dayStr) != null) {
                jsonObject1.put("sun", "第"+dayStr+"季度");
                jsonObject1.put("num", numDayMap.get(dayStr));
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("sun","第"+dayStr+"季度");
                jsonObject1.put("num", 0);
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
    }






}




