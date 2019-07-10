package com.yhxc.entity.financial;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/29 15:52
 *
 * 企业收入管理
 */

@Entity
@Table(name = "t_income")
public class Income {

    @Id
    @Column(length=100)
    private String id;//唯一主键

    @Column(length=100)
    private String userName;      //充值用户的用户名

    @Column(length=100)
    private String deviceId;       //设备ID

    private BigDecimal cost;        //消费金额

    private Integer time;           //使用时长

    private Integer type;            //计费方式

    private BigDecimal price;       //单价

    private String createTime;        //创建时间,自动加入

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", cost=" + cost +
                ", time=" + time +
                ", type=" + type +
                ", price=" + price +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
