package com.yhxc.service.send.impl;

import com.yhxc.repository.send.CommandRepository;
import com.yhxc.service.send.CommandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/27 14:58
 */

@Service("CommandService")
public class CommandServiceImpl implements CommandService {

    @Resource
    private CommandRepository commandRepository;

    @Override
    public List<String> findByMessageidAndUuid(String messageid, String uuid) {
        return commandRepository.findByMessageidAndUuid(messageid, uuid);
    }
}
