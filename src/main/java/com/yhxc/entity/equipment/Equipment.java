package com.yhxc.entity.equipment;

import javax.persistence.*;

/**
 * @Author: 赵贺飞
 * @Date: 2018/10/30 14:42
 * 设备基础信息表
 */

@Entity
@Table(name = "t_equipment")
public class
Equipment {

    @Id
    @Column(length = 100)
    private String id;              //主键

    @Column(length = 100)
    private String uuid;            //设备唯一编号

    private String protocol ;          //通讯协议

    private String name;            //设备名称

    private String eqTypeId;            //设备型号Id

    private String brandId;            //设备品牌ID

    private String enameId;            //设备名称ID


    private String productionDate;  //生产日期


    private String createTime;          //创建时间

    @Column(name = "deleted", columnDefinition = "int default 0")
    private Integer deleted=0;            //逻辑删除。1删除；0正常

    private String remark;          //设备描述（备注）

    @Column(length=255)
    private int  transrate;//互感器费率
    @Column(length=255)
    private int  voltage;//电压

    @Column(length=255)
    private String  groupName;//任务组

    @Column(length=255)
    private String  unitType;//平台单位1，项目机构2

    @Column(length=255)
    private String  unitId;//所属机构ID

    @Column(length = 100)
    private Integer state;   //设备状态  1：启用，2：停用



    /*******下列字段仅做返回值用途*******/
    @Transient
    private String address;         //项目or设备地址


    @Transient
    private String pname;           //项目名称

    @Transient
    private String unitName;           //平台单位或者项目机构

    @Transient
    private String brand;           //品牌
    @Transient
    private String model;
    @Transient
    private String pid;

    @Transient
    private String eqId;//设备id

    @Transient//型号
    private String projectId;

    @Transient
    private String statdate;//开始时间
    @Transient
    private String enddate;//结束时间


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStatdate() {
        return statdate;
    }

    public void setStatdate(String statdate) {
        this.statdate = statdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getTransrate() {
        return transrate;
    }

    public void setTransrate(int transrate) {
        this.transrate = transrate;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    public String getEqTypeId() {
        return eqTypeId;
    }

    public void setEqTypeId(String eqTypeId) {
        this.eqTypeId = eqTypeId;
    }

    public String getPname() {
        return pname;
    }


    public String getAddress() {
        return address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getEnameId() {
        return enameId;
    }

    public void setEnameId(String enameId) {
        this.enameId = enameId;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", protocol='" + protocol + '\'' +
                ", name='" + name + '\'' +
                ", eqTypeId='" + eqTypeId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", enameId='" + enameId + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", createTime='" + createTime + '\'' +
                ", deleted=" + deleted +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                ", pname='" + pname + '\'' +
                ", unitName='" + unitName + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", projectId='" + projectId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", state='" + state +
                ", unitType='" + unitType + '\'' +
                '}';
    }
}
