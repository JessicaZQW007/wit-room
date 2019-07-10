package com.yhxc.controller.aftersale;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.aftersale.WarrantyRecord;
import com.yhxc.service.aftersale.WarrantyRecordService;
import com.yhxc.service.aftersale.WarrantyService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 9:40
 */

@Controller
@RequestMapping("warrantyRecord")
public class WarrantyRecordController {

    @Resource
    private WarrantyRecordService warrantyRecordService;
    @Resource
    private WarrantyService warrantyService;


    /**
     * 根据UUID查询该设备的报修记录
     *
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByUUID")
    public ResultInfo findByUUID(String uuid) {
        return new ResultInfo(StatusCode.SUCCESS, "success", warrantyRecordService.findAll(uuid));
    }


    /**
     * 续保
     *
     * @param warrantyRecord
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResultInfo save(WarrantyRecord warrantyRecord) {
        if (StringUtil.isEmpty(warrantyRecord.getId())) {
            warrantyRecord.setId(UuidUtil.get32UUID());
            warrantyRecord.setUserName(Jurisdiction.getUserName());
            warrantyRecord.setCreateTime(DateUtil.getTime());
        }
        warrantyRecordService.save(warrantyRecord);
        if (DateUtil.compare(warrantyRecord.getDeadline())) {
            warrantyService.updState(0, warrantyRecord.getUuid());
        }
        return new ResultInfo(StatusCode.SUCCESS, "续保成功！");
    }
}
