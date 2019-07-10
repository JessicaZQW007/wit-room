package com.yhxc.entity.send;

import javax.persistence.*;

@Entity
@Table(name="command")
public class Command {

  @Id
  @GeneratedValue
  private Integer id;// 编号
  @Column(length=255)
  private String command;
  @Column(length=255)
  private String commstatus;//状态
  @Column(length=255)
  private String messageid; //messageid
  @Column(length=255)
  private String receDate; //送达时间
  @Column(length=255)
  private String result; //
  @Column(length=255)
  private String sendDate; //发送时间
  @Column(length=255)
  private String uuid; //uuid

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }


  public String getCommstatus() {
    return commstatus;
  }

  public void setCommstatus(String commstatus) {
    this.commstatus = commstatus;
  }


  public String getMessageid() {
    return messageid;
  }

  public void setMessageid(String messageid) {
    this.messageid = messageid;
  }


  public String getReceDate() {
    return receDate;
  }

  public void setReceDate(String receDate) {
    this.receDate = receDate;
  }


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }


  public String getSendDate() {
    return sendDate;
  }

  public void setSendDate(String sendDate) {
    this.sendDate = sendDate;
  }


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public String toString() {
    return "Command{" +
            "id=" + id +
            ", command='" + command + '\'' +
            ", commstatus='" + commstatus + '\'' +
            ", messageid='" + messageid + '\'' +
            ", receDate='" + receDate + '\'' +
            ", result='" + result + '\'' +
            ", sendDate='" + sendDate + '\'' +
            ", uuid='" + uuid + '\'' +
            '}';
  }
}
