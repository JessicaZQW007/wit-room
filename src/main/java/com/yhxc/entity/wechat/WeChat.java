package com.yhxc.entity.wechat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/8/22 15:10
 */

@Entity
@Table(name = "t_wachat")
public class WeChat {

    @Id
    @Column(length=100)
    private String id;

    private Integer subscribe;      //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。

    @Column(length=100)
    private String openid;          //用户的标识，对当前公众号唯一

    @Column(length=100)
    private String nickname;        //用户的昵称

    private Integer sex;            //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知

    @Column(length=100)
    private String language;        //用户所在城市

    @Column(length=100)
    private String city;            //用户所在国家

    @Column(length=100)
    private String province;        //用户所在省份

    @Column(length=100)
    private String country;         //用户的语言，简体中文为zh_CN

    @Column(length=500)
    private String headimgurl;      //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。

    private Integer subscribeTime;  //用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间

    @Column(length=100)
    private String remark;          //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。

    private Integer groupid;        //公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注

    @Column(length=100)
    private String tagidList;       //用户被打上的标签ID列表

    @Column(length=100)
    private String subscribeScene;  //返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他

    private Integer qrScene;         //	二维码扫码场景（开发者自定义）

    @Column(length=100)
    private String qrSceneStr;       //二维码扫码场景描述（开发者自定义）


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Integer getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Integer subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getTagidList() {
        return tagidList;
    }

    public void setTagidList(String tagidList) {
        this.tagidList = tagidList;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public Integer getQrScene() {
        return qrScene;
    }

    public void setQrScene(Integer qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    @Override
    public String toString() {
        return "WeChat{" +
                "id='" + id + '\'' +
                ", subscribe=" + subscribe +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", remark='" + remark + '\'' +
                ", groupid=" + groupid +
                ", tagidList='" + tagidList + '\'' +
                ", subscribeScene='" + subscribeScene + '\'' +
                ", qrScene=" + qrScene +
                ", qrSceneStr='" + qrSceneStr + '\'' +
                '}';
    }
}
