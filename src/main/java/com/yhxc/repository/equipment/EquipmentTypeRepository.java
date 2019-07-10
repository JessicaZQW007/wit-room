package com.yhxc.repository.equipment;

import com.yhxc.entity.equipment.EquipmentType;
import com.yhxc.entity.project.AirBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/31 14:51
 */
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, String>, JpaSpecificationExecutor<EquipmentType> {



    @Query(value="delete from t_equipment_type where p_id=?1",nativeQuery=true)
    @Modifying
    @Transactional
    public void deleteBypId(String pId);

    /**
     * 更新品牌
     *
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE t_equipment_type  SET brand=:brand WHERE p_id=:id ", nativeQuery = true)
    void unpdateModel(@Param("brand") String brand, @Param("id") String id);


    /**
     * 新增品牌
     *
     * @param id
     * @param brand
     * @param createTime
     */
    @Transactional
    @Query(value = "INSERT INTO t_equipment_type value(:id, :brand, null, null, '-1', :createTime)", nativeQuery = true)
    void addBrand(@Param("id") String id, @Param("brand") String brand, @Param("createTime") String createTime);


    /**
     * 新增型号
     *
     * @param id
     * @param model
     * @param createTime
     */
    @Transactional
    @Query(value = "INSERT INTO t_equipment_type value(:id, null, :model, null, :img, :pId, :createTime)", nativeQuery = true)
    void addModel(@Param("id") String id, @Param("model") String model, @Param("img") String img, @Param("pId") String pId, @Param("createTime") String createTime);


    /**根据品牌，型号，设备名称查询 ID
     * @param brand
     * @param model
     */
    @Query(value = " select id from  t_equipment_type  where " +
            "if(:brand !='', brand=:brand, 1=1 ) " +
            "and if(:model !='', model=:model, 1=1 ) " +
            "and if(:ename !='', ename=:ename, 1=1 ) ", nativeQuery = true)
    public String findBrandId(@Param("brand") String brand,@Param("model") String model,@Param("ename") String ename);


    /**根据品牌，查询
     * @param brand
     */
    @Query(value = " select * from  t_equipment_type  where brand=:brand and (model !='' and model is not null )  ", nativeQuery = true)
    public List<EquipmentType> findlist(@Param("brand") String brand);


    /**根据品牌id，查询型号
     * @param id
     */
    @Query(value = " select * from  t_equipment_type  where p_id=:id  ", nativeQuery = true)
    public List<EquipmentType> findmodel(@Param("id") String id);



    /**根据pid 查询型号品牌等信息
     * @param
     */
    @Query(value = " select id,brand,model from  t_equipment_type  where p_id=:pid  ", nativeQuery = true)
    public List<?> findByPid(@Param("pid") String pid);


    /**根据型号 查询型号下的品牌
     * @param
     */
    @Query(value = " select id,brand,model from  t_equipment_type  where model=:model  ", nativeQuery = true)
    public List<?> findByModel(@Param("model") String model);


    /**修改品牌和型号
     * @param
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_equipment_type set brand = :brand, model = :model where id = :id ", nativeQuery = true)
    public void updateBrandAndModel(@Param("brand") String brand,@Param("model") String model,@Param("id") String id);


    /**根据设备名称 型号，品牌查询该设备下是否存在该品牌和型号
     * @param
     */
    @Query(value = " select count(*) from  t_equipment_type  where  brand=:brand and model=:model  ", nativeQuery = true)
    public int count(@Param("brand") String brand,@Param("model") String model);



    /**查询设备名称是否存在
     * @param
     */
    @Query(value = " select count(*) from  t_equipment_type  where ename=:ename  ", nativeQuery = true)
    public int countEname(@Param("ename") String ename);


    /**
     * 根据设备名称修改设备名称
     * @param
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_equipment_type set ename = :newEname where id = :id ", nativeQuery = true)
    public void updateName(@Param("newEname") String newEname,@Param("id") String id);


    /**查询所有父Pid等于-1的设备名称
     * @param
     */
    @Query(value = " select id,ename from  t_equipment_type  where p_id=-1 " +
            "And if(:ename != '' , ename LIKE CONCAT('%',:ename,'%') , 1 = 1) ", nativeQuery = true)
    public List<?> findEname(@Param("ename") String ename);


    /**
     * 多条件查询所有设备
     * @return
     */
    @Query(value = "SELECT * from t_equipment e,t_unit u, t_equipment_type et where e.eq_type_id=et.id and e.unit_id=u.id " +
            "AND if(:ename !='', ename=:ename, 1=1 ) " +
            "AND if(:brand !='', brand=:brand, 1=1 ) " +
            "AND if(:model !='', model=:model, 1=1 )" , nativeQuery = true)
    public String findByEnameBrandModel(@Param("ename")String ename,@Param("brand")String brand,@Param("model")String model);


}
