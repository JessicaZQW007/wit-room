package com.yhxc.entity.system;

import javax.persistence.*;

/**
 * 红外代码表
 * @author yhxc
 *
 */
@Entity
@Table(name="t_infraredCode")
public class InfraredCode {
	
	@Id
	@GeneratedValue
	private Integer id; // 编号


	@Column(length = 250)
	private String brand; // 品牌名称

	private String code; // 红外代码

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "InfraredCode{" +
				"id=" + id +
				", brand='" + brand + '\'' +
				", code='" + code + '\'' +
				'}';
	}
}
