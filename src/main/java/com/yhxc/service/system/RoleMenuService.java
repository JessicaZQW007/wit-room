package com.yhxc.service.system;

import com.yhxc.entity.system.RoleMenu;

/**
 * 角色权限关联Service接口
 * @author yhxc 赵贺飞
 *
 */
public interface RoleMenuService {

	/**
	 * 根据角色id删除所有关联信息
	 * @param id
	 */
	public void deleteByRoleId(Integer roleId);
	
	/**
	 * 保存
	 * @param roleMenu
	 */
	public void save(RoleMenu roleMenu);
}
