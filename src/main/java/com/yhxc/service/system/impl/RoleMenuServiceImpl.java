package com.yhxc.service.system.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhxc.entity.system.RoleMenu;
import com.yhxc.repository.system.RoleMenuRepository;
import com.yhxc.service.system.RoleMenuService;

/**
 * 角色权限菜单关联Service实现类
 * @author yhxc 赵贺飞
 *
 */
@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService{

	@Resource
	private RoleMenuRepository roleMenuRepository;

	@Override
	public void deleteByRoleId(Integer roleId) {
		roleMenuRepository.deleteByRoleId(roleId);
	}

	@Override
	public void save(RoleMenu roleMenu) {
		roleMenuRepository.save(roleMenu);
	}
	
	
}
