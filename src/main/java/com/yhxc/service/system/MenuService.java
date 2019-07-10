package com.yhxc.service.system;

import java.util.List;

import com.yhxc.entity.system.Menu;
import net.sf.json.JSONArray;
import org.springframework.data.jpa.repository.Query;

/**
 * 权限菜单Service实现类
 * @author yhxc 赵贺飞
 *
 */
public interface MenuService {


	public List<Integer> findId();

	/**
	 * 添加菜单
	 * @param menu
	 */
	public void save(Menu menu);

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Menu findById(Integer id);
	
	/**
	 * 根据id获取权限菜单集合
	 * @return
	 */
	public List<Menu> findByRoleId(int roleId);


	public List<Menu> findByRoleIdNotPid(int roleId);
	
	/**
	 * 根据父节点以及角色id集合查询子节点
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public List<Menu> findByParentIdAndRoleId(int type, int parentId,int roleId);
	
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Menu> findByParentId(int parentId);

	public List<Menu> findByParentIdAndRoleId(int parentId, int roleId);

	public List<Menu> findByMenuPid(int menuPid, int roleId);

	public List<Menu> findByMenuPid2(int menuPid);

	/**
	 * 平台权限菜单
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public JSONArray getAllMenuByParentId_PT(Integer type, Integer parentId, Integer roleId);

	/**
	 * 用户权限菜单
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public JSONArray getAllMenuByParentId_YH(Integer type, Integer parentId, Integer roleId);


	public List<Menu> findByMenuLevel1(Integer type);

	public List<Menu> findByMenuLevel2();

	public List<Menu> findByMenu();


	/**
	 * 获取所有菜单
	 * @return
	 */
	public JSONArray getAllMenu();
}
