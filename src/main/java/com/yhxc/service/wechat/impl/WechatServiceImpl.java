package com.yhxc.service.wechat.impl;

import com.yhxc.entity.wechat.WeChat;
import com.yhxc.repository.wechat.WechatRepository;
import com.yhxc.service.wechat.WechatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 赵贺飞
 * @Date: 2018/8/22 15:23
 */
@Service
public class WechatServiceImpl implements WechatService {

    @Resource
    private WechatRepository wechatRepository;

    @Override
    public void save(WeChat weChat) {
        wechatRepository.save(weChat);
    }
}
