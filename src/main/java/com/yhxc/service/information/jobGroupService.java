package com.yhxc.service.information;

import com.yhxc.entity.information.jobGroup;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 15:29
 */
public interface jobGroupService {
    public void save(jobGroup group);
    public void update(String remakes,Integer id);
    public List<jobGroup> findAll();
    public Integer findCount(String jobGroup);
    public void delete(Integer id);
}
