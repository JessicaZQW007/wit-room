package com.yhxc.entity.aftersale;


import javax.persistence.*;

/**
 * 在线报修实体类
 */

@Entity
@Table(name = "t_repair")
public class Repair   {

    @Id
    @Column(length = 100)
    private String id;

    private String uuid;        //故障设备UUID

    @Column(length = 1000)
    private String problem;         //问题描述

    private String img1;             //故障图片1

    private String img2;             //故障图片2

    private Integer state;          //报修处理状态，0受理中，1已分派，2已处理

    private String userName;          //申请人

    private String phone;

    private String createTime;        //申请时间


    //以下是分派状态
    private String acceptName;        //分派人姓名

    private String acceptPhone;       //分派人电话

    private String acceptNum;         //分派人工号

    private String acceptTime;        //分派时间


    //以下是处理后状态
    private String signature;         //处理后签字

    private String finishTime;        //处理时间

    @Column(length = 1000)
    private String remark;            //处理后备注


    public Repair() {
        super();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
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

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getAcceptPhone() {
        return acceptPhone;
    }

    public void setAcceptPhone(String acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    public String getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(String acceptNum) {
        this.acceptNum = acceptNum;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", problem='" + problem + '\'' +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                ", acceptName='" + acceptName + '\'' +
                ", acceptPhone='" + acceptPhone + '\'' +
                ", acceptNum='" + acceptNum + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", signature='" + signature + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
