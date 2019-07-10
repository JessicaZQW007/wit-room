package com.yhxc.service.equipment.Impl;

import com.yhxc.entity.equipment.Equipment;
import com.yhxc.mapper.equipment.EquipmentMapper;
import com.yhxc.repository.equipment.EquipmentRepository;
import com.yhxc.repository.equipment.EquipmentTypeRepository;
import com.yhxc.repository.project.ProjectRepository;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:52
 */

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Resource
    private EquipmentRepository equipmentRepository;
    @Resource
    private EquipmentTypeRepository equipmentTypeRepository;

    @Resource
    private ProjectRepository projectRepository;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public void delete(String id) {
        equipmentRepository.delete(id);
    }

    @Override
    public void saveOrUpdate(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Override
    public Equipment findById(String id) {
        return equipmentRepository.findOne(id);
    }

    @Override
    public void updState(String uuid) {
        equipmentRepository.updState(uuid);
    }

    @Override
    public Equipment findByUUID(String uuid) {
        return equipmentRepository.findByUUID(uuid);
    }

    @Override
    public long count(Equipment equipment) {
        return equipmentRepository.count(new Specification<Equipment>() {
            @Override
            public Predicate toPredicate(Root<Equipment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(equipment, cb, root);
            }
        });
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<Equipment> findAll(Equipment equipment) {
        return equipmentRepository.findAll(new Specification<Equipment>() {
            @Override
            public Predicate toPredicate(Root<Equipment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(equipment, cb, root);
            }
        });
    }


    public Predicate serach(Equipment equipment, CriteriaBuilder cb, Root<Equipment> root) {
        Predicate predicate = cb.conjunction();
        if (equipment != null) {
            if (StringUtil.isNotEmpty(equipment.getUuid())) {
                predicate.getExpressions().add(cb.equal(root.get("uuid"), equipment.getUuid().trim()));
            }
            if (StringUtil.isNotEmpty(equipment.getName())) {
                predicate.getExpressions().add(cb.like(root.get("name"), "%" + equipment.getName().trim() + "%"));
            }if (StringUtil.isNotEmpty(equipment.getEqTypeId())) {
                predicate.getExpressions().add(cb.equal(root.get("eqTypeId"), equipment.getEqTypeId().trim()));
            }
        }
        return predicate;
    }

    @Override
    public List<Equipment> listEquipmentResPage(Equipment equipment, Integer pageNum, Integer pageSize) {
        return equipmentMapper.listEquipmentRes(equipment);
    }


    @Override
    public List<Equipment> listEquipmentRes(Equipment equipment) {
        return equipmentMapper.listEquipmentRes(equipment);
    }


    /**
     * 根据房间号查询设备
     *
     * @param roomId
     */
    @Override
    public JSONArray findByRoomId(String roomId) {
        List<?> datas = equipmentRepository.findByRoomId(roomId);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", objects[0]);
            jsonObject.put("name", objects[1]);
            jsonObject.put("brand", objects[2]);
            jsonObject.put("model", objects[3]);
            jsonObject.put("active_time", objects[4]);
            jsonObject.put("production_time", objects[5]);
            jsonObject.put("img", objects[6]);
            jsonObject.put("id", objects[7]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;

    }

    /**
     * 项目管理中，项目设备的信息，设备总数
     *
     * @param projectId
     */
    @Override
    public JSONArray eqMessage(String projectId) {
        List<?> eqTypeData = equipmentRepository.findEqType(projectId);//设备类型
        List<?> eqBrandData = equipmentRepository.findEqBrand(projectId);//设备品牌型号

        JSONArray jsonArray = new JSONArray();
        String eqType = "";//设备类型
        String eqBrand = "";//设备品牌
        String eqModel = "";//设备型号

        for (int i = 0; i < eqTypeData.size(); i++) {
            Object[] objects = (Object[]) eqTypeData.get(i);
            eqType += (String) objects[1] + ",";
        }

        for (int i = 0; i < eqBrandData.size(); i++) {
            Object[] objects = (Object[]) eqBrandData.get(i);
            eqBrand += (String) objects[0] + ",";
            eqModel += (String) objects[1] + ",";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eqType", eqType.substring(0, eqType.length() - 1));
        jsonObject.put("eqBrand", eqBrand.substring(0, eqBrand.length() - 1));
        jsonObject.put("eqModel", eqModel.substring(0, eqModel.length() - 1));


        jsonArray.add(jsonObject);


        return jsonArray;
    }

    /**
     * 查询所有的设备名称
     *
     * @param
     */
    @Override
    public JSONArray findUUidNameList(String ename) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas = equipmentRepository.findUuidName(ename);//查询出设备UUid和设备名称Name
        for (int i = 0; i < datas.size(); i++) {

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",datas.get(i));
           jsonArray.add(jsonObject);
        }



        return jsonArray;
    }



    @Override
    public void deleted(String id) {
        equipmentRepository.delete(id);
    }

    @Override
    public void reduction(String id) {
        equipmentRepository.reduction(id);
    }



    @Override
    public String eqcount(String projectId) {
        return equipmentRepository.eqcount(projectId);
    }

    @Override
    public List<Map> equipmentsheng() {
        return equipmentMapper.equipmentsheng();
    }

    @Override
    public List<Map> equipmentshi() {
        return equipmentMapper.equipmentshi();
    }

    @Override
    public List<Map> equipmentqu() {
        return equipmentMapper.equipmentqu();
    }


    @Override
    public JSONObject findEqStatus() {
        List<?> datas=  equipmentRepository.findEqStatus();
        JSONObject json = new JSONObject();
        int  online=0;
        int  offline=0;
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            System.out.println(objects[0].toString());
            if(objects[0].toString().equals("1")){
                online=Integer.parseInt(objects[1].toString());
            }
            if(objects[0].toString().equals("2")){
                offline=Integer.parseInt(objects[1].toString());
            }

        }
        json.put("online",online);
        json.put("offline",offline);
        return json;

    }

    @Override
    public JSONObject findIndexData() {
        JSONObject json = new JSONObject();
        int projectNum=projectRepository.ProjectNum();
        int eqNum=equipmentRepository.eqNum();
        int airNum=projectRepository.airNum();
        json.put("projectNum",projectNum);
        json.put("eqNum",eqNum);
        json.put("airNum",airNum);
        return json;
    }

    @Override
    public JSONObject pageList(String projectType, String address, String pname, String allDate,String runStatus, int pageNum, int pageSize) {
        JSONObject jsonObject2 = new JSONObject();
        String startDate="";
        String endDate="";
        if(StringUtil.isNotEmpty(allDate)){
            startDate = allDate.substring(0, 10);
            endDate = allDate.substring(11, 21);
        }
        pageNum=(pageNum-1)*pageSize;
        List<?> datas = equipmentRepository.findrunStatusPage( projectType,address,pname,startDate,endDate,runStatus,pageNum,pageSize);
        int datascount = equipmentRepository.findrunStatusCount( projectType,address,pname,startDate,endDate,runStatus);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pname",objects[0]);//id
            jsonObject.put("address", objects[1]);// 项目名
            jsonObject.put("createtime", objects[2]);//项目地址
            jsonObject.put("img", objects[3]);//项目类型
            jsonObject.put("puser", objects[4]);//空调数据
            jsonObject.put("puser_phone", objects[5]);
            jsonObject.put("equipment_num", objects[6]);
            jsonObject.put("room_area", objects[7]);
            jsonObject.put("type", objects[8]);
            jsonObject.put("runStatus", objects[9]);
            jsonObject.put("uuid", objects[10]);
            jsonObject.put("location", objects[11]);
            jsonObject.put("id", objects[12]);
            jsonObject.put("nb_card", objects[13]);

            jsonArray.add(jsonObject);
        }
        jsonObject2.put("datas",jsonArray);
        jsonObject2.put("datasCount",datascount);//总条数
        return jsonObject2;
    }

    @Override
    public List<String> finduuidByGroup(String group_name) {
        return equipmentRepository.finduuidByGroup(group_name);
    }

    @Override
    public void updateGroup_name(String group_name, String uuid) {
         equipmentRepository.updateGroup_name(group_name,uuid);
    }



    //修改设备名称
    @Override
    public void updateName(String newName, String name) {
        equipmentRepository.updateName(newName,name);
        equipmentTypeRepository.updateName(newName,name);
    }

    @Override
    public JSONArray findByuuidGroup(String uuid) {
        List<?> datas = equipmentRepository.findByuuidGroup(uuid);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("group_name", objects[0]);
            jsonObject.put("display_name", objects[1]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findweiBanding(String uuid) {
        List<?> datas = equipmentRepository.findweiBanding(uuid);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", objects[0]);
            jsonObject.put("uuid", objects[1]);
            jsonObject.put("name", objects[2]);
            jsonObject.put("brand", objects[3]);
            jsonObject.put("model", objects[4]);
            jsonObject.put("nb_card", objects[5]);
            jsonObject.put("eq_id", objects[6]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }



    @Override
    public void updateTransrate(int transrate,int voltage,String id){
        equipmentRepository.updateTransrate(transrate,voltage,id);

    }

}
