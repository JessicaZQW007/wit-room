package com.yhxc.entity.fault;

import javax.persistence.*;

/**
 * 故障事件实体类
 *
 * @author 李文光
 */

@Entity
@Table(name = " ex_fault")
public class Fault {
    @Id
    @GeneratedValue
    @Column(length = 100)
    private Integer id;//主键id
    @Column(length = 255)
    private String uuid;//设备uuid
    @Column(length=255)
    private String airId;//故障空调编号
    @Column(length = 255)
    private String ndate;//故障时间
    @Column(length = 255)
    private String endDate;//解除时间
    @Column(length = 100)
    private String userName;//操作者
    @Column(length = 100)
    private String message;//故障信息
    @Column(length=255)
    private String code;//故障代码
    @Column(length=255)
    private String faState;//故障状态


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

    public String getAirId() {
        return airId;
    }

    public void setAirId(String airId) {
        this.airId = airId;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFaState() {
        return faState;
    }

    public void setFaState(String faState) {
        this.faState = faState;
    }

    @Override
    public String toString() {
        return "Fault{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", airId='" + airId + '\'' +
                ", ndate='" + ndate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", faState='" + faState + '\'' +
                '}';
    }
}
