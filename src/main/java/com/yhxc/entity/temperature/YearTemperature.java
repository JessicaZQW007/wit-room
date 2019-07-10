package com.yhxc.entity.temperature;

import javax.persistence.*;

/**
 * @Author: 陈霖炎
 * @Date: 2019/06/17 16:44
 * 每天设备温度湿度记录
 */

@Entity
@Table(name="t_yearTemperature")
public class YearTemperature {


    @Id
    @GeneratedValue
    private Integer id; 		// 主键

    @Column(length=50)
    private String uuid; 		// uuid

    @Column(name = "swhumi")
    private String swhumi;      //室外湿度

    @Column(name = "swtemp")
    private String swtemp;      //室外温度

    @Column(name = "hjtemp")
    private String hjtemp;      //室内环境温度

    @Column(name = "sfktemp1")
    private String sfktemp1;    //空调1送风口温度

    @Column(name = "sfktemp2")
    private String sfktemp2;    //空调2送风口温度

    @Column(length=100)
    private String date; 		// 记录时间

    @Column(length=100)
    private String createtime; 		// 创建时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSwhumi() {
        return swhumi;
    }

    public void setSwhumi(String swhumi) {
        this.swhumi = swhumi;
    }

    public String getSwtemp() {
        return swtemp;
    }

    public void setSwtemp(String swtemp) {
        this.swtemp = swtemp;
    }

    public String getHjtemp() {
        return hjtemp;
    }

    public void setHjtemp(String hjtemp) {
        this.hjtemp = hjtemp;
    }

    public String getSfktemp1() {
        return sfktemp1;
    }

    public void setSfktemp1(String sfktemp1) {
        this.sfktemp1 = sfktemp1;
    }

    public String getSfktemp2() {
        return sfktemp2;
    }

    public void setSfktemp2(String sfktemp2) {
        this.sfktemp2 = sfktemp2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }


    @Override
    public String toString() {
        return "YearTemperature{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", swhumi=" + swhumi +
                ", swtemp='" + swtemp + '\'' +
                ", hjtemp='" + hjtemp + '\'' +
                ", sfktemp1='" + sfktemp1 + '\'' +
                ", sfktemp2='" + sfktemp2 + '\'' +
                ", date='" + date + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
