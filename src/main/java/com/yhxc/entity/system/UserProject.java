package com.yhxc.entity.system;

import com.yhxc.entity.project.Project;

import javax.persistence.*;

/**
 * 用户项目关联实体
 * @author yhxc 李文光
 *
 */
@Entity
@Table(name="t_userProject")
public class UserProject {

	@Id
	@GeneratedValue
	private Integer id; // 编号
	
	@ManyToOne
	@JoinColumn(name="projectId")
	private Project project; // 用户
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user; // 角色

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserProject{" +
				"id=" + id +
				", project=" + project +
				", user=" + user +
				'}';
	}
}
