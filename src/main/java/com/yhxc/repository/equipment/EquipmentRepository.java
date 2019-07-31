package com.yhxc.repository.equipment;

import com.yhxc.entity.equipment.Equipment;
import com.yhxc.entity.project.Project;
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
public interface EquipmentRepository extends JpaRepository<Equipment, String>, JpaSpecificationExecutor<Equipment> {


    /**
     * 根据UUID查询一条数据
     * @param uuid
     * @return
     */
    @Query(value = "select * from t_equipment where uuid = :uuid", nativeQuery = true)
    public Equipment findByUUID(@Param("uuid") String uuid);
    /**
     * 设备绑定定时任务
     * @param
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_equipment  SET group_name=:group_name WHERE uuid=:uuid ",nativeQuery = true)
    public void updateGroup_name(@Param("group_name") String group_name,@Param("uuid") String uuid);

    /**
     * 根据项目组查询uuid
     * @param
     * @return
     */
    @Query(value = "select uuid from t_equipment where group_name = :group_name", nativeQuery = true)
    public List<String> finduuidByGroup(@Param("group_name") String group_name);



    /**
     * 根据UUID查询一条数据
     * @param uuid
     * @return
     */
    @Query(value = "select transrate from t_equipment where uuid = :uuid  ", nativeQuery = true)
    public int findtransrate(@Param("uuid") String uuid);



    /**
     * 根据UUID查询一条数据
     * @param uuid
     * @return
     */
    @Query(value = "select voltage from t_equipment where uuid = :uuid", nativeQuery = true)
    public int findvoltage(@Param("uuid") String uuid);






    /**
     * 用户绑定设备时候，这里根据设备UUID更新这条设备数据，表示此设备已被绑定
     * @param uuid
     * @return
     */
    @Query(value = "update t_equipment set state = 1 WHERE uuid = :uuid", nativeQuery = true)
    @Transactional
    @Modifying
    public void updState(@Param("uuid") String uuid);


    /**

     * 根据房间号查询设备
     *
     * @param roomId
     */
    @Query(value = "  select e.uuid,e.`name`,b.brand,b.model,e.active_time,e.production_date,b.img,e.id from t_equipment e,t_equipment_brand b where e.brand_id=b.id   and  e.deleted=0 and e.build_num=:roomId ORDER BY e.create_time desc  ", nativeQuery = true)

    public List<?> findByRoomId(@Param("roomId") String roomId);


    /**
     * 查询项目设备类别
     *
     * @param projectId
     */
    @Query(value = " SELECT t.id, t.type from t_equipment e,t_equipment_type t where e.project_id=:projectId and e.type_id=t.id GROUP BY e.type_id   ", nativeQuery = true)

    public List<?> findEqType(@Param("projectId") String projectId);


    /**
     * 查询项目设备品牌和型号
     *
     * @param projectId
     */
    @Query(value = " SELECT t.brand,t.model from t_equipment e,t_equipment_brand t where e.project_id=:projectId and e.brand_id=t.id GROUP BY e.brand_id  ", nativeQuery = true)

    public List<?> findEqBrand(@Param("projectId") String projectId);


    /**
     * 查询项目中设备的总数量
     *
     * @param projectId
     */
    @Query(value = " SELECT e.eq_id from p_project e where e.id=:projectId  ", nativeQuery = true)

    public String eqcount(@Param("projectId") String projectId);



    /**
     * 删除撤销
     *
     * @return
     */
    @Query(value = "update t_equipment set deleted = 0 WHERE id = :id", nativeQuery = true)
    @Transactional
    @Modifying
    public void reduction(@Param("id") String id);

    @Query(value = "SELECT * FROM t_equipment where brand_id in (SELECT id FROM t_equipment_brand where p_id = :pId)", nativeQuery = true)
    public List<Equipment> findBrandIdIn(@Param("pId") String pId);





