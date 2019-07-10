package com.yhxc.common;

import com.yhxc.utils.Jurisdiction;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 权限不足异常全局捕获。
 * @author 赵贺飞
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private Logger logger = Logger.getLogger(getClass());

    @ExceptionHandler(value = AuthorizationException.class)
    public void defaultException(Exception e){
        String username = Jurisdiction.getUserName();
        logger.error(e.getMessage() + "权限不足！" + username);
    }
}
