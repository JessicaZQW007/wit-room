package com.yhxc.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 赵贺飞
 * @Date: 2018/4/27 11:01
 * 判断是否是电脑登录
 */
public class Device {
    public static String isMobileDevice(HttpServletRequest request) {
        String requestHeader = request.getHeader("User-Agent");
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android", "mac os", "windows phone"};
        if (requestHeader == null)
            return "电脑端";
        requestHeader = requestHeader.toLowerCase();
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) > 0) {
                return "移动端";
            }
        }
        return "电脑端";
    }


    /**
     *判断是不是平台管理员
     */
public  static boolean isadmin(){
    int roleId = Jurisdiction.getCurrentRole().getId();
    if(roleId==1)//判断是不是平台管理员
    {
        return  true;
    }
    else{
        return  false;
    }

    }
}




