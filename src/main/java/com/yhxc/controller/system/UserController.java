package com.yhxc.controller.system;

import com.yhxc.common.Constant;
import com.yhxc.common.PageInfo;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.Role;
import com.yhxc.entity.system.User;
import com.yhxc.entity.system.UserRole;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.RoleService;
import com.yhxc.service.system.UserRoleService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.*;
import com.yhxc.utils.sms.SmsUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 后台管理用户Controller
 *
 * @author yhxc 赵贺飞
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private LogService logService;

    /**
     * 修改密码
     *
     * @param newPassword
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/changePassword")
    public ResultInfo changePassword(String newPassword, String code, HttpSession session) throws Exception {
        if (StringUtil.isEmpty(newPassword)) {
            return new ResultInfo(StatusCode.FAIL, "请输入新密码！");
        }
        String info = "";
        User user = null;
        Object sessionCode = session.getAttribute(Const.SESSION_EMAIL_CODE);        //获取session中的验证码
        if (!String.valueOf(sessionCode).equalsIgnoreCase(code)) {
            info = "验证码输入有误！";
            return new ResultInfo(StatusCode.FAIL, info);
        } else {
            Integer secondTime = Integer.parseInt(session.getAttribute("secondTime").toString());
            int i = (DateUtil.getSecondTimestamp(new Date()) - secondTime) / 60;
            if (i > 3) {
                info = "验证码已过期！";
                return new ResultInfo(StatusCode.FAIL, info);
            }
            String phone = (String) session.getAttribute("phone");
            user = userService.findByPhoneOrUserName(phone);
            user.setPassword(MD5.md5(newPassword, Constant.SALT));
            userService.save(user);
            info = "修改成功！";
        }
        logService.save(new Log(Log.UPDATE_ACTION, "修改密码"));
        session.removeAttribute("phone");
        session.removeAttribute("secondTime");
        session.removeAttribute(Const.SESSION_EMAIL_CODE);
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 忘记密码----获取手机验证码
     */
    @ResponseBody
    @RequestMapping("/forgetPassword")
    public ResultInfo forgetPwd(User user, HttpSession session) {
        String info = "";
        int code = Tools.getRandomNum();
        String content = "您的验证码是：" + code + "。请不要把验证码泄露给其他人。";
        List<User> listU = userService.findAll(user);
        if (listU.size() != 0) {
            if (Tools.checkMobileNumber(user.getPhone())) {
                if (SmsUtil.sendSms2(user.getPhone(), content, session)) {
                    info = "发送成功！";
                    session.setAttribute(Const.SESSION_EMAIL_CODE, code);
                    session.setAttribute("secondTime", DateUtil.getSecondTimestamp(new Date()));
                    session.setAttribute("phone", user.getPhone());
                    return new ResultInfo(StatusCode.SUCCESS, info);
                } else {
                    info = "消息发送失败，请联系系统管理员！";
                }
            } else {
                info = "手机号格式不正确!";
            }
        } else {
            info = "未找到该用户，请检查输入信息是否正确！";
        }
        return new ResultInfo(StatusCode.FAIL, info);
    }


    /**
     * 忘记密码 -- 通过邮箱验证找回密码
     */
