package com.yhxc.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机构实体类
 * lwg
 */

@Entity
@Table(name="t_organization")
public class Organization {

    @Id
  //  @GeneratedValue
    @Column(length=100)
    private String id;

    @Column(length=100)
    private String  name;    //机构名称

    @Column(length=10000)
    private String content;      //机构描述

    @Column(length=100)
    private String phone;         //机构联系方式

	private String user;     //负责人

    @Column(length=100)
    private String email;         //机构邮箱
    
    @Column(length=100)
    private String organizationWeb;         //机构网址
    
    @Column(length=255)
    private String address;   //机构地址

	@Column(length=255)
	private String img;   //图片
	@Column(length=255)
	private String weixinImg;   //微信公众号二维码
	@Column(length=255)
	private String createTime;   //创建时间

	@Column(length=255)
	private String dealerId;   //经销商Id



	@Column(length=100)
	private String t_phone;         //电话方式
	@Column(length=100)
	private String postalCode;    //邮政编码
	@Column(length=255)
	private String type;      //经销商/代理商/承建商

	public String getT_phone() {
		return t_phone;
	}

	public void setT_phone(String t_phone) {
		this.t_phone = t_phone;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWeixinImg() {
		return weixinImg;
	}

	public void setWeixinImg(String weixinImg) {
		this.weixinImg = weixinImg;
	}

	public Organization(String dealerId) {
		this.dealerId = dealerId;
	}

	public Organization() {
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrganizationWeb() {
		return organizationWeb;
	}

	public void setOrganizationWeb(String organizationWeb) {
		this.organizationWeb = organizationWeb;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	@Override
	public String toString() {
		return "Organization{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", phone='" + phone + '\'' +
				", user='" + user + '\'' +
				", email='" + email + '\'' +
				", organizationWeb='" + organizationWeb + '\'' +
				", address='" + address + '\'' +
				", img='" + img + '\'' +
				", weixinImg='" + weixinImg + '\'' +
				", createTime='" + createTime + '\'' +
				", dealerId='" + dealerId + '\'' +
				", t_phone='" + t_phone + '\'' +
				", postalCode='" + postalCode + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
