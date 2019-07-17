package com.yhxc.repository.project;


import com.yhxc.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 项目 管理Repository
 * @author lwg
 *
 */
public interface ProjectRepository extends JpaRepository<Project, String>,JpaSpecificationExecutor<Project>{





    /**查询所有的项目
     * @param
     */
    @Query(value = " SELECT   p.id,p.pname,p.address,p.type,p.equipment_num,p.puser,p.puser_phone ,p.createtime ,p.status ,p.location,p.img,p.room_area,e.uuid,p.unit_type,p.unit_id,p.transrate,p.voltage  from   p_project p LEFT JOIN t_equipment e   ON p.eq_id=e.id LEFT JOIN t_unit AS u ON p.unit_id = u.id where 1=1 and p.unit_id=u.id " +
            " AND   if(:address !='', p.address like CONCAT('%',:address,'%'), 1 = 1) " +
            "and  if(:pname !='', p.pname like CONCAT('%',:pname,'%'), 1 = 1) " +
            "and  if(:projectType !='', p.type like CONCAT('%',:projectType,'%'), 1 = 1) " +
            "and  if(:statDate !='', p.createtime BETWEEN :statDate  and :endDate, 1 = 1) " +
            "AND if(:pId !='', u.p_id=:pId, 1=1 )  " +
            "AND if(:unitId !='', p.unit_id=:unitId, 1=1 ) " +
            " ORDER BY p.createtime DESC  limit :pageNum,:pageSize ", nativeQuery = true)
    public List<?> pageList(@Param("projectType") String projectType,@Param("address") String address,@Param("pname") String pname,@Param("statDate") String statDate,@Param("endDate") String endDate,@Param("pId") String pId,@Param("unitId") String unitId,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);



    /**查询所有的项目数量
     * @param
     */
    @Query(value = " SELECT   count(p.pname)    from   p_project p LEFT JOIN t_equipment e   ON p.eq_id=e.id  LEFT JOIN t_unit AS u ON p.unit_id = u.id where 1=1  and p.unit_id=u.id" +
            " and if(:address !='', p.address like CONCAT('%',:address,'%'), 1 = 1) " +
            " and  if(:projectType !='', p.type like CONCAT('%',:projectType,'%'), 1 = 1) " +
            "and  if(:pname !='', p.pname like CONCAT('%',:pname,'%'), 1 = 1) " +
            "and  if(:statDate !='', p.createtime BETWEEN :statDate  and :endDate, 1 = 1) " +
            "AND if(:pId !='', u.p_id=:pId, 1=1 )  " +
            "AND if(:unitId !='', p.unit_id=:unitId, 1=1 )  " +
            " ORDER BY p.createtime DESC  ", nativeQuery = true)
    public int pageListCout(@Param("projectType") String projectType,@Param("address") String address,@Param("pname") String pname,@Param("statDate") String statDate,@Param("endDate") String endDate,@Param("pId") String pId,@Param("unitId") String unitId);





    /**
     *解绑设备
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE p_project SET  eq_id=''  WHERE id =:id  ", nativeQuery = true)
    public void untieEq(@Param("id") String id);


    /**
     *解绑设备
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE p_project SET  eq_id=''  WHERE eq_id =:eq_id  ", nativeQuery = true)
    public void juebangEq(@Param("eq_id") String eq_id);


    /**
     *绑定设备
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE p_project SET  eq_id=:eq_id  WHERE id =:projectId  ", nativeQuery = true)
    public void bangdingEq(@Param("eq_id") String eq_id,@Param("projectId") String projectId);




    /**
     *启用
     *
     * @return
     * @author: 李文光
     */
    @Modifying
    @Transactional
    @Query(value = "  UPDATE t_company SET  status='1'  WHERE id =:id  ", nativeQuery = true)
    public void updateStatus1(@Param("id") String id);


    /**查询地址（省）
     * @param
     */
    @Query(value = "   SELECT p.id,substring_index(p.address,',',1)from p_project p,t_unit u where p.unit_id =u.id " +
            "AND if(:pId != '' , u.p_id =:pId , 1 = 1 ) " +
            "AND if(:unitId != '' ,p.unit_id =:unitId , 1 = 1 ) " +
            " GROUP BY substring_index(p.address,',',1); ", nativeQuery = true)
    public List<?> findProvince(@Param("pId") String pId,@Param("unitId") String unitId);

    /**查询地址（市）
     * @param
     */
    @Query(value = " SELECT p.id,substring_index(p.address,',',2)     from p_project p,t_unit u  where p.unit_id=u.id " +
            "and substring_index(p.address,',',1)=:province  " +
            "AND if(:pId != '' , u.p_id =:pId , 1 = 1 ) " +
            "AND if(:unitId != '' ,p.unit_id =:unitId , 1 = 1 ) " +
            "GROUP BY substring_index(p.address,',',2); ", nativeQuery = true)
    public List<?> findCity(@Param("province") String province,@Param("pId") String pId,@Param("unitId") String unitId);


