package com.yhxc.entity.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 空调实体类
 * lwg
 */
@Entity
@Table(name = "p_air")
public class Air {

    @Id
    @Column(length=100)
    private String id;//唯一主键

    @Column(length=100)
    private String createtime;//创建时间
    @Column(length=100)
    private String brandId;//空调品牌ID
    @Column(length=255)
    private String power;//额定功率
    @Column(length=255)
    private String current;//额定电流
    @Column(length=100)
    private String projectId;//项目Id

    @Column(length=100)
    private int airName;//空调名

    @Column(length=100)
    private String airType;//空调类型（红外空调）

    public int getAirName() {
        return airName;
    }

    public void setAirName(int airName) {
        this.airName = airName;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPower() {
        return power;
    }

    public String getAirType() {
        return airType;
    }

    public void setAirType(String airType) {
        this.airType = airType;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "Air{" +
                "id='" + id + '\'' +
                ", createtime='" + createtime + '\'' +
                ", brandId='" + brandId + '\'' +
                ", power='" + power + '\'' +
                ", projectId='" + projectId + '\'' +
                ", airType='" + airType + '\'' +
                '}';
    }
}
