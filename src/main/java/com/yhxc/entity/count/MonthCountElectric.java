package com.yhxc.entity.count;

import javax.persistence.*;

/**
 * 每月电量汇总
 * @author yhxc 李文光
 *
 */
@Entity
@Table(name="c_monthCountElectric")
public class MonthCountElectric {
    @Id
    @GeneratedValue
    private Integer id; 		// 主键

    @Column(length=50)
    private String uuid; 		// uuid

    @Column(columnDefinition="float(15,2) default '0.00'")
    private float elequantity; 	// 电量值

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

    public float getElequantity() {
        return elequantity;
    }

    public void setElequantity(float elequantity) {
        this.elequantity = elequantity;
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
        return "MonthCountElectric{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", elequantity=" + elequantity +
                ", date='" + date + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
