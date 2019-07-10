package com.yhxc.service.send.impl;

import com.yhxc.entity.send.SetMessage;
import com.yhxc.repository.send.SetMessageRepository;
import com.yhxc.service.send.SetMessageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 16:03
 */
@Service("SetMessageService")
public class SetMessageServiceImpl implements SetMessageService {

    @Resource
    SetMessageRepository messageRepository;

    @Override
//    @Cacheable(value = "SystemCache",key = "0")
    public SetMessage findByUuidAndNum(String uuid, String num) {
        return messageRepository.findByUuidAndNum(uuid,num);
    }

    @Override
//    @CacheEvict(value = "SystemCache",beforeInvocation=true,allEntries=true) //缓存全清
//    @CacheEvict(value = "SystemCache",key = "0",beforeInvocation=true)
    public void save(SetMessage message) {
        messageRepository.save(message);
    }
}
