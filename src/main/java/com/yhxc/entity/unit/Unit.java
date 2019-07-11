package com.yhxc.entity.unit;


import javax.persistence.*;

/**
 * 单位机构
 *
 * @author yhxc 陈霖炎
 */
@Entity
@Table(name = "t_unit")
public class Unit {

    @Id
    @Column(length = 100)
    private String id;         // 唯一主键

    @Column(length = 50)
    private String name;    // 单位名称

    @Column(length = 50)
    private String contacts;    // 联系人

    @Column(length = 50)
    private String phone;    // 联系电话

    @Column(length = 50)
    private String site;    // 单位地址

    @Column(length = 50)
    private String address;    // 详细地址

    @Column(length = 50)
    private String type;    // 单位类型：1为 平台单位 2为机构单位

    @Column(length = 50)
    private String pId;    // 所属上级单位，平台1级，机构2级

    @Column(length = 50)
    private String createtime;    // 创建时间

    @Column(name = "state", columnDefinition = "tinyint default 0")
    private Integer state;      //状态；0正常；1锁定


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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", phone='" + phone + '\'' +
                ", site='" + site + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", pId='" + pId + '\'' +
                ", createtime='" + createtime + '\'' +
                ", state=" + state  +
                '}';
    }


}
