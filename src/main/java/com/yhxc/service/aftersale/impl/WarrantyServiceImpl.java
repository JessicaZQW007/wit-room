package com.yhxc.service.aftersale.impl;

import com.yhxc.entity.aftersale.Warranty;
import com.yhxc.mapper.warranty.WarrantyMapper;
import com.yhxc.repository.aftersale.WarrantyRepository;
import com.yhxc.service.aftersale.WarrantyService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/11 16:05
 */
@Service
public class WarrantyServiceImpl implements WarrantyService {
    @Resource
    private WarrantyMapper warrantyMapper;

    @Resource
    private WarrantyRepository warrantyRepository;

    @Override
    public List<Warranty> findAll() {
        return null;
    }

    @Override
    public List<Warranty> findAll(String var) {
        return null;
    }

    @Override
    public List<Warranty> findAll(Warranty obj) {
        return null;
    }

    @Override
    public Warranty findById(String id) {
        return null;
    }

    @Override
    public Warranty findById(Integer id) {
        return null;
    }

    @Override
    public Page<Warranty> listPage(Warranty object, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(Warranty var) {

    }

    @Override
    public List<Warranty> list(Warranty warranty, Integer pageNum, Integer pageSize) {
        return warrantyMapper.listWarranty(warranty);
    }

    @Override
    public List<Warranty> list(Warranty warranty) {
        return warrantyMapper.listWarranty(warranty);
    }

    @Override
    public List<Warranty> listOverdue() {
        return warrantyMapper.listOverdue();
    }

    @Override
    public int updState(Integer state, String uuid) {
        return warrantyRepository.updState(state, uuid);
    }
}
