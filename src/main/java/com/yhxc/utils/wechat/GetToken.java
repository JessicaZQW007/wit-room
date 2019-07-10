package com.yhxc.utils.wechat;

import net.sf.json.JSONObject;

import static java.lang.Thread.sleep;

public class GetToken {

    public static final String SESSION_SECURITY_CODE = "sessionSecCode";
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //访问地址
        String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        //appid
        String APPID = "wxf62ff5631358ef2e";
        //appsecret
        String APPSECRET = "2af33a0092006148a5000ff06e9151bb";
        String request_url = TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        HttpsUtil httpsUtil = new HttpsUtil();
        System.out.println(request_url);
        int i = 0;
        while(true){
            JSONObject jsonObject = httpsUtil.HttpsUtil(request_url,"GET",null);
            if(null != jsonObject){
                String access_tocken = jsonObject.getString("access_token");
                String expires_in = jsonObject.getString("expires_in");
                //获取到的access_tocken值可以写入到文本文件中供其他业务逻辑调用，实例中只打印了没有保存到文件
                System.out.println("获取成功"+"access_tocken="+access_tocken+"||expires_in="+expires_in);
                i=Integer.parseInt(expires_in);
            }
            //休眠1小时57分钟，提前三分钟获取新的access_token
            sleep((7200-180)*1000);
        }
    }
}
