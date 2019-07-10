package com.yhxc.entity.aftersale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 9:29
 */

@Entity
@Table(name = "t_warranty_record")
public class WarrantyRecord {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String uuid;        //保修的设备UUID

    private String year;            //保修年限

    private String deadline;        //保修到期时间

    private String unit;            //保修单位

    private String head;            //负责人

    private String phone;           //联系电话

    private String remark;          //备注

    private String userName;        //操作人

    private String createTime;      //创建时间

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WarrantyRecord{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", year='" + year + '\'' +
                ", deadline='" + deadline + '\'' +
                ", unit='" + unit + '\'' +
                ", head='" + head + '\'' +
                ", phone='" + phone + '\'' +
                ", remark='" + remark + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
