package com.yhxc.utils.wechat;

import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.net.URL;
        import java.net.HttpURLConnection;
        import javax.net.ssl.HttpsURLConnection;

        import net.sf.json.JSONObject;

/**
 *
 * @author liu
 */
public class HttpsUtil {
    /**
     * HttpsUtil方法https请求结果返回蔚json类型
     * @param Url http请求地址
     * @param Method http请求类型支持POST GET
     * @param Output
     * @return InputStream转换成JSONObject后返回
     * @throws Exception
     */
    public JSONObject HttpsUtil(String Url, String Method, String Output) throws Exception{
        JSONObject jsonObject = null;
        URL conn_url =  new URL(Url);
        HttpURLConnection conn = (HttpsURLConnection)conn_url.openConnection();
        conn.setRequestMethod(Method);
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.connect();
        //output获取access_token是不会用到
        if(Output != null){
            OutputStream outputstream =conn.getOutputStream();
            //字符集，防止出现中文乱码
            outputstream.write(Output.getBytes("UTF-8"));
            outputstream.close();
        }
        //正常返回代码为200
        if(conn.getResponseCode()==200){
            InputStream stream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(stream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            stream.close();
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        }
        System.out.println(conn.getResponseCode());
        return jsonObject;
    }
}
