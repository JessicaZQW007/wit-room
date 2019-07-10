package com.yhxc.service.system;

import java.util.List;

import com.yhxc.common.ResultInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.yhxc.entity.system.Role;

/**
 * 角色Service实现类
 * @author yhxc 赵贺飞
 *
 */
public interface RoleService {

	/**
	 * 根据用户id查询角色
	 * @param id
	 * @return
	 */
	public Role findByUserId(Integer id);
	
	/**
	 * 根据id查询实体
	 * @return
	 */
	public Role findById(Integer id);
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<Role> listAll();
	
	/**
	 * 根据条件分页查询角色信息
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public Page list(Role role, Integer page, Integer pageSize, Direction direction, String... properties);

	/**
	 * 添加或者修改角色信息
	 * @param role
	 */
	public void save(Role role);
	
	/**
	 * 根据id删除角色
	 * @param id
	 */
	public void delete(Integer id);


	void updAdd(String adds, Integer id);
	void updDel(String dels, Integer id);
	void updEdit(String edits, Integer id);
	void updQuery(String querys, Integer id);
}
