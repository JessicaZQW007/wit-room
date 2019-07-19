package com.yhxc.repository.system;

import com.yhxc.entity.system.User;
import com.yhxc.entity.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 用户Repository接口
 *
 * @author yhxc 赵贺飞
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {


    /**
     * 修改账号状态
     *
     * @param state
     * @param id
     * @return
     */
    @Query(value = "update t_user set state = ?1 WHERE ID = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public int updState(@Param("state") Integer state, @Param("id") Integer id);


    /**
     * 用户注册绑定设备
     * @param uuid
     * @param userName
     */
    @Query(value = "update t_user set uuid = :uuid  WHERE user_name = :userName", nativeQuery = true)
    @Transactional
    @Modifying
    public void bind(@Param("uuid") String uuid, @Param("userName") Integer userName);

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    @Query(value = "select * from t_user where user_name=?1", nativeQuery = true)
    public User findByUserName(String userName);

    /**
     * 根据手机号查找用户
     *
     * @param phone
     * @return
     */
    @Query(value = "select * from t_user where phone=?1", nativeQuery = true)
    public User findByPhone(String phone);




    /**
     * 查询某个企业下的经销商用户列表
     *
     * @param companyId
     * @return
     */
    @Query(value = "SELECT * from t_user u where u.dealer_id in (SELECT d.id FROM t_company c, t_dealer d where c.id = d.company_id and c.id = :companyId);", nativeQuery = true)
    public List<User> listDealerByCompany(@Param("companyId") String companyId);

    /**
     * 查询某个经销商下的组织机构用户列表
     *
     * @param dealerId
     * @return
     */
    @Query(value = "SELECT * from t_user u WHERE u.organization_id in (SELECT o.id FROM t_dealer d, t_organization o where o.dealer_id = d.id and o.dealer_id = :dealerId)", nativeQuery = true)
    public List<User> listOrganizationByDealer(@Param("dealerId") String dealerId);

    /**
     * 查询某个组织机构下的终端用户
     * @param organizationId
     * @return
     */
    @Query(value = "SELECT * from t_user where type = 5 and organization_id = :organizationId", nativeQuery = true)
    public List<User> listZzByOrganization(@Param("organizationId") String organizationId);


    /**
     * 查询某个经销商下的所有终端用户
     * @param dealerId
     * @return
     */
    @Query(value = "SELECT u.* FROM `t_user` u, t_organization o, t_dealer d where type = 5 and o.dealer_id = d.id and o.id = u.organization_id and d.id= :dealerId", nativeQuery = true)
    public List<User> listZzByDealerId(@Param("dealerId") String dealerId);


    /**
     * 根据当前企业，查询当前企业所有用户
     * @param companyId
     * @return
     */
    @Query(value = "SELECT * FROM t_user WHERE type = 2 and company_id = :companyId", nativeQuery = true)
    public List<User> listCompanyByCompany(@Param("companyId") String companyId);

    /**
     * 查询某个经销商的所有经销商用户
     * @param dealerId
     * @return
     */
    @Query(value = "SELECT * from t_user WHERE type = 3 and dealer_id = :dealerId", nativeQuery = true)
    public List<User> listDealerByDealer(@Param("dealerId") String dealerId);


    /**
     * 查询某个机构的所有用户
     * @param organizationId
     * @return
     */
    @Query(value = "SELECT * FROM t_user WHERE type = 4 AND organization_id = :organizationId", nativeQuery = true)
    public List<User> listOrganizationByOrganization(@Param("organizationId") String organizationId);


    /**
     * 根据用户名查询该用户的项目
     * @return
     */
    @Query(value = "SELECT project_id FROM t_user WHERE user_name = :userName", nativeQuery = true)
    public String projectIds(@Param("userName") String userName);


    /**
     * 分页 多条件查询用户
     * @return
     */
    @Query(value = "select r.* from t_user r,t_unit t where r.unit_id=t.id  " +
            "AND if(:pId != '' , t.p_id=:pId, 1 = 1 ) " +
            "AND if(:type !='' , r.type=:type, 1=1 ) " +
            "AND if(:userName != '' , r.user_name LIKE CONCAT('%',:userName,'%') , 1 = 1 ) " +
            "limit :pageNum,:pageSize ", nativeQuery = true)
    public List<User> findAllListPage(@Param("pId")String pId, @Param("type")String type, @Param("userName")String userName, @Param("pageNum")int pageNum, @Param("pageSize") int pageSize);


    /**
     * 分页 多条件查询用户 统计数量
     * @return
     */
    @Query(value = "select count(*) from t_user r,t_unit t where r.unit_id=t.id  " +
            "AND if(:pId != '' , t.p_id=:pId, 1 = 1 ) " +
            "AND if(:type !='' , r.type=:type, 1=1 ) " +
            "AND if(:userName != '' , r.user_name LIKE CONCAT('%',:userName,'%') , 1 = 1 )", nativeQuery = true)
    public int findAllListCount(@Param("pId")String pId, @Param("type")String type, @Param("userName")String userName);



    /**
     * 分页 多条件查询用户
     * @return
     */
    @Query(value = "(select r.* from t_user r,t_unit t where r.unit_id=t.id  " +
            "AND if(:pId != '' , t.p_id=:pId, 1 = 1 ) " +
            "AND if(:type !='' , r.type=:type, 1=1 ) " +
            "AND if(:userName != '' , r.user_name LIKE CONCAT('%',:userName,'%') , 1 = 1 ) ) " +
            "union all " +
            "(select r.* from t_user r,t_unit t where r.unit_id=t.id " +
            "AND if(:unitId != '' , r.unit_id=:unitId, 1 = 1 ) " +
            "AND if(:type !='' , r.type=:type, 1=1 ) " +
            "AND if(:userName != '' , r.user_name LIKE CONCAT('%',:userName,'%') , 1 = 1 ) ) " +
            "limit :pageNum,:pageSize ", nativeQuery = true)
    public List<User> findAllListPageType(@Param("pId")String pId, @Param("type")String type, @Param("userName")String userName,@Param("unitId") String unitId ,@Param("pageNum")int pageNum, @Param("pageSize") int pageSize);


    /**
     * 分页 多条件查询用户 统计数量
     * @return
     */
    @Query(value = "select count(*) from t_user r,t_unit t where r.unit_id=t.id  " +
            "AND if(:pId != '' , t.p_id=:pId, 1 = 1 ) " +
            "AND if(:type !='' , r.type=:type, 1=1 ) " +
            "AND if(:userName != '' , r.user_name LIKE CONCAT('%',:userName,'%') , 1 = 1 )" +
            "union all " +
            "select count(*) from t_user r,t_unit t where r.unit_id=t.id " +
            "AND if(:unitId != '' , r.unit_id=:unitId, 1 = 1 )" +
            "AND if(:type !='' , r.type=:type, 1=1 )" +
            "AND if(:userName != '' , r.user_name LIKE CONCAT('%',:userName,'%') , 1 = 1 )", nativeQuery = true)
    public List<?> findAllListCountType(@Param("pId")String pId, @Param("type")String type, @Param("userName")String userName,@Param("unitId") String unitId);


}
