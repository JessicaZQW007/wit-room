package com.yhxc.service.send;

import com.yhxc.entity.send.SetMessage;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 15:58
 */
public interface SetMessageService {
    public SetMessage findByUuidAndNum(String uuid, String num);

    public void save(SetMessage message);
}
