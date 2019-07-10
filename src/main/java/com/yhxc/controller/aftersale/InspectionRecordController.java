package com.yhxc.controller.aftersale;

import com.github.pagehelper.PageHelper;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.aftersale.InspectionRecord;
import com.yhxc.entity.aftersale.Warranty;
import com.yhxc.service.aftersale.InspectionRecordService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 10:53
 */

@RequestMapping("/inspectionRecord")
@Controller
public class InspectionRecordController {
    @Resource
    private InspectionRecordService inspectionRecordService;


    /**
     * 根据巡检任务ID查询，巡检过的设备列表
     * @return
     */
    @RequestMapping("/listAllRecord")
    @ResponseBody
    public ResultInfo listAllRecord(InspectionRecord inspectionRecord, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<InspectionRecord> list = inspectionRecordService.listAllRecord(inspectionRecord, pageNum, pageSize);
        com.github.pagehelper.PageInfo<InspectionRecord> page = new com.github.pagehelper.PageInfo<InspectionRecord>(list);
        com.yhxc.common.PageInfo pi = new com.yhxc.common.PageInfo();
        pi.setPageNum(page.getPageNum());
        pi.setPageSize(page.getPageSize());
        pi.setTotal(page.getTotal());
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getList(), pi);
    }


    /**
     * 获取设备列表
     * @param inspectionRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ResultInfo list(InspectionRecord inspectionRecord, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InspectionRecord> list = inspectionRecordService.listRecord(inspectionRecord, pageNum, pageSize);
        com.github.pagehelper.PageInfo<InspectionRecord> page = new com.github.pagehelper.PageInfo<InspectionRecord>(list);
        com.yhxc.common.PageInfo pi = new com.yhxc.common.PageInfo();
        pi.setPageNum(page.getPageNum());
        pi.setPageSize(page.getPageSize());
        pi.setTotal(page.getTotal());
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getList(), pi);
    }


    /**
     * 新增记录
     * @param inspectionRecord
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResultInfo save(InspectionRecord inspectionRecord){
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }


    /**
     * 更新问题描述
     * @return
     */
    @ResponseBody
    @RequestMapping("/updProblem")
    public ResultInfo updProblem(InspectionRecord inspectionRecord){
        inspectionRecordService.updProblem(inspectionRecord.getProblem(), DateUtil.getTime(), inspectionRecord.getId());
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }


    /**
     * 批量巡检
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/updProblems")
    public ResultInfo updProblems(String ids){
        if(StringUtil.isNotEmpty(ids)){
            String idStr[] = ids.split(",");
            for (String s : idStr) {
                inspectionRecordService.updProblem("正常", DateUtil.getTime(), s);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }
}