//    @ResponseBody
//    @RequestMapping("/forgetPassword")
//    public ResultInfo forgetPwd(User user, HttpSession session) {
//        String info = "";
//        String title = "*智慧能源设备管理系统*密码找回操作.";      //验证码
//        int code = Tools.getRandomNum();
//        String content = "您的验证码是：" + code + "。验证码3分钟后失效，请及时处理！";
//        List<User> listU = userService.findAll(user);
//        if (listU.size() != 0) {
//            //校验成功！
//            if (Tools.checkEmail(user.getEmail())) {
//                try {
//                    boolean isSuccess = SimpleMailSender.send(user.getEmail(), title, content, "");//调用发送邮件函数
//                    if (isSuccess) {
//                        info = "发送成功！";
//                        session.setAttribute(Constant.SESSION_EMAIL_CODE, code);
//                        session.setAttribute("tempName", user.getUserName());
//                        return new ResultInfo(StatusCode.SUCCESS, info);
//                    } else {
//                        info = "发送验证码失败！";
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    info = "发送验证码失败！";
//                }
//            } else {
//                info = "邮箱格式不正确！";
//            }
//        } else {
//            info = "为找到该用户，请检查输入信息是否正确！";
//        }
//        return new ResultInfo(StatusCode.FAIL, info);
//    }


    /**
     * 查询用户列表
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/list")
    public ResultInfo listPage(User user, Integer pageNum, Integer pageSize) throws Exception {
        Page p = userService.listPage(user, pageNum, pageSize);
        System.out.println("p="+p);
        System.out.println("p.getContent()"+p.getContent());
        PageInfo pi = new PageInfo();
        pi.setPageNum(p.getNumber() + 1);
        pi.setPageSize(p.getSize());
        pi.setTotal(p.getTotalElements());
        pi.setSize(p.getNumberOfElements());

        System.out.println("Jurisdiction.getQX()="+Jurisdiction.getQX());

        return new ResultInfo(StatusCode.SUCCESS, "成功！", p.getContent(), pi, Jurisdiction.getQX());
    }


    /**
     * 保存用户角色设置
     *
     * @param roleId
     * @param userId
     * @return
     * @throws Exception
     */
    public ResultInfo saveRoleSet(Integer roleId, Integer userId) throws Exception {
        if (null == roleId || null == userId) {
            return new ResultInfo(StatusCode.FAIL, "角色ID或用户ID不能为空！");
        }
        userRoleService.deleteByUserId(userId);  // 根据用户id删除所有用户角色关联实体
        UserRole userRole = new UserRole();
        User user = userService.findById(userId);
        userRole.setUser(user);
        Role role = roleService.findById(roleId);
        userRole.setRole(role);
        userRoleService.save(userRole);
        //更行权限到列表
        user.setRoleName(role.getName());
        userService.save(user);
        logService.save(new Log(Log.ADD_ACTION, "保存用户角色设置"));
        return new ResultInfo(StatusCode.SUCCESS, "角色设置成功！");
    }

    @ResponseBody
    @RequestMapping("/findRoleId")
    public ResultInfo findByUid(Integer userId) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", userRoleService.findByUid(userId));
    }


    /**
     * 添加或者修改用户信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public ResultInfo saveOrUpdate(User user) throws Exception {
        if (user.getId() == null) {
            if (userService.findByUserName(user.getUserName()) != null) {
                return new ResultInfo(StatusCode.FAIL, "用户名已存在！");
            }
            if (userService.findByPhone(user.getPhone()) != null) {
                return new ResultInfo(StatusCode.FAIL, "手机号已被占用！");
            }
        }
        String info = "";
        if (user.getId() != null) { // 写入日志
            User u = userService.findById(user.getId());
            info = "更新用户信息成功！";
            user.setCreateTime(u.getCreateTime());
            if (StringUtil.isNotEmpty(user.getPassword())) {
                user.setPassword(MD5.md5(user.getPassword(), Constant.SALT));
            } else {
                user.setPassword(u.getPassword());
            }
            user.setType(u.getType());
            user.setCreateTime(u.getCreateTime());
            logService.save(new Log(Log.UPDATE_ACTION, "更新用户信息" + user));
        } else {
            user.setPassword(MD5.md5(user.getPassword(), Constant.SALT));
            user.setCreateTime(DateUtil.getTime());
            info = "添加用户信息成功！";
            logService.save(new Log(Log.ADD_ACTION, "添加用户信息" + user));
        }

        userService.save(user);
        //新增成功后授予权限
        User u = userService.findByUserName(user.getUserName());
        if (null != u) {
            saveRoleSet(user.getRoleId(), u.getId());
        }
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @ResponseBody
    public ResultInfo findById(Integer id) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCSSS", userService.findById(id));
    }


    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    @RequestMapping("/findByUserName")
    @ResponseBody
    public ResultInfo findByUserName(String userName) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCSSS", userService.findByUserName(userName));
    }


    /**
     * 修改手机号
     */
    @ResponseBody
    @RequestMapping("/updatePhone")
    public ResultInfo updatePhone(String newPhone) {
        if (userService.findByPhone(newPhone) != null) {
            return new ResultInfo(StatusCode.FAIL, "手机号已被占用！");
        }
        User user = Jurisdiction.getCurrentUser();
        user.setPhone(newPhone);
        logService.save(new Log(Log.UPDATE_ACTION, "修改手机号" + user.getPhone()));
        userService.save(user);
        return new ResultInfo(StatusCode.SUCCESS, "修改成功！");
    }

    /**
     * 锁定&启用账号
     * @param state
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/editState")
    public ResultInfo editState(Integer state, Integer id) {
        Integer flag = state == 0 ? 1 : 0;
        userService.updState(flag, id);
        logService.save(new Log(Log.UPDATE_ACTION, "锁定&启用账号！"));
        return new ResultInfo(StatusCode.SUCCESS, "修改成功！");
    }


    /**
     * 删除用户信息--单个
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/del")
    public ResultInfo delete(Integer id) throws Exception {
        User u = userService.findById(id);
        logService.save(new Log(Log.DELETE_ACTION, "删除用户信息" + u));
        userRoleService.deleteByUserId(id); // 删除用户角色关联信息
        userService.delete(id);
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }

    /**
     * 删除用户信息--批量
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/dels")
    public ResultInfo deletes(String ids) throws Exception {
        if (StringUtil.isNotEmpty(ids)) {
            String idStr[] = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                Integer id = Integer.parseInt(idStr[i]);
                User u = userService.findById(id);
                logService.save(new Log(Log.DELETE_ACTION, "删除用户信息" + u));
                userRoleService.deleteByUserId(id); // 删除用户角色关联信息
                userService.delete(id);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }
}
