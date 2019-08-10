package com.yhxc.service.information;

import com.yhxc.entity.information.Instruct;


import java.util.List;

public interface InstructService {

    public void save(Instruct instruct);

    public List<Instruct> findInstruct();

    public Instruct findByPassword(String password);
}
