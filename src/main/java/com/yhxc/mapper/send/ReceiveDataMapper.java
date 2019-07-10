package com.yhxc.mapper.send;

import com.yhxc.entity.send.ReceiveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 刘俊涛
 * 数据接收Repository
 * @Date: 2019/3/20 12:53
 */
public interface ReceiveDataMapper{

    /**
     *查全部
     * @param uuid
     * @return
     */
    public ReceiveData findAllList(String uuid);

    /**
     *查空调 1
     * @param uuid
     * @return
     */
    public ReceiveData findNnm1(String uuid);

    /**
     *查空调 2
     * @param uuid
     * @return
     */
    public ReceiveData findNnm2(String uuid);

}
