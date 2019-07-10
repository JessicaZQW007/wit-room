package com.yhxc.controller.fault;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.fault.FaultSet;
import com.yhxc.entity.project.AirBrand;
import com.yhxc.entity.system.Log;
import com.yhxc.service.fault.FaultService;
import com.yhxc.service.fault.FaultSetService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/23 17:38
 */
@Controller
@RequestMapping("faultSet")
public class FaultSetController {

    @Resource
    private FaultSetService faultsetService;
    @Resource
    private LogService logService;

    /**新增或者修改预警范围
     * @param eb
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(FaultSet eb) {
        String info = "";
        if (StringUtil.isEmpty(eb.getId())) {
            eb.setId(UuidUtil.get32UUID());
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, info + "," + eb));
        } else {
            info = "修改成功";
            logService.save(new Log(Log.UPDATE_ACTION, info + "," + eb));
        }
        faultsetService.save(eb);
        return new ResultInfo(StatusCode.SUCCESS, "success",info);
    }

    /**根据uuid查询故障预警
     * @param uuid
     * @return
     */
    @RequestMapping("/findByUuid")
    @ResponseBody
    public ResultInfo findByUuid(String  uuid) {

        return new ResultInfo(StatusCode.SUCCESS, "success",faultsetService.findByUuid(uuid));
    }



}
