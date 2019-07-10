package com.yhxc.service.count.impl;


import com.yhxc.entity.count.DayAirCountElectric;
import com.yhxc.entity.count.MonthAirCountElectric;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.repository.count.DayAirCountElectricRepository;
import com.yhxc.repository.count.MonthAirCountElectricRepository;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.service.count.DayAirCountElectricService;
import com.yhxc.service.count.MonthAirCountElectricService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 每月电量汇总ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class DayAirCountElectricServiceImpl implements DayAirCountElectricService {

    @Resource
    private DayAirCountElectricRepository dayAirCountElectricRepository;
    @Resource
    EquipmentRepository equipmentRepository;


    @Override
    public void save(DayAirCountElectric dayAirCountElectric) {
        dayAirCountElectricRepository.save(dayAirCountElectric);
    }

    @Override
    public Double findOne(String date,String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage=0;
        JSONObject jsonObject=new JSONObject();
        voltage=equipmentRepository.findvoltage(uuid);
        List<?> eqmedata=dayAirCountElectricRepository.byUuidDate1(date,uuid);
        Double numArry[]=new Double[eqmedata.size()];
        String dateArry[] =new String[eqmedata.size()];
        for (int j= 0; j < eqmedata.size(); j++) {
            Object[] object1 = (Object[]) eqmedata.get(j);
            Double current=Double.valueOf(object1[0].toString());
            String cdate=(object1[1].toString());
            numArry[j]=current;
            dateArry[j]=cdate;
        }
        //计算用了多度电单位（kw。h）
        double numCount = 0.0;
        for (int k = 0; k < dateArry.length - 1; k++) {
            Date date1 = sdf.parse(dateArry[k]);
            Date date2 = sdf.parse(dateArry[k + 1]);
            double dateNum = date1.getTime() - date2.getTime();
            double hour = dateNum /3600000;//转为小时
            if(numArry[k]<=0.0){
                double num = numArry[k] + numArry[k + 1];
                double count = voltage * (num) * (hour);
                numCount += count;
            } else if (numArry[k+1]<=0.0){
                double num = numArry[k] + numArry[k + 1];
                double count = voltage * (num) * (hour);
                numCount += count;
            }else{
                double num = numArry[k] + numArry[k + 1];
                double count = voltage * (num / 2) * (hour);
                numCount += count;
            }
        }
        double f1 = numCount /1000;//kw.h
        BigDecimal b = new BigDecimal(f1);
        double eqNum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点


        return eqNum;
    }

    @Override
    public Double findTwo(String date,String uuid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        int voltage=0;
        JSONObject jsonObject=new JSONObject();
        voltage=equipmentRepository.findvoltage(uuid);
        List<?> eqmedata=dayAirCountElectricRepository.byUuidDate2(date,uuid);
        Double numArry[]=new Double[eqmedata.size()];
        String dateArry[] =new String[eqmedata.size()];
        for (int j= 0; j < eqmedata.size(); j++) {
            Object[] object1 = (Object[]) eqmedata.get(j);
            Double current=Double.valueOf(object1[0].toString());
            String cdate=(object1[1].toString());
            numArry[j]=current;
            dateArry[j]=cdate;
        }
        //计算用了多度电单位（kw。h）
        double numCount = 0.0;
        for (int k = 0; k < dateArry.length - 1; k++) {
            Date date1 = sdf.parse(dateArry[k]);
            Date date2 = sdf.parse(dateArry[k + 1]);
            double dateNum = date1.getTime() - date2.getTime();
            double hour = dateNum /3600000;//转为小时
            if(numArry[k]<=0.0){
                double num = numArry[k] + numArry[k + 1];
                double count = voltage * (num) * (hour);
                numCount += count;
            } else if (numArry[k+1]<=0.0){
                double num = numArry[k] + numArry[k + 1];
                double count = voltage * (num) * (hour);
                numCount += count;
            }else{
                double num = numArry[k] + numArry[k + 1];
                double count = voltage * (num / 2) * (hour);
                numCount += count;
            }
        }
        double f1 = numCount / 1000;//kw.h
        BigDecimal b = new BigDecimal(f1);
        double eqNum = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
        return eqNum;
    }
}
