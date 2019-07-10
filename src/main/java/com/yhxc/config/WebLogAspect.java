/**
 * Copyright © 2018 eSunny Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.yhxc.energy.config
 * @author: 10713
 * @date: 2018年3月15日 上午10:19:12
 */
package com.yhxc.config;

import com.yhxc.entity.system.Role;
import com.yhxc.utils.Device;
import com.yhxc.utils.Jurisdiction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.catalina.Session;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.SchedulerException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Company:元泓星辰研发部
 * @ClassName: WebLogAspect
 * @Description: 拦截日志类（AOP）
 * @author: 张权威
 * @E-mail: 1071306022@qq.com
 * @date: 2018年3月15日 上午10:19:12
 */

@Aspect
@Order(5)
@Component
public class WebLogAspect {
    private Logger logger = Logger.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.yhxc.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userName = Jurisdiction.getUserName();
        String urls = request.getRequestURL().toString();
        // 记录下请求内容
        logger.info("*************************AOP请求日志拦截START***************************");
        logger.info("* USERNAME : " + userName);
        logger.info("* URL : " + urls);
        logger.info("* HTTP_METHOD : " + request.getMethod());
        logger.info("* IP : " + request.getRemoteAddr());
        logger.info("* CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("* ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("* DEVICE : " + Device.isMobileDevice(request));
        logger.info("***********************************************************************");
    }


    /**
     * 请求之后的响应信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("***************************拦截器END*****************************");
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        logger.info("****************************************************************");
    }
}