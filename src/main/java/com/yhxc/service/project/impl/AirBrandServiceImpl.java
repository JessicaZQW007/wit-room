package com.yhxc.service.project.impl;

import com.yhxc.entity.project.Air;
import com.yhxc.entity.project.AirBrand;
import com.yhxc.entity.system.InfraredCode;
import com.yhxc.entity.system.User;
import com.yhxc.repository.project.AirBrandRepository;
import com.yhxc.repository.project.AirRepository;
import com.yhxc.repository.system.InfraredCodeRepository;
import com.yhxc.service.project.AirBrandService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;
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
public class AirBrandServiceImpl implements AirBrandService {

    @Resource
    private AirBrandRepository airBrandRepository;
    @Resource
    private InfraredCodeRepository infraredCodeRepository;
    @Resource
    private AirRepository airRepository;


    @Override
    public void addBrand(String id, String brand, String createTime) {
        airBrandRepository.addBrand(id, brand, createTime);
    }

    @Override
    public void addModel(String id, String model, String img, String pId, String createTime) {
        airBrandRepository.addModel(id, model, img, pId, createTime);
    }

    @Override
    public void save(AirBrand eb) {
        airBrandRepository.save(eb);
    }

    @Override
    public List<AirBrand> findAll(AirBrand eb) {
        return airBrandRepository.findAll(new Specification<AirBrand>() {
            @Override
            public Predicate toPredicate(Root<AirBrand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return serach(eb, cb, root);
            }
        });
    }

    @Override
    public List<AirBrand> findAll(String var,String brand) {
        return airBrandRepository.findAll(new Specification<AirBrand>() {
            @Override
            public Predicate toPredicate(Root<AirBrand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

    public Predicate serach(AirBrand eb, CriteriaBuilder cb, Root<AirBrand> root) {
        Predicate predicate = cb.conjunction();
        User user = Jurisdiction.getCurrentUser();
        Integer type = user.getType();
        if (StringUtil.isNotEmpty(eb.getpId())) {
            predicate.getExpressions().add(cb.equal(root.get("pId"), eb.getpId()));
        }


        return predicate;
    }

    @Override
    public AirBrand findById(String id) {
        return airBrandRepository.findOne(id);
    }



    @Override
    public void delete(String id) {
        airBrandRepository.delete(id);
    }

    @Override
    public void deleteBypId(String pId) {
        airBrandRepository.deleteBypId(pId);
    }

    @Override
    public String findBrandId(String brand, String model) {
        return airBrandRepository.findBrandId(brand, model);
    }

    @Override
    public JSONArray findlist(String brand,String id) {
        JSONArray jsonArray=new JSONArray();
        List<AirBrand> datas=airBrandRepository.findlist(brand);
        for (int i = 0; i < datas.size(); i++) {

            Air air=airRepository.findByBrandId(datas.get(i).getId());

            if(air!=null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("airBrandID", datas.get(i).getId());
                jsonObject.put("brand", datas.get(i).getBrand());
                jsonObject.put("airType", air.getAirType());
                jsonObject.put("model", datas.get(i).getModel());
                jsonObject.put("airID", air.getId());
                jsonObject.put("power", air.getPower());
                jsonObject.put("current", air.getCurrent());
                jsonObject.put("pid", id);
                jsonObject.put("airBrandCreateTime",datas.get(i).getCreatetime());
                jsonObject.put("airCreateTime",air.getCreatetime());
                if (datas.get(i).getImg() == null) {
                    System.out.println("进了");
                    jsonObject.put("img", 0);
                } else {
                    jsonObject.put("img", datas.get(i).getImg());
                }


                jsonArray.add(jsonObject);

            }



        }

        return jsonArray;


    }

    @Override
    public void updateCodeId(String codeId, String id) {
        airBrandRepository.updateCodeId(codeId,id);
    }

    @Override
    public AirBrand findByBrandModel(String brand, String model) {
        return airBrandRepository.findByBrandModel(brand,model);
    }

    @Override
    public List<AirBrand> findModel(String id) {
        return airBrandRepository.findModel(id);
    }

    @Override
    public void unpdateModel(String brand, String id) {
        airBrandRepository.unpdateModel(brand,id);
    }



    @Override
    public void updateModelAndImg(String model,String img,String id) {
        airBrandRepository.updateModelAndImg(model,img,id);
    }

    @Override
    public JSONArray findOne(String brand,String model) {
        JSONArray jsonArray=new JSONArray();


        AirBrand airBrand=airBrandRepository.findByBrandAndModel(brand,model);




        System.out.println("airID="+airBrand.getId());
        System.out.println("img="+airBrand.getImg());

        String airID=airBrand.getId();
        Air air=airRepository.findByBrandId(airID);

        if(air!=null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("airBrandID", airBrand.getId());
            jsonObject.put("brand", airBrand.getBrand());
            jsonObject.put("airType", air.getAirType());
            jsonObject.put("model", airBrand.getModel());
            jsonObject.put("airID", air.getId());
            jsonObject.put("power", air.getPower());
            jsonObject.put("current", air.getCurrent());
            if (airBrand.getImg() == null) {
                System.out.println("进了");
                jsonObject.put("img", 0);
            } else {
                jsonObject.put("img", airBrand.getImg());
            }


            jsonArray.add(jsonObject);

        }


        return jsonArray;
    }


}