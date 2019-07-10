package com.yhxc.controller.equipment;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.EquipmentAgreement;
import com.yhxc.entity.equipment.EquipmentProtocol;
import com.yhxc.entity.system.User;
import com.yhxc.service.equipment.EquipmentProtocolService;
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

@RequestMapping("equipmentProtocol")
@Controller
public class EquipmentProtocolController {

    @Resource
    private EquipmentProtocolService equipmentProtocolService;


    @RequestMapping("/listPage")
    @ResponseBody
    public ResultInfo listPage(EquipmentProtocol ep, Integer pageNum, Integer pageSize) {
        Page<EquipmentProtocol> page = equipmentProtocolService.listPage(ep, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getContent(), PageInfo.pageInfo(page));
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(EquipmentProtocol ep) {
        return new ResultInfo(StatusCode.SUCCESS, "success", equipmentProtocolService.findAll(ep));
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(EquipmentProtocol ep) {
        if (StringUtil.isEmpty(ep.getId())) {
            ep.setId(UuidUtil.get32UUID());
            User user = Jurisdiction.getCurrentUser();
            ep.setCompanyId(user.getCompanyId());
            ep.setUserName(user.getUserName());
        } else {
            EquipmentProtocol equipmentProtocol = equipmentProtocolService.findById(ep.getId());
            ep.setCompanyId(equipmentProtocol.getCompanyId());
            ep.setUserName(equipmentProtocol.getUserName());
        }
        equipmentProtocolService.save(ep);
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo delete(String id) {
        equipmentProtocolService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }
}
