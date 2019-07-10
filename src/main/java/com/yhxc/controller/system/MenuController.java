package com.yhxc.controller.system;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.Menu;
import com.yhxc.entity.system.Role;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.MenuService;
import com.yhxc.service.system.RoleService;
import com.yhxc.utils.Jurisdiction;
import com.yhxc.utils.StringUtil;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 菜单权限类
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;
    @Resource
    private LogService logService;
    @Resource
    private RoleService roleService;

    /**
     * 新增或修改菜单
     *
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    @RequiresPermissions(value = {"menu:add", "menu:edit"}, logical = Logical.OR)
    public ResultInfo saveOrUpdate(Menu menu, Integer level) {
        if (menu == null || level == null) {
            return new ResultInfo(StatusCode.FAIL, "新增菜单异常，参数不能为空！");
        }

        //level == 1 新增一级菜单
        if (level == 1) {
            Integer state = 1;
            Integer pId = 1;
            String resourceType = "menu";
            if (StringUtil.isNotEmpty(menu.getName()) && StringUtil.isNotEmpty(menu.getUrl()) && StringUtil.isNotEmpty(menu.getPermission())) {
                menu.setState(state);
                menu.setpId(pId);
                menu.setResourceType(resourceType);
                logService.save(new Log(Log.ADD_ACTION, "新增一级菜单！" + menu));
                menuService.save(menu);
            }
        }
        //level == 2 新增二级菜单,findByMenuLevel1()方法获取获取一级菜单ID即pId
        else if (level == 2) {
            Integer state = 0;
            String resourceType = "menu";
            if (StringUtil.isNotEmpty(menu.getName()) && StringUtil.isNotEmpty(menu.getUrl()) && StringUtil.isNotEmpty(menu.getPermission()) && null != menu.getpId()) {
                menu.setState(state);
                menu.setResourceType(resourceType);
                logService.save(new Log(Log.ADD_ACTION, "新增二级菜单！" + menu));
                menuService.save(menu);
            }
        }
        //level == 3 新增三级菜单,配合findByMenuLevel2()获取二级菜单ID即pId
        else if (level == 3) {
            Integer state = 0;
            String resourceType = "menu";
            if (StringUtil.isNotEmpty(menu.getName()) && StringUtil.isNotEmpty(menu.getUrl()) && StringUtil.isNotEmpty(menu.getPermission()) && null != menu.getMenuPid()) {
                menu.setState(state);
                menu.setResourceType(resourceType);
                logService.save(new Log(Log.ADD_ACTION, "新增三级菜单！" + menu));
                menuService.save(menu);
            }
        }
        //level == 4 新增，增、改、删、或其它按钮,配合findByMenu()获取可添加button的菜单ID
        else if (level == 4) {
            Integer state = 0;
            String resourceType = "button";
            if (StringUtil.isNotEmpty(menu.getName()) && StringUtil.isNotEmpty(menu.getUrl()) && StringUtil.isNotEmpty(menu.getPermission()) && null != menu.getButtonPid()) {
                menu.setState(state);
                menu.setResourceType(resourceType);
                logService.save(new Log(Log.ADD_ACTION, "新增按钮！" + menu));
                menuService.save(menu);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "新增成功！");
    }

    /**
     * 查询一级菜单,让前端获取一级菜单ID，供二级菜单使用
     *
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/findByMenuLevel1")
    public ResultInfo findByMenuLevel1() {
        return new ResultInfo(StatusCode.SUCCESS, "获取一级菜单ID成功！", menuService.findByMenuLevel1());
    }*/

    /**
     * 查询二级级父菜单,让前端获取二级菜单ID，供三级级菜单使用
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByMenuLevel2")
    public ResultInfo findByMenuLevel2() {
        return new ResultInfo(StatusCode.SUCCESS, "获取二级菜单ID成功！", menuService.findByMenuLevel2());
    }


    /**
     * 查询可添加按钮的菜单，获取其ID，供新增button使用
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByMenu")
    public ResultInfo findByMenu() {
        return new ResultInfo(StatusCode.SUCCESS, "获取可添加按钮的菜单成功！", menuService.findByMenu());
    }

    /**
     * 获取菜单列表，供配置角色权限使用
     *
     * @return
     */
    @RequestMapping("/getAllMenu")
    @ResponseBody
    public ResultInfo listMenu(Integer roleId) {
        List<Menu> menuList = menuService.findByRoleId(roleId); // 根据角色查询所有权限菜单信息
        List<Integer> menuIdList = new LinkedList<>();
        for (Menu menu : menuList) {
            Integer pId = menu.getpId();
            Integer menuId = menu.getId();
            if (pId != null && pId == 1) {
                List<Menu> listM = menuService.findByParentIdAndRoleId(menuId, roleId);  //判断一级菜单是否有二级菜单
                if(listM.size() == 0){
                    menuIdList.add(menuId);
                } else {
                    for (Menu menu1 : listM) {
                        Integer id2 = menu1.getId();
                        List<Menu> list = menuService.findByMenuPid(id2, roleId);
                        if(list.size() == 0){
                            menuIdList.add(id2);
                        } else {
                            for (Menu menu2 : list) {
                                Integer id3 = menu2.getId();
                                menuIdList.add(id3);
                            }
                        }
                    }
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Role role = roleService.findById(roleId);
        map.put("json", menuService.getAllMenu());      //原始json
        map.put("check", menuIdList);                  //菜单权限
        map.put("checkAdds", role.getAdds());              //新增操作权限
        map.put("checkDels", role.getDels());                  //删除操作权限
        map.put("checkEdits", role.getEdits());
        map.put("checkQuerys", role.getQuerys());                   //修改操作权限 //查询操作权限
        return new ResultInfo(StatusCode.SUCCESS, "权限菜单获取成功！", map);
    }
}
