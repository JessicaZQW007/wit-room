package com.yhxc.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yhxc.entity.system.Role;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 角色Repository接口
 *
 * @author yhxc 赵贺飞
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    /**
     * 根据用户id查询角色集合
     *
     * @param id
     * @return
     */
    @Query(value = "SELECT r.* FROM t_user u,t_role r,t_user_role ur WHERE ur.user_id=u.`id` AND ur.role_id=r.`id` AND u.`id`=?1", nativeQuery = true)
    public Role findByUserId(Integer id);


    /**
     * 更新
     *
     * @param adds
     * @param id
     */
    @Query(value = "update t_role set adds = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public void updAdd(@Param("adds") String adds, @Param("id") Integer id);

    @Query(value = "update t_role set dels = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public void updDel(@Param("dels") String dels, @Param("id") Integer id);


    @Query(value = "update t_role set edits = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public void updEdit(@Param("edits") String edits, @Param("id") Integer id);


    @Query(value = "update t_role set querys = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying
    public void updQuery(@Param("querys") String querys, @Param("id") Integer id);

}
