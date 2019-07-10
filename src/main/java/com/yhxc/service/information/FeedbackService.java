package com.yhxc.service.information;

import com.yhxc.entity.information.Feedback;
import com.yhxc.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/29 16:30
 */
public interface FeedbackService extends BaseService<Feedback> {

    void release(MultipartFile uploadFile, Feedback feedback);

    void updDeal(Feedback feedback);

    long count(Feedback feedback);
}
