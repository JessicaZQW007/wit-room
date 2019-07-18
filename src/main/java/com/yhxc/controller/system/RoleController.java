package com.yhxc.controller.system;

import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.equipment.Equipment;
import com.yhxc.entity.project.Project;
import com.yhxc.entity.system.*;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.project.ProjectService;
import com.yhxc.service.system.*;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 后台管理角色Controller
 *
 * @author yhxc 赵贺飞
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private UserProjectService userProjectService;
    @Resource
    private UserService userService;
    @Resource
    private ProjectService projectService;
    @Resource
    private LogService logService;
    @Resource
    private EquipmentService equipmentService;

    /**
     * 查询所有角色，不分页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public ResultInfo listAll() throws Exception {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", roleService.listAll());
    }

    /**
     * 分页查询角色信息
     *
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultInfo list(Role role, Integer pageNum, Integer pageSize) throws Exception {
        Page page = roleService.list(role, pageNum, pageSize, Direction.ASC, "id");
        return new ResultInfo(StatusCode.SUCCESS, "成功", page.getContent(), PageInfo.pageInfo(page), Jurisdiction.getQX());
    }

    /**
     * 添加或者修改角色信息
     *
     * @param role
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResultInfo saveOrUpdate(Role role) throws Exception {
        String info = "";
        if (role.getId() == null) { // 写入日志
            info = "添加角色信息成功！";
            logService.save(new Log(Log.ADD_ACTION, "添加角色信息" + role));
        } else {
            info = "更新角色信息成功！";
            logService.save(new Log(Log.UPDATE_ACTION, "更新角色信息" + role));
        }
        roleService.save(role);
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 根据ID查询角色
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ResultInfo findById(Integer id){
        return new ResultInfo(StatusCode.SUCCESS, "success", roleService.findById(id));
    }


    /**
     * 删除角色信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/del")
    @ResponseBody
    public ResultInfo delete(Integer id) throws Exception {
        logService.save(new Log(Log.DELETE_ACTION, "删除角色信息" + roleService.findById(id)));  // 写入日志
        if (id == 1 || id == 2 || id == 3 || id == 4 || id == 5) {
            return new ResultInfo(StatusCode.FAIL, "删除失败！初始角色不能删除！");
        }
        userRoleService.deleteByRoleId(id);     // 删除用户角色关联信息
        roleMenuService.deleteByRoleId(id);     // 删除菜单角色关联信息
        roleService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }

    /**
     * 批量删除角色信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/dels")
    @ResponseBody
    public ResultInfo delete(String ids) throws Exception {
        String info = "";
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                Integer id = Integer.parseInt(idStr[i]);
                //终端客户角色为初始数据，不能删除
                if (id == 1 || id == 2 || id == 3 || id == 4 || id == 5) {
                    info = "删除失败！初始角色不能删除！";
                    continue;
                }
                logService.save(new Log(Log.DELETE_ACTION, "删除角色信息" + roleService.findById(id)));  // 写入日志
                userRoleService.deleteByRoleId(id);     // 删除用户角色关联信息
                roleMenuService.deleteByRoleId(id);     // 删除菜单角色关联信息
                roleService.delete(id);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！" + info);
    }

    /**
     * 保存角色权限设置
     *
     * @param menuIds
     * @param roleId
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveMenuSet")
    @ResponseBody
    public ResultInfo saveMenuSet(String menuIds, String adds, String dels, String edits, String querys, Integer roleId) throws Exception {
        if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4 || roleId == 5) {
            return new ResultInfo(StatusCode.SUCCESS, "授权失败，初始角色不能操作！");
        }
        roleMenuService.deleteByRoleId(roleId); // 根据角色id删除所有角色权限关联实体
        if (StringUtil.isNotEmpty(menuIds)) {
            String idsStr[] = menuIds.split(",");
            for (int i = 0; i < idsStr.length; i++) { // 然后添加所有角色权限关联实体
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRole(roleService.findById(roleId));
                roleMenu.setMenu(menuService.findById(Integer.parseInt(idsStr[i])));
                roleMenuService.save(roleMenu);
            }
        }

        //通过程序控制 某一个角色菜单的增删改查操作
        if (StringUtil.isNotEmpty(adds)) {
            roleService.updAdd(adds, roleId);
        }
        if (StringUtil.isNotEmpty(dels)) {
            roleService.updDel(dels, roleId);
        }
        if (StringUtil.isNotEmpty(edits)) {
            roleService.updEdit(edits, roleId);
        }
        if (StringUtil.isNotEmpty(querys)) {
            roleService.updQuery(querys, roleId);
        }
        logService.save(new Log(Log.ADD_ACTION, "保存角色权限设置"));  // 写入日志
        return new ResultInfo(StatusCode.SUCCESS, "角色权限设置成功！");
    }



    /**
     * 保存多个项目终端用户关系
     *
     * @param projectIds
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveProjectSet")
    @ResponseBody
    public ResultInfo saveProjectSet(String projectIds, Integer userId) throws Exception {
        if (StringUtil.isNotEmpty(projectIds)) {
            String idsStr[] = projectIds.split(",");
            for (int i = 0; i < idsStr.length; i++) { // 然后添加所有角色权限关联实体
                UserProject userProject = new UserProject();
                userProject.setUser(userService.findById(userId));
                userProject.setProject(projectService.findById(idsStr[i]));
                userProjectService.save(userProject);
            }
        }
        logService.save(new Log(Log.ADD_ACTION, "新增终端用户绑定项目设置"));  // 写入日志
        return new ResultInfo(StatusCode.SUCCESS, "sussce！");
    }



    /**
     * 根据用户id查询绑定的设备信息
     *
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findbangdingEq")
    @ResponseBody
    public ResultInfo findbangdingEq(Integer userId,String uuid ) throws Exception {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", userProjectService.findbangdingEq(userId,uuid));
    }



    /**
     * 根据用户id查询未绑定绑定的设备信息
     *
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findbangdingEqNo")
    @ResponseBody
    public ResultInfo findbangdingEqNo(Integer userId,String uuid ) throws Exception {
        User u=Jurisdiction.getCurrentUser();
        String pId="";
        String unitId="";
        if (u.getUserType()==1){
            pId=u.getUnitId();

        }else if (u.getUserType()==2){
            unitId=u.getUnitId();

        }

        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", userProjectService.findbangdingEqNo(userId,uuid,pId,unitId));
    }


    /**
     * 批量解绑
     *
     * @param projectIds
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/deletemany")
    @ResponseBody
    public ResultInfo deletemany(String projectIds, Integer userId) throws Exception {
        if (StringUtil.isNotEmpty(projectIds)) {
            String idsStr[] = projectIds.split(",");
            for (int i = 0; i < idsStr.length; i++) {
              userProjectService.deleteByuserIdProjectId(userId,idsStr[i]);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "解绑成功!");
    }



}
