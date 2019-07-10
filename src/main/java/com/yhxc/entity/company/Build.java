package com.yhxc.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 建筑，房间实体类
 */

@Entity
@Table(name="t_build")
public class Build {

    @Id
    @Column(length=100)
    private String id;
    @Column(length=255)
    private String  name;    //建筑名称 ，房间号
	@Column(length=255)
	private String projectId;//项目ID

	@Column(length=255)
	private String pid;//父级id 0表示父级
	@Column(length=2555)
	private String createtime;//创建时间

	@Column(length=2555)
	private String remarks;//备注

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

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



	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Build{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", projectId='" + projectId + '\'' +
				", pid='" + pid + '\'' +
				", createtime='" + createtime + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}
