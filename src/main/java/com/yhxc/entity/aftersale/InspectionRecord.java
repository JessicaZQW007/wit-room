package com.yhxc.entity.aftersale;

import javax.persistence.*;

/**
 * @Author: 赵贺飞
 * @Date: 2018/12/19 10:36
 * 巡检多台设备记录表
 */

@Entity
@Table(name = "t_inspection_record")
public class InspectionRecord {

    @Id
    @Column(length = 100)
    private String id;              //唯一主键

    private String uuid;            //设备UUID

    private String problem;         //问题描述

    private String remark;          //备注

    private String createTime;      //巡检时间

    private String inspectionId;    //巡检任务ID

    @Transient
    private String projectId;       //项目ID


    public InspectionRecord() {
    }

    public InspectionRecord(String inspectionId) {
        this.inspectionId = inspectionId;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    @Override
    public String toString() {
        return "InspectionRecord{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", problem='" + problem + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", inspectionId='" + inspectionId + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
