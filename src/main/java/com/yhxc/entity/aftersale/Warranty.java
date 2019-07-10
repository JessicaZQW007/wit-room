package com.yhxc.entity.aftersale;


import javax.persistence.*;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/11 15:40
 * 设备保修
 */

@Entity
@Table(name = "t_warranty")
public class Warranty {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String uuid;        //关联设备uuid

    private Integer runTime;    //累计运行时长

    private Integer state;       //保修状态;0保修期内：1已过保修期

    private String warrantyTime;    //保修截止日期


    /******查询参数，不生成数据库字段******/
    @Transient
    private String projectId;
    @Transient
    private String deadline;



    public Warranty() {
        //super();
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Warranty(String uuid) {
        this.uuid = uuid;
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

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(String warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    @Override
    public String toString() {
        return "Warranty{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", runTime=" + runTime +
                ", state=" + state +
                ", warrantyTime='" + warrantyTime + '\'' +
                ", projectId='" + projectId + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
