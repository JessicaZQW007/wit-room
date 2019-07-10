package com.yhxc.service.system.impl;

import com.yhxc.entity.send.SetMessage;
import com.yhxc.entity.send.SetMessageRecord;
import com.yhxc.entity.system.User;
import com.yhxc.repository.system.SetMessageRecordRepository;
import com.yhxc.service.system.SetMessageRecordService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/25 17:00
 */
@Service("SetMessageRecordService")
public class SetMessageRecordServiceImpl implements SetMessageRecordService {

    @Resource
    SetMessageRecordRepository messageRecordRepository;

    @Override
    public void save(SetMessage setMessage) {
        SetMessageRecord messageRecord = new SetMessageRecord();
        messageRecord.setRunmodel(setMessage.getRunmodel());
        messageRecord.setTemp(setMessage.getTemp());
        messageRecord.setUuid(setMessage.getUuid());
        messageRecord.setWinddirection(setMessage.getWinddirection());
        messageRecord.setWindspeed(setMessage.getWindspeed());
        messageRecord.setNum(setMessage.getNum());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageRecord.setOperationDate(dateFormat.format(new Date()));//设置操作时间
        try {
            User user = Jurisdiction.getCurrentUser();
            if (user != null) {
                messageRecord.setUserName(user.getUserName());     //设置操作用户名
                messageRecord.setRealName(user.getRealName());      //设置操作用户真实姓名
            }
        } catch (Exception e) {
        }
        messageRecordRepository.save(messageRecord);
    }

    @Override
    public JSONObject findAllByUuidPage(String uuid,String num,String realName,String allDate, int pageNum, int pageSize) {
        String	statDate="";
        String	endDate="";
        if (StringUtil.isNotEmpty(allDate)) {
            statDate = allDate.substring(0, 10);
            endDate = allDate.substring(11, 21);
        }
        JSONObject jsonObject2 = new JSONObject();
        pageNum=(pageNum-1)*pageSize;
        List<?> datas = messageRecordRepository.findAllByUuidPage(uuid,num,realName,statDate,endDate,pageNum, pageSize);
        int number = messageRecordRepository.findAllByUuid(uuid,num,realName,statDate,endDate);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0;i<datas.size();i++){
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("runmodel",objects[1]);
            jsonObject.put("temp",objects[2]);
            jsonObject.put("uuid",objects[3]);
            jsonObject.put("winddirection",objects[4]);
            jsonObject.put("windspeed",objects[5]);
            jsonObject.put("num",objects[6]);
            jsonObject.put("userName",objects[7]);
            jsonObject.put("realName",objects[8]);
            jsonObject.put("operationDate",objects[9]);
            jsonArray.add(jsonObject);
        }
        jsonObject2.put("datas",jsonArray);
        jsonObject2.put("datasCount",number);//总条数
        return jsonObject2;
    }
}
