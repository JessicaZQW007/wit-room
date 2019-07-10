package com.yhxc.controller.equipment;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.EquipmentAgreement;
import com.yhxc.entity.system.User;
import com.yhxc.service.equipment.EquipmentAgreementService;
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
 * @Date: 2018/12/13 16:56
 */

@RequestMapping("equipmentAgreement")
@Controller
public class EquipmentAgreementController {

    @Resource
    private EquipmentAgreementService equipmentAgreementService;


    @RequestMapping("/listPage")
    @ResponseBody
    public ResultInfo listPage(EquipmentAgreement ea, Integer pageNum, Integer pageSize) {
        Page<EquipmentAgreement> page = equipmentAgreementService.listPage(ea, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getContent(), PageInfo.pageInfo(page));
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(EquipmentAgreement ea) {
        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentAgreementService.findAll(ea));
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(EquipmentAgreement ea) {
        if (StringUtil.isEmpty(ea.getId())) {
            ea.setId(UuidUtil.get32UUID());
            User user = Jurisdiction.getCurrentUser();
            ea.setCompanyId(user.getCompanyId());
            ea.setUserName(user.getUserName());
        } else {
            EquipmentAgreement equipmentAgreement = equipmentAgreementService.findById(ea.getId());
            ea.setCompanyId(equipmentAgreement.getCompanyId());
            ea.setUserName(equipmentAgreement.getUserName());

        }
        equipmentAgreementService.save(ea);
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo delete(String id) {
        equipmentAgreementService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }
}
