package com.yhxc.repository.system;

import com.yhxc.entity.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 权限菜单Repository接口
 *
 * @author yhxc 赵贺飞
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query(value = "select id from t_menu ORDER BY id asc", nativeQuery = true)
    public List<Integer> findId();

    /**
     * 根据id获取权限菜单集合
     *
     * @param roleId
     * @return
     */
    @Query(value = "SELECT m.* FROM t_role r,t_menu m,t_role_menu rm WHERE rm.`role_id`=r.`id` AND rm.`menu_id`=m.id AND r.id=?1 ORDER BY m.order ASC", nativeQuery = true)
    public List<Menu> findByRoleId(int roleId);


    /**
     * 根据id获取权限菜单集合，不包含父级
     * @param roleId
     * @return
     */
    @Query(value = "SELECT m.* FROM t_role r,t_menu m,t_role_menu rm WHERE rm.`role_id`=r.`id` AND rm.`menu_id`=m.id AND m.p_id != 1 AND r.id=?1 ORDER BY m.order ASC", nativeQuery = true)
    public List<Menu> findByRoleIdNotPid(int roleId);
    /**
     * 根据父节点以及用户角色id集合查询子节点
     *
     * @param parentId
     * @param roleId
     * @return
     */
    @Query(value = "select * from t_menu where `type` = ?1 and p_id=?2 and id in (SELECT menu_id FROM t_role_menu WHERE role_id=?3) ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByParentIdAndRoleId(int type, int parentId, int roleId);

    /**
     * 根据父节点查找所有子节点
     *
     * @param parentId
     * @return
     */
    @Query(value = "select * from t_menu where p_id=?1 ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByParentId(int parentId);


    @Query(value = "select * from t_menu where p_id=?1 and id in (SELECT menu_id FROM t_role_menu WHERE role_id =?2) ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByParentIdAndRoleId(int parentId, int roleId);

    /**
     * 根据菜单父ID和权限查询
     * @param menuPid
     * @param roleId
     * @return
     */
    @Query(value = "select * from t_menu where menu_pid= ?1 and id in (SELECT menu_id FROM t_role_menu WHERE role_id= ?2) ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByMenuPid(int menuPid, int roleId);

    @Query(value = "select * from t_menu where menu_pid= ?1 ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByMenuPid2(int menuPid);

    /**
     * 查询一级菜单的id
     * 1平台；0是系统用户
     *
     * @return
     */
    @Query(value = "SELECT * FROM t_menu where p_id = 1 and type = :type ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByMenuLevel1(@Param("type") Integer type);


    /**
     * 查询二级菜单ID
     */
    @Query(value = "SELECT * FROM t_menu where p_id != 1 and p_id != -1 ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByMenuLevel2();


    /**
     * 查询所有menU,为其增加按钮
     *
     * @return
     */
    @Query(value = "SELECT * FROM t_menu where (p_id != 1 and p_id != -1) or menu_pid is not null ORDER BY `order` ASC", nativeQuery = true)
    public List<Menu> findByMenu();

    /**
     * 根据url查询
     */
    @Query(value = "select * from t_menu where url like CONCAT('%',:url,'%')", nativeQuery = true)
    public List<Menu> findByUrl(@Param("url") String url);
}
