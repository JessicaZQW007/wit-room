package com.yhxc.service.system;

import com.yhxc.entity.send.SetMessage;
import net.sf.json.JSONObject;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/25 16:59
 */
public interface SetMessageRecordService {
    public void save(SetMessage setMessage);

    JSONObject findAllByUuidPage(String uuid,String num,String realName,String allDate,int pageNum, int pageSize);
}
