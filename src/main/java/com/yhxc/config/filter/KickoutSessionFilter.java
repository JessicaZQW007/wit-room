package com.yhxc.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.utils.ShiroFilterUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author 赵贺飞
 * @类名称：KickoutSessionFilter
 * @类描述：自定义过滤器，进行用户访问控制
 * @创建时间：2018年10月24日 下午5:18:29
 */
public class KickoutSessionFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(KickoutSessionFilter.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private String kickoutUrl; // 踢出后到的地址
    private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户
    private int maxSession; // 同一个帐号最大会话数 默认1
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);

        //session过期返回登录界面
        HttpServletRequest sr = (HttpServletRequest) request;
        String strURL = sr.getRequestURL().toString();
        String dizhi= StringUtils.substringBefore(strURL,"login");
        String url=dizhi+"login.html;JSESSIONID="+sr.getSession().getId();
        if (strURL.equals(url)||strURL.equals(dizhi+"login.html")){

            return isResponse(request, response);
        }


        // 没有登录授权 且没有记住我
            /*if (!subject.isAuthenticated() && !subject.isRemembered()) {
                System.out.println("session过期1");
                if (ShiroFilterUtils.isAjax(request)) {
                    System.out.println("传值到前端");
                    isResponse(request, response);
                    return false;
                } else {
                    System.out.println("传值到前端1");
                    //没有session 返回登录页
                    return true;
                }


            }*/



        // 获得用户请求的URI
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        logger.debug("===当前请求的uri：==" + path);
        //放行登录
        if (path.equals("/toLogin")) {
            return true;
        }
        Session session = subject.getSession();
        try {
            // 当前用户
            String username = (String) subject.getPrincipal();
            logger.debug("===当前用户username：==" + username);
            Serializable sessionId = session.getId();
            logger.debug("===当前用户sessionId：==" + sessionId);
            // 读取缓存用户 没有就存入
            Deque<Serializable> deque = cache.get(username);
            logger.debug("===当前deque：==" + deque);
            if (deque == null) {
                // 初始化队列
                deque = new LinkedList<Serializable>();
            }


            // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
            if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
                // 将sessionId存入队列
                deque.push(sessionId);
                // 将用户的sessionId队列缓存
                cache.put(username, deque);
            }
            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                logger.debug("===deque队列长度：==" + deque.size());
                Serializable kickoutSessionId = null;
                // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
                if (kickoutAfter) { // 如果踢出后者
                    kickoutSessionId = deque.removeFirst();
                } else { // 否则踢出前者
                    kickoutSessionId = deque.removeLast();
                }
                // 踢出后再更新下缓存队列
                cache.put(username, deque);
                try {
                    // 获取被踢出的sessionId的session对象
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                    if (kickoutSession != null) {
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setAttribute("kickout", true);
                    }
                } catch (Exception e) {// ignore exception
                }
            }

            // 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
            if ((Boolean) session.getAttribute("kickout") != null
                    && (Boolean) session.getAttribute("kickout") == true) {
                // 会话被踢出了
                try {
                    // 退出登录
                    subject.logout();
                } catch (Exception e) { // ignore
                }
                saveRequest(request);
                logger.debug("==踢出后用户重定向的路径kickoutUrl:" + kickoutUrl);
                // ajax请求
                // 重定向
                return isAjaxResponse(request, response);
            }
            return true;
        } catch (Exception e) { // ignore
            logger.error("控制用户在线数量【lyd-admin-->KickoutSessionFilter.onAccessDenied】异常！", e);
            return isAjaxResponse(request, response);
        }
    }


    private boolean isAjaxResponse(ServletRequest request, ServletResponse response) throws IOException {
        // ajax请求
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        //判断是不是Ajax请求
        // if (ShiroFilterUtils.isAjax(request)) {
        out(response, new ResultInfo(StatusCode.ALREADY_LOGIN, "您已在别处登录，请您修改密码或重新登录！"));
        //} else {
        // 重定向
          WebUtils.issueRedirect(request, response, kickoutUrl);
        //}
        return false;
    }

    private boolean isResponse(ServletRequest request, ServletResponse response) throws IOException {
        // ajax请求
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        //判断是不是Ajax请求
        // if (ShiroFilterUtils.isAjax(request)) {
        out(response, new ResultInfo(StatusCode.SESSION_EXPIRED, "登录过期！重新登录"));
        //} else {
        // 重定向
        //   WebUtils.issueRedirect(request, response, kickoutUrl);
        //}
        return false;
    }

    /**
     * @param response
     * @param result
     * @描述：response输出json
     * @创建时间：2018年4月24日 下午5:14:22
     */
    public static void out(ServletResponse response, ResultInfo result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");//设置编码
            response.setContentType("application/json");//设置返回类型
            out = response.getWriter();
            out.println(objectMapper.writeValueAsString(result));//输出
            logger.error("用户在线数量限制【wyait-manager-->KickoutSessionFilter.out】响应json信息成功");
        } catch (Exception e) {
            logger.error("用户在线数量限制【wyait-manager-->KickoutSessionFilter.out】响应json信息出错", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
