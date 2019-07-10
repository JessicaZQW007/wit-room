package com.yhxc.entity.financial;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/29 16:06
 *
 * 用户充值记录
 */

@Entity
@Table(name = "t_recharge")
public class Recharge {

    @Id
    @Column(length=100)
    private String id;//唯一主键

    @Column(length=100)
    private String userName;    //终端用户名

    private BigDecimal money;   //金额

    private Integer type;       //充值类型

    @Column(length=100)
    private String orderNo;     //充值流水号

    @Column(length=100)
    private String tradeNo;         //平台方流水号

    private Date payTime;           //支付时间

    private String createTime;        //创建时间

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Recharge{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", money=" + money +
                ", type=" + type +
                ", orderNo='" + orderNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", payTime=" + payTime +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
