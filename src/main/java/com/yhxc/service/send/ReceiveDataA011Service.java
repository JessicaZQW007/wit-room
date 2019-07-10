package com.yhxc.service.send;

import com.yhxc.entity.send.ReceiveDataA011;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/6/17 9:43
 */
public interface ReceiveDataA011Service {
    public List findUnitAddr(String uuid);
    public List<ReceiveDataA011> findMessageByUuid(String uuid,String unitaddr);
    public List findTempByDate(String uuid,String time,String unitaddr);
    public List findCurrentByDate(String uuid, String time, String unitaddr);
    public String findTotPowerByTime(String uuid,String unitaddr,String hourTotpower);
    public String tadayTotalpower(String uuid,String time,String unitaddr);
}
