package com.yhxc.entity.equipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/1 11:14
 * 设备类别；空调、热水、光伏....
 */

@Entity
@Table(name = "t_equipment_type")
public class EquipmentType {
    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String brand;       //品牌

    private String model;       //型号

    private String img;         //设备图片

    private String pId;         //型号所对应的品牌ID

    private String uuid;         //品牌和型号对应的设备ID

    private String ename;       //品牌和型号对应的设备名称

    private String createtime;    //




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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Override
    public String toString() {
        return "EquipmentType{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", img='" + img + '\'' +
                ", pId='" + pId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", ename='" + ename + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
