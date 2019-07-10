package com.yhxc.utils.wechat;

import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: 赵贺飞
 * @Date: 2018/9/3 10:51
 */
public class WaChatUtil {

    public static void main(String[] args) throws IOException {
        Integer flag = 2;
        String token = getAccessToken().getString("access_token");
        String touser = "oNLtTw6H2Rx5Qg-SWaEjsbKfPkGY";
        //String text = "文字消息内容";
        String text = "E:\\SougouImages\\SogouWP\\Net\\WallPaper\\405376.jpg";
        System.err.println(customSend(flag, token, touser, text));
    }




    /**
     * @param flag  == 1是文字消息；==2是图片消息
     * @param token
     * @param text  当flag ==1时，text为发送的文字内容；当flag == 2时，text为发送图片的路径
     * @param touser 信息接收者对应的openid
     *
     *              通过公司微信公众号推送信息
     */
    public static String customSend(Integer flag, String token, String touser, String text) throws IOException {
        JSONObject json = new JSONObject();
        if (flag == 1) {
            JSONObject j = new JSONObject();
            j.element("content", text);
            json.element("touser", touser);
            json.element("text", j);
            json.element("msgtype", "text");
        } else {
            File file = new File(text);
            if (!file.exists() || !file.isFile()) {
               return "文件不存在！";
            } else {
                JSONObject j = new JSONObject();
                j.element("media_id", uploadImg(text, token, "image"));
                json.element("touser", touser);
                json.element("image", j);
                json.element("msgtype", "image");
            }
        }

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(json.toString());
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
           // System.out.println("客服消息result：" + result);
        } catch (Exception e) {
            System.out.println("向客服发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    /**
     * @param filePath
     * @param accessToken
     * @param type
     * @return
     * @throws IOException
     */
    public static String uploadImg(String filePath, String accessToken, String type) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        //String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=" + type;
        URL urlobj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlobj.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        //设置头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNFARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNFARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNFARY);
        sb.append("\r\n");
        sb.append("Content-Disposition:from-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/actet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");
        //获得输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件以流的方式 推入到url
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        //结尾部分
        byte[] foot = ("\r\n--" + BOUNFARY + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        if (result == null) {
            result = buffer.toString();
        }
        if (result != null) {
            reader.close();
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject);
        String typeName = "media_id";
        if (!"image".equals(type) && !"voice".equals(type) && !"video".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaid = jsonObject.getString(typeName);
        return mediaid;
    }


    /**
     * 获取token
     *
     * @return
     */
    public static JSONObject getAccessToken() {
        String appId = "wxa770ee1863ad64d8";
        String appSecret = "bf51ea00b1e0f645b9aa885aae2a5df7";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        JSONObject jsonObj = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String accessToken = new String(jsonBytes, "UTF-8");
            System.err.println(accessToken);
            jsonObj = JSONObject.fromObject(accessToken);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
