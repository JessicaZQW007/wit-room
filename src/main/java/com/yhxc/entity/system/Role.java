package com.yhxc.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色实体
 * @author yhxc 赵贺飞
 *
 */
@Entity
@Table(name="t_role")
public class Role {

	@Id
	@GeneratedValue
	private Integer id; 		// 主键
	
	@Column(length=50)
	private String name; 		// 角色名称

	@Column(length=2000)
	private String adds;		//当前角色的新增功能按钮权限

	@Column(length=2000)
	private String dels;		//当前角色的删除功能按钮权限

	@Column(length=2000)
	private String edits;		//当前角色的修改功能按钮权限

	@Column(length=2000)
	private String querys;		//当前角色的查询功能按钮权限

	//private String companyId;	//企业ID，用于分别当前角色是属于哪个企业

	//private String dealerId;		//经销商ID

	//private String organizationId;		//组织机构ID
	
	@Column(length=500)
	private String remarks; 	// 备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdds() {
		return adds;
	}

	public void setAdds(String adds) {
		this.adds = adds;
	}

	public String getDels() {
		return dels;
	}

	public void setDels(String dels) {
		this.dels = dels;
	}

	public String getEdits() {
		return edits;
	}

	public void setEdits(String edits) {
		this.edits = edits;
	}

	public String getQuerys() {
		return querys;
	}

	public void setQuerys(String querys) {
		this.querys = querys;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remark) {
		this.remarks = remark;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", remarks=" + remarks + "]";
	}

}
