package com.yhxc.entity.count;

import javax.persistence.*;

/**
 * 每天保存 设备 空调的电流统计
 * @author yhxc 李文光
 *
 */
@Entity
@Table(name="c_dayRunTimeCount")
public class DayRunTimeCount {
    @Id
    @GeneratedValue
    private Integer id; 		// 主键

    @Column(length=50)
    private String uuid; 		// uuid

    @Column(length=255)
    private String airCurrent1; //空调1电流
    @Column(length=255)
    private String airCurrent2; //空调2电流

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

    public String getAirCurrent1() {
        return airCurrent1;
    }

    public void setAirCurrent1(String airCurrent1) {
        this.airCurrent1 = airCurrent1;
    }

    public String getAirCurrent2() {
        return airCurrent2;
    }

    public void setAirCurrent2(String airCurrent2) {
        this.airCurrent2 = airCurrent2;
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
        return "DayRunTimeCount{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", airCurrent1='" + airCurrent1 + '\'' +
                ", airCurrent2='" + airCurrent2 + '\'' +
                ", date='" + date + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
