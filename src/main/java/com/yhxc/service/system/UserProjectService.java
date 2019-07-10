package com.yhxc.service.system;


import com.yhxc.entity.system.UserProject;
import net.sf.json.JSONArray;

/**
 * 用户项目关联Service接口
 * @author yhxc lwg
 *
 */
public interface UserProjectService {

	/**
	 * 添加或者修改用户项目关联
	 * @param userProject
	 */
	public void save(UserProject userProject);


	/**
	 * 根据用户id删除所有关联信息
	 * @param user_id
	 */
	public void deleteByuserId(Integer user_id);

	/**
	 * 根据项目Id删除所有关联信息
	 * @param project_id
	 */
	public void deleteByprojectId(String project_id);

	/**根据用户id查询绑定的设备信息
	 */
	public JSONArray findbangdingEq(Integer userId,String uuid);

	/**根据用户id查询未绑定的设备信息
	 */
	public JSONArray findbangdingEqNo(Integer userId,String uuid);
	/**
	 * 根据用户id，项目id 删除所有关联信息
	 * @param user_id
	 */

	public void deleteByuserIdProjectId(Integer user_id,String project_id);
}
