package com.yhxc.entity.information;

import javax.persistence.*;

/**
 * 公告实体
 */
@Entity
@Table(name = "t_notice")
public class Notice {
    @Id
    @Column(length = 100)
    private String id;

    private String title;       // 公告标题

    @Column(name = "state", columnDefinition = "tinyint default 0")
    private Integer state;      //0草稿，1发布成功

    @Column(columnDefinition = "TEXT")
    private String content;     //公告内容

    private String attachment;  //附件地址

    private String startTime;   //有效时间

    private String stopTime;    //失效时间

    @Column(length = 100)
    private String userName;    //创建者

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    private String recipient;   //指定谁能看

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "TEXT")
    private String flag;        //标识：存储用户名，用来判断该用户是否查看这条信息

    //private Integer sendType;   //公告发布者的用户类别

    //private Integer recType;       //可以看到该条公告的用户类别：：1是平台用户；2企业用户；3经销商用户；4机构用户；5终端用户；0体验用户

    //@Column(name = "isAll", columnDefinition = "tinyint default 0")
    //private Integer isAll;      //是否全体公告。1全体；0部分

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", userName='" + userName + '\'' +
                ", flag='" + flag + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
