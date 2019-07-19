package com.yhxc.repository.unit;


import com.yhxc.entity.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, String>,JpaSpecificationExecutor<Unit> {

    //    查询平台单位或者项目机构的全部信息
    @Query(value = "select * from t_unit  WHERE if(:type !='', type=:type, 1=1 ) " +
            "AND if(:name != '' , name LIKE CONCAT('%',:name,'%') , 1 = 1 ) " +
            "AND if(:pId != '' , p_id =:pId , 1 = 1 ) " +
            "limit :pageNum,:pageSize", nativeQuery = true)
    public List<Unit> findAllListPage(@Param("type")String type, @Param("name")String name,@Param("pId")String pId,  @Param("pageNum")int pageNum, @Param("pageSize") int pageSize);



    //    统计平台单位或者项目机构的全部数量
    @Query(value = "select count(*) from t_unit  WHERE if(:type !='', type=:type, 1=1 ) " +
            "AND if(:name != '' , name LIKE CONCAT('%',:name,'%') , 1 = 1 ) " +
            "AND if(:pId != '' , p_id =:pId , 1 = 1 ) ", nativeQuery = true)
    public int findAllListCount(@Param("type")String type, @Param("name")String name,@Param("pId")String pId);


    //    根据ID查询数据
    @Query(value = "select * from t_unit  WHERE  " +
            " id=:id ", nativeQuery = true)
    public Unit findOneById(@Param("id")String id );


    //    查询单位名称是否存在
    @Query(value = "select count(*) from t_unit  WHERE if(:type !='', type=:type, 1=1 ) " +
            "AND name=:name ", nativeQuery = true)
    public int findByName(@Param("type")String type, @Param("name")String name);



    //修改单位或者机构的状态
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_unit set state = :state where id = :id ", nativeQuery = true)
    public void updateState(@Param("state") int state,@Param("id") String id);


    //查询平台或者机构 (状态：正常 或者 锁定)
    @Query(value = "select id,name from t_unit  WHERE " +
            "if(:type !='', type=:type, 1=1 ) " +
            "AND if(:state !=-1, state=:state, 1=1 ) " +
            "AND if(:pId !='', p_id=:pId, 1=1 ) " +
            "AND if(:unitId !='', id=:unitId, 1=1 )" , nativeQuery = true)
    public List<?> findByType(@Param("type")String type,@Param("state")Integer state,@Param("pId")String pId,@Param("unitId")String unitId);


    //查询所有没有被锁定的平台或者机构
    @Query(value = "select * from t_unit  WHERE " +
            "if(:name !='', name=:name, 1=1 ) " +
            "AND if(:type !='', type=:type, 1=1 ) " , nativeQuery = true)
    public Unit findByNameType(@Param("name")String name,@Param("type")String type);


    //通过设备名称，设备型号，设备品牌查询
    @Query(value = "select id from t_unit  WHERE " +
            "if(:ename !='', ename=:ename, 1=1 ) " +
            "AND if(:brand !='', brand=:brand, 1=1 ) " +
            "AND if(:model !='', model=:model, 1=1 )" , nativeQuery = true)
    public String findByEnameBrandModel(@Param("ename")String ename,@Param("brand")String brand,@Param("model")String model);


    //查询平台下的机构(状态：正常 或者 锁定)
    @Query(value = "select id,name from t_unit  WHERE " +
            "if(:pId !='', p_id=:pId, 1=1 ) " +
            "AND if(:state !=-1, state=:state, 1=1 ) " , nativeQuery = true)
    public List<?> findByPId(@Param("pId")String pId,@Param("state")int state);


    //根据ID修改单位名称
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_unit set name = :name where id = :id ", nativeQuery = true)
    public void updateName(@Param("name") String name,@Param("id") String id);


}
