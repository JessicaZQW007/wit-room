package com.yhxc.common;

/**
 * 常量类
 */
public class Constant {

    public static final String SALT = "yhxc";   //密码加密的盐

    public static final String fileRoot = "C:/webFileRoot/";				//文件上传映射本地目录

    public static final String SESSION_SECURITY_CODE = "sessionSecCode";	//验证码

    public static final String SESSION_EMAIL_CODE = "emailCode";			//修改密码时的邮箱验证码

    public static final String Qrcode = "favicon.ico";						//二维码中心的小图片

    public static final String SMS1 = "config/SMS1.txt";				//短信账户配置路径1

    public static final String SMS2 = "config/SMS2.txt";				//短信账户配置路径2

    public static final String EMAIL = "config/EMAIL.txt";			//邮箱服务器配置路径

    public static final String SESSION_QX = "qx";               //增删改查的权限session
}
