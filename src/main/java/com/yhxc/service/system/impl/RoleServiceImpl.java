package com.yhxc.service.system.impl;

import com.yhxc.entity.system.Role;
import com.yhxc.repository.system.RoleRepository;
import com.yhxc.service.system.RoleService;
import com.yhxc.utils.StringUtil;
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
 * 角色Service实现类
 *
 * @author yhxc 赵贺飞
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public Role findByUserId(Integer id) {
        return roleRepository.findByUserId(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public void updAdd(String adds, Integer id) {
        roleRepository.updAdd(adds, id);
    }

    @Override
    public void updDel(String dels, Integer id) {
        roleRepository.updDel(dels, id);
    }

    @Override
    public void updEdit(String edits, Integer id) {
        roleRepository.updEdit(edits, id);
    }

    @Override
    public void updQuery(String querys, Integer id) {
        roleRepository.updQuery(querys, id);
    }


    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page list(Role role, Integer page, Integer pageSize, Direction direction, String... properties) {
        Pageable pageable = new PageRequest(page - 1, pageSize, direction, properties);
        Page<Role> pageUser = roleRepository.findAll(new Specification<Role>() {

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (role != null) {
                    if (StringUtil.isNotEmpty(role.getName())) {
                        predicate.getExpressions().add(cb.like(root.get("name"), "%" + role.getName().trim() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);

        return pageUser;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(id);
    }
}
