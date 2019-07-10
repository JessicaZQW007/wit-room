package com.yhxc.entity.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 企业实体类
 * lwg
 */

@Entity
@Table(name="t_company")
public class Company {

    @Id
  //  @GeneratedValue
    @Column(length=100)
    private String id;

    @Column(length=100)
    private String  companyName;    //企业名称

    @Column(length=10000)
    private String content;      //企业描述
	@Column(length=255)
	private String user;   //联系人
    @Column(length=100)
    private String companyPhone;         //联系方式
	@Column(length=100)
	private String t_phone;         //电话方式
    @Column(length=100)
    private String companyEmail;         //邮箱
    
    @Column(length=100)
    private String companyWeb;         //网址
    
    @Column(length=255)
    private String address;   //地址
	@Column(length=100)
	private String postalCode;         //邮政编码
	@Column(length=255)
	private String type;      //设备厂商、经销商

	@Column(length=255)
	private String img;   //图片

	@Column(length=255)
	private String createtime;   //创建时间

	@Column(length=255)
	private String status;   // 企业禁用启用  1:启用，0：禁用

	@Column(length=255)
	private String weixinImg;   //微信公众号二维码

	public String getT_phone() {
		return t_phone;
	}

	public void setT_phone(String t_phone) {
		this.t_phone = t_phone;
	}

	public String getWeixinImg() {
		return weixinImg;
	}

	public void setWeixinImg(String weixinImg) {
		this.weixinImg = weixinImg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
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

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Company{" +
				"id='" + id + '\'' +
				", companyName='" + companyName + '\'' +
				", content='" + content + '\'' +
				", user='" + user + '\'' +
				", companyPhone='" + companyPhone + '\'' +
				", companyEmail='" + companyEmail + '\'' +
				", companyWeb='" + companyWeb + '\'' +
				", address='" + address + '\'' +
				", postalCode='" + postalCode + '\'' +
				", type='" + type + '\'' +
				", img='" + img + '\'' +
				", createtime='" + createtime + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