    /**查询地址（区/县）
     * @param
     */
    @Query(value = " SELECT p.id,substring_index(p.address,',',3)     from p_project p,t_unit u  where p.unit_id=u.id " +
            "and substring_index(p.address,',',2)=:city  " +
            "AND if(:pId != '' , u.p_id =:pId , 1 = 1 ) " +
            "AND if(:unitId != '' ,p.unit_id =:unitId , 1 = 1 ) " +
            "GROUP BY substring_index(p.address,',',3);", nativeQuery = true)
    public List<?> findqu(@Param("city") String city,@Param("pId") String pId,@Param("unitId") String unitId);



    /**根据项目地址 和项目类别查询
     * @param
     */
    @Query(value = " SELECT  p.id, p.pname   from p_project p  where " +
            "  if(:address !='', p.address like CONCAT('%',:address,'%'), 1 = 1) " +
            " and  if(:projectType !='', p.type like CONCAT('%',:projectType,'%'), 1 = 1) " +
            " ORDER BY p.createtime DESC  ", nativeQuery = true)
    public List<?> findProjectName(@Param("projectType") String projectType,@Param("address") String address);


    /**查询项目中空调数量
     * @param
     */
    @Query(value = " SELECT p.equipment_num  FROM p_project p  where  p.pname=:pname  ", nativeQuery = true)
    public Integer findProjectAirNum(@Param("pname") String pname);



    /**查询项目中空调数量(项目id查询)
     * @param
     */
    @Query(value = " SELECT p.equipment_num  FROM p_project p  where  p.id=:id  ", nativeQuery = true)
    public Integer findProjectAirNumbyId(@Param("id") String id);




    /**查询项目中 电价
     * @param
     */
    @Query(value = " SELECT p.electricity_price  FROM p_project p  where  p.pname=:pname  ", nativeQuery = true)
    public String findElectricityPrice(@Param("pname") String pname);



    /**
     * 查询项目数量

     */
    @Query(value = "SELECT  count(p.id) from p_project p,t_unit u where p.unit_id=u.id " +
            "and  if(:pId !='', u.p_id=:pId, 1 = 1) " +
            "and  if(:unitId !='', p.unit_id=:unitId, 1 = 1) ", nativeQuery = true)
    public Integer ProjectNum(@Param("pId") String pId,@Param("unitId") String unitId);

    /**
     * 查询空调数量
     *//*
    @Query(value = "SELECT  sum(equipment_num) from p_project ", nativeQuery = true)
    public Integer airNum();*/

    /**
     * 查询空调数量
     */
    @Query(value = "SELECT  sum(p.equipment_num) from p_project p,t_unit u where p.unit_id=u.id " +
            "and  if(:pId !='', u.p_id=:pId, 1 = 1) " +
            "and  if(:unitId !='', p.unit_id=:unitId, 1 = 1)", nativeQuery = true)
    public Integer airNum(@Param("pId") String pId,@Param("unitId") String unitId);



    /**
     * 查询app登录用户负责的项目
     */
    @Query(value = " SELECT p.id,p.pname,e.uuid,p.puser,p.puser_phone,e.nb_card,p.type,p.room_area,p.createtime,p.address,p.equipment_num,ifnull(p.img,'') from p_project p,t_equipment e where  p.eq_id=e.id and       p.id in (  SELECT  project_id from   t_user_project where user_id=:userId) ", nativeQuery = true)
    public List<?> findprojectMessages( @Param("userId") int userId );



    /**
     * 查询项目名存不存在

     */
    @Query(value = "SELECT  count(pname) from p_project  where pname=:pname ", nativeQuery = true)
    public Integer findpame(@Param("pname") String pname);



    /**
     * 根据设备id查询项目信息

     */
    @Query(value = "SELECT  * from p_project  where eq_id=:eq_id ", nativeQuery = true)
    public Project findByEqId(@Param("eq_id") String eq_id);

    /**
     * 多条件分权限查询所有项目

     */
    @Query(value = "select p.* FROM p_project p,t_unit u where p.unit_id=u.id " +
            " AND if(:pId !='', u.p_id=:pId, 1=1 ) " +
            " AND if(:unitId !='', p.unit_id=:unitId, 1=1 ) ", nativeQuery = true)
    public List<Project> findList(@Param("pId") String pId,@Param("unitId") String unitId);



    /**
     * 查出所有的项目名称
     * @return
     */
    @Query(value = " select id,pname from p_project ", nativeQuery = true)
    public List<?> findAllPname();


}
