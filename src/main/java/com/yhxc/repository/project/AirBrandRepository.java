package com.yhxc.repository.project;

import com.yhxc.entity.project.AirBrand;
import com.yhxc.entity.system.InfraredCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**空调品牌型号Repository
 */
public interface AirBrandRepository extends JpaRepository<AirBrand, String>, JpaSpecificationExecutor<AirBrand> {



    @Query(value="delete from p_ari_brand where p_id=?1",nativeQuery=true)
    @Modifying
    @Transactional
    public void deleteBypId(String pId);

    /**查询品牌型号查询红外码
     * @param
     */
    @Query(value = "SELECT * from   p_ari_brand where brand=:brand  and model=:model ", nativeQuery = true)
    public AirBrand findByBrandModel(@Param("brand") String brand,@Param("model") String model);

    /**
     * 新增品牌
     *
     * @param id
     * @param brand
     * @param createTime
     */
    @Transactional
    @Query(value = "INSERT INTO p_ari_brand value(:id, :brand, null, null, '-1', :createTime)", nativeQuery = true)
    void addBrand(@Param("id") String id, @Param("brand") String brand, @Param("createTime") String createTime);


    /**
     * 新增型号
     *
     * @param id
     * @param model
     * @param createTime
     */
    @Transactional
    @Query(value = "INSERT INTO p_ari_brand value(:id, null, :model, null, :img, :pId, :createTime)", nativeQuery = true)
    void addModel(@Param("id") String id, @Param("model") String model, @Param("img") String img, @Param("pId") String pId, @Param("createTime") String createTime);


    /**根据品牌，型号查询 ID
     * @param brand
     * @param model
     */
    @Query(value = " select id from  p_ari_brand  where brand=:brand  and   model=:model  ", nativeQuery = true)
    public String findBrandId(@Param("brand") String brand,@Param("model") String model);
    /**
     * 更新品牌
     *
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE p_ari_brand  SET brand=:brand WHERE p_id=:id ", nativeQuery = true)
    void unpdateModel(@Param("brand") String brand, @Param("id") String id);



    /**根据品牌，查询
     * @param brand
     */
    @Query(value = " select * from  p_ari_brand  where brand=:brand and (model !='' and model is not null )  ", nativeQuery = true)
    public List<AirBrand> findlist(@Param("brand") String brand);


    /**根据品牌id，查询 型号
     * @param id
     */
    @Query(value = " select * from  p_ari_brand  where p_id=:id ", nativeQuery = true)
    public List<AirBrand> findModel(@Param("id") String id);



    /**
     *保存红外码
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE p_ari_brand SET  code_id=:codeId  WHERE id =:id  ", nativeQuery = true)
    public void updateCodeId(@Param("codeId") String codeId,@Param("id") String id);



    @Query(value = "  SELECT *   FROM    (SELECT * from t_infrared_code GROUP BY brand)  tb   order by tb.id ASC  ", nativeQuery = true)
    public List<?> gertt();



    /**根据品牌名称和型号查询
     * @param
     */
    @Query(value = "select * from  p_ari_brand  where brand=:brand and model=:model ", nativeQuery = true)
    public AirBrand findByBrandAndModel(@Param("brand") String brand,@Param("model") String model);



    /**修改品牌型号和图片
     * @param
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE p_ari_brand set model = :model, img = :img where id = :id ", nativeQuery = true)
    public void updateModelAndImg(@Param("model") String model,@Param("img") String img,@Param("id") String id);






}
