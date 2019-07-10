package com.yhxc.utils;

import com.yhxc.entity.system.Role;
import com.yhxc.entity.system.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵贺飞
 */
public class Jurisdiction {


    /**
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getUserName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }


    /**
     * 获取当前用户的真实姓名
     *
     * @return
     */
    public static String getCurrentRealName() {
        User user = (User) getSession().getAttribute("currentUser");
        return user.getRealName();
    }


    /**
     * 获取当前用户全部信息
     *
     * @return
     */
    public static User getCurrentUser() {
        return (User) getSession().getAttribute("currentUser");
    }

    /**
     * 获取当前角色全部信息
     *
     * @return
     */
    public static Role getCurrentRole() {
        return (Role) getSession().getAttribute("currentRole");
    }


    /**
     * shiro管理的session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }


    public static Map<String, Integer> getQX() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userName = Jurisdiction.getUserName();
        String urls = request.getRequestURL().toString();

        Map<String, Integer> map = new HashMap<String, Integer>();
        int index = urls.indexOf("/");
        index = urls.indexOf("/", index + 2) + 1;
        //截取url
        String url = urls.substring(index);
        HttpSession session = request.getSession();
        if (session != null) {

            JSONArray menuList = (JSONArray) session.getAttribute("menu_json");
            String menuId = null;
            if (menuList != null) {
                for (int i = 0; i < menuList.size(); i++) {
                    JSONObject json = menuList.getJSONObject(i);
                    JSONArray array = (JSONArray) json.get("children");

                    //获取到第二级children
                    for (int j = 0; j < array.size(); j++) {
                        JSONObject json1 = array.getJSONObject(j);

                        //判断第三级children是否存在
                        if (json1.containsKey("children")) {
                            JSONArray array1 = (JSONArray) json1.get("children");

                            //获取到第三级children
                            for (int x = 0; x < array1.size(); x++) {
                                JSONObject json2 = array1.getJSONObject(x);
                                if (url.equals(json2.getString("path"))) {
                                    menuId = json2.getString("id");
                                }
                            }
                        } else {
                            if (url.equals(json1.getString("path"))) {
                                menuId = json1.getString("id");
                            }
                        }
                    }
                }
                if (menuId != null) {
                    Boolean isAdmin = "admin".equals(userName);
                    Role role = Jurisdiction.getCurrentRole();
                    String adds = role.getAdds();
                    String dels = role.getDels();
                    String edits = role.getEdits();
                    String querys = role.getQuerys();
                    String[] addL = adds.split(",");
                    String[] delL = dels.split(",");
                    String[] editL = edits.split(",");
                    String[] queryL = querys.split(",");
                    int a = 0;
                    for (String s : addL) {
                        if (menuId.equals(s)) {
                            a++;
                        }
                    }
                    map.put("add", a == 1 || isAdmin ? 1 : 0);


                    int b = 0;
                    for (String s : delL) {
                        if (menuId.equals(s)) {
                            b++;
                        }
                    }
                    map.put("del", b == 1 || isAdmin ? 1 : 0);


                    int c = 0;
                    for (String s : editL) {
                        if (menuId.equals(s)) {
                            c++;
                        }
                    }
                    map.put("edit", c == 1 || isAdmin ? 1 : 0);


                    int d = 0;
                    for (String s : queryL) {
                        if (menuId.equals(s)) {
                            d++;
                        }
                    }
                    map.put("query", d == 1 || isAdmin ? 1 : 0);
                    System.err.println("按钮权限：" + map);
                }
            }
        }
        return map;
    }
}
