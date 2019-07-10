package com.yhxc.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 企业类别
 */

@Entity
@Table(name="t_companyType")
public class CompanyType {

    @Id
    @Column(length=100)
    private String id;
    @Column(length=255)
    private String  name;    //类别名称

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

	@Override
	public String toString() {
		return "CompanyType{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
