package com.yhxc.entity.count;

import javax.persistence.*;

/**
 * 每月电量汇总
 * @author yhxc 李文光
 *
 */
@Entity
@Table(name="c_monthAirCountElectric")
public class MonthAirCountElectric {
    @Id
    @GeneratedValue
    private Integer id; 		// 主键

    @Column(length=50)
    private String uuid; 		// uuid

    @Column(columnDefinition="float(15,2) default '0.00'")
    private float    elequantity1; 	// 空调1电量值
    @Column(columnDefinition="float(15,2) default '0.00'")
    private float    elequantity2;// 空调2电量值
    @Column(length=100)
    private String date; 		// 汇总时间

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

    public float getElequantity1() {
        return elequantity1;
    }

    public void setElequantity1(float elequantity1) {
        this.elequantity1 = elequantity1;
    }

    public float getElequantity2() {
        return elequantity2;
    }

    public void setElequantity2(float elequantity2) {
        this.elequantity2 = elequantity2;
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


}
