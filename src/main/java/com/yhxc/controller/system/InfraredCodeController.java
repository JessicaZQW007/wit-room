package com.yhxc.controller.system;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.financial.Cash;
import com.yhxc.entity.system.InfraredCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.system.InfraredCodeService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 红外代码Controller
 *
 * @author lwg
 */
@RestController
@RequestMapping("/infraredCode")
public class InfraredCodeController {

    @Resource
    private InfraredCodeService infraredCodeService;

    @Resource
    private LogService logService;

    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(InfraredCode infraredCode) {
        String info="";
        if (infraredCode.getId()!=null) {
            info = "更新成功！";
            logService.save(new Log(Log.UPDATE_ACTION, "更新:" + infraredCode.getBrand()+" :"+infraredCode.getCode()));
        } else {
            info = "新增成功！";
            logService.save(new Log(Log.ADD_ACTION, "新增" + infraredCode.getBrand()+" :"+infraredCode.getCode()));
        }
        infraredCodeService.save(infraredCode);

        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }


    /**
     * 根据条件分页查询红外代码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(InfraredCode infraredCode, Integer pageNum, Integer pageSize) throws Exception {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", infraredCodeService.list(infraredCode, pageNum, pageSize, Direction.ASC, "id"));
    }




    @RequestMapping("/del")
    @ResponseBody
    public ResultInfo delete(Integer id) throws Exception {
        logService.save(new Log(Log.DELETE_ACTION, "删除了"+infraredCodeService.findById(id).getBrand()+infraredCodeService.findById(id).getCode() ));
        infraredCodeService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }


    /**
     * 批量删除红外码
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/dels")
    @ResponseBody
    public ResultInfo deleteAll(String ids) throws Exception {
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                logService.save(new Log(Log.DELETE_ACTION, "删除了"+infraredCodeService.findById(Integer.parseInt(idStr[i])).getBrand()+infraredCodeService.findById(Integer.parseInt(idStr[i])).getCode() ));

                infraredCodeService.delete(Integer.parseInt(idStr[i]));
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }



    @RequestMapping("/savetest")
    @ResponseBody
    public ResultInfo savetest(int num,int numSize,String Brand) {
       for(int i=num;i<=numSize;i++){
           InfraredCode infraredCode=new InfraredCode();
           infraredCode.setBrand(Brand);

           infraredCode.setCode((String.valueOf(i)));
           infraredCodeService.save(infraredCode);
        }
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }




}
