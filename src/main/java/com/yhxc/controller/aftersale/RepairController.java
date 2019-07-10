package com.yhxc.controller.aftersale;

import com.github.pagehelper.PageHelper;
import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.aftersale.Repair;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.aftersale.RepairService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/3/22 12:01
 */
@RequestMapping("/repair")
@Controller
public class RepairController {

    @Resource
    private RepairService repairService;
    @Resource
    private LogService logService;


    /**
     * 查询当前未处理的报修申请
     * @return
     */
    @RequestMapping("/count")
    @ResponseBody
    public ResultInfo count() {
        Repair repair = new Repair();
        repair.setState(0);
        repair.setCreateTime(DateUtil.getDay());
        long count = repairService.count(repair);
        return new ResultInfo(StatusCode.SUCCESS, "success", count);
    }


    /**我的报修记录
     * @param repair
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public ResultInfo findAll(Repair repair, Integer pageNum, Integer pageSize) {
        repair.setUserName(Jurisdiction.getUserName());
        Page<Repair> page = repairService.listPage(repair, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getContent(), PageInfo.pageInfo(page));
    }


    /**
     * 所有人的报修申请
     * @param repair
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public ResultInfo listAll(Repair repair, Integer pageNum, Integer pageSize) {
        Page<Repair> page = repairService.listPage(repair, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "success", page.getContent(), PageInfo.pageInfo(page));
    }


    /**
     * 提交报修申请
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo save(Repair repair, MultipartFile uploadFile1, MultipartFile uploadFile2) {
        if (StringUtil.isEmpty(repair.getId())) {
            User user = Jurisdiction.getCurrentUser();
            repair.setId(UuidUtil.get32UUID());
            repair.setUserName(user.getUserName());
            repair.setCreateTime(DateUtil.getTime());
            repair.setState(0);

            if (uploadFile1 != null && uploadFile1.getSize() > 10) {
                repair.setImg1(UploadUtil.uploadFile(uploadFile1, "repair"));
            }

            if (uploadFile2 != null && uploadFile2.getSize() > 10) {
                repair.setImg2(UploadUtil.uploadFile(uploadFile2, "repair"));
            }
        }
        logService.save(new Log(Log.ADD_ACTION, "提交报修申请"));
        repairService.save(repair);
        return new ResultInfo(StatusCode.SUCCESS, "提交成功！");
    }


    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo delete(String id){
        logService.save(new Log(Log.DELETE_ACTION, "删除报修申请"));
        repairService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "提交成功！");
    }

    /**
     * 分派
     *
     * @param repair
     * @return
     */
    @RequestMapping("/accept")
    @ResponseBody
    public ResultInfo accept(Repair repair) {
        String id = repair.getId();
        if (StringUtil.isNotEmpty(id)) {
            Repair r = repairService.findById(id);
            r.setAcceptName(repair.getAcceptName());
            r.setAcceptNum(repair.getAcceptNum());
            r.setAcceptPhone(repair.getAcceptPhone());
            r.setState(1);
            r.setAcceptTime(DateUtil.getTime());
            repairService.save(r);
        }
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS");
    }

    /**
     * 处理
     *
     * @return
     */
    @RequestMapping("/deal")
    @ResponseBody
    public ResultInfo deal(Repair repair) {
        String id = repair.getId();
        if (StringUtil.isNotEmpty(id)) {
            Repair r = repairService.findById(id);
            r.setSignature(Jurisdiction.getCurrentRealName());
            r.setRemark(repair.getRemark());
            r.setState(2);
            r.setFinishTime(DateUtil.getTime());

            repairService.save(r);
        }
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS");
    }

}
