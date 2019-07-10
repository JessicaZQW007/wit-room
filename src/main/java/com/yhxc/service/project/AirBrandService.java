package com.yhxc.service.project;

import com.yhxc.entity.project.AirBrand;
import com.yhxc.entity.system.InfraredCode;
import net.sf.json.JSONArray;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:52
 */
public interface AirBrandService {

    void addBrand(String id, String brand, String createTime);

    void addModel(String id, String model, String img, String pId, String createTime);

    void save(AirBrand eb);

    List<AirBrand> findAll(AirBrand eb);

    List<AirBrand> findAll(String var,String brand);


    AirBrand findById(String id);


    /**查询空调型号等详细信息
     *
     */
    public JSONArray findOne(String brand,String model);


    public void delete(String id);

    public void deleteBypId(String pId);



    /**根据品牌，型号查询 ID
     * @param brand
     * @param model
     */
    public String findBrandId( String brand,  String model);



    /**根据品牌名称 查询

     * @param brand
     */
    public JSONArray findlist(String brand,String id);

    /**保存红外码Id

     * @param codeId,id
     */

    public void updateCodeId(@Param("codeId") String codeId, @Param("id") String id);

    /**查询红外码

     * @param brand,model
     */
    public AirBrand findByBrandModel( String brand, String model);


    public List<AirBrand> findModel(String id);

    /**
     * 更新品牌
     * @param
     * @return
     */
    public void unpdateModel( String brand, String id);


    public void updateModelAndImg(String model,String img,String id);
}
