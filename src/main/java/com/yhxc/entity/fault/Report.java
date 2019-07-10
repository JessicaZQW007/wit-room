package com.yhxc.entity.fault;

import javax.persistence.*;

/**
 *告警事件实体类
 * 
 * @author 李文光
 */
@Entity
@Table(name=" ex_report")
public class Report {
	     @Id
	    @GeneratedValue
	    private Integer id;//主键id
	    @Column(length=255)
	    private String uuid;//设备uuid
	    @Column(length=255)
	    private String code;//故障代码
	    @Column(length=255)
	    private String airId;//故障空调编号
	   @Column(length=255)
	   private String state="1";//未处理为1
	    @Column(length=255)
	    private String createtime;//故障时间

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
}
