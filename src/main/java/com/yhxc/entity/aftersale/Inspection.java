package com.yhxc.entity.aftersale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/11 16:07
 * 设备巡检
 */

@Entity
@Table(name = "t_inspection")
public class Inspection {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String name;        //任务名称；

    private String content;    //任务描述

    private String startTime;   //开始时间

    private String finishTime;  //完后时间

    private Integer state;      //任务状态

    private Integer num;        //故障数量

    private String head;        //负责人

    private String phone;       //负责人电话

    private String createTime;      //创建时间


    private String companyId;           //所属企业的ID

    private String organizationId;      //所属机构的ID

    private String dealerId;              //所属经销商的ID


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Inspection{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", state=" + state +
                ", num=" + num +
                ", head='" + head + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                ", companyId='" + companyId + '\'' +
                ", organizationId='" + organizationId + '\'' +
                ", dealerId='" + dealerId + '\'' +
                '}';
    }
}
