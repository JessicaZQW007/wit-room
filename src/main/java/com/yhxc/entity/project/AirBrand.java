package com.yhxc.entity.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 空调品牌型号实体类
 */

@Entity
@Table(name = "p_ariBrand")
public class AirBrand {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String brand;       //品牌

    private String model;       //型号

    private String img;         //设备图片

    private String pId;         //型号所对应的品牌ID


    private String createtime;    //

    private int codeId;    // 红外码Id

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "AirBrand{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", img='" + img + '\'' +
                ", pId='" + pId + '\'' +
                ", createtime='" + createtime + '\'' +
                ", codeId=" + codeId +
                '}';
    }
}
