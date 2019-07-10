package com.yhxc.entity.equipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/7 10:39
 * 设备状态表
 */

@Entity
@Table(name = "t_equipment_state")
public class EquipmentState {

    @Id
    @Column(length = 100)
    private String id;          //唯一主键

    private String uuid;        //设备uuid

    private Integer state;      //设备状态  1：启用，  0：维修  2：删除

    private Integer net;        //网络状态  1：在线，  0：离线

    private Integer run;        //运行状态  1：运行，  0：停机

    private String createTime;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EquipmentState{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", state=" + state +
                ", net=" + net +
                ", run=" + run +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
