package com.yhxc.service.system.impl;

import com.yhxc.entity.system.Menu;
import com.yhxc.entity.system.User;
import com.yhxc.repository.system.MenuRepository;
import com.yhxc.service.system.MenuService;
import com.yhxc.utils.Jurisdiction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限菜单Service实现类
 *
 * @author yhxc 赵贺飞
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;


    @Override
    public List<Integer> findId() {
        return menuRepository.findId();
    }

    @Override
    public void save(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public List<Menu> findByRoleId(int roleId) {
        return menuRepository.findByRoleId(roleId);
    }

    @Override
    public List<Menu> findByRoleIdNotPid(int roleId) {
        return menuRepository.findByRoleIdNotPid(roleId);
    }

    @Override
    public List<Menu> findByParentIdAndRoleId(int type, int parentId, int roleId) {
        return menuRepository.findByParentIdAndRoleId(type, parentId, roleId);
    }

    @Override
    public List<Menu> findByParentIdAndRoleId(int parentId, int roleId) {
        return menuRepository.findByParentIdAndRoleId(parentId, roleId);
    }

    @Override
    public List<Menu> findByParentId(int parentId) {
        return menuRepository.findByParentId(parentId);
    }


    @Override
    public Menu findById(Integer id) {
        return menuRepository.findOne(id);
    }

    public List<Menu> findByMenuPid2(int menuPid) {
        return menuRepository.findByMenuPid2(menuPid);
    }

    @Override
    public List<Menu> findByMenuPid(int id, int roleId) {
        return menuRepository.findByMenuPid(id, roleId);
    }


    @Override
    public List<Menu> findByMenuLevel2() {
        return menuRepository.findByMenuLevel2();
    }

    @Override
    public List<Menu> findByMenu() {
        return menuRepository.findByMenu();
    }


    //---------------------------------分割线-------------------------------------------

    /**
     * 获取所有菜单信息----平台
     *
     * @param parentId
     * @param roleId
     * @return
     */
    @Override
    public JSONArray getAllMenuByParentId_PT(Integer type, Integer parentId, Integer roleId) {
        List<Menu> menuList = null;
        if (roleId != null) {
            menuList = findByParentIdAndRoleId(type, parentId, roleId);
        }
        JSONArray jsonArray = new JSONArray();
        //循环菜单
        for (Menu menu : menuList) {
            if ("menu".equals(menu.getResourceType())) {
                if (menu.getMenuPid() == null) {
                    List<Menu> listMenu = findByParentIdAndRoleId(menu.getId(), roleId);
                    JSONObject json1 = null;
                    //size == 0为一级菜单，反之为二级菜单
                    if (listMenu.size() != 0) {
                        json1 = json(menu);
                    } else {
                        json1 = json(menu);
                    }
                    json1.put("children", children(listMenu, menu, roleId));  //二级子菜单
                    jsonArray.add(json1);
                }
            }
        }
        return jsonArray;
    }


    public JSONArray children(List<Menu> listMenu, Menu menu1, Integer roleId) {
        JSONArray jsonArray = new JSONArray();
        //size == 0为一级菜单，反之为二级菜单
        if (listMenu.size() != 0) {
            for (Menu menu : listMenu) {
                JSONObject j = json(menu);
                //判断是否有三级
                List<Menu> menuMenp = findByMenuPid(menu.getId(), roleId);
                if (menuMenp.size() != 0) {
                    j.put("children", jsonObj(menuMenp));
                }
                jsonArray.add(j);
            }
        } else {
            jsonArray.add(json1(menu1));
        }
        return jsonArray;
    }

    //判断是否有第三级，size!=0 则有第三级
    public JSONArray jsonObj(List<Menu> menuMenp) {
        JSONArray jsonArray = new JSONArray();
        for (Menu menu1 : menuMenp) {
            jsonArray.add(json(menu1));
        }
        return jsonArray;
    }

    //最原始JSONOBJ
    public JSONObject json(Menu menu) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", menu.getId());
        jsonObject.put("path", menu.getUrl());          //后台接口地址
        jsonObject.put("name", menu.getName());                     //前端路由地址名称
        jsonObject.put("component", menu.getHref());    //前端路由地址
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("title", menu.getName());       //菜单名称
        jsonObject1.put("icon", menu.getIcon());        //图标
        jsonObject.put("meta", jsonObject1);
        return jsonObject;
    }

    //一级菜单专用的特殊jSON
    public JSONObject json1(Menu menu) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", menu.getId());
        jsonObject.put("path", menu.getUrl());          //后台接口地址
        jsonObject.put("name", menu.getName());                     //前端路由地址名称
        jsonObject.put("component", menu.getHref());    //前端路由地址
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("title", menu.getIcon());       //菜单名称
        jsonObject1.put("icon", menu.getIcon());        //图标
        jsonObject.put("meta", jsonObject1);
        return jsonObject;
    }


    /**
     * 用户权限菜单---用户
     *
     * @param parentId
     * @param roleId
     * @return
     */
    @Override
    public JSONArray getAllMenuByParentId_YH(Integer type, Integer parentId, Integer roleId) {
        List<Menu> menuList = null;
        if (roleId != null) {
            menuList = findByParentIdAndRoleId(type, parentId, roleId);
        }
        JSONArray jsonArray = new JSONArray();
        //循环菜单
        for (Menu menu : menuList) {
            if ("menu".equals(menu.getResourceType())) {
                if (menu.getMenuPid() == null) {
                    List<Menu> listMenu = findByParentIdAndRoleId(menu.getId(), roleId);
                    JSONObject json1 = jsonYH(menu);
                    //size == 0为一级菜单，反之为二级菜单
                    json1.put("children", childrenYH(listMenu, roleId));  //二级子菜单
                    jsonArray.add(json1);
                }
            }
        }
        return jsonArray;
    }

    @Override
    public List<Menu> findByMenuLevel1(Integer type) {
        return menuRepository.findByMenuLevel1(type);
    }


    public JSONObject jsonYH(Menu menu) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", menu.getId());
        jsonObject.put("name", menu.getName());
        jsonObject.put("path", menu.getUrl());          //后台接口地址
        jsonObject.put("icon", menu.getIcon());          //后台接口地址
        return jsonObject;
    }

    public JSONArray childrenYH(List<Menu> listMenu, Integer roleId) {
        JSONArray jsonArray = new JSONArray();
        //size == 0为一级菜单，反之为二级菜单
        if (listMenu.size() != 0) {
            for (Menu menu : listMenu) {
                JSONObject j = jsonYH(menu);
                //判断是否有三级
                List<Menu> menuMenp = findByMenuPid(menu.getId(), roleId);
                j.put("children", jsonObjYH(menuMenp));
                jsonArray.add(j);
            }
        }
        return jsonArray;
    }


    public JSONArray jsonObjYH(List<Menu> menuMenp) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = null;
        for (Menu menu1 : menuMenp) {
            jsonObj = jsonYH(menu1);
            jsonObj.put("children", new JSONArray());
            jsonArray.add(jsonObj);
        }
        return jsonArray;
    }

    /**
     * ==================         菜单授权JSON        ============================
     */
    @Override
    public JSONArray getAllMenu() {
        User user = Jurisdiction.getCurrentUser();
        Integer type = user.getType() == 1 ? 1 : 0;
        List<Menu> listMenu = findByMenuLevel1(type);
        JSONArray jsonArray = new JSONArray();
        //循环菜单
        JSONObject json1 = null;
        for (Menu menu : listMenu) {
            if ("menu".equals(menu.getResourceType())) {
                if (menu.getMenuPid() == null) {
                    json1 = new JSONObject();
                    json1.put("id", menu.getId());
                    json1.put("label", menu.getName());
                    //if (type == 0) {
                        //json1.put("disabled", true);
                    //}
                    json1.put("children", json1(findByParentId(menu.getId())));  //二级子菜单
                    jsonArray.add(json1);
                }
            }
        }
        return jsonArray;
    }

    public JSONArray json1(List<Menu> listMenu) {
        JSONArray array = new JSONArray();
        JSONObject json = null;
        for (Menu m : listMenu) {
            json = new JSONObject();
            json.put("id", m.getId());
            json.put("label", m.getName());
            List<Menu> listM = findByMenuPid2(m.getId());
            if (listM.size() != 0) {
                json.put("children", json1(listM));
            }
            array.add(json);
        }
        return array;
    }

}
