package com.yhxc.entity.equipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/27 15:49
 * 设备通讯协议
 */


@Entity
@Table(name = "t_equipment_protocol")
public class EquipmentProtocol {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String code;        //协议代码

    private String name;        //协议名称

    private String type;        //协议类别

    private Integer num;        //协议数量

    private String remark;      //备注

    private String createTime;  //创建时间

    private String userName;        //创建人

    private String companyId;   //所属企业的ID

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "EquipmentProtocol{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", num=" + num +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userName='" + userName + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
