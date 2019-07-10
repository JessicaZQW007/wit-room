package com.yhxc.controller.aftersale;

import com.github.pagehelper.PageHelper;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.aftersale.Warranty;
import com.yhxc.entity.system.User;
import com.yhxc.service.aftersale.WarrantyRecordService;
import com.yhxc.service.aftersale.WarrantyService;
import com.yhxc.utils.Jurisdiction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/11 16:06
 */
@Controller
@RequestMapping("warranty")
public class WarrantyController {
    @Resource
    private WarrantyService warrantyService;
    @Resource
    private WarrantyRecordService warrantyRecordService;


    @ResponseBody
    @RequestMapping("/list")
    public ResultInfo list(Warranty warranty, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Warranty> list = warrantyService.list(warranty, pageNum, pageSize);
        com.github.pagehelper.PageInfo<Warranty> page = new com.github.pagehelper.PageInfo<Warranty>(list);
        com.yhxc.common.PageInfo pi = new com.yhxc.common.PageInfo();
        pi.setPageNum(page.getPageNum());
        pi.setPageSize(page.getPageSize());
        pi.setTotal(page.getTotal());
        //查询过期的保修记录，更新为1，过期
        List<Warranty> listW = warrantyService.listOverdue();
        for (Warranty warranty1 : listW) {
            warrantyService.updState(1, warranty1.getUuid());
        }
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getList(), pi);
    }
}
