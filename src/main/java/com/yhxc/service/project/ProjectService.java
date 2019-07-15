package com.yhxc.service.project;


import com.yhxc.entity.project.Project;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 项目管理Service接口
 * @author lwg
 *
 */
public interface ProjectService {

	/**
	 * 根据id查询项目
	 * @param id
	 */
	public Project findById(String id);


	public List<Project> findAll();

	/**根据id删除
	 * @param id
	 */
	public void delete(String id);

	/**新增项目
	 * @param project
	 */
	public void save(Project project);


	/**项目地址树形图
	 *
	 */
	public JSONArray addressTree(String pId,String unitId);


	/**项目地址树形图（省 市 区）
	 *
	 */
	public JSONArray addressTreeOne(String pId,String unitId);

	/**查询所有的项目（分页）
	 *
	 */

	public JSONObject pageList( String projectType,String address, String pname, String allDate,String pId,String unitId, int pageNum, int pageSize);


	/**校验输入地址是否正确
	 *
	 */
	public int checkAddress(String address);



	/**查询地址的坐标
	 *
	 */
	public String addressLocatin(String address);


	/**解绑设备
	 *
	 */

	public void untieEq( String id);

	/**空调数量
	 *
	 */

	public int findProjectAirNum( String pname);

	/**
	 *查询app登录用户负责的项目
	 *
	 */
	public JSONArray findprojectMessages(int userId);
	/**
	 * 查询项目名存不存在

	 */
	public int findpame( String pname);

	/**
	 *解绑设备
	 *
	 * @return
	 * @author: 李文光
	 */
	public void juebangEq(String eq_id);

	/**
	 *绑定设备
	 *
	 * @return
	 * @author: 李文光
	 */
	public void bangdingEq( String eq_id, String projectId);


	/**
	 * 根据设备id查询项目信息

	 */
	public Project findByEqId(String eq_id);


	/**
	 * 多条件分权限查询所有项目
	 */
	public List<Project> findList(String pId,String unitId);


}
