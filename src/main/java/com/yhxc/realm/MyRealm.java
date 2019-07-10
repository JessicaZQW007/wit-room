package com.yhxc.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.yhxc.service.system.MenuService;
import com.yhxc.service.system.RoleService;
import com.yhxc.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.yhxc.entity.system.Menu;
import com.yhxc.entity.system.Role;
import com.yhxc.entity.system.User;
import com.yhxc.repository.system.MenuRepository;
import com.yhxc.repository.system.RoleRepository;
import com.yhxc.repository.system.UserRepository;

/**
 * 自定义Realm
 *
 * @author yhxc 赵贺飞
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    /**
     * 权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByPhoneOrUserName(userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Role role = roleService.findByUserId(user.getId());
        Set<String> roles = new HashSet<String>();
        roles.add(role.getName());
        List<Menu> menuList = menuService.findByRoleId(role.getId());
        for (Menu menu : menuList) {
            info.addStringPermission(menu.getPermission()); // 添加权限
        }
        info.setRoles(roles);
        return info;
    }

    /**
     * 身份验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        User user = userService.findByPhoneOrUserName(userName);
        if (user != null) {
            if (user.getState() != null && user.getState() == 1) {
                throw new DisabledAccountException("账号已被锁定！");
            } else {
                return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xxx");
            }
        } else {
            return null;
        }
    }
}
