package com.yhxc.controller.information;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: 张权威
 * @Date: 2019/6/17 9:01
 */
@Controller
@RequestMapping("/commandA011")
public class commandA011Comtroller {
    @RequestMapping("/command")
    @ResponseBody
    public ResultInfo doPostTestFour(String type, String uuid, String unitAddr, Integer time, Integer temp, String status, Integer Humidity, Integer tempDeviation, Integer humidityDeviation) throws IOException {
        String MessageID = "";
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        StringBuffer params = new StringBuffer();
        String uri = "";
        try {
            switch (type) {
                case "01"://开关机
                    params.append("type=" + type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("status=" + URLEncoder.encode(status, "utf-8"));
                    params.append("&");
                    params.append("unitAddr=" + URLEncoder.encode(unitAddr, "utf-8"));
                    uri = "http://183.56.202.31:8082/commandA011/command";
                    break;
                case "02"://主板上报周期
                    params.append("type=" + type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("time=" + time);
                    params.append("&");
                    params.append("unitAddr=" + URLEncoder.encode(unitAddr, "utf-8"));
                    uri = "http://183.56.202.31:8082/commandA011/command";
                    break;
                case "03"://温度设定值
                    params.append("type=" + type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("temp=" + temp);
                    params.append("&");
                    params.append("unitAddr=" + URLEncoder.encode(unitAddr, "utf-8"));
                    uri = "http://183.56.202.31:8082/commandA011/command";
                    break;
                case "04"://湿度设定值
                    params.append("type=" + type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("Humidity=" + Humidity);
                    params.append("&");
                    params.append("unitAddr=" + URLEncoder.encode(unitAddr, "utf-8"));
                    uri = "http://183.56.202.31:8082/commandA011/command";
                    break;
                case "05"://温度偏差值
                    params.append("type=" + type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("tempDeviation=" + tempDeviation);
                    params.append("&");
                    params.append("unitAddr=" + URLEncoder.encode(unitAddr, "utf-8"));
                    uri = "http://183.56.202.31:8082/commandA011/command";
                    break;
                case "06"://湿度偏差值
                    params.append("type=" + type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("humidityDeviation=" + humidityDeviation);
                    params.append("&");
                    params.append("unitAddr=" + URLEncoder.encode(unitAddr, "utf-8"));
                    uri = "http://183.56.202.31:8082/commandA011/command";
                    break;
            }
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri + "?" + params);

        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");

        // 响应模型
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        try {
            // 由客户端执行(发送)Post请求


            // 从响应模型中获取响应实体

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                MessageID = EntityUtils.toString(responseEntity);
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", response.getStatusLine() + "," + MessageID);
    }

}
