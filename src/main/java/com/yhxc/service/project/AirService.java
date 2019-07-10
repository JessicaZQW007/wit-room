package com.yhxc.service.project;


import com.yhxc.entity.project.Air;
import net.sf.json.JSONArray;

import java.util.List;

/**
 * 空调管理Service接口
 * @author lwg
 *
 */
public interface AirService {

	/**
	 * 根据ProjectId查询 空调
	 * @param projectId
	 */
	public JSONArray findById(String projectId);



	/**根据id删除
	 * @param id
	 */
	public void delete(String id);

	/**新增空调
	 * @param air
	 */
	public void save(Air air);

	/**根据品牌型号id查询空调
	 * @param
	 */
	public Integer findBybrand_id(String brand_id);


	/**根据id删除
	 * @param
	 */
	public void deleteAir(String projectId);



	public void updatePowerCuurrent(String power,String current,String airType,String airID);

	public void deleteByBrandId(String id);

}
