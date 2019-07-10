package com.yhxc.entity.information;

import javax.persistence.*;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 10:07
 */
@Entity
@Table(name="quartz")
public class quartz {
    @Id
    @GeneratedValue
    private Integer id; // 编号
    @Column(name = "jobKey")
    private String jobKey;//任务key
    @Column(name = "runModel")
    private String runModel;//运行模式
    @Column(name = "temp")
    private String temp;//温度下限值
    @Column(name = "corn")
    private String corn;//表达式
    @Column(name = "cornStatus")
    private String cornStatus;//表达式状态
    @Column(name = "remarks")
    private String remarks;//表达式说明
    @Column(name = "create_date")
    private String create_date;//创建时间
    @Column(name = "jobGroup")
    private String jobGroup;//任务组
    @Column(name = "runDate")
    private String runDate;//任务组
    @Column(name = "num")
    private String num;//任务组

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getRunModel() {
        return runModel;
    }

    public void setRunModel(String runModel) {
        this.runModel = runModel;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(String cornStatus) {
        this.cornStatus = cornStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    @Override
    public String toString() {
        return "quartz{" +
                "id=" + id +
                ", jobKey='" + jobKey + '\'' +
                ", runModel='" + runModel + '\'' +
                ", temp='" + temp + '\'' +
                ", corn='" + corn + '\'' +
                ", cornStatus='" + cornStatus + '\'' +
                ", remarks='" + remarks + '\'' +
                ", create_date='" + create_date + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", runDate='" + runDate + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}

