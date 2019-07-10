package com.yhxc.controller.company;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.Dealer;
import com.yhxc.entity.company.Organization;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.company.DealerService;
import com.yhxc.service.company.OrganizationService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 经销商
 */
@RequestMapping("/dealer")
@Controller
public class DealerController {
    @Resource
    private OrganizationService organizationService;
    @Resource
    private DealerService dealerService;
    @Resource
    private LogService logService;
    /**
     * 根据公司ID查询下面的经销商
     *
     * @return
     */
    @RequestMapping("/findByCompanyId")
    @ResponseBody
    public ResultInfo list(Dealer dealer) {
        User u = Jurisdiction.getCurrentUser();
        Dealer d = new Dealer();
        d.setCompanyId(u.getCompanyId());
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", dealerService.findAll(d),Jurisdiction.getQX());
    }





    /**
     * Page：第几页 ;PageSize:页内容的数量;Dealer 对象(经销商)
     *
     * @param @return
     * @return json
     * @author: lwg
     * @Title:分页查询
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public ResultInfo pageList(Integer Page, Integer PageSize, Dealer dealer) {
        String info = "";
        String companyId=Jurisdiction.getCurrentUser().getCompanyId();
        dealer.setCompanyId(companyId);
        Page<Dealer> datas = dealerService.listPage(Page, PageSize, dealer);
        PageInfo pi = new PageInfo();
        pi.setPageNum(datas.getNumber() + 1);
        pi.setPageSize(datas.getSize());
        pi.setTotal(datas.getTotalElements());
        pi.setSize(datas.getNumberOfElements());
        return new ResultInfo(StatusCode.SUCCESS, info, datas.getContent(), pi, Jurisdiction.getQX());

    }





    /**
     * 根据公司ID查询下面的经销商   地方——经销商 树形图
     *
     * @return
     */
    @RequestMapping("/dealerTree")
    @ResponseBody
    public ResultInfo dealerTree() {
        String companyId=Jurisdiction.getCurrentUser().getCompanyId();
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", dealerService.dealerTree(companyId));
    }


    /**
     * .
     * 新增或者修改
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResultInfo save(MultipartFile uploadFile,MultipartFile uploadFile1, Dealer dealer) throws IOException {
        String info = "";
        if (StringUtil.isEmpty(dealer.getId())) {
            dealer.setId(UuidUtil.get32UUID());
           dealer.setCreateTime(DateUtil.getTime());
            if (uploadFile != null && uploadFile.getSize() > 10) {
                dealer.setImg(UploadUtil.uploadFile(uploadFile, "dealer"));
            }
            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                dealer.setWeixinImg(UploadUtil.uploadFile(uploadFile1, "dealer"));
            }
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, "新增:" + dealer));
        } else {
            if (uploadFile != null && uploadFile.getSize() > 10) {
                FileUtil.deleteDir(dealer.getImg(), Const.fileRoot + "dealer");
                dealer.setImg(UploadUtil.uploadFile(uploadFile, "dealer"));
            } else {
                String img = dealer.getImg();
                dealer.setImg(img);
            }
            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                FileUtil.deleteDir(dealer.getWeixinImg(), Const.fileRoot + "dealer");
                dealer.setWeixinImg(UploadUtil.uploadFile(uploadFile1, "dealer"));
            } else {
                String img = dealer.getWeixinImg();
                dealer.setWeixinImg(img);
            }
            info = "更新成功！";
            logService.save(new Log(Log.UPDATE_ACTION, "更新:" + dealer));
        }
        dealerService.save(dealer);

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
        Dealer dealer = dealerService.findById(id);
        Organization o = new Organization();
        o.setDealerId(dealer.getId());
        if (organizationService.findAll(o).size() > 0) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！该经销商下有机构！请删除相关联机构");
        } else {
            FileUtil.deleteDir(dealer.getImg(), Const.fileRoot + "dealer");
            FileUtil.deleteDir(dealer.getWeixinImg(), Const.fileRoot + "dealer");
            dealerService.delete(id);
            return new ResultInfo(StatusCode.SUCCESS, "删除成功！");

        }
    }
    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteMany")
    public ResultInfo deleteMany(String ids) {
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                Dealer dealer = dealerService.findById(idStr[i]);
                FileUtil.deleteDir(dealer.getImg(), Const.fileRoot + "dealer");
                FileUtil.deleteDir(dealer.getWeixinImg(), Const.fileRoot + "dealer");
                dealerService.delete(idStr[i]);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }

    /**
     * 根据id查询经销商信息
     *.000000
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/findOne")
    public ResultInfo findOne(String id) {
        Dealer data = dealerService.findById(id);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功", data, null);
    }



}
