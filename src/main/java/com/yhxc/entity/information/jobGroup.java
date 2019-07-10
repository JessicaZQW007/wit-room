package com.yhxc.entity.information;

import javax.persistence.*;

/**
 * @Author: 张权威
 * @Date: 2019/4/12 15:26
 */
@Entity
@Table(name="jobGroup")
public class jobGroup {
    @Id
    @GeneratedValue
    private Integer id; // 编号
    @Column(name = "jobName")
    private String jobName;//规则名字
    @Column(name = "remakes")
    private String remakes;//规则描述
    @Column(name = "creat_date")
    private String creat_date;//创建时间
    @Column(name = "displayName")
    private String displayName;//显示名称

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

    public String getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(String creat_date) {
        this.creat_date = creat_date;
    }

    @Override
    public String toString() {
        return "jobGroup{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", remakes='" + remakes + '\'' +
                ", creat_date='" + creat_date + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
