package com.yhxc.repository.system;


import com.yhxc.entity.system.UserProject;
import com.yhxc.entity.system.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户项目关联Repository接口
 * @author yhxc 李文光
 *
 */
public interface UserProjectRepository extends JpaRepository<UserProject, Integer>,JpaSpecificationExecutor<UserProject>{


	
	/**
	 * 根据项目Id删除所有关联信息
	 * @param project_id
	 */
	@Query(value="delete from t_user_project where project_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByprojectId(String project_id);

	/**
	 * 根据用户Id删除所有关联信息
	 * @param user_id
	 */
	@Query(value="delete from t_user_project where user_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByuserId(Integer user_id);





	@Query(value="delete from t_user_project where user_id=?1 and project_id=?2 ",nativeQuery=true)
	@Modifying
	public void deleteByuserIdProjectId(Integer user_id,String project_id);


	/**
	 */
	@Query(value="SELECT project_id from t_user_project where user_id=?1",nativeQuery=true)
	public Integer findByUid(Integer userId);

	/**根据用户id查询绑定的设备信息
	 */
	@Query(value="SELECT p.id,e.uuid,p.pname,e.`name`,t.brand,t.model,e.nb_card ,p.puser,p.puser_phone,p.equipment_num,p.type,p.room_area,p.createtime,p.address  from t_user_project u,p_project p,t_equipment e,t_equipment_type t " +
			" where u.project_id=p.id and p.eq_id=e.id and e.eq_type_id=t.id and u.user_id=:userId  " +
			"and  if(:uuid !='', e.uuid like CONCAT('%',:uuid,'%'), 1 = 1) ",nativeQuery=true)
	public List<?> findbangdingEq(@Param("userId")Integer userId,@Param("uuid")String uuid);


	/**根据用户id查询未绑定的设备信息
	 */
	@Query(value="   SELECT pr.id,e.uuid,pr.pname,e.`name`,t.brand,t.model,e.nb_card,pr.address from\n" +
			"  (SELECT p.*  from p_project p  where p.id  not in (SELECT project_id  FROM t_user_project where   user_id=:userId )) pr,t_equipment e,t_equipment_type t,t_unit u  \n" +
			"where  pr.eq_id=e.id and e.eq_type_id=t.id and e.unit_id=u.id " +
			"and  if(:uuid !='', e.uuid like CONCAT('%',:uuid,'%'), 1 = 1) " +
			"AND if(:pId !='' , u.p_id=:pId, 1=1 ) " +
			"AND if(:unitId !='' , e.unit_id=:unitId, 1=1 ) ",nativeQuery=true)
	public List<?> findbangdingEqNo(@Param("userId")Integer userId,@Param("uuid")String uuid,@Param("pId") String pId,@Param("unitId") String unitId);




}
 