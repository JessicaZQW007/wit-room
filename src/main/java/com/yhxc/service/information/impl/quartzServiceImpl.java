package com.yhxc.service.information.impl;

import com.yhxc.entity.information.quartz;
import com.yhxc.repository.information.quartzRepository;
import com.yhxc.service.information.quartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author: 张权威
 * @Date: 2019/4/12 10:36
 */
@Service("quartzService")
public class quartzServiceImpl implements quartzService {


    @Autowired
    private quartzRepository quartzrepository;

    @Override
    public List<quartz> findByjobGroup(String jobGroup) {
        return quartzrepository.findquartzByjobGroup(jobGroup);
    }
    @Override
    public void save(quartz corn) {
        quartzrepository.save(corn);
    }

    @Override
    public void updateCornStatus(String cornStatus, String jobKey) {
        quartzrepository.updateCornStatus(cornStatus, jobKey);
    }

    @Override
    public void delete(Integer id) {
        quartzrepository.delete(id);
    }

    @Override
    public void updateTemp(String jobKey,String temp ,String remarks, String create_date) {
        quartzrepository.updateTemp(jobKey, temp,remarks, create_date);
    }

    @Override
    public void updateRunModel(String jobKey,String runModel, String remarks, String create_date) {
        quartzrepository.updateRunModel(jobKey,runModel, remarks, create_date);
    }

    @Override
    public void updateCorn(String jobKey,String corn, String remarks, String create_date) {
        quartzrepository.updateCorn(jobKey,corn, remarks, create_date);
    }

    @Override
    public List<quartz> findAll() {
        return quartzrepository.findAll();
    }
}
