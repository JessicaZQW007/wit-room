package com.yhxc.controller.wechat;

import com.yhxc.entity.wechat.WeChat;
import com.yhxc.service.wechat.WechatService;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.UuidUtil;
import com.yhxc.utils.wechat.GetCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @Author: 赵贺飞
 * @Date: 2018/7/9 11:54
 */
@Controller
@RequestMapping("test")
public class WechatController {

    @Resource
    private WechatService wechatService;

    /**
     * 同步微信关注的人
     *
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public String save() throws UnsupportedEncodingException {
        String token = "12_NVBywWucUdDQRH5IFz2_B-TJIRAJOkP3TdleszD8R6Zm2un5hfLuboU7WeYiTcDl5ZXxBFK1MdX1rSlG879BzzlQ1YDNF2ceXYAW2X7KSMFBuvHgmQNV_CvEkAo0dnLCwCrvFvEvZzAcCISqFDQjAAAUSF";
        //获取关注公众号的用户的OPENID列表
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token;
        JSONObject json = GetCode.HttpGet(url);
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
            wechatService.save(wc);
        }
        return "ok";
    }

}
