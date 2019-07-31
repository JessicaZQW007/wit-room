package com.yhxc.service.count.impl;


import com.yhxc.entity.count.DayCountElectric;
import com.yhxc.entity.count.MonthCountElectric;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.repository.count.MonthCountElectricRepository;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.service.count.MonthCountElectricService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 每月电量汇总ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class MonthCountElectricServiceImpl  implements MonthCountElectricService {

    @Resource
    private MonthCountElectricRepository monthCountElectricRepository;
    @Resource
    EquipmentRepository equipmentRepository;
    @Override
    public void save(MonthCountElectric monthCountElectric) {
        monthCountElectricRepository.save(monthCountElectric);
    }

    @Override
    public void sumDayCount(String date) {
        List<?> minData = monthCountElectricRepository.sumDayMin(date);
        List<?> maxData = monthCountElectricRepository.sumDayMax(date);
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
                    int transrate= equipmentRepository.findtransrate(uuid);//互感器赔率
                    f1=f1*transrate;
                    MonthCountElectric monthCountElectric=new MonthCountElectric();
                    monthCountElectric.setUuid(objects1[0].toString());
                    monthCountElectric.setElequantity(f1);
                    monthCountElectric.setDate(objects1[2].toString());
                    monthCountElectric.setCreatetime(DateUtil.getTime());
                    monthCountElectricRepository.save(monthCountElectric);
                }
            }

        }
    }

    @Override
    public Double sumDayCountBiao(String date,String uuid) {
        //String minData = monthCountElectricRepository.sumDayMinuuid(date,uuid);
        //时间减一天
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String  lastDate="";
        try {
            Date dd = dft.parse(date);
            Calendar time = Calendar.getInstance();

            time.setTime(dd);
            time.set(Calendar.DATE, time.get(Calendar.DATE) - 1);
            lastDate =dft.format( dft.parse(dft.format(time.getTime())));
            System.out.println("lastDate="+lastDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String minData = monthCountElectricRepository.sumDayMaxuuid(lastDate,uuid);
        String maxData = monthCountElectricRepository.sumDayMaxuuid(date,uuid);


        int  transrate= equipmentRepository.findtransrate(uuid);

        Double f1=0.0;
        if(StringUtil.isNotEmpty(minData)&&StringUtil.isNotEmpty(maxData)){
            Double electricity=Double.valueOf(maxData)-Double.valueOf(minData);
            electricity=electricity*Double.valueOf(transrate);
            BigDecimal b = new BigDecimal(electricity);
             f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); // 保留两个小数点
        }
        return  f1;
    }


}
