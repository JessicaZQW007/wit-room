package com.yhxc.service.system.impl;

import com.yhxc.entity.system.InfraredCode;
import com.yhxc.repository.system.InfraredCodeRepository;
import com.yhxc.service.system.InfraredCodeService;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 红外代码Service实现类
 *
 * @author yhxc
 */
@Service
public class InfraredCodeServiceImpl implements InfraredCodeService {

    @Resource
    private InfraredCodeRepository infraredCodeRepository;


    @Override
    public void save(InfraredCode infraredCode) {
     infraredCodeRepository.save(infraredCode);
    }

    @Override
    public InfraredCode findById(Integer id) {
        return infraredCodeRepository.findOne(id);
    }

    @Override
    public Page<InfraredCode> list(InfraredCode infraredCode, Integer page, Integer pageSize, Direction direction, String... properties) {
        Pageable pageable = new PageRequest(page - 1, pageSize, direction, properties);
        Page<InfraredCode> listInfraredCode = infraredCodeRepository.findAll(new Specification<InfraredCode>() {
            @Override
            public Predicate toPredicate(Root<InfraredCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (infraredCode != null) {
                    if (infraredCode.getBrand() != null && StringUtil.isNotEmpty(infraredCode.getBrand())) {
                        predicate.getExpressions().add(cb.like(root.get("brand"), "%" + infraredCode.getBrand() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
        return listInfraredCode;
    }

    @Override
    public void delete(Integer id) {
        infraredCodeRepository.delete(id);
    }

    @Override
    public List<InfraredCode> findByBrand(String brand) {
        return infraredCodeRepository.findByBrand(brand);
    }



}
