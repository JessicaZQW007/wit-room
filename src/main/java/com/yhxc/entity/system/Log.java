package com.yhxc.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统日志实体
 *
 * @author yhxc 赵贺飞
 */
@Entity
@Table(name = "t_log")
public class Log {

    public final static String LOGIN_ACTION = "登录操作";
    public final static String LOGOUT_ACTION = "退出操作";
    public final static String SEARCH_ACTION = "查询操作";
    public final static String UPDATE_ACTION = "修改操作";
    public final static String ADD_ACTION = "新增操作";
    public final static String DELETE_ACTION = "删除操作";

    @Id
    @GeneratedValue
    private Integer id; // 编号

    @Column(length = 50)
    private String userName; // 操作用户名

    private String realName;    //操作者真实姓名

    private String unit;        //单位

    @Column(length = 1000)
    private String content; // 操作内容

    @Column(length = 50)
    private String type;    //操作类型，

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time; // 操作时间

    @Transient
    private Date btime; // 起始时间  搜索用到

    @Transient
    private Date etime; // 结束时间  搜索用到

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Log() {
    }

    public Log(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getBtime() {
        return btime;
    }

    public void setBtime(Date btime) {
        this.btime = btime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", btime=" + btime +
                ", etime=" + etime +
                '}';
    }
}
