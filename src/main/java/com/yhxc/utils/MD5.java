package com.yhxc.utils;

import com.yhxc.common.Constant;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 说明：MD5处理
 * 创建人：元弘星辰-研发部
 * 修改时间：2018年3月20日
 */
public class MD5 {

    /**
     * Md5加密
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str,String salt){
        return new Md5Hash(str,salt).toString();
    }

   /* public static void main(String[] args) {
        System.out.println(md5("1", Constant.SALT));
    }*/
}
