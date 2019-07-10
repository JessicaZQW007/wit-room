package com.yhxc.controller.system;

import com.yhxc.common.Constant;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.Role;
import com.yhxc.entity.system.User;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.MenuService;
import com.yhxc.service.system.RoleService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.*;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private LogService logService;

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * web端用户登录请求
     *
     * @param userName
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResultInfo login(String userName, String password, String code, boolean rememberMe, HttpSession session) {

        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            return new ResultInfo(StatusCode.FAIL, "用户名或密码不能为空！");
        }
        //校验验证码
        /*String sessionCode = (String) session.getAttribute(Constant.SESSION_SECURITY_CODE);        //获取session中的验证码
        if (!sessionCode.equalsIgnoreCase(code)) {
            //msg = "验证码输入有误!";
            msg = "Verification code not found!";
            return "redirect:/login.html?msg=" + msg;
        }*/
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5.md5(password, Constant.SALT), rememberMe);
        try {
            subject.login(token); // 登录认证
            String userNameOrPhone = (String) SecurityUtils.getSubject().getPrincipal();
            User currentUser = userService.findByPhoneOrUserName(userNameOrPhone);
            session.setAttribute("currentUser", currentUser);
            Role currentRole = roleService.findByUserId(currentUser.getId());
            session.setAttribute("currentRole", currentRole);
            logService.save(new Log(Log.LOGIN_ACTION, "登录成功!"));
            session.removeAttribute(Constant.SESSION_SECURITY_CODE);    //清除登录验证码的session
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("currentUser", currentUser);
            map.put("currentRole", currentRole);
            //session.setMaxInactiveInterval(20);设置当前会话超时时间
            return new ResultInfo(StatusCode.SUCCESS, "登录成功！", map);
        } catch (UnknownAccountException e) {
            logService.save(new Log(Log.LOGIN_ACTION, "登录失败，账号不存在！"));
            return new ResultInfo(StatusCode.FAIL, "登录失败，账号不存在！");
        } catch (IncorrectCredentialsException e) {
            logService.save(new Log(Log.LOGIN_ACTION, "用户名或密码错误!" + userName));
            return new ResultInfo(StatusCode.FAIL, "用户名或密码错误！");
        } catch (DisabledAccountException e) {
            logService.save(new Log(Log.LOGIN_ACTION, "账号已被锁定!" + userName));
            return new ResultInfo(StatusCode.FAIL, "账号已被锁定！");
        }
    }



    /**
     * 移动端用户登录请求
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/appLogin")
    @ResponseBody
    public ResultInfo appLogin(String userName, String password, boolean rememberMe, HttpSession session) {
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            return new ResultInfo(StatusCode.DATA_NOT, "用户名或密码不能为空！");
        }
        User user = userService.findByPhoneOrUserName(userName);
        //系统端
        if (user != null) {
            if (user.getType() != 1) {
                return new ResultInfo(StatusCode.FAIL, "只能登录APP账号！");
            }
        }
        //校验验证码
        /*String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE);        //获取session中的验证码
        if (!sessionCode.equalsIgnoreCase(code)) {
            //msg = "验证码输入有误!";
            msg = "Verification code not found!";
            return "redirect:/login.html?msg=" + msg;
        }*/
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5.md5(password, Constant.SALT), rememberMe);
        try {
            subject.login(token); // 登录认证
            String userNameOrPhone = (String) SecurityUtils.getSubject().getPrincipal();
            User currentUser = userService.findByPhoneOrUserName(userNameOrPhone);
            session.setAttribute("currentUser", currentUser);
            Role currentRole = roleService.findByUserId(currentUser.getId());
            session.setAttribute("currentRole", currentRole);
            logService.save(new Log(Log.LOGIN_ACTION, "登录成功!" ));
            session.removeAttribute(Const.SESSION_SECURITY_CODE);    //清除登录验证码的session
            return new ResultInfo(StatusCode.SUCCESS, "登录成功！", currentUser);
        } catch (UnknownAccountException e) {
            logService.save(new Log(Log.LOGIN_ACTION, "登录失败,账号不存在!" + userName));
            return new ResultInfo(StatusCode.FAIL,"登录失败,账号不存在!");
        } catch (IncorrectCredentialsException e) {
            logService.save(new Log(Log.LOGIN_ACTION, "用户名或密码错误!" + userName));
            return new ResultInfo(StatusCode.FAIL,"用户名或密码错误!");
        } catch (DisabledAccountException e) {
            logService.save(new Log(Log.LOGIN_ACTION, "账号已被锁定!" + userName));
            return new ResultInfo(StatusCode.FAIL,"账号已被锁定!");
        }
    }






    /**
     * 切换验证码
     *
     * @param response
     */
    @RequestMapping("/changeCode")
    public void generate(HttpServletResponse response) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = Tools.drawImg(output);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(Constant.SESSION_SECURITY_CODE, code);
        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }


    /**
     * 退出登录
     * @return
     */
    @ResponseBody
    @GetMapping("/logout")
    public ResultInfo logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        logService.save(new Log(Log.LOGOUT_ACTION, "退出登录！"));
        //清空session缓存
        Jurisdiction.getSession().removeAttribute("currentUser");
        session.removeAttribute("menu_pt");
        //退出登录
        SecurityUtils.getSubject().logout();
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }

    /**
     * 获取当前用户
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserName")
    public ResultInfo getUser() {
        return new ResultInfo(StatusCode.SUCCESS, "获取成功！", Jurisdiction.getCurrentUser());
    }

    /**
     * 当前在线人数--平台
     * @return
     */
    @ResponseBody
    @RequestMapping("/online")
    public ResultInfo getOnline() {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        System.err.println("当前在线人数：" + sessions.size());
        /*for (Session session : sessions) {
            System.out.println("登录ip:" + session.getHost());
            System.out.println("登录用户" + session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
            System.out.println("最后操作日期:" + session.getLastAccessTime());
        }*/
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", sessions.size());
    }


    /**
     * 加载权限菜单
     * @param
     * @return flag = 1 为平台管理员  ==2为用户
     * @throws Exception
     */
    @GetMapping("/loadMenuInfo")
    @ResponseBody
    public ResultInfo loadMenuInfo(HttpSession session) throws Exception {
        Role role = Jurisdiction.getCurrentRole();
        //User user = Jurisdiction.getCurrentUser();
        //Integer type = user.getType();
        //Integer flag = type == 1 ? type : 0;
        JSONArray menuList = new JSONArray();
        //第一次登录把菜单放入session，
        if (null == session.getAttribute("menu_pt")) {
            menuList = menuService.getAllMenuByParentId_PT(0, 1, role.getId());
            session.setAttribute("menu_pt", menuList);
        } else {
            menuList = (JSONArray) session.getAttribute("menu_pt");
        }
        return new ResultInfo(StatusCode.SUCCESS, "菜单加载成功！", menuList);
    }
}
