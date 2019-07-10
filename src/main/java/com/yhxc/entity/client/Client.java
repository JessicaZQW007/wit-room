package com.yhxc.entity.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/1 10:56
 * 客户端表
 */

@Entity
@Table(name = "t_client")
public class Client {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    @Column(length = 100)
    private String name;        //客户端名称

    @Column(length = 100)
    private String appKey;         //密钥

    private int type;        //客户端类型，1是安卓；2是IOS

    @Column(length = 100)
    private String projectId;       //项目id

    private String createTime;        //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", appKey='" + appKey + '\'' +
                ", type=" + type +
                ", projectId='" + projectId + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
