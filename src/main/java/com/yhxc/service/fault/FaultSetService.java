package com.yhxc.service.fault;


import com.yhxc.entity.fault.FaultSet;
import com.yhxc.entity.send.ReceiveData;

import java.util.List;

/**
 * 故障预警 Service接口
 * @author lwg
 *
 */
public interface FaultSetService {

    //    故障预警
    public void save(FaultSet faultSet);

    public FaultSet findByUuid(String uuid);

    /**
     * 新增故障
     * @param date
     */
    public  void saveFalutReport(String date);


    /**
     * 根据电流，环境温度 判断故障
     * @param date
     */

    public  void saveReport(String date);
}

