package com.yhxc.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单实体
 *
 * @author yhxc 赵贺飞
 */
@Entity
@Table(name = "t_menu")
public class Menu {

    @Id
    @GeneratedValue
    private Integer id; // 编号

    @Column(length = 50)
    private String name; // 菜单名称

    @Column(length = 255)
    private String permission;    //菜单权限

    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;// 资源类型，[menu|button]

    private Integer menuPid;  // 二级菜单父ID

    private Integer buttonPid; // 按钮父ID

    @Column(length = 200)
    private String url; // 菜单地址

    @Column(length = 200)
    private String href;    //html地址

    private Integer state; // 菜单节点类型 1 根节点 0 叶子节点

    @Column(length = 100)
    private String icon; // 图标

    private Integer pId; // 父菜单Id

    private Integer order;    //排序

    private Integer type;       //类别： 1平台  0用户

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getButtonPid() {
        return buttonPid;
    }

    public void setButtonPid(Integer buttonPid) {
        this.buttonPid = buttonPid;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(Integer menuPid) {
        this.menuPid = menuPid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", url=" + url + ", state=" + state + ", icon=" + icon + ", pId="
                + pId + "permission=" + permission + ", resourceType=" + resourceType + "]";
    }
}
