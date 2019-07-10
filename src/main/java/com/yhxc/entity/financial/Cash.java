package com.yhxc.entity.financial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/13 17:38
 * 提现设置
 */

@Entity
@Table(name = "t_cash")
public class Cash {
    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private BigDecimal cost;        //费用

    private BigDecimal upper;       //上限

    private BigDecimal lower;        //下限

    private String remark;      //备注

    private String companyId;           //所属企业的ID

    //private String organizationId;      //所属机构的ID

    //private String dealerId;              //所属经销商的ID

    private String userName;            //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getUpper() {
        return upper;
    }

    public void setUpper(BigDecimal upper) {
        this.upper = upper;
    }

    public BigDecimal getLower() {
        return lower;
    }

    public void setLower(BigDecimal lower) {
        this.lower = lower;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

/*    public String getOrganizationId() {
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
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Cash{" +
                "id='" + id + '\'' +
                ", cost=" + cost +
                ", upper=" + upper +
                ", lower=" + lower +
                ", remark='" + remark + '\'' +
                ", companyId='" + companyId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