    /**
     * 查询项目中绑定设备 的运行状态  1在线  ，2离线
     *
     *
     */
    @Query(value = "SELECT xxx3.aa,count(xxx3.aa)  from(select CASE \n" +
            " WHEN  round(unix_timestamp(NOW())-unix_timestamp(xx3.receive_date))>(xx3.board_data_report_interval+300) or ISNULL(xx3.receive_date ) then '2' else '1'\n" +
            " end aa ,xx3.uuid from (\n" +
            "select x3.receive_date,e.uuid,x3.board_data_report_interval   \n" +
            "from  t_equipment e  left   join  (SELECT * from s_receive_data where   id in (  select max(id) from s_receive_data group by uuid)  ) x3  on   e.uuid=x3.uuid         \n" +
            " group by e.uuid) xx3) xxx3 ,t_equipment e,p_project p,t_unit u  where xxx3.uuid=e.uuid and p.eq_id=e.id and e.unit_id=u.id and p.unit_id=u.id " +
            "and  if(:pId !='', u.p_id=:pId, 1 = 1) " +
            "and  if(:unitId !='', e.unit_id=:unitId, 1 = 1) GROUP  by xxx3.aa", nativeQuery = true)
    public List<?> findEqStatus(@Param("pId") String pId,@Param("unitId") String unitId);



    /**
     * 查询设备数量

     */
    @Query(value = "SELECT  count(e.uuid) from t_equipment e,t_unit u where e.unit_id=u.id " +
            "and  if(:pId !='', u.p_id=:pId, 1 = 1) " +
            "and  if(:unitId !='', e.unit_id=:unitId, 1 = 1) ", nativeQuery = true)
    public Integer eqNum(@Param("pId") String pId,@Param("unitId") String unitId);


    /**
     * 查询所有的设备UUID和设备名称

     */
    @Query(value = "SELECT  name from t_equipment where name LIKE CONCAT('%',:ename,'%') group by name  " , nativeQuery = true)
    public List<?> findUuidName(@Param("ename") String ename);


    /**
     * 查询所有的设备UUID

     */
    @Query(value = "SELECT  uuid from t_equipment group by uuid", nativeQuery = true)
    public List<?> findUuid();





    /**
     * 查询项目中设备运行状态
     *
     */
    @Query(value = "SELECT   p.pname,p.address,p.createtime,ifnull(p.img,'')  ,p.puser,p.puser_phone,p.equipment_num,p.room_area,p.type,xxx3.aa ,e.uuid ,p.location ,p.id ,e.nb_card from(select CASE \n" +
            " WHEN  round(unix_timestamp(NOW())-unix_timestamp(xx3.receive_date))>(xx3.board_data_report_interval+1800) or ISNULL(xx3.receive_date ) then '2' else '1'\n" +
            " end aa ,xx3.uuid from (\n" +
            "select x3.receive_date,e.uuid,x3.board_data_report_interval   \n" +
            "from  t_equipment e  left   join  (SELECT * from s_receive_data where   id in (  select max(id) from s_receive_data group by uuid)  ) x3  on   e.uuid=x3.uuid         \n" +
            " group by e.uuid) xx3) xxx3 ,t_equipment e,p_project p,t_unit u where xxx3.uuid=e.uuid and p.eq_id=e.id  and p.unit_id=u.id " +
            "AND  if(:pId != '' , u.p_id =:pId , 1 = 1 ) " +
            "AND  if(:unitId != '' , e.unit_id =:unitId , 1 = 1 ) " +
            "AND  if(:address !='', p.address like CONCAT('%',:address,'%'), 1 = 1) " +
            "and  if(:pname !='', p.pname like CONCAT('%',:pname,'%'), 1 = 1) " +
            "and  if(:runStatus !='', xxx3.aa like CONCAT('%',:runStatus,'%'), 1 = 1) " +
            "and  if(:projectType !='', p.type like CONCAT('%',:projectType,'%'), 1 = 1) " +
            "and  if(:statDate !='', p.createtime BETWEEN :statDate  and :endDate, 1 = 1)  " +
            " ORDER BY p.createtime desc  limit :pageNum,:pageSize  ", nativeQuery = true)

    public List<?> findrunStatusPage(@Param("pId") String pId ,@Param("unitId") String unitId,@Param("projectType") String projectType,@Param("address") String address,@Param("pname") String pname,@Param("statDate") String statDate,@Param("endDate") String endDate, @Param("runStatus") String runStatus,  @Param("pageNum") int pageNum,@Param("pageSize") int pageSize);


