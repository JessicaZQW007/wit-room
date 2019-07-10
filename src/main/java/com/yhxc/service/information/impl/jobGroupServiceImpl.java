package com.yhxc.service.information.impl;

import com.yhxc.entity.information.jobGroup;
import com.yhxc.repository.information.jobGroupRepository;
import com.yhxc.service.information.jobGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 15:31
 */
@Service("jobGroupService")
public class jobGroupServiceImpl implements jobGroupService {
    @Autowired
    private jobGroupRepository jobgrouprepository;
    @Override
    public void save(jobGroup group) {
        jobgrouprepository.save(group);
    }

    @Override
    public void update(String remakes,Integer id ) {
        jobgrouprepository.updateRemakes(remakes,id);
    }

    @Override
    public void delete(Integer id) {
        jobgrouprepository.delete(id);
    }

    @Override
    public Integer findCount(String jobGroup) {
        return jobgrouprepository.findCount(jobGroup);
    }

    @Override
    public List<jobGroup> findAll() {
        return jobgrouprepository.findAll();
    }
}
