package com.yhxc.entity.timer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/5/6 16:51
 * 手机端系统定时和供水泵定时
 */
@Entity
@Table(name = "app_timer")
public class Timer {

    @Id
    @Column(length = 100)
    private String id;

    private String uuid;              //定时器对应的设备编号

    private String startTime;         // 开始时间，整点或半小时

    private String stopTime;          // 结束时间，整点或半小时

    //private String model;              //模式，直接存命令，01或02

    private String temp;                //温度

    private String week;              //星期几

    //private String type;             //机型：热水机，冷暖机

    private Integer isStart;          //是否启用，1启用，0未启用

    private Integer isRun;            //设备是否在运行中，1运行中，0未运行

    private String userName;          //设置定时器的用户

    public Timer(String uuid) {
        this.uuid = uuid;
    }

    public Timer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public Integer getIsRun() {
        return isRun;
    }

    public void setIsRun(Integer isRun) {
        this.isRun = isRun;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Timer{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", startTime='" + startTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", temp='" + temp + '\'' +
                ", week='" + week + '\'' +
                ", isStart=" + isStart +
                ", isRun=" + isRun +
                ", userName='" + userName + '\'' +
                '}';
    }
}
