package com.yhxc.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 经销商实体类
 * lwg
 */

@Entity
@Table(name="t_dealer")
public class Dealer {

    @Id
  //  @GeneratedValue
    @Column(length=100)
    private String id;

    @Column(length=100)
    private String  name;    //经销商名称

    @Column(length=10000)
    private String content;      //经销商描述

	private String user;		//经销商负责人

    @Column(length=100)
    private String phone;         //经销商联系方式

	@Column(length=100)
	private String t_phone;         //电话方式
	@Column(length=100)
	private String postalCode;    //邮政编码
	@Column(length=255)
	private String type;      //经销商/代理商/承建商

	@Column(length=100)
    private String email;         //经销商邮箱
    
    @Column(length=100)
    private String companyWeb;         //经销商网址
    
    @Column(length=255)
    private String address;   //经销商地址

	public String getT_phone() {
		return t_phone;
	}

	public void setT_phone(String t_phone) {
		this.t_phone = t_phone;
	}

	@Column(length=255)
	private String img;   //图片
	@Column(length=255)
	private String weixinImg;   //微信公众号二维码
	@Column(length=255)
	private String createTime;   //创建时间

	@Column(length=255)
	private String companyId;   //公司Id

	public Dealer(String companyId) {
		this.companyId = companyId;
	}

	public Dealer() {
	}

	public String getWeixinImg() {
		return weixinImg;
	}

	public void setWeixinImg(String weixinImg) {
		this.weixinImg = weixinImg;
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

	public String getCompanyWeb() {
		return companyWeb;
	}

	public void setCompanyWeb(String companyWeb) {
		this.companyWeb = companyWeb;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Dealer{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", user='" + user + '\'' +
				", phone='" + phone + '\'' +
				", t_phone='" + t_phone + '\'' +
				", postalCode='" + postalCode + '\'' +
				", type='" + type + '\'' +
				", email='" + email + '\'' +
				", companyWeb='" + companyWeb + '\'' +
				", address='" + address + '\'' +
				", img='" + img + '\'' +
				", weixinImg='" + weixinImg + '\'' +
				", createTime='" + createTime + '\'' +
				", companyId='" + companyId + '\'' +
				'}';
	}
}
