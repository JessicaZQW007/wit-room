package com.yhxc.entity.fault;

import javax.persistence.*;


/**
 * 故障代码表
 *
 * @author 李文光
 */
@Entity
@Table(name = " ex_faultCode")
public class FaultCode {
    @Id
    @GeneratedValue
    @Column(length = 100)
    private Integer id;//主键id
    @Column(length=255)
    private String code;//故障代码

    @Column(length=255)
    private String message;//故障内容
    @Column(length=255)
    private String rank;//故障等级

    @Column(length=255)
    private String manage;//处理方式


    public String getManage() {
        return manage;
    }

    public void setManage(String manage) {
        this.manage = manage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
