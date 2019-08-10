package com.yhxc.service.information.impl;

import com.yhxc.entity.information.Instruct;
import com.yhxc.entity.information.jobGroup;
import com.yhxc.repository.information.InstructRepository;
import com.yhxc.service.information.InstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("instructService")
public class InstructServiceImpl implements InstructService{

    @Autowired
    private InstructRepository instructRepository;


    @Override
    public void save(Instruct instruct) {
        instructRepository.save(instruct);
    }


    @Override
    public List<Instruct> findInstruct() {
        return instructRepository.findAll();
    }

    @Override
    public Instruct findByPassword(String password){
        return instructRepository.findByPassword(password);
    }


}
