package com.yhxc.entity.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 项目实体类
 * lwg
 */
@Entity
@Table(name = "p_project")
public class Project  {

    @Id
    @Column(length=100)
    private String id;//唯一主键
    @Column(length=100)
    private String pname;//项目名称
    @Column(length=100)
    private String address;//所在地址
    @Column(length=255)
    private String img;//项目图片
    @Column(length=100)
    private String puser;//负责人
    @Column(length=100)
    private String puserPhone;//联系方式
    @Column(length=100)
    private String status;//项目状态（启用 停用）
    @Column(length=100)
    private String roomArea;//机房面积
    @Column(length=2555)
    private String remarks;//项目简介

    @Column(length=255)
    private String type;//项目类型（分体空调，精密空调，专用空调）

    @Column(length=2555)
    private String location;//经度纬度

    private int equipmentNum;//空调数量

    @Column(length=100)
    private String createtime;//创建日期

    private Double electricityPrice;//每度电的单价

    @Column(length=100)
    private String eqId;//设备id

    @Column(length=100)
    private String unitType;//平台单位或项目机构

    @Column(length=100)
    private String unitId;//机构ID



    @Column(length=255)
    private Integer  transrate;//互感器倍率 默认1

    @Column(length=255)
    private Integer  voltage;//设备电压 默认380V





    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(int equipmentNum) {
        this.equipmentNum = equipmentNum;
    }


    public Double getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(Double electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPuser() {
        return puser;
    }

    public void setPuser(String puser) {
        this.puser = puser;
    }

    public String getPuserPhone() {
        return puserPhone;
    }

    public void setPuserPhone(String puserPhone) {
        this.puserPhone = puserPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(String roomArea) {
        this.roomArea = roomArea;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getTransrate() {
        return transrate;
    }

    public void setTransrate(Integer transrate) {
        this.transrate = transrate;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }



    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", pname='" + pname + '\'' +
                ", address='" + address + '\'' +
                ", img='" + img + '\'' +
                ", puser='" + puser + '\'' +
                ", puserPhone='" + puserPhone + '\'' +
                ", status='" + status + '\'' +
                ", roomArea='" + roomArea + '\'' +
                ", remarks='" + remarks + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", equipmentNum=" + equipmentNum +
                ", createtime='" + createtime + '\'' +
                ", electricityPrice=" + electricityPrice +
                ", unitType=" + unitType + '\'' +
                ", unitId=" + unitId  + '\'' +
                ", transrate=" + transrate  +
                ", voltage=" + voltage  +
                '}';
    }
}
