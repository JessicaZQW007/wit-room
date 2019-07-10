package com.yhxc.service.company;

import com.yhxc.entity.company.Build;
import net.sf.json.JSONArray;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 建筑，房间 Service接口
 * @author lwg
 *
 */
public interface BuildService {

	/**
	 * 根据id查询
	 * @param id
	 */
	public Build findById(String id);

	/**
	 * 新增或者修改建筑，房间
	 * @param build
	 */
	public void save(Build build);
	
	/**  @param Page(页数)    PageSize（每页显示的条数） Company（实体类）
	 * 根据条件分页查询建筑，房间
	 * @param build
	 * @return
	 */
	public	Page<Build> listPage(Integer Page, Integer PageSize, Build build);


	/**根据id删除
	 * @param id
	 */
	public void delete(String id);

	/**根据企业id删除所有的建筑
	 * @param companyId
	 */
	public void deletesRoom(String companyId);

	/**建筑树形图(单个)
	 */
	public JSONArray buildTree(String projectId);


	public JSONArray buildTreeByProject();

	/**多个建筑查询房间号
	 * @param pids
	 */
	public List<Build> buildTreeList(String pids);

	/**
	 查询所有的建筑
	 */

	public JSONArray buildAllList(String name,String projectId);

	/**
	 查询所有的房间号
	 */

	public JSONArray roomAllList(String name,String projectId,String pid );


	/**
      根据父级查询房间号(单个建筑)
       */
	public List<Build> findRooms(String pid);



}
