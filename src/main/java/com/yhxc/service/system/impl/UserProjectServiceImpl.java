package com.yhxc.service.system.impl;


import com.yhxc.entity.system.UserProject;
import com.yhxc.entity.system.UserRole;
import com.yhxc.repository.system.UserProjectRepository;
import com.yhxc.repository.system.UserRoleRepository;
import com.yhxc.service.system.UserProjectService;
import com.yhxc.service.system.UserRoleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色关联Service实现类
 * @author yhxc lwg
 *
 */
@Service
@Transactional
public class UserProjectServiceImpl implements UserProjectService {

	@Resource
	private UserProjectRepository userProjectRepository;


	@Override
	public void save(UserProject userProject) {
		userProjectRepository.save(userProject);
	}

	@Override
	public void deleteByuserId(Integer user_id) {
		userProjectRepository.deleteByuserId(user_id);
	}


	@Override
	public void deleteByprojectId(String project_id) {
		userProjectRepository.deleteByprojectId(project_id);
	}

	@Override
	public JSONArray findbangdingEq(Integer userId,String uuid) {

		List<?> datas = userProjectRepository.findbangdingEq(userId,uuid);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", objects[0]);//项目id
			jsonObject.put("uuid", objects[1]);//设备uuid
			jsonObject.put("pname", objects[2]);//项目名
			jsonObject.put("name", objects[3]);//设备名
			jsonObject.put("brand", objects[4]);//设备品牌
			jsonObject.put("model", objects[5]);//设备型号
			jsonObject.put("nb_card", objects[6]);//设备nb卡号
			jsonObject.put("puser", objects[7]);//联系人
			jsonObject.put("puser_phone", objects[8]);//联系人电话
			jsonObject.put("equipment_num", objects[9]);//空调数量
			jsonObject.put("type", objects[10]);//项目类型
			jsonObject.put("room_area", objects[11]);//房间面积
			jsonObject.put("createtime", objects[12]);//房间面积
			jsonObject.put("address", objects[13]);//房间面积
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	@Override
	public JSONArray findbangdingEqNo(Integer userId,String uuid) {
		List<?> datas = userProjectRepository.findbangdingEqNo(userId,uuid);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", objects[0]);//项目id
			jsonObject.put("uuid", objects[1]);//设备uuid
			jsonObject.put("pname", objects[2]);//项目名
			jsonObject.put("name", objects[3]);//设备名
			jsonObject.put("brand", objects[4]);//设备品牌
			jsonObject.put("model", objects[5]);//设备型号
			jsonObject.put("nb_card", objects[6]);//
			jsonObject.put("address", objects[7]);//地址

			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	@Override
	public void deleteByuserIdProjectId(Integer user_id, String project_id) {
		userProjectRepository.deleteByuserIdProjectId(user_id,project_id);
	}
}
