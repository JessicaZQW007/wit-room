package com.yhxc.repository.wechat;

import com.yhxc.entity.wechat.WeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 赵贺飞
 * @Date: 2018/8/22 15:21
 */
public interface WechatRepository extends JpaRepository<WeChat, Integer>,JpaSpecificationExecutor<WeChat> {
}
