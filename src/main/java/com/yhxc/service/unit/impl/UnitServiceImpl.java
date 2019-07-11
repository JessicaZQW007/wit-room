package com.yhxc.service.unit.impl;


import com.yhxc.entity.unit.Unit;
import com.yhxc.repository.unit.UnitRepository;
import com.yhxc.service.unit.UnitService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 陈霖炎
 * @Date: 2019/7/5 10:32
 */
@Service("unitService")
public class UnitServiceImpl implements UnitService{

    @Resource
    private UnitRepository unitRepository;


    @Override
    public List<Unit> findAllListPage(String type, String name, int pageNum, int pageSize ){
        List<Unit> unitList=unitRepository.findAllListPage(type,name,pageNum,pageSize);



        return unitList;
    }



    @Override
    public int findAllListCount(String type, String name ){
        int num=unitRepository.findAllListCount(type,name);


        return num;
    }


    @Override
    public Unit findOneById(String id ){
        Unit unit=unitRepository.findOneById(id);


        return unit;
    }


    @Override
    public int findByName(String type, String name){
        int num=unitRepository.findByName(type,name);


        return num;
    }




    @Override
    public void save(Unit unit){
        unitRepository.save(unit);
    }

    @Override
    public void delete(String id){
        unitRepository.delete(id);
    }

    @Override
    public void updateState(int state,String id){
        unitRepository.updateState(state,id);
    }


    //查询状态为正常(state 0)的数据
    @Override
    public JSONArray findNameList(String type){
        JSONArray jsonArray=new JSONArray();
        List<?> datas=unitRepository.findByType(type,0);
        for (int i = 0; i < datas.size(); i++) {
            Object[] objects = (Object[]) datas.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("name",objects[1]);
            jsonArray.add(jsonObject);
        }



        return  jsonArray;
    }


    /*//下拉联动 查询状态为正常(state 0)的数据
    @Override
    public JSONArray findNameAll(){
        JSONArray jsonArray=new JSONArray();
        //分为平台单位 1和项目机构 2
        for (int i = 1; i <=2; i++) {
            JSONArray jsonArray1=new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            if(i==1){
                //平台单位
                jsonObject1.put("id","1");
                jsonObject1.put("name","平台单位");
                List<?> datas=unitRepository.findByType(String.valueOf(i),0);
                for (int j = 0; j < datas.size(); j++) {
                    Object[] objects = (Object[]) datas.get(j);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject2.put("id",objects[0]);
                    jsonObject2.put("name",objects[1]);
                    jsonArray1.add(jsonObject2);
                }
                jsonObject1.put("children",jsonArray1);
            }

            if(i==2){
                //机构单位
                jsonObject1.put("id","2");
                jsonObject1.put("name","项目机构");
                List<?> datas=unitRepository.findByType(String.valueOf(i),0);
                for (int j = 0; j < datas.size(); j++) {
                    Object[] objects = (Object[]) datas.get(j);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject2.put("id",objects[0]);
                    jsonObject2.put("name",objects[1]);
                    jsonArray1.add(jsonObject2);
                }
                jsonObject1.put("children",jsonArray1);
            }

            jsonArray.add(jsonObject1);

        }





        return jsonArray;
    }*/


    //下拉联动 查询状态为正常(state 0)的数据
    @Override
    public JSONArray findNameAll(){
        JSONArray jsonArray=new JSONArray();
        //先查询所有平台 再查询平台下的机构
        List<?> datas=unitRepository.findByType("1",0);
        if(datas.size()!=0) {
            for (int j = 0; j < datas.size(); j++) {
                Object[] objects = (Object[]) datas.get(j);
                JSONArray jsonArray1 = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", objects[0]);
                jsonObject.put("name", objects[1]);
                List<?> datas1 = unitRepository.findByPId(objects[0].toString(), 0);
                if (datas1.size() != 0) {
                    for (int k = 0; k < datas1.size(); k++) {
                        JSONObject jsonObject1 = new JSONObject();
                        Object[] objects2 = (Object[]) datas1.get(k);
                        jsonObject1.put("id", objects2[0]);
                        jsonObject1.put("name", objects2[1]);
                        jsonArray1.add(jsonObject1);
                    }

                }


                jsonObject.put("children", jsonArray1);
                jsonArray.add(jsonObject);
            }
        }




        return jsonArray;
    }








    @Override
    public Unit findByNameType(String name,String type){
        Unit unit=unitRepository.findByNameType(name,type);

        return  unit;
    }





}
