package com.yhxc.service.project.impl;


import com.yhxc.entity.project.Project;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.project.ProjectService;
import com.yhxc.utils.AddressLngLatExchange;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目管理ServiceImpl实现类
 *
 * @author lwg
 */

@Service
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectRepository projectRepository;


	@Override
	public Project findById(String id) {
		return projectRepository.findOne(id);
	}

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public void delete(String id) {
		projectRepository.delete(id);
	}

	@Override
	public void save(Project project) {
		projectRepository.save(project);
	}

	@Override
	public JSONArray addressTree() {
		JSONArray jsonArray4 = new JSONArray();
		JSONObject jsonObject4 = new JSONObject();
		List<?> datas = projectRepository.findProvince();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			int num1 = 1000;
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("location", objects[1]);// 地方
			jsonObject.put("num", num1 + i);// id
			List<?> datas2 = projectRepository.findCity(objects[1].toString());
			JSONArray jsonArray2 = new JSONArray();
			for (int j = 0; j < datas2.size(); j++) {
				Object[] objects2 = (Object[]) datas2.get(j);
				JSONObject jsonObject2 = new JSONObject();
				String locations = (String) objects2[1];
				String[] strs = locations.split(",");

				int num2 = 2000;
				jsonObject2.put("location", strs[1].toString());// 地方
				jsonObject2.put("num", num2 + j);
				List<?> datas3 = projectRepository.findqu(objects2[1].toString());
				JSONArray jsonArray3 = new JSONArray();
				for (int k = 0; k < datas3.size(); k++) {
					int num3 = 4000;
					Object[] objects3 = (Object[]) datas3.get(k);
					JSONObject jsonObject3 = new JSONObject();
					String locations3 = (String) objects3[1];
					String[] strs3 = locations3.split(",");
					jsonObject3.put("location", strs3[2].toString());// 地方
					jsonObject3.put("num", num3 + k);
					jsonArray3.add(jsonObject3);
				}
				jsonObject2.put("childs", jsonArray3);
				jsonArray2.add(jsonObject2);

			}
			jsonObject.put("childs", jsonArray2);
			jsonArray.add(jsonObject);
		}
		jsonObject4.put("location","选择项目所在区域:");// 地方
		jsonObject4.put("num", 1);
		jsonObject4.put("childs",jsonArray);
		jsonArray4.add(jsonObject4);
		return jsonArray4;
	}

	@Override
	public JSONArray addressTreeOne() {

		List<?> datas = projectRepository.findProvince();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			int num1 = 1000;
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("location", objects[1]);// 地方
			jsonObject.put("num", num1 + i);// id
			List<?> datas2 = projectRepository.findCity(objects[1].toString());
			JSONArray jsonArray2 = new JSONArray();
			for (int j = 0; j < datas2.size(); j++) {
				Object[] objects2 = (Object[]) datas2.get(j);
				JSONObject jsonObject2 = new JSONObject();
				String locations = (String) objects2[1];
				String[] strs = locations.split(",");

				int num2 = 2000;
				jsonObject2.put("location", strs[1].toString());// 地方
				jsonObject2.put("num", num2 + j);
				List<?> datas3 = projectRepository.findqu(objects2[1].toString());
				JSONArray jsonArray3 = new JSONArray();
				for (int k = 0; k < datas3.size(); k++) {
					int num3 = 4000;
					Object[] objects3 = (Object[]) datas3.get(k);
					JSONObject jsonObject3 = new JSONObject();
					String locations3 = (String) objects3[1];
					String[] strs3 = locations3.split(",");
					jsonObject3.put("location", strs3[2].toString());// 地方
					jsonObject3.put("num", num3 + k);
					jsonArray3.add(jsonObject3);
				}
				jsonObject2.put("childs", jsonArray3);
				jsonArray2.add(jsonObject2);

			}
			jsonObject.put("childs", jsonArray2);
			jsonArray.add(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public JSONObject pageList(String projectType,String address, String pname, String allDate, int pageNum, int pageSize) {
		JSONObject jsonObject2 = new JSONObject();
		String startDate="";
		String endDate="";
		if(StringUtil.isNotEmpty(allDate)){
			startDate = allDate.substring(0, 10);
			endDate = allDate.substring(11, 21);
		}
		pageNum=(pageNum-1)*pageSize;
		List<?> datas = projectRepository.pageList( projectType,address,pname,startDate,endDate,pageNum,pageSize);
		int datascount = projectRepository.pageListCout( projectType,address,pname,startDate,endDate);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",objects[0]);//id
			jsonObject.put("pname", objects[1]);// 项目名
			jsonObject.put("address", objects[2]);//项目地址
			jsonObject.put("type", objects[3]);//项目类型
			jsonObject.put("equipment_num", objects[4]);//空调数据
			jsonObject.put("puser", objects[5]);//负责人
			jsonObject.put("puser_phone", objects[6]);//电话
			jsonObject.put("createtime", objects[7]);//创建时间
			jsonObject.put("status", objects[8]);//项目状态
			jsonObject.put("location", objects[9]);//经纬度
			jsonObject.put("img", objects[10]);//项目图片
			jsonObject.put("roomArea", objects[11]);//房间面积
			jsonObject.put("uuid", objects[12]);//设备uuid
			/*jsonObject.put("unitType", objects[13]);//机构类型
			jsonObject.put("unitId", objects[14]);//机构ID
			jsonObject.put("transrate", objects[15]);//互感器倍率
			jsonObject.put("voltage", objects[16]);//设备电压
*/
			jsonArray.add(jsonObject);
		}
		jsonObject2.put("datas",jsonArray);
		jsonObject2.put("datasCount",datascount);//总条数
		return jsonObject2;
	}

	@Override
	public int checkAddress(String address) {
		int num=0;
		String  location= AddressLngLatExchange.getLngLatFromOneAddr(address);//项目地址转化成经纬度
		if(StringUtil.isNotEmpty(location)){
			num=1;
		}else{
			num=0;
		}

		return num;
	}

	@Override
	public String addressLocatin(String address) {
		return null;
	}

	@Override
	public void untieEq(String id) {
		projectRepository.untieEq(id);
	}

	@Override
	public int findProjectAirNum(String pname) {
		return projectRepository.findProjectAirNum(pname);
	}

	@Override
	public JSONArray findprojectMessages(int userId) {
		List<?> datas = projectRepository.findprojectMessages( userId);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			Object[] objects = (Object[]) datas.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",objects[0]);//id
			jsonObject.put("pname", objects[1]);
			jsonObject.put("uuid", objects[2]);
			jsonObject.put("puser", objects[3]);
			jsonObject.put("puser_phone", objects[4]);
			jsonObject.put("nb_card", objects[5]);
			jsonObject.put("type", objects[6]);
			jsonObject.put("room_area", objects[7]);
			jsonObject.put("createtime", objects[8]);
			jsonObject.put("address", objects[9]);
			jsonObject.put("airNum", objects[10]);
			jsonObject.put("img", objects[11]);

			jsonArray.add(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public int findpame(String pname) {

		return projectRepository.findpame(pname);
	}

	@Override
	public void juebangEq(String eq_id) {
		 projectRepository.juebangEq(eq_id);
	}

	@Override
	public void bangdingEq(String eq_id, String projectId) {
		projectRepository.bangdingEq(eq_id,projectId);
	}

	@Override
	public Project findByEqId(String eq_id) {
		return projectRepository.findByEqId(eq_id);
	}

}
