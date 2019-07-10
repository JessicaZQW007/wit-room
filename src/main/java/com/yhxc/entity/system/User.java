package com.yhxc.entity.system;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 用户实体
 *
 * @author yhxc 赵贺飞
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;         // 编号

    @NotEmpty(message = "请输入用户名！")
    private String userName;    // 用户名

    @NotEmpty(message = "请输入密码！")
    @Column(length = 100)
    private String password;    // 密码

    @Column(length = 50)
    private String realName;    // 真实姓名

    @Column(length = 50)
    private String phone;       //用户手机号，也是登录账号

    private String address;     // 用户地址

    private String qq;          //qq

    private String email;       //邮箱

    private String wx;       //微信

    private String position;    //职务

    private String openId;      //微信公众号推送消息的用户openId

    private String unit;        //单位名称

    @Column(name = "isMsg", columnDefinition = "tinyint default 0")
    private Integer isMsg;      //是否接受短信提醒，1提醒，0不提醒

    @Column(name = "isWechat", columnDefinition = "tinyint default 0")
    private Integer isWechat;   //是否接受微信提醒，1提醒，0不提醒

    private Integer type;       //用户类别：1终端用户；2企业用户

    private Integer roleId;    //所属角色名称

    private String roleName;

    @Column(name = "state", columnDefinition = "tinyint default 0")
    private Integer state;      //账号状态；0正常；1锁定

    private String createTime;      //注册时间

    @Column(length = 1000)
    private String remark;          // 备注


    @Column(length = 50)
    private Integer userType;    // 用户类别：1平台用户，2机构用户

   /* @Column(length = 50)
    private String unitName;    // 单位名称*/

    @Column(length = 50)
    private String unitId;    // 单位ID



    @Column(length = 100)
    private String projectId;       //项目ID

    @Column(length = 100)
    private String dealerId;        //经销商ID

    @Column(length = 100)
    private String organizationId;  //组织机构ID

    @Column(length = 100)
    private String companyId;       // 公司id


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getIsMsg() {
        return isMsg;
    }

    public void setIsMsg(Integer isMsg) {
        this.isMsg = isMsg;
    }

    public Integer getIsWechat() {
        return isWechat;
    }

    public void setIsWechat(Integer isWechat) {
        this.isWechat = isWechat;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

   /* public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }*/

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isMsg=" + isMsg +
                ", isWechat=" + isWechat +
                ", type=" + type +
                ", state=" + state +
                ", createTime='" + createTime + '\'' +
                ", userType='" + userType +
               /* ", unitName='" + unitName + '\'' +*/
                ", unitId='" + unitId + '\'' +
                '}';
    }
}
