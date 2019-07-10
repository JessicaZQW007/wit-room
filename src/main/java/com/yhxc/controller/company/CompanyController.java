package com.yhxc.controller.company;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.company.Company;
import com.yhxc.entity.company.CompanyType;
import com.yhxc.entity.company.Dealer;
import com.yhxc.entity.system.Log;
import com.yhxc.service.company.BuildService;
import com.yhxc.service.company.CompanyService;
import com.yhxc.service.company.CompanyTypeService;
import com.yhxc.service.company.DealerService;
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
 * 企业管理Controller
 *
 * @Author:李文光
 */
@RequestMapping("/company")
@Controller
public class CompanyController {
    @Resource
    private DealerService dealerService;
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyTypeService companyTypeService;
    @Resource
    private LogService logService;

    /**
     * Page：第几页 ;PageSize:页内容的数量;Company 对象
     *
     * @param @return
     * @return json
     * @author: lwg
     * @Title: 分页查询
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public ResultInfo pageList(Integer Page, Integer PageSize, Company company) {
        String info = "";
            Page<Company> datas = companyService.listPage(Page, PageSize, company);
            PageInfo pi = new PageInfo();
            pi.setPageNum(datas.getNumber() + 1);
            pi.setPageSize(datas.getSize());
            pi.setTotal(datas.getTotalElements());
            pi.setSize(datas.getNumberOfElements());
            return new ResultInfo(StatusCode.SUCCESS, info, datas.getContent(), pi, Jurisdiction.getQX());


    }

    
    @ResponseBody
    @RequestMapping("/findAll")
    public ResultInfo findAll() {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", companyService.findAll());
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
    public ResultInfo save(MultipartFile uploadFile,MultipartFile uploadFile1, Company company) throws IOException {
        String info = "";
        if (StringUtil.isEmpty(company.getId())) {
            company.setId(UuidUtil.get32UUID());
            company.setCreatetime(DateUtil.getTime());
            if (uploadFile != null && uploadFile.getSize() > 10) {
                company.setImg(UploadUtil.uploadFile(uploadFile, "company"));
            }
            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                company.setWeixinImg(UploadUtil.uploadFile(uploadFile1, "company"));
            }
            info = "新增成功";
            logService.save(new Log(Log.ADD_ACTION, "新增:" + company));
        } else {
            if (uploadFile != null && uploadFile.getSize() > 10) {
                FileUtil.deleteDir(company.getImg(), Const.fileRoot + "company");
                company.setImg(UploadUtil.uploadFile(uploadFile, "company"));
            } else {
                String img = company.getImg();
                company.setImg(img);
            }
            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                FileUtil.deleteDir(company.getWeixinImg(), Const.fileRoot + "company");
                company.setWeixinImg(UploadUtil.uploadFile(uploadFile1, "company"));
            } else {
                String img = company.getWeixinImg();
                company.setWeixinImg(img);
            }

            info = "更新成功！";
            logService.save(new Log(Log.UPDATE_ACTION, "更新:" + company));
        }
        companyService.save(company);

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
        Company company = companyService.findById(id);
        Dealer d = new Dealer();
        d.setCompanyId(company.getId());

        if (dealerService.findAll(d).size()>0){
            return new ResultInfo(StatusCode.FAIL, "删除失败！该公司下有经销商！请删除相关联经销商");
        }
        else{
            FileUtil.deleteDir(company.getImg(), Const.fileRoot + "company");
            FileUtil.deleteDir(company.getWeixinImg(), Const.fileRoot + "company");
            companyService.delete(id);
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
                Company company = companyService.findById(idStr[i]);
                FileUtil.deleteDir(company.getImg(), Const.fileRoot + "company");
                FileUtil.deleteDir(company.getWeixinImg(), Const.fileRoot + "company");
                companyService.delete(idStr[i]);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }

    /**
     * 根据id查询公司信息
     *.000000
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/findOne")
    public ResultInfo findOne(String id) {
        Company data = companyService.findById(id);
        return new ResultInfo(StatusCode.SUCCESS, "查询成功", data, null);
    }

    /**
     * 根据idg更改状态
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateStatus")
    public ResultInfo updateStatus(String id) {
        companyService.updateStatus(id);
        return new ResultInfo(StatusCode.SUCCESS, "更改成功");
    }



    /**
     * 查询所有的公司类型
     *
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findAllCompanyType")
    public ResultInfo findAllCompanyType() {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", companyTypeService.findAll());
    }


    /**
     * 新增公司类型
     *
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveCompanyType")
    public ResultInfo saveCompanyType(CompanyType companyType) {
     String   info="";
        if(StringUtil.isEmpty(companyType.getId())){
            companyType.setId(UuidUtil.get32UUID());
            info="新增成功";
        }else{
            info="修改成功";
        }
        companyTypeService.save(companyType);
        return new ResultInfo(StatusCode.SUCCESS, info);
    }

    /**
     * 删除的公司类型
     *
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteCompanyType")
    public ResultInfo deleteCompanyType(String id) {

        companyTypeService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "删除成功" );
    }






}
