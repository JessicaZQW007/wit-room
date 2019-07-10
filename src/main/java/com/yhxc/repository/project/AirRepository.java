package com.yhxc.repository.project;


import com.yhxc.entity.project.Air;
import com.yhxc.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 空调 管理Repository
 * @author lwg
 *
 */
public interface AirRepository extends JpaRepository<Air, String>,JpaSpecificationExecutor<Air>{



    /**根据项目ID查询项目下的空调信息
     * @param project_id
     */
    @Query(value = "  select p.id,b.brand,b.model,p.power,p.air_type,p.current,p.air_name,ifnull(b.img,''), p.createtime,ifnull((SELECT code from t_infrared_code where id=b.code_id),'' ) ,p.brand_id    from p_air p,p_ari_brand b where p.brand_id=b.id and p.project_id=:project_id    ORDER BY p.air_name asc  ", nativeQuery = true)
    public List<?> findByproject(@Param("project_id") String project_id);




    /**根据ID查询项目下的空调信息
     * @param brand_id
     */
    @Query(value = "  select count(p.id) from p_air p where   p.brand_id=:brand_id   ", nativeQuery = true)
    public Integer findBybrand_id(@Param("brand_id") String brand_id);



    /**
     * 删除
     * @param
     * @return
     */
    @Query(value = "DELETE  FROM  p_air  WHERE project_id = :project_id", nativeQuery = true)
    @Transactional
    @Modifying
    public void deleteAirByProjectId(@Param("project_id") String project_id);




    /**根据项目ID查询项目下的空调电流
     * @param pname
     */
    @Query(value = " select p.current,p.air_name    from p_air p,p_project a where a.id=p.project_id and a.pname=:pname  ", nativeQuery = true)
    public List<?> findCurrent(@Param("pname") String pname);


    /**根据品牌ID查询空调信息
     * @param
     */
    @Query(value = "select * from  p_air where brand_id=:brandId and air_name=0 ", nativeQuery = true)
    public Air findByBrandId(@Param("brandId") String brandId );


    /**修改空调信息
     * @param
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE p_air set power = :power, current = :current,air_type=:airType where id = :airID ", nativeQuery = true)
    public void updatePowerCuurrent(@Param("power") String power,@Param("current") String current,@Param("airType") String airType,@Param("airID")String airID);

}
