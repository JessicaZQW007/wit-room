package com.yhxc.entity.count;


import javax.persistence.*;

/**
 * 每天每小时的运行时间统计
 * @author yhxc 陈霖炎
 *
 */

@Entity
@Table(name="c_dayRunSum")
public class DayRunSum {

    @Id
    @GeneratedValue
    private Integer id; 		// 主键

    @Column(length=50)
    private String uuid; 		// uuid

    @Column(length=255)
    private String airRunTime1; //空调1运行时间
    @Column(length=255)
    private String airRunTime2; //空调2运行时间

    @Column(length=255)
    private String airRunCoolTime1; //空调1制冷运行时间
    @Column(length=255)
    private String airRunCoolTime2; //空调2制冷运行时间
    @Column(length=100)
    private String date; 		// 汇总时间

    @Column(length=100)
    private String createtime; 		// 创建时间

    public String getAirRunCoolTime1() {
        return airRunCoolTime1;
    }

    public void setAirRunCoolTime1(String airRunCoolTime1) {
        this.airRunCoolTime1 = airRunCoolTime1;
    }

    public String getAirRunCoolTime2() {
        return airRunCoolTime2;
    }

    public void setAirRunCoolTime2(String airRunCoolTime2) {
        this.airRunCoolTime2 = airRunCoolTime2;
    }

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

    public String getAirRunTime1() {
        return airRunTime1;
    }

    public void setAirRunTime1(String airRunTime1) {
        this.airRunTime1 = airRunTime1;
    }

    public String getAirRunTime2() {
        return airRunTime2;
    }

    public void setAirRunTime2(String airRunTime2) {
        this.airRunTime2 = airRunTime2;
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
        return "MonthRunTimeCount{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", airRunTime1='" + airRunTime1 + '\'' +
                ", airRunTime2='" + airRunTime2 + '\'' +
                ", date='" + date + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }





}
