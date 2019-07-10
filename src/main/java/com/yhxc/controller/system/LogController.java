package com.yhxc.controller.system;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.User;
import com.yhxc.utils.ExportExcel;
import com.yhxc.utils.PageData;
import com.yhxc.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.yhxc.entity.system.Log;
import com.yhxc.service.system.LogService;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理系统日志Controller
 *
 * @author 赵贺飞
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    /**
     * 根据条件分页查询日志信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(Log log, Integer pageNum, Integer pageSize) throws Exception {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", logService.list(log, pageNum, pageSize, Direction.DESC, "time"));
    }


    @RequestMapping("/del")
    @ResponseBody
    public ResultInfo delete(Integer id) throws Exception {
        logService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }


    /**
     * 批量删除日志
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
                logService.delete(Integer.parseInt(idStr[i]));
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }

}
