package com.yhxc.service.equipment;

import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.project.AirBrand;
import net.sf.json.JSONArray;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:52
 */
public interface EquipmentTypeService {

    void addBrand(String id, String brand, String createTime);

    void addModel(String id, String model, String img, String pId, String createTime);

    void save(EquipmentType eb);

    List<EquipmentType> findAll(EquipmentType eb);

    List<EquipmentType> findAll(String var,String brand);


    EquipmentType findById(String id);



    public void delete(String id);

    public void deleteBypId(String pId);



    /**根据品牌，型号查询 ID
     * @param brand
     * @param model
     */
    public String findBrandId( String brand,  String model ,String ename);



    /**根据品牌名称 查询

     * @param brand
     */
    List<EquipmentType> findlist(String brand);

    public List<EquipmentType> findmodel( String id);


    /**
     * 更新品牌
     * @param
     * @return
     */
    public void unpdateModel( String brand, String id);



    public JSONArray findByPid(String pid);


    /**
     * 更新品牌和型号信息
     * @param
     * @return
     */
    public void updateBrandAndModel( String brand,String model, String id);



    /**根据设备名称 型号，品牌查询该设备下是否存在该品牌和型号
     * @param
     */
    public int count (String brand,String model);


    /**查询设备名称是否存在
     * @param
     */
    public int countEname (String ename);


    /**查询设备名称是否存在
     * @param
     */
    public void updateEname (String newEname,String id);


    /**查询所有父Pid等于-1的设备名称
     * @param
     */
    public JSONArray findEname(String ename);


    /**下拉联动查询
     * @param
     */
    public JSONArray findEnameModelBrand();


}
