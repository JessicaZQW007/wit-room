package com.yhxc.entity.fault;

import javax.persistence.*;

/**
 * 故障预警值设定
 *
 * @author 李文光
 */

@Entity
@Table(name = " ex_faultSet")
public class FaultSet {
    @Id
    @Column(length = 100)
    private String id;//主键id
    private String  currentSymbol;//电流大于或者小于
    private String  current;//电流
    private String  hjtempSymbol;//环境大于或者小于
    private String  hjtemp;//环境温度
    private String  sfktempSymbol;//回风温度大于或者小于
    private String  sfktemp;//回风温度

    private String  uuid;//设备uuid

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentSymbol() {
        return currentSymbol;
    }

    public void setCurrentSymbol(String currentSymbol) {
        this.currentSymbol = currentSymbol;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getHjtempSymbol() {
        return hjtempSymbol;
    }

    public void setHjtempSymbol(String hjtempSymbol) {
        this.hjtempSymbol = hjtempSymbol;
    }

    public String getHjtemp() {
        return hjtemp;
    }

    public void setHjtemp(String hjtemp) {
        this.hjtemp = hjtemp;
    }

    public String getSfktempSymbol() {
        return sfktempSymbol;
    }

    public void setSfktempSymbol(String sfktempSymbol) {
        this.sfktempSymbol = sfktempSymbol;
    }

    public String getSfktemp() {
        return sfktemp;
    }

    public void setSfktemp(String sfktemp) {
        this.sfktemp = sfktemp;
    }

    @Override
    public String toString() {
        return "FaultSet{" +
                "id='" + id + '\'' +
                ", currentSymbol='" + currentSymbol + '\'' +
                ", current='" + current + '\'' +
                ", hjtempSymbol='" + hjtempSymbol + '\'' +
                ", hjtemp='" + hjtemp + '\'' +
                ", sfktempSymbol='" + sfktempSymbol + '\'' +
                ", sfktemp='" + sfktemp + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
