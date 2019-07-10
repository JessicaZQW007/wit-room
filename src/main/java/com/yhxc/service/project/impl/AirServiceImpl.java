package com.yhxc.service.project.impl;


import com.yhxc.entity.project.Air;
import com.yhxc.entity.project.Project;
import com.yhxc.repository.project.AirRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.project.AirService;
import com.yhxc.service.project.ProjectService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 空调管理ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class AirServiceImpl implements AirService {

    @Resource
    private AirRepository airRepository;


	@Override
	public JSONArray findById(String projectId) {
		List<?> datas = airRepository.findByproject(projectId);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", objects[0]);
			jsonObject.put("brand", objects[1]);//空调品牌
			jsonObject.put("model", objects[2]);//空调型号
			jsonObject.put("power", objects[3]);//空调功率
			jsonObject.put("airType", objects[4]);//空调类型
			jsonObject.put("current", objects[5]);//空调额定电流
			jsonObject.put("airName", objects[6]);//空调名称
			jsonObject.put("img", objects[7]);//空调图片
			jsonObject.put("createtime", objects[8]);//创建时间
			jsonObject.put("code", objects[9]);//创建时间
			jsonObject.put("modelId", objects[10]);//型号id
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	@Override
	public void delete(String id) {
        airRepository.delete(id);
	}

	@Override
	public void save(Air air) {
		airRepository.save(air);
	}

	@Override
	public Integer findBybrand_id(String brand_id) {
		return airRepository.findBybrand_id(brand_id);
	}

	@Override
	public void deleteAir(String projectId) {
		 airRepository.deleteAirByProjectId(projectId);
	}

	@Override
	public void updatePowerCuurrent(String power,String current,String airType,String airID) {
		airRepository.updatePowerCuurrent(power,current,airType,airID);
	}

	@Override
	public void deleteByBrandId(String id) {
		Air air=airRepository.findByBrandId(id);
		airRepository.delete(air.getId());
	}


}
