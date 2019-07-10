package com.yhxc.entity.information;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/29 15:38
 * 意见反馈实体类
 */

@Entity
@Table(name = "t_feedback")
public class Feedback {

    @Id
    @Column(length = 100)
    private String id;

    private Integer type;           //类型：1产品建议，2程序问题，3其它问题

    @Column(columnDefinition = "TEXT")
    private String content;         //问题描述

    private String img;             //问题拍照或截图

    private Integer state;          //处理状态:1已处理；0待处理

    private String userName;        //反馈人

    private String createTime;      //反馈时间

    private Integer userType;       //反馈人用户类别


    private String dealTime;        //处理时间

    private String dealUserName;    //处理人的用户名

    private String dealRemark;      //处理后的备注



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealUserName() {
        return dealUserName;
    }

    public void setDealUserName(String dealUserName) {
        this.dealUserName = dealUserName;
    }

    public String getDealRemark() {
        return dealRemark;
    }

    public void setDealRemark(String dealRemark) {
        this.dealRemark = dealRemark;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userType=" + userType +
                ", dealTime='" + dealTime + '\'' +
                ", dealUserName='" + dealUserName + '\'' +
                ", dealRemark='" + dealRemark + '\'' +
                '}';
    }
}
