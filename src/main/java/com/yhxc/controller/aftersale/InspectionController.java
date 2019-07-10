package com.yhxc.controller.aftersale;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.aftersale.Inspection;
import com.yhxc.entity.aftersale.InspectionRecord;
import com.yhxc.entity.system.User;
import com.yhxc.service.aftersale.InspectionRecordService;
import com.yhxc.service.aftersale.InspectionService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/12 11:29
 */

@RequestMapping("/inspection")
@Controller
public class InspectionController {

    @Resource
    private InspectionService inspectionService;
    @Resource
    private InspectionRecordService inspectionRecordService;


    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(Inspection inspection, Integer pageNum, Integer pageSize) {
        User user = Jurisdiction.getCurrentUser();
        Integer type = user.getType();
        if (type == 1) {

        } else if (type == 2) {
            inspection.setCompanyId(user.getCompanyId());
        } else if (type == 3) {
            inspection.setDealerId(user.getDealerId());
        } else if (type == 4) {
            inspection.setOrganizationId(user.getOrganizationId());
        }
        Page<Inspection> page = inspectionService.listPage(inspection, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", page.getContent(), PageInfo.pageInfo(page));
    }


    /**
     * 新增记录
     * @param inspection
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResultInfo save(Inspection inspection, String uuids){
        String inspectionId = UuidUtil.get32UUID();
        if(StringUtil.isEmpty(inspection.getId())){
            inspection.setId(inspectionId);
            User user = Jurisdiction.getCurrentUser();
            Integer type = user.getType();
            if (type == 1) {

            } else if (type == 2) {
                inspection.setCompanyId(user.getCompanyId());
            } else if (type == 3) {
                inspection.setDealerId(user.getDealerId());
            } else if (type == 4) {
                inspection.setOrganizationId(user.getOrganizationId());
            }
            inspection.setCreateTime(DateUtil.getTime());
        }
        inspectionService.save(inspection);

        //根据批量选择的UUID新增巡检记录
        if(StringUtil.isNotEmpty(uuids)){
            String idStr[] = uuids.split(",");
            InspectionRecord ir = null;
            for (String uuid : idStr) {
                ir = new InspectionRecord();
                ir.setId(UuidUtil.get32UUID());
                ir.setInspectionId(inspectionId);
                ir.setCreateTime(DateUtil.getTime());
                inspectionRecordService.save(ir);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }


    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo delete(String id){
        inspectionService.delete(id);
        inspectionRecordService.deleteByinspectionId(id);
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }
}
