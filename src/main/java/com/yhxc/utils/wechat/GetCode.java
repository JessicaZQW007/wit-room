package com.yhxc.utils.wechat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yhxc.entity.wechat.WeChat;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @Author: 赵贺飞
 * @Date: 2018/8/14 17:11
 */
public class GetCode {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws IOException {
        String token = "12_NVBywWucUdDQRH5IFz2_B-TJIRAJOkP3TdleszD8R6Zm2un5hfLuboU7WeYiTcDl5ZXxBFK1MdX1rSlG879BzzlQ1YDNF2ceXYAW2X7KSMFBuvHgmQNV_CvEkAo0dnLCwCrvFvEvZzAcCISqFDQjAAAUSF";
        //获取关注公众号的用户的OPENID列表
        /*String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token;
        JSONObject json = HttpGet(url);
        JSONObject json1 = json.getJSONObject("data");
        String openidListStr = json1.getString("openid");
        JSONArray array = JSONArray.fromObject(openidListStr);
        for (int i = 0; i < array.size(); i++) {
            String openid = array.get(i).toString();
            System.err.println(openid);
            String url1 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid;
            WeChat wc = new WeChat();
            JSONObject j1 = GetCode.HttpGet(url1);
            wc.setCity(j1.getString("city"));
            wc.setGroupid(Integer.parseInt(j1.getString("groupid")));
            wc.setHeadimgurl(j1.getString("headimgurl"));
            wc.setId(UuidUtil.get32UUID());
            wc.setLanguage(j1.getString("language"));
            String b_text = j1.getString("nickname");
            wc.setNickname(StringUtil.filterOffUtf8Mb4(b_text));
            wc.setOpenid(j1.getString("openid"));
            wc.setProvince(j1.getString("province"));
            wc.setCountry(j1.getString("country"));
            wc.setQrScene(Integer.parseInt(j1.getString("qr_scene")));
            wc.setQrSceneStr(j1.getString("qr_scene_str"));
            wc.setRemark(j1.getString("remark"));
            wc.setSex(Integer.parseInt(j1.get("sex").toString()));
            //wc.setSubscribe(Integer.parseInt(j1.getString("")));
            wc.setSubscribeScene(j1.getString("subscribe_scene"));
            wc.setSubscribeTime(Integer.parseInt(j1.getString("subscribe_time")));
        }*/
        String url1 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=oNLtTw6H2Rx5Qg-SWaEjsbKfPkGY";
        System.err.println(HttpGet(url1));;
    }


    /**
     * 获取员工信息的接口地址
     **/
    public  String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";


    /**
     * 根据code获取成员信息
     * @param access_token 调用接口凭证
     * @param code   通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
     * @param agentid   跳转链接时所在的企业应用ID 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
     * */
    public  String getUserID(String access_token, String code, String agentid) {
        String UserId = "";
        CODE_TO_USERINFO = CODE_TO_USERINFO.replace("ACCESS_TOKEN", access_token).replace("CODE", code).replace("AGENTID", agentid);
        JSONObject jsonobject = HttpGet(CODE_TO_USERINFO);
        if (null != jsonobject) {
            UserId = jsonobject.getString("UserId");
            if (!"".equals(UserId)) {
                System.out.println("获取信息成功，o(∩_∩)o ————UserID:" + UserId);
            } else {
                int errorrcode = jsonobject.getInt("errcode");
                String errmsg = jsonobject.getString("errmsg");
                String error = "错误码：" + errorrcode + "————" + "错误信息：" + errmsg;
            }
        } else {
        }
        return UserId;
    }



    public static JSONObject HttpGet(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        JSONObject jsonObject = null;
        try {
            String response = httpClient.execute(httpGet, responseHandler);
            jsonObject = JSONObject.fromObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}