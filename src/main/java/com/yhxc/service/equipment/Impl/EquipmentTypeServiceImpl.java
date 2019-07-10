package com.yhxc.service.equipment.Impl;

import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.system.User;
import com.yhxc.repository.equipment.EquipmentTypeRepository;
import com.yhxc.service.equipment.EquipmentTypeService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/8 16:52
 */
@Service
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

    @Resource
    private EquipmentTypeRepository equipmentTypeRepository;

    @Override
    public void addBrand(String id, String brand, String createTime) {
        equipmentTypeRepository.addBrand(id, brand, createTime);
    }

    @Override
    public void addModel(String id, String model, String img, String pId, String createTime) {
        equipmentTypeRepository.addModel(id, model, img, pId, createTime);
    }

    @Override
    public void save(EquipmentType eb) {
        equipmentTypeRepository.save(eb);
    }

    @Override
    public List<EquipmentType> findAll(EquipmentType eb) {
        return equipmentTypeRepository.findAll(new Specification<EquipmentType>() {
            @Override
            public Predicate toPredicate(Root<EquipmentType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(eb, cb, root);
            }
        });
    }

    @Override
    public List<EquipmentType> findAll(String var,String brand) {
        return equipmentTypeRepository.findAll(new Specification<EquipmentType>() {
            @Override
            public Predicate toPredicate(Root<EquipmentType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                User user = Jurisdiction.getCurrentUser();
                Integer type = user.getType();

                if (StringUtil.isNotEmpty(var)) {
                    predicate.getExpressions().add(cb.equal(root.get("pId"), var));
                } else {
                    predicate.getExpressions().add(cb.notEqual(root.get("pId"), "-1"));
                }
                if (StringUtil.isNotEmpty(brand)) {
                    predicate.getExpressions().add(cb.like(root.get("brand"), "%" + brand + "%"));
                }
                query.orderBy(cb.desc(root.get("createtime").as(String.class)));
                return predicate;
            }
        });
    }

    public Predicate serach(EquipmentType eb, CriteriaBuilder cb, Root<EquipmentType> root) {
        Predicate predicate = cb.conjunction();
        User user = Jurisdiction.getCurrentUser();
        Integer type = user.getType();
        if (StringUtil.isNotEmpty(eb.getpId())) {
            predicate.getExpressions().add(cb.equal(root.get("pId"), eb.getpId()));
        }


        return predicate;
    }

    @Override
    public EquipmentType findById(String id) {
        return equipmentTypeRepository.findOne(id);
    }



    @Override
    public void delete(String id) {
        equipmentTypeRepository.delete(id);
    }

    @Override
    public void deleteBypId(String pId) {
        equipmentTypeRepository.deleteBypId(pId);
    }

    @Override
    public String findBrandId(String brand, String model,String ename) {

        return equipmentTypeRepository.findBrandId(brand, model,ename);
    }

    @Override
    public List<EquipmentType> findlist(String brand) {
        return equipmentTypeRepository.findlist(brand);
    }

    @Override
    public List<EquipmentType> findmodel(String id) {
        return equipmentTypeRepository.findmodel(id);
    }

    @Override
    public void unpdateModel(String brand, String id) {
        equipmentTypeRepository.unpdateModel(brand,id);
    }



    /**
     * 查询设备下的品牌型号
     *
     * @param
     */
    @Override
    public JSONArray findByPid(String pid) {
        JSONArray jsonArray = new JSONArray();
        List<?> datas = equipmentTypeRepository.findByPid(pid);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("brand",objects[1]);
            jsonObject.put("model",objects[2]);
            jsonArray.add(jsonObject);
        }



        return jsonArray;
    }


    @Override
    public void updateBrandAndModel(String brand,String model,String id) {
        equipmentTypeRepository.updateBrandAndModel(brand,model,id);
    }


    @Override
    public void updateEname(String newEname,String id) {
        equipmentTypeRepository.updateName(newEname,id);
    }



    @Override
    public int count(String brand,String model) {
       int num= equipmentTypeRepository.count(brand,model);

       return num;
    }


    @Override
    public int countEname(String ename) {
        int num= equipmentTypeRepository.countEname(ename);

        return num;
    }

    @Override
    public JSONArray findEname(String ename) {
        JSONArray jsonArray=new JSONArray();
        List<?> datas=equipmentTypeRepository.findEname(ename);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("ename",objects[1]);
            jsonArray.add(jsonObject);
        }



        return jsonArray;
    }


    @Override
    public JSONArray findEnameModelBrand() {
        JSONArray jsonArray=new JSONArray();

        //先查询出所有的设备名称
        List<?> datas=equipmentTypeRepository.findEname("");
        for (int i = 0; i < datas.size(); i++) {
            JSONArray jsonArray1=new JSONArray();
            JSONArray jsonArray2=new JSONArray();
            JSONObject jsonObject2=new JSONObject();
            JSONObject jsonObject3=new JSONObject();
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("id",objects[0]);
            jsonObject1.put("name",objects[1]);
            //根据ID查询出所有的型号
            List<?> datas1=equipmentTypeRepository.findByPid(objects[0].toString());
            for (int j = 0; j < datas1.size(); j++) {
                Object[] objects1 = (Object[]) datas1.get(j);

                jsonObject2.put("id",objects1[0]);
                jsonObject2.put("name",objects1[2]);


                //再根据型号查询出所有的品牌
                List<?> datas2=equipmentTypeRepository.findByModel(objects1[2].toString());
                for (int k = 0; k < datas2.size(); k++) {
                    Object[] objects2 = (Object[]) datas2.get(k);

                    jsonObject3.put("id",objects2[0]);
                    jsonObject3.put("name",objects2[1]);
                    jsonArray2.add(jsonObject3);
                }
                jsonObject2.put("children",jsonArray2);
                jsonArray1.add(jsonObject2);

            }
            jsonObject1.put("children",jsonArray1);
            jsonArray.add(jsonObject1);

        }





        return jsonArray;
    }

}
