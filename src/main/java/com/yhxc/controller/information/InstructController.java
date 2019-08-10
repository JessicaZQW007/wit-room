package com.yhxc.controller.information;


import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.information.Instruct;
import com.yhxc.service.information.InstructService;
import com.yhxc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/instruct")
public class InstructController {

    @Autowired
    private InstructService instructService;



    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(){

        return new ResultInfo(StatusCode.SUCCESS, "success", instructService.findInstruct());
    }


    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResultInfo saveModel(Instruct instruct) {

        String info="";
        if (instruct.getId()==null) {


            info = "新增成功";

        } else {


            info = "修改成功";

        }
        instructService.save(instruct);
        return new ResultInfo(StatusCode.SUCCESS, "success",info);



    }


    @RequestMapping("/findByPassword")
    @ResponseBody
    public ResultInfo findByPassword(String password) {

        String info="";
        Instruct instruct= instructService.findByPassword(password);

        if(instruct!=null){
            info="验证通过";

        }else{
            info="验证失败";
        }

        return new ResultInfo(StatusCode.SUCCESS, "success",info);



    }



}
