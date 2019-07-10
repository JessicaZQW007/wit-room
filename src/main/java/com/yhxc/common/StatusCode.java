package com.yhxc.common;

/**
 * 系统状态码类
 */
public class StatusCode {

    public static final int SUCCESS = 0;	//成功
    public static final int FAIL = 1;		//失败


    // 800号段用户信息异常
    // 用户尚未登录
    public static final int NO_LOGIN = 800;
    // 用户不存在
    public static final int USER_NOT_EXIST = 801;
    // 密码错误
    public static final int PWD_ERROR = 802;
    // 重复登陆错误
    public static final int ALREADY_LOGIN = 803;
    // 会话超时
    public static final int SESSION_EXPIRED = 804;
    // 用户权限不足
    public static final int UNAUTHORIZED = 805;
    // 用户退出错误
    public static final int LOGOUT_ERROR = 806;
    // 账号处于锁定状态
    public static final int USER_LOCKED = 807;
    // 用户尚未造成跨域
    public static final int NOT_LOGIN_CORS = 808;
    // ...可自行增加错误状态码


    //600号段
    // 数据重复
    public static final int DATA_REPETITION = 600;
    // 数据不存在
    public static final int DATA_NOT = 601;
    // 数据输入有误
    public static final int DATA_WRONG = 602;
    // 其他异常
    public static final int DATA_OTHER = 603;
    // 系统异常
    public static final int SYSTEM_ERROR = 604;
    // ...可自行增加错误状态码
}
