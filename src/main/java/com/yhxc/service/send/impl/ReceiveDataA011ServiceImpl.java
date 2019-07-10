package com.yhxc.service.send.impl;

import com.yhxc.entity.send.ReceiveDataA011;
import com.yhxc.repository.send.receiveDataA011Repository;
import com.yhxc.service.send.ReceiveDataA011Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/6/17 9:45
 */
@Service
public class ReceiveDataA011ServiceImpl implements ReceiveDataA011Service{
    @Resource
    private receiveDataA011Repository A011;
    @Override
    public List findUnitAddr(String uuid) {
        return A011.findUnitAddr(uuid);
    }

    @Override
    public List<ReceiveDataA011> findMessageByUuid(String uuid, String unitaddr) {
        return A011.findMessageByUuid(uuid, unitaddr);
    }

    @Override
    public List findTempByDate(String uuid, String time, String unitaddr) {
        return A011.findTempByDate(uuid, time, unitaddr);
    }

    @Override
    public List findCurrentByDate(String uuid, String time, String unitaddr) {
        return A011.findCurrentByDate(uuid, time, unitaddr);
    }

    @Override
    public String tadayTotalpower(String uuid, String time, String unitaddr) {
        return A011.tadayTotalpower(uuid, time, unitaddr);
    }

    @Override
    public String findTotPowerByTime(String uuid, String unitaddr, String hourTotpower) {
        return A011.findTotPowerByTime(uuid, unitaddr, hourTotpower);
    }
}
