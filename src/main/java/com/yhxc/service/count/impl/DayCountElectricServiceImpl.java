package com.yhxc.service.count.impl;


import com.yhxc.entity.count.DayCountElectric;
import com.yhxc.entity.count.MonthCountElectric;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.repository.count.DayCountElectricRepository;
import com.yhxc.repository.count.MonthCountElectricRepository;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.repository.send.ReceiveDataRepository;
import com.yhxc.service.count.DayCountElectricService;
import com.yhxc.service.count.MonthCountElectricService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 每天电量汇总ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class DayCountElectricServiceImpl implements DayCountElectricService {

    @Resource
    private DayCountElectricRepository dayCountElectricRepository;
    @Resource
    private MonthCountElectricRepository monthCountElectricRepository;

    @Resource
    private ReceiveDataRepository receiveDataRepository;


    @Resource
    EquipmentRepository equipmentRepository;

    @Override
    public void save(DayCountElectric dayCountElectric) {
        dayCountElectricRepository.save(dayCountElectric);
    }


/*
    public void sumHourCount(String date) {
        List<?> minData = dayCountElectricRepository.sumHourMin(date);
        List<?> maxData = dayCountElectricRepository.sumHourMax(date);
        JSONArray jsonArray = new JSONArray();
        Double num = 0.0;
        if (minData.size() == maxData.size()) {
            for (int i = 0; i < minData.size(); i++) {
                Object[] objects1 = (Object[]) minData.get(i);
                Object[] objects2 = (Object[]) maxData.get(i);
                JSONObject jsonObject = new JSONObject();
                if (objects1[0].equals(objects2[0]) && objects1[2].equals(objects2[2])) {
                    num = Double.valueOf(objects2[1].toString()) - Double.valueOf(objects1[1].toString());
                    BigDecimal b = new BigDecimal(num);
                    float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留两个小数点
                    String uuid=objects1[0].toString();
                    int Transrate= equipmentRepository.findtransrate(uuid);
                    f1=f1*Transrate;
                    DayCountElectric dayCountElectric=new DayCountElectric();
                    dayCountElectric.setUuid(uuid);
                    dayCountElectric.setElequantity(f1);
                    dayCountElectric.setDate(objects1[2].toString());
                    dayCountElectric.setCreatetime(DateUtil.getTime());
                    dayCountElectricRepository.save(dayCountElectric);
                }
            }
        }
    }
*/


    /*@Override
    public void sumHourCount(String date) {
        List<?> uuids = dayCountElectricRepository.finduuid(date);//求出所有的uuid
        for (int k = 0; k < uuids.size(); k++) {
            Object[] objects = (Object[]) uuids.get(k);
            String uuid = objects[0].toString();
            List<?> minData = dayCountElectricRepository.sumHourMin(date, uuid);
            Double num = 0.0;
            for (int i = 0; i < minData.size() - 1; i++) {

                Object[] objects1 = (Object[]) minData.get(i);
                Object[] objects2 = (Object[]) minData.get(i + 1);
                if (i != 0 && "0.0".equals(objects1[1].toString())) {
                    if ("0.0".equals(objects2[1].toString())) {

                    } else {
                        for (int j = 1; j < minData.size(); j++) {
                            objects1 = (Object[]) minData.get(i - j);
                            if ("0.0" != objects1[1].toString())
                                break;
                        }
                    }
                } else {
                    objects1 = (Object[]) minData.get(i);
                }
                num = Double.valueOf(objects2[1].toString()) - Double.valueOf(objects1[1].toString());
                BigDecimal b = new BigDecimal(num);
                float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留两个小数点
                int Transrate = equipmentRepository.findtransrate(uuid);
                f1 = f1 * Transrate;
                if (f1 < 0) {
                    BigDecimal c = new BigDecimal(0.0);
                    f1 = c.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留两个小数点
                }
                DayCountElectric dayCountElectric = new DayCountElectric();
                dayCountElectric.setUuid(uuid);
                dayCountElectric.setElequantity(f1);
                dayCountElectric.setDate(objects1[2].toString());
                dayCountElectric.setCreatetime(DateUtil.getTime());
                dayCountElectricRepository.save(dayCountElectric);
            }
            if (minData.size() != 0) {
                Object[] objects1 = (Object[]) minData.get(minData.size() - 1);
                JSONObject jsonObject = new JSONObject();
                String maxData = monthCountElectricRepository.sumDayMaxtotalpower(date, uuid);//每天最大值
                num = Double.valueOf(maxData) - Double.valueOf(objects1[1].toString());
                BigDecimal b = new BigDecimal(num);
                float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留两个小数点
                int Transrate = equipmentRepository.findtransrate(uuid);
                f1 = f1 * Transrate;
                DayCountElectric dayCountElectric = new DayCountElectric();
                dayCountElectric.setUuid(uuid);
                dayCountElectric.setElequantity(f1);
                dayCountElectric.setDate(objects1[2].toString());
                dayCountElectric.setCreatetime(DateUtil.getTime());
                dayCountElectricRepository.save(dayCountElectric);

            }


        }
    }
*/

    //计算每个设备每个小时的用电量
    @Override
    public void sumHourCount(String date) {
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";//小时


        List<?> uuids =equipmentRepository.findUuid();//uuid求出所有的uuid
        System.out.println("uuids="+uuids);
        for (int k = 0; k < uuids.size(); k++) {
            System.out.println("uuidList.get(i)=" + uuids.get(k));
            String uuid = (String) uuids.get(k);
            System.out.println("uuid="+uuid);
            List<?> datas1 = receiveDataRepository.findHour(uuid, date);
            if(datas1.size()!=0){
                System.out.println("有值的uuid="+uuid);
                String[] hourArray = hours.split(",");
                for (int b = 0; b < hourArray.length; b++) {

                        //非凌晨0点 一直往前找 找本小时之前的 凌晨0点就直接找前一天的
                        String dateNow=date+" "+hourArray[b];//本小时的时间
                    System.out.println("第一条dateNow="+dateNow);
                        String dateLast="";//上一小时的时间

                        String totalpowerNow=receiveDataRepository.findTotalpowerHourMax(uuid,dateNow);//本小时的最大总功率
                        System.out.println("本小时的最大总功率 totalpowerNow"+totalpowerNow);
                        String totalpowerLast="";
                        String test="0.0";
                        String test1="0";
                        if(StringUtil.isNotEmpty(totalpowerNow)&&Double.valueOf(totalpowerNow)!=0){
                            System.out.println("本小时有数据");

                            if(!(hourArray[b].equals("00"))) {

                                System.out.println("非凌晨00点的时间"+hourArray[b]);
                                for (int i = 1; -1 != (Integer.parseInt(hourArray[b]) - i); i++) {
                                    dateLast = date + " " + hourArray[b - i];//前面小时的时间
                                    System.out.println("前面小时的时间111 dateLast"+dateLast);
                                    totalpowerLast = receiveDataRepository.findTotalpowerHourMax(uuid, dateLast);//前面小时的最大总功率
                                    System.out.println("前面小时的最大总功率 totalpowerLast="+totalpowerLast);
                                    if (StringUtil.isNotEmpty(totalpowerLast)&&Double.valueOf(totalpowerLast)!=0) {
                                        System.out.println("往前面的小时找到数据了");
                                        //前面小时最大总功率有数据 用本小时最大减前面小时的最大总功率
                                        double num = Double.valueOf(totalpowerNow.toString()) - Double.valueOf(totalpowerLast.toString());
                                        System.out.println("减完之后的功率 num="+num);
                                        BigDecimal a = new BigDecimal(num);
                                        double f1 = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();




                                        DayCountElectric dayCountElectric = new DayCountElectric();
                                        dayCountElectric.setUuid(uuid);
                                        dayCountElectric.setElequantity((float) f1);
                                        dayCountElectric.setDate(dateNow);
                                        dayCountElectric.setCreatetime(DateUtil.getTime());
                                        dayCountElectricRepository.save(dayCountElectric);

                                        System.out.println("找到前面小时的数据了");


                                        break;
                                    }

                                }
                            }

                            if(StringUtil.isEmpty(totalpowerLast)||hourArray[b].equals("00")||Double.valueOf(totalpowerLast)==0){
                                //如果循环完全天的还是没有找到数据，就继续循环上一天的
                                String[] hourArrayLast = hours.split(",");

                                for(int y=2;y<15;y++){
                                    Calendar cal = Calendar.getInstance();
                                        System.out.println("循环了多少次Y=" + y);

                                        cal.add(Calendar.DATE, -y);
                                        String lastTowDay = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                                        System.out.println("减天数 lastTowDay=" + lastTowDay);

                                    List<?> datas2 = receiveDataRepository.findHour(uuid, lastTowDay);//先查询前一天一整天有没有数据
                                    System.out.println("datas2.size="+datas2.size());
                                    if(datas2.size()!=0) {

                                        for (int x = hourArrayLast.length - 1; x != -1; x--) {
                                            dateLast = lastTowDay + " " + hourArrayLast[x];
                                            System.out.println("这里是前一天或者前几天的时间了 dateLast=" + dateLast);
                                            totalpowerLast = receiveDataRepository.findTotalpowerHourMax(uuid, dateLast);//找前一天的最接近的最大功率，从23点开始往前找
                                            System.out.println("往前面天数找的数据 totalpowerLast=" + totalpowerLast);

                                            if (StringUtil.isNotEmpty(totalpowerLast)&&Double.valueOf(totalpowerLast)!=0) {
                                                System.out.println("下面天数的找到数据了");
                                                //前面小时最大总功率有数据 用本小时最大减前面小时的最大总功率
                                                double num = Double.valueOf(totalpowerNow.toString()) - Double.valueOf(totalpowerLast.toString());
                                                System.out.println("下面天数的找到数据了 num=" + num);
                                                BigDecimal a = new BigDecimal(num);
                                                double f1 = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                                DayCountElectric dayCountElectric = new DayCountElectric();
                                                dayCountElectric.setUuid(uuid);
                                                dayCountElectric.setElequantity((float) f1);
                                                dayCountElectric.setDate(dateNow);
                                                dayCountElectric.setCreatetime(DateUtil.getTime());
                                                dayCountElectricRepository.save(dayCountElectric);
                                                System.out.println("找到前面天数的数据了");
                                                break;
                                            }


                                        }
                                    }

                            if(StringUtil.isNotEmpty(totalpowerLast)&&Double.valueOf(totalpowerLast)!=0){
                                System.out.println("找到数据了天数循环结束");
                                break;

                            }

                                }
                                if (StringUtil.isEmpty(totalpowerLast)||Double.valueOf(totalpowerLast)==0) {

                                    //前面小时最大总功率有数据 用本小时最大减前面小时的最大总功率
                                    double num = Double.valueOf(totalpowerNow.toString()) - 0;

                                    BigDecimal a = new BigDecimal(num);
                                    double f1 = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    DayCountElectric dayCountElectric = new DayCountElectric();
                                    dayCountElectric.setUuid(uuid);
                                    dayCountElectric.setElequantity((float) f1);
                                    dayCountElectric.setDate(dateNow);
                                    dayCountElectric.setCreatetime(DateUtil.getTime());
                                    dayCountElectricRepository.save(dayCountElectric);
                                    System.out.println("这里是全天的也没找到，往前找也没找到");
                                }
                        }


                        }else{
                            System.out.println("当前小时数据为空 用0填补");
                            DayCountElectric dayCountElectric = new DayCountElectric();
                            dayCountElectric.setUuid(uuid);
                            dayCountElectric.setElequantity(0);
                            dayCountElectric.setDate(dateNow);
                            dayCountElectric.setCreatetime(DateUtil.getTime());
                            dayCountElectricRepository.save(dayCountElectric);




                        }


                }

            }



        }

    }








    @Override
    public JSONArray sumHourCountbiao(String date, String uuid) {
        JSONArray jsonArray = new JSONArray();
        List<?> minData = dayCountElectricRepository.sumHourMinbiao(date, uuid);//每小时的最小值
        Double num = 0.0;
        for (int i = 0; i < minData.size() - 1; i++) {
            Object[] objects1 = (Object[]) minData.get(i);
            Object[] objects2 = (Object[]) minData.get(i + 1);
            JSONObject jsonObject = new JSONObject();
            num = Double.valueOf(objects2[0].toString()) - Double.valueOf(objects1[0].toString());
            BigDecimal b = new BigDecimal(num);
            float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留两个小数点
            int Transrate = equipmentRepository.findtransrate(uuid);
            f1 = f1 * Transrate;
            jsonObject.put("num", f1);
            jsonObject.put("dateHour", objects1[1].toString().substring(objects1[1].toString().length() - 2, objects1[1].toString().length()));
            jsonArray.add(jsonObject);
        }
        if (minData.size() != 0) {
            Object[] objects1 = (Object[]) minData.get(minData.size() - 1);
            JSONObject jsonObject = new JSONObject();
            String maxData = monthCountElectricRepository.sumDayMaxtotalpower(date, uuid);//每天最大值
            num = Double.valueOf(maxData) - Double.valueOf(objects1[0].toString());
            BigDecimal b = new BigDecimal(num);
            float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留两个小数点
            int Transrate = equipmentRepository.findtransrate(uuid);
            f1 = f1 * Transrate;
            jsonObject.put("num", f1);
            jsonObject.put("dateHour", objects1[1].toString().substring(objects1[1].toString().length() - 2, objects1[1].toString().length()));
            jsonArray.add(jsonObject);
        }
        return hourDispose(jsonArray);
    }

    @Override
    public JSONArray sumHourCount(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        voltage = equipmentRepository.findtransrate(uuid);//互感率倍率
        List<?> eqmedata = dayCountElectricRepository.sumHourCount(date, uuid);
        Double numArry[] = new Double[eqmedata.size()];
        String dateArry[] = new String[eqmedata.size()];
        for (int j = 0; j < eqmedata.size(); j++) {
            Object[] object1 = (Object[]) eqmedata.get(j);
            Double current = Double.valueOf(object1[0].toString());
            String cdate = (object1[1].toString());
            numArry[j] = current;
            dateArry[j] = cdate;
        }
        //计算用了多度电单位（kw。h）
        double numCount = 0.0;
        for (int k = 0; k < numArry.length - 1; k++) {
            JSONObject jsonObject = new JSONObject();
            Double current1 = numArry[k];
            Double current2 = numArry[k + 1];
            numCount = current2 - current1;
            BigDecimal b = new BigDecimal(numCount * voltage);
            double eqNum = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("numCount", eqNum);
            jsonObject.put("date", dateArry[k + 1].substring(dateArry[k + 1].length() - 8, dateArry[k + 1].length()));
            jsonArray.add(jsonObject);

        }

        return jsonArray;
    }

    @Override
    public JSONArray sumHourCountair1(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        voltage = equipmentRepository.findvoltage(uuid);//电压
        List<?> eqmedata = dayCountElectricRepository.sumHourCountair1(date, uuid);
        Double numArry[] = new Double[eqmedata.size()];
        String dateArry[] = new String[eqmedata.size()];
        for (int j = 0; j < eqmedata.size(); j++) {
            Object[] object1 = (Object[]) eqmedata.get(j);
            Double current = Double.valueOf(object1[0].toString());
            String cdate = (object1[1].toString());
            numArry[j] = current;
            dateArry[j] = cdate;
        }
        //计算用了多度电单位（kw。h）
        double numCount = 0.0;
        for (int k = 0; k < numArry.length - 1; k++) {
            JSONObject jsonObject = new JSONObject();
            Date date1 = sdf.parse(dateArry[k]);
            Date date2 = sdf.parse(dateArry[k + 1]);
            double dateNum = date2.getTime() - date1.getTime();
            double hour = dateNum / 3600000;//转为小时
            Double current1 = numArry[k];
            Double current2 = numArry[k + 1];
            numCount = ((current2 + current1) / 2) * (voltage) * (hour);
            double f1 = numCount / 1000;//kw.h
            BigDecimal b = new BigDecimal(f1);
            double eqNum = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("numCount", eqNum);
            jsonObject.put("date", dateArry[k + 1].substring(dateArry[k + 1].length() - 8, dateArry[k + 1].length()));
            jsonArray.add(jsonObject);

        }

        return jsonArray;
    }

    @Override
    public JSONArray sumHourCountair2(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        voltage = equipmentRepository.findvoltage(uuid);//电压
        List<?> eqmedata = dayCountElectricRepository.sumHourCountair2(date, uuid);
        Double numArry[] = new Double[eqmedata.size()];
        String dateArry[] = new String[eqmedata.size()];
        for (int j = 0; j < eqmedata.size(); j++) {
            Object[] object1 = (Object[]) eqmedata.get(j);
            Double current = Double.valueOf(object1[0].toString());
            String cdate = (object1[1].toString());
            numArry[j] = current;
            dateArry[j] = cdate;
        }
        //计算用了多度电单位（kw。h）
        double numCount = 0.0;
        for (int k = 0; k < numArry.length - 1; k++) {
            JSONObject jsonObject = new JSONObject();
            Date date1 = sdf.parse(dateArry[k]);
            Date date2 = sdf.parse(dateArry[k + 1]);
            double dateNum = date2.getTime() - date1.getTime();
            double hour = dateNum / 3600000;//转为小时
            Double current1 = numArry[k];
            Double current2 = numArry[k + 1];
            numCount = ((current2 + current1) / 2) * (voltage) * (hour);
            double f1 = numCount / 1000;//kw.h
            BigDecimal b = new BigDecimal(f1);
            double eqNum = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("numCount", eqNum);
            jsonObject.put("date", dateArry[k + 1].substring(dateArry[k + 1].length() - 8, dateArry[k + 1].length()));
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }


    /**
     * 处理小时，补齐小时的数据，为null的数据就等于0
     *
     * @param json
     * @return
     */
    public JSONArray hourDispose(JSONArray json) {
        JSONArray jsonArray = new JSONArray();
        HashMap<String, Object> numDayMap = new HashMap<String, Object>();
        if (json.size() > 0) {
            for (int i = 0; i < json.size(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject job = json.getJSONObject(i);
                String day = job.get("dateHour").toString();
                if (day.substring(0, 1).equals("0")) {
                    day = day.substring(1, 2);
                }
                // 得到 每个对象中的属性值
                numDayMap.put(day, job.get("num"));
            }
        }
        int dayNum = 24; // 小时
        String dayStr = "";
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < dayNum; i++) {
            dayStr = String.valueOf(i);
            JSONObject jsonObject1 = new JSONObject();
            if (numDayMap.get(dayStr) != null) {
                jsonObject1.put("num", numDayMap.get(dayStr));
                jsonObject1.put("dateHour", (i) + ":00");
                jsonArray1.add(jsonObject1);

            } else {
                jsonObject1.put("num", 0);
                jsonObject1.put("dateHour", (i) + ":00");
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
    }


    @Override
    public JSONArray findOne(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        List<?> uuids = dayCountElectricRepository.finduuid(date);//求出所有的uuid
        for (int i = 0; i < uuids.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            Object[] objects = (Object[]) uuids.get(i);
            String uuid = objects[0].toString();
            voltage = equipmentRepository.findvoltage(uuid);
            List<?> eqmedata = dayCountElectricRepository.byUuidDate1(date, uuid);
            Double numArry[] = new Double[eqmedata.size()];
            String dateArry[] = new String[eqmedata.size()];
            for (int j = 0; j < eqmedata.size(); j++) {
                Object[] object1 = (Object[]) eqmedata.get(j);
                Double current = Double.valueOf(object1[0].toString());
                String cdate = (object1[1].toString());
                numArry[j] = current;
                dateArry[j] = cdate;
            }
            //计算用了多度电单位（kw。h）
            double numCount = 0.0;
            for (int k = 0; k < dateArry.length - 1; k++) {
                Date date1 = sdf.parse(dateArry[k]);
                Date date2 = sdf.parse(dateArry[k + 1]);
                double dateNum = date1.getTime() - date2.getTime();
                double hour = dateNum / 3600000;//转为小时
                if (numArry[k] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else if (numArry[k + 1] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num / 2) * (hour);
                    numCount += count;
                }
            }
            double f1 = numCount / 1000;//kw.h
            BigDecimal b = new BigDecimal(f1);
            double eqNum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("uuid", uuid);
            jsonObject.put("num", eqNum);
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }

    @Override
    public JSONArray findTwo(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        List<?> uuids = dayCountElectricRepository.finduuid(date);//求出所有的uuid
        for (int i = 0; i < uuids.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            Object[] objects = (Object[]) uuids.get(i);
            String uuid = objects[0].toString();
            voltage = equipmentRepository.findvoltage(uuid);
            List<?> eqmedata = dayCountElectricRepository.byUuidDate2(date, uuid);
            Double numArry[] = new Double[eqmedata.size()];
            String dateArry[] = new String[eqmedata.size()];
            for (int j = 0; j < eqmedata.size(); j++) {
                Object[] object1 = (Object[]) eqmedata.get(j);
                Double current = Double.valueOf(object1[0].toString());
                String cdate = (object1[1].toString());
                numArry[j] = current;
                dateArry[j] = cdate;
            }
            //计算用了多度电单位（kw。h）
            double numCount = 0.0;
            for (int k = 0; k < dateArry.length - 1; k++) {
                Date date1 = sdf.parse(dateArry[k]);
                Date date2 = sdf.parse(dateArry[k + 1]);
                double dateNum = date1.getTime() - date2.getTime();
                double hour = dateNum / 3600000;//转为小时
                if (numArry[k] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else if (numArry[k + 1] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num / 2) * (hour);
                    numCount += count;
                }
            }

            double f1 = numCount / 1000;//kw.h
            BigDecimal b = new BigDecimal(f1);
            double eqNum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("uuid", uuid);
            jsonObject.put("num", eqNum);
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }

    @Override
    public JSONArray findOneHour(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            String dateHour = date + " " + hourArray[b];
            List<?> uuids = dayCountElectricRepository.finduuidHour(dateHour);//求出所有的uuid
            if (uuids.size() > 0) {
                for (int i = 0; i < uuids.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    Object[] objects = (Object[]) uuids.get(i);
                    String uuid = objects[0].toString();
                    voltage = equipmentRepository.findvoltage(uuid);
                    List<?> eqmedata = dayCountElectricRepository.byUuidDate1Hour(dateHour, uuid);
                    Double numArry[] = new Double[eqmedata.size()];
                    String dateArry[] = new String[eqmedata.size()];
                    for (int j = 0; j < eqmedata.size(); j++) {
                        Object[] object1 = (Object[]) eqmedata.get(j);
                        Double current = Double.valueOf(object1[0].toString());
                        String cdate = (object1[1].toString());
                        numArry[j] = current;
                        dateArry[j] = cdate;
                    }
                    //计算用了多度电单位（kw。h）
                    double numCount = 0.0;
                    for (int k = 0; k < dateArry.length - 1; k++) {
                        Date date1 = sdf.parse(dateArry[k]);
                        Date date2 = sdf.parse(dateArry[k + 1]);
                        double dateNum = date1.getTime() - date2.getTime();
                        double hour = dateNum / 3600000;//转为小时
                        if (numArry[k] <= 0.0) {
                            double num = numArry[k] + numArry[k + 1];
                            double count = voltage * (num) * (hour);
                            numCount += count;
                        } else if (numArry[k + 1] <= 0.0) {
                            double num = numArry[k] + numArry[k + 1];
                            double count = voltage * (num) * (hour);
                            numCount += count;
                        } else {
                            double num = numArry[k] + numArry[k + 1];
                            double count = voltage * (num / 2) * (hour);
                            numCount += count;
                        }
                    }
                    double f1 = numCount / 1000;//kw.h
                    BigDecimal c = new BigDecimal(f1);
                    double eqNum = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
                    jsonObject.put("uuid", uuid);
                    jsonObject.put("num", eqNum);
                    jsonObject.put("dateHour", dateHour);

                    jsonArray.add(jsonObject);

                }
            }


        }
        return jsonArray;
    }

    @Override
    public JSONArray findTwoHour(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            String dateHour = date + " " + hourArray[b];
            List<?> uuids = dayCountElectricRepository.finduuidHour(dateHour);//求出所有的uuid
            if (uuids.size() > 0) {
                for (int i = 0; i < uuids.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    Object[] objects = (Object[]) uuids.get(i);
                    String uuid = objects[0].toString();
                    voltage = equipmentRepository.findvoltage(uuid);
                    List<?> eqmedata = dayCountElectricRepository.byUuidDate2Hour(dateHour, uuid);
                    Double numArry[] = new Double[eqmedata.size()];
                    String dateArry[] = new String[eqmedata.size()];
                    for (int j = 0; j < eqmedata.size(); j++) {
                        Object[] object1 = (Object[]) eqmedata.get(j);
                        Double current = Double.valueOf(object1[0].toString());
                        String cdate = (object1[1].toString());
                        numArry[j] = current;
                        dateArry[j] = cdate;
                    }
                    //计算用了多度电单位（kw。h）
                    double numCount = 0.0;
                    for (int k = 0; k < dateArry.length - 1; k++) {
                        Date date1 = sdf.parse(dateArry[k]);
                        Date date2 = sdf.parse(dateArry[k + 1]);
                        double dateNum = date1.getTime() - date2.getTime();
                        double hour = dateNum / 3600000;//转为小时
                        if (numArry[k] <= 0.0) {
                            double num = numArry[k] + numArry[k + 1];
                            double count = voltage * (num) * (hour);
                            numCount += count;
                        } else if (numArry[k + 1] <= 0.0) {
                            double num = numArry[k] + numArry[k + 1];
                            double count = voltage * (num) * (hour);
                            numCount += count;
                        } else {
                            double num = numArry[k] + numArry[k + 1];
                            double count = voltage * (num / 2) * (hour);
                            numCount += count;
                        }
                    }
                    double f1 = numCount / 1000;//kw.h
                    BigDecimal c = new BigDecimal(f1);
                    double eqNum = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
                    jsonObject.put("uuid", uuid);
                    jsonObject.put("num", eqNum);
                    jsonObject.put("dateHour", dateHour);
                    jsonArray.add(jsonObject);

                }
            }


        }
        return jsonArray;
    }

    @Override
    public JSONArray findOneHouruuidDay(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            String dateHour = date + " " + hourArray[b];
            voltage = equipmentRepository.findvoltage(uuid);
            JSONObject jsonObject = new JSONObject();
            List<?> eqmedata = dayCountElectricRepository.byUuidDate1Hour(dateHour, uuid);
            Double numArry[] = new Double[eqmedata.size()];
            String dateArry[] = new String[eqmedata.size()];
            for (int j = 0; j < eqmedata.size(); j++) {
                Object[] object1 = (Object[]) eqmedata.get(j);
                Double current = Double.valueOf(object1[0].toString());
                String cdate = (object1[1].toString());
                numArry[j] = current;
                dateArry[j] = cdate;
            }
            //计算用了多度电单位（kw。h）
            double numCount = 0.0;
            for (int k = 0; k < dateArry.length - 1; k++) {
                Date date1 = sdf.parse(dateArry[k]);
                Date date2 = sdf.parse(dateArry[k + 1]);
                double dateNum = date1.getTime() - date2.getTime();
                double hour = dateNum / 3600000;//转为小时
                if (numArry[k] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else if (numArry[k + 1] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num / 2) * (hour);
                    numCount += count;
                }
            }
            double f1 = numCount / 1000;//kw.h
            BigDecimal c = new BigDecimal(f1);
            double eqNum = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("num", eqNum);
            jsonObject.put("dateHour", dateHour.substring(dateHour.length() - 2, dateHour.length()) + ":00");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findTwoHouruuidDay(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            String dateHour = date + " " + hourArray[b];
            voltage = equipmentRepository.findvoltage(uuid);
            JSONObject jsonObject = new JSONObject();
            List<?> eqmedata = dayCountElectricRepository.byUuidDate2Hour(dateHour, uuid);
            Double numArry[] = new Double[eqmedata.size()];
            String dateArry[] = new String[eqmedata.size()];
            for (int j = 0; j < eqmedata.size(); j++) {
                Object[] object1 = (Object[]) eqmedata.get(j);
                Double current = Double.valueOf(object1[0].toString());
                String cdate = (object1[1].toString());
                numArry[j] = current;
                dateArry[j] = cdate;
            }
            //计算用了多度电单位（kw。h）
            double numCount = 0.0;
            for (int k = 0; k < dateArry.length - 1; k++) {
                Date date1 = sdf.parse(dateArry[k]);
                Date date2 = sdf.parse(dateArry[k + 1]);
                double dateNum = date1.getTime() - date2.getTime();
                double hour = dateNum / 3600000;//转为小时
                if (numArry[k] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else if (numArry[k + 1] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num / 2) * (hour);
                    numCount += count;
                }
            }
            double f1 = numCount / 1000;//kw.h
            BigDecimal c = new BigDecimal(f1);
            double eqNum = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("num", eqNum);
            jsonObject.put("dateHour", dateHour.substring(dateHour.length() - 2, dateHour.length()) + ":00");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findOneHouruuid(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            String dateHour = date + " " + hourArray[b];
            voltage = equipmentRepository.findvoltage(uuid);
            JSONObject jsonObject = new JSONObject();
            List<?> eqmedata = dayCountElectricRepository.byUuidDate1Hours(dateHour, uuid);
            Double numArry[] = new Double[eqmedata.size()];
            String dateArry[] = new String[eqmedata.size()];
            for (int j = 0; j < eqmedata.size(); j++) {
                Object[] object1 = (Object[]) eqmedata.get(j);
                Double current = Double.valueOf(object1[0].toString());
                String cdate = (object1[1].toString());
                numArry[j] = current;
                dateArry[j] = cdate;
            }
            //计算用了多度电单位（kw。h）
            double numCount = 0.0;
            for (int k = 0; k < dateArry.length - 1; k++) {
                Date date1 = sdf.parse(dateArry[k]);
                Date date2 = sdf.parse(dateArry[k + 1]);
                double dateNum = date1.getTime() - date2.getTime();
                double hour = dateNum / 3600000;//转为小时
                if (numArry[k] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else if (numArry[k + 1] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num / 2) * (hour);
                    numCount += count;
                }
            }
            double f1 = numCount / 1000;//kw.h
            BigDecimal c = new BigDecimal(f1);
            double eqNum = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("num", eqNum);
            jsonObject.put("dateHour", dateHour.substring(dateHour.length() - 2, dateHour.length()) + ":00");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findTwoHouruuid(String date, String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage = 0;
        JSONArray jsonArray = new JSONArray();
        String hours = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
        String[] hourArray = hours.split(",");
        for (int b = 0; b < hourArray.length; b++) {
            String dateHour = date + " " + hourArray[b];
            voltage = equipmentRepository.findvoltage(uuid);
            JSONObject jsonObject = new JSONObject();
            List<?> eqmedata = dayCountElectricRepository.byUuidDate2Hours(dateHour, uuid);
            Double numArry[] = new Double[eqmedata.size()];
            String dateArry[] = new String[eqmedata.size()];
            for (int j = 0; j < eqmedata.size(); j++) {
                Object[] object1 = (Object[]) eqmedata.get(j);
                Double current = Double.valueOf(object1[0].toString());
                String cdate = (object1[1].toString());
                numArry[j] = current;
                dateArry[j] = cdate;
            }
            //计算用了多度电单位（kw。h）
            double numCount = 0.0;
            for (int k = 0; k < dateArry.length - 1; k++) {
                Date date1 = sdf.parse(dateArry[k]);
                Date date2 = sdf.parse(dateArry[k + 1]);
                double dateNum = date1.getTime() - date2.getTime();
                double hour = dateNum / 3600000;//转为小时
                if (numArry[k] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else if (numArry[k + 1] <= 0.0) {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num) * (hour);
                    numCount += count;
                } else {
                    double num = numArry[k] + numArry[k + 1];
                    double count = voltage * (num / 2) * (hour);
                    numCount += count;
                }
            }
            double f1 = numCount / 1000;//kw.h
            BigDecimal c = new BigDecimal(f1);
            double eqNum = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
            jsonObject.put("num", eqNum);
            jsonObject.put("dateHour", dateHour.substring(dateHour.length() - 2, dateHour.length()) + ":00");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
