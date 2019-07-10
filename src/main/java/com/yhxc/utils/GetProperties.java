package com.yhxc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/28 15:16
 */
public class GetProperties {

    /**
     * 读取dbfh.properties 配置文件
     *
     * @return
     * @throws IOException
     */
    public static Properties getPprVue(String filePath) {
        InputStream inputStream = GetProperties.class.getClassLoader().getResourceAsStream(filePath);
        Properties p = new Properties();
        try {
            p.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            //读取配置文件出错
            e.printStackTrace();
        }
        return p;
    }
}
