package com.yhxc.controller.information;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.aftersale.Repair;
import com.yhxc.entity.information.Feedback;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.information.FeedbackService;
import com.yhxc.service.system.LogService;
import com.yhxc.utils.DateUtil;
import com.yhxc.utils.Jurisdiction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/29 16:32
 */

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    @Resource
    private FeedbackService feedbackService;
    @Resource
    private LogService logService;



    /**
     * 查询当前未处理的反馈数量
     * @return
     */
    @RequestMapping("/count")
    @ResponseBody
    public ResultInfo count() {
        Feedback feedback = new Feedback();
        feedback.setState(0);
        feedback.setCreateTime(DateUtil.getDay());
        long count = feedbackService.count(feedback);
        return new ResultInfo(StatusCode.SUCCESS, "success", count);
    }


    /**
     * 当前登录的用户发布过的反馈列表
     *
     * @param feedback
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/findAll")
    public ResultInfo findAll(Feedback feedback, Integer pageNum, Integer pageSize) {
        User user = Jurisdiction.getCurrentUser();
        feedback.setUserName(user.getUserName());
        Page<Feedback> page = feedbackService.listPage(feedback, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", page.getContent(), PageInfo.pageInfo(page));
    }

    /**
     * 查看所有人的反馈信息
     * @param feedback
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/listAll")
    public ResultInfo listAll(Feedback feedback, Integer pageNum, Integer pageSize) {
        Page<Feedback> page = feedbackService.listPage(feedback, pageNum, pageSize);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", page.getContent(), PageInfo.pageInfo(page));
    }

    /**
     * 发布反馈
     *
     * @param uploadFile
     * @param feedback
     * @return
     */
    @ResponseBody
    @RequestMapping("/release")
    public ResultInfo save(MultipartFile uploadFile, Feedback feedback) {
        feedbackService.release(uploadFile, feedback);
        logService.save(new Log(Log.ADD_ACTION, "提交反馈"));
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS");
    }



    /**
     * 删除一条反馈
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResultInfo delete(String id) {
        feedbackService.delete(id);
        logService.save(new Log(Log.DELETE_ACTION, "删除反馈"));
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS");
    }


    /**
     * 处理反馈
     *
     * @param feedback
     * @return
     */
    @ResponseBody
    @RequestMapping("/deal")
    public ResultInfo updDeal(Feedback feedback) {
        feedback.setDealUserName(Jurisdiction.getUserName());
        feedback.setDealTime(DateUtil.getTime());
        feedbackService.updDeal(feedback);
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS");
    }
}
