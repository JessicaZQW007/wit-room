package com.yhxc.entity.equipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/13 16:51
 * 设备协议
 */

@Entity
@Table(name = "t_equipment_agreement")
public class EquipmentAgreement {
    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String code;        //协议代码

    private String name;        //协议名称

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "EquipmentAgreement{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", companyId='" + companyId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
