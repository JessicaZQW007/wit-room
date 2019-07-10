package com.yhxc.controller.financial;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.financial.Cash;
import com.yhxc.entity.system.User;
import com.yhxc.service.financial.CashService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/13 17:44
 */
@RequestMapping("cash")
@Controller
public class CashController {
    @Resource
    private CashService cashService;


    /**
     * 查询当前机构或企业的设置
     *
     * @return
     */
    @RequestMapping("/findCash")
    @ResponseBody
    public ResultInfo findCash() {
        return new ResultInfo(StatusCode.SUCCESS, "success", cashService.findCurrent());
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(Cash cash) {
        if (StringUtil.isEmpty(cash.getId())) {
            cash.setId(UuidUtil.get32UUID());
            User user = Jurisdiction.getCurrentUser();
            cash.setCompanyId(user.getCompanyId());
            cash.setUserName(user.getUserName());
        } else {
            Cash c = cashService.findById(cash.getId());
            cash.setCompanyId(c.getCompanyId());
            cash.setUserName(c.getUserName());
        }

        cashService.save(cash);
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }
}
