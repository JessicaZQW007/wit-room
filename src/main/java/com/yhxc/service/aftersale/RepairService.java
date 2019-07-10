package com.yhxc.service.aftersale;

import com.yhxc.entity.aftersale.Repair;
import com.yhxc.service.BaseService;

import java.util.List;

public interface RepairService extends BaseService<Repair> {

    List<Repair> list(Repair repair, Integer pageNum, Integer pageSize);

    List<Repair> list(Repair repair);

    long count(Repair repair);
}
