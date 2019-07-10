package com.yhxc.controller.information;

import com.yhxc.entity.information.jobGroup;
import com.yhxc.service.information.jobGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 15:38
 */
@Controller
@RequestMapping("/jobGroup")
public class jobGroupController {
    @Autowired
    private jobGroupService jobgroupservice;
    @RequestMapping("/save")
    @ResponseBody
    public void save(jobGroup group){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = format.format(new Date());
        group.setCreat_date(date);
        jobgroupservice.save(group);
    }
    @RequestMapping("/update")
    @ResponseBody
    public void update(String remakes,Integer id){
        jobgroupservice.update(remakes, id);
    }

    @RequestMapping("/find")
    @ResponseBody
    public List<jobGroup> find(){
        List<jobGroup> list=jobgroupservice.findAll();
        return list;
    }
    @RequestMapping("/del")
    @ResponseBody
    public String findCount(String jobGroup,Integer id){
        String message="";
        Integer count=jobgroupservice.findCount(jobGroup);
        if(count==0){
            jobgroupservice.delete(id);
            message="删除成功！";
        }else {
            message="此规则下已经有当时任务不能删除！";
        }
        return message;
    }
}
