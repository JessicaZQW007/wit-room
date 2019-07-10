package com.yhxc.service.send;

import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/27 14:56
 */
public interface CommandService {

    public List<String> findByMessageidAndUuid(String messageid,String uuid);

}