    /**
     * 查询项目中设备运行状态 总数
     *
     */
    @Query(value = "SELECT   count(e.uuid)    from(select CASE \n" +
            " WHEN  round(unix_timestamp(NOW())-unix_timestamp(xx3.receive_date))>(xx3.board_data_report_interval+1800) or ISNULL(xx3.receive_date ) then '2' else '1'\n" +
            " end aa ,xx3.uuid from (\n" +
            "select x3.receive_date,e.uuid,x3.board_data_report_interval\n" +
            "from  t_equipment e  left    join  (SELECT * from s_receive_data where   id in (  select max(id) from s_receive_data group by uuid)  ) x3  on   e.uuid=x3.uuid        " +
            " group by e.uuid) xx3) xxx3 ,t_equipment e,p_project p,t_unit u where xxx3.uuid=e.uuid and p.eq_id=e.id  and p.unit_id=u.id " +
            "AND  if(:pId != '' , u.p_id =:pId , 1 = 1 )" +
            "AND  if(:unitId != '' , e.unit_id =:unitId , 1 = 1 )  " +
            " AND   if(:address !='', p.address like CONCAT('%',:address,'%'), 1 = 1) " +
            "and  if(:pname !='', p.pname like CONCAT('%',:pname,'%'), 1 = 1) " +
            "and  if(:runStatus !='', xxx3.aa like CONCAT('%',:runStatus,'%'), 1 = 1) " +
            "and  if(:projectType !='', p.type like CONCAT('%',:projectType,'%'), 1 = 1) " +
            "and  if(:statDate !='', p.createtime BETWEEN :statDate  and :endDate, 1 = 1)  " +
            " ORDER BY p.createtime desc  ", nativeQuery = true)

    public int findrunStatusCount(@Param("pId") String pId ,@Param("unitId") String unitId,@Param("projectType") String projectType,@Param("address") String address,@Param("pname") String pname,@Param("statDate") String statDate,@Param("endDate") String endDate,@Param("runStatus") String runStatus);




    /**
     * 根据UUID查询设备的任务组
     * @param uuid
     * @return
     */
    @Query(value = "SELECT  e.group_name,g.display_name from t_equipment e,job_group g where e.group_name=g.job_name and e.uuid=:uuid ", nativeQuery = true)
    public List<?> findByuuidGroup(@Param("uuid") String uuid);

    /**
     * 根据UUID查询未绑定设备
     * @param uuid
     * @return
     */
    @Query(value = "  SELECT  e.id, e.uuid,e.`name`,u.brand,u.model,e.nb_card,p.eq_id,e.production_date FROM t_equipment e   LEFT JOIN  p_project p\n" +
            "    ON p.eq_id=e.id LEFT JOIN t_equipment_type AS u ON e.eq_type_id = u.id\n" +
            "    where 1 = 1  and  p.eq_id is null " +
            "and  if(:uuid !='', e.uuid like CONCAT('%',:uuid,'%'), 1 = 1) " +
            " ORDER BY e.create_time desc ", nativeQuery = true)
    public List<?> findweiBanding(@Param("uuid") String uuid);





    /**
     * 根据设备名称修改设备名称
     * @param name
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_equipment set name = :newName where name = :name ", nativeQuery = true)
    public void updateName(@Param("newName") String newName,@Param("name") String name);




    /**
     * 根据ID修改互感器倍率和电压
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE t_equipment set transrate = :transrate,voltage = :voltage  where id = :id ", nativeQuery = true)
    public void updateTransrate(@Param("transrate") int transrate,@Param("voltage") int voltage,@Param("id") String id);





    /**
     * 多条件查询所有设备
     * @return
     */
    @Query(value = "SELECT * from t_equipment e,t_unit u, t_equipment_type et where e.eq_type_id=et.id and e.unit_id=u.id " +
            "AND if(:ename !='', ename=:ename, 1=1 ) " +
            "AND if(:brand !='', brand=:brand, 1=1 ) " +
            "AND if(:model !='', model=:model, 1=1 )" , nativeQuery = true)
    public String findByEnameBrandModel(@Param("ename")String ename,@Param("brand")String brand,@Param("model")String model);


    /**
     * 根据机构ID查询 所属机构下未绑定的设备
     * @param unitId
     * @return
     */
    @Query(value = "SELECT  e.id, e.uuid,e.`name`,u.brand,u.model,e.nb_card,p.eq_id,e.production_date FROM t_equipment e   LEFT JOIN  p_project p\n" +
            "    ON p.eq_id=e.id LEFT JOIN t_equipment_type AS u ON e.eq_type_id = u.id\n" +
            "    where 1 = 1  and  p.eq_id is null " +
            "and  if(:unitId !='', e.unit_id like CONCAT('%',:unitId,'%'), 1 = 1) " +
            "and  if(:uuid !='', e.uuid like CONCAT('%',:uuid,'%'), 1 = 1) " +
            " ORDER BY e.create_time desc ", nativeQuery = true)
    public List<?> findByUnitId(@Param("unitId") String unitId,@Param("uuid") String uuid);




}
