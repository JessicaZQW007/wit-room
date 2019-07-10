package com.yhxc.service.aftersale.impl;

import com.yhxc.entity.aftersale.InspectionRecord;
import com.yhxc.mapper.inspection.InspectionRecordMapper;
import com.yhxc.repository.aftersale.InspectionRecordRepository;
import com.yhxc.service.aftersale.InspectionRecordService;
import com.yhxc.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 10:46
 */

@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {
    @Resource
    private InspectionRecordRepository inspectionRecordRepository;
    @Resource
    private InspectionRecordMapper inspectionRecordMapper;

    @Override
    public List<InspectionRecord> findAll() {
        return null;
    }

    @Override
    public List<InspectionRecord> findAll(String var) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return inspectionRecordRepository.findAll(new Specification<InspectionRecord>() {
            @Override
            public Predicate toPredicate(Root<InspectionRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtil.isNotEmpty(var)) {
                    predicate.getExpressions().add(cb.equal(root.get("inspectionId"), var));
                }
                return predicate;
            }
        }, sort);
    }

    @Override
    public List<InspectionRecord> findAll(InspectionRecord obj) {
        return null;
    }

    @Override
    public InspectionRecord findById(String id) {
        return null;
    }

    @Override
    public InspectionRecord findById(Integer id) {
        return null;
    }

    @Override
    public Page<InspectionRecord> listPage(InspectionRecord object, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public void delete(String id) {
        inspectionRecordRepository.delete(id);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void save(InspectionRecord var) {
        inspectionRecordRepository.save(var);
    }

    @Override
    public List<InspectionRecord> listRecord(InspectionRecord inspectionRecord, Integer pageNum, Integer pageSize) {
        return inspectionRecordMapper.listRecord(inspectionRecord);
    }

    @Override
    public List<InspectionRecord> listAllRecord(InspectionRecord inspectionRecord, Integer pageNum, Integer pageSize) {
        return inspectionRecordMapper.listAllRecord(inspectionRecord);
    }

    @Override
    public int updProblem(String problem,String createTime, String id) {
        return inspectionRecordRepository.updProblem(problem,createTime, id);
    }

    @Override
    public void deleteByinspectionId(String inspectionId) {
        inspectionRecordRepository.delete(inspectionId);
    }
}
