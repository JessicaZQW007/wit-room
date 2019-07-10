package com.yhxc.service.information;

import com.yhxc.entity.information.quartz;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 10:20
 */
public interface quartzService {
    public void save(quartz corn);
    public void delete(Integer id);
    public void updateTemp( String jobKey,String temp,String remarks, String create_date);
    public void updateRunModel( String jobKey,String runModel, String remarks, String create_date);
    public void updateCorn( String jobKey,String corn, String remarks, String create_date);
    public void updateCornStatus( String jobKey,String cornStatus);
    public List<quartz> findAll();
    public List<quartz> findByjobGroup(String jobGroup);
}
