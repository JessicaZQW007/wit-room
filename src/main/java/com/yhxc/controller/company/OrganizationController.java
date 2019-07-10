package com.yhxc.controller.company;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.Dealer;
import com.yhxc.entity.company.Organization;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.company.OrganizationService;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 机构
 */
@RequestMapping("/organization")
@Controller
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;
    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    /**
     * 根据经销商ID查询下面的机构
     *
     * @return
     */
    @RequestMapping("/findByDealerId")
    @ResponseBody
    public ResultInfo list() {
        User u = Jurisdiction.getCurrentUser();
        Organization o = new Organization();
        o.setDealerId(u.getDealerId());
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", organizationService.findAll(o));
    }


    /**
     * 根据id查询机构用户信息
     * .000000
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/findOne")
    public ResultInfo findOne(String id) {
        Organization data = organizationService.findById(id);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功", data, null);
    }

    /**
     * Page：第几页 ;PageSize:页内容的数量;organization 对象(机构用户)
     *
     * @param @return
     * @return json
     * @author: lwg
     * @Title:分页查询
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public ResultInfo pageList(Integer Page, Integer PageSize, Organization organization) {
        String info = "";
        String dealerId = Jurisdiction.getCurrentUser().getDealerId();
        organization.setDealerId(dealerId);
        Page<Organization> datas = organizationService.listPage(Page, PageSize, organization);
        PageInfo pi = new PageInfo();
        pi.setPageNum(datas.getNumber() + 1);
        pi.setPageSize(datas.getSize());
        pi.setTotal(datas.getTotalElements());
        pi.setSize(datas.getNumberOfElements());
        return new ResultInfo(StatusCode.SUCCESS, info, datas.getContent(), pi, Jurisdiction.getQX());

    }


    /**
     * .
     * 新增或者修改
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResultInfo save(MultipartFile uploadFile, MultipartFile uploadFile1, Organization organization) throws IOException {
        String info = "";
        if (StringUtil.isEmpty(organization.getId())) {
            organization.setId(UuidUtil.get32UUID());
            organization.setCreateTime(DateUtil.getTime());
            if (uploadFile != null && uploadFile.getSize() > 10) {
                organization.setImg(UploadUtil.uploadFile(uploadFile, "organization"));
            }
            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                organization.setWeixinImg(UploadUtil.uploadFile(uploadFile1, "organization"));
            }
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, "新增:" + organization));
        } else {
            if (uploadFile != null && uploadFile.getSize() > 10) {
                FileUtil.deleteDir(organization.getImg(), Const.fileRoot + "organization");
                organization.setImg(UploadUtil.uploadFile(uploadFile, "organization"));
            } else {
                String img = organization.getImg();
                organization.setImg(img);
            }
            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                FileUtil.deleteDir(organization.getWeixinImg(), Const.fileRoot + "organization");
                organization.setWeixinImg(UploadUtil.uploadFile(uploadFile1, "organization"));
            } else {
                String img = organization.getWeixinImg();
                organization.setWeixinImg(img);
            }
            info = "更新成功！";
            logService.save(new Log(Log.UPDATE_ACTION, "更新:" + organization));
        }
        organizationService.save(organization);

        return new ResultInfo(StatusCode.SUCCESS, info);
    }

    /**
     * 删除单个
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResultInfo delete(String id) {
        Organization organization = organizationService.findById(id);
        List<User> users = userService.listOrganizationByOrganization(organization.getId());

        if (users.size() > 0) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！该机构下有终端用户！请删除相关联终端用户");
        } else {
            FileUtil.deleteDir(organization.getImg(), Const.fileRoot + "organization");
            FileUtil.deleteDir(organization.getWeixinImg(), Const.fileRoot + "organization");
            organizationService.delete(id);
            return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
        }

    }


}
