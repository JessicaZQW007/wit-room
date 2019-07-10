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
 * @Date: 2019/3/25 16:07
 */
@Controller
@RequestMapping("/command")
public class commandController {
    /*public static void main(String[] args) {*/
    @RequestMapping("/command")
    @ResponseBody
    public ResultInfo doPostTestFour(String type, String uuid ,String ststus, String num, String model, String tempp, String speed, String direction, Integer time, String status ,String code)throws IOException {
        String MessageID="";
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        StringBuffer params = new StringBuffer();
        String uri="";
        try {
            switch(type){
                case "01"://开关机
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("ststus="+ URLEncoder.encode(ststus, "utf-8"));
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "02"://设置运行模式
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("model="+ URLEncoder.encode(model, "utf-8"));
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "03"://设置空调温度
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("tempp="+ URLEncoder.encode(tempp, "utf-8"));
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "04"://设置风速
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("speed="+ URLEncoder.encode(speed, "utf-8"));
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "05"://设置风向
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("direction="+ URLEncoder.encode(direction, "utf-8"));
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "06"://初始化命令
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "07"://设置空调回风口温度
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("tempp="+tempp);
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/snedSWind";
                    break;
                case "08"://设置室内环境温度
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("tempp="+tempp);
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "09"://设置主板上周周期
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("time="+time);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "10"://设置继电器状态
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("status="+status);
                    params.append("&");
                    params.append("num="+num);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "11"://设置新风状态
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("status="+status);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "12"://设置温度上限值
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("tempp="+tempp);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "13"://设置主板运行规则
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("code="+code);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "14"://设置温度下限值
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("tempp="+tempp);
                    uri="http://183.56.202.31:8082/command/command";
                    break;
                case "15"://主板锁定
                    params.append("type="+type);
                    params.append("&");
                    params.append("uuid=" + URLEncoder.encode(uuid, "utf-8"));
                    params.append("&");
                    params.append("status="+status);
                    uri="http://183.56.202.31:8082/command/command";
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
                MessageID=EntityUtils.toString(responseEntity);
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
      return new ResultInfo(StatusCode.SUCCESS, "查询成功！", response.getStatusLine()+","+MessageID);
    }

}
