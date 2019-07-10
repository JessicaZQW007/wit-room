package com.yhxc.entity.send;

import javax.persistence.*;

/**
 * 空调操作日志实体
 *
 * @author yhxc 刘俊涛
 */

@Entity
@Table(name = "set_message_record")
public class SetMessageRecord {

  @Id
  @GeneratedValue
  private Integer id;//编号
  @Column(length=255)
  private String runmodel;//运行模式
  @Column(length=255)
  private String temp;//温度
  @Column(length=255)
  private String uuid;//uuid
  @Column(length=255)
  private String winddirection;//风向
  @Column(length=255)
  private String windspeed;//风速
  @Column(length=255)
  private String num;//空调编号
  @Column(length=255)
  private String userName;// 操作用户名
  @Column(length=255)
  private String realName;//操作者真实姓名
  @Column(length=255)
  private String operationDate;// 操作时间


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getRunmodel() {
    return runmodel;
  }

  public void setRunmodel(String runmodel) {
    this.runmodel = runmodel;
  }


  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }


  public String getWinddirection() {
    return winddirection;
  }

  public void setWinddirection(String winddirection) {
    this.winddirection = winddirection;
  }


  public String getWindspeed() {
    return windspeed;
  }

  public void setWindspeed(String windspeed) {
    this.windspeed = windspeed;
  }


  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }


  public String getOperationDate() {
    return operationDate;
  }

  public void setOperationDate(String operationDate) {
    this.operationDate = operationDate;
  }

  @Override
  public String toString() {
    return "SetMessageRecord{" +
            "id=" + id +
            ", runmodel='" + runmodel + '\'' +
            ", temp='" + temp + '\'' +
            ", uuid='" + uuid + '\'' +
            ", winddirection='" + winddirection + '\'' +
            ", windspeed='" + windspeed + '\'' +
            ", num='" + num + '\'' +
            ", userName='" + userName + '\'' +
            ", realName='" + realName + '\'' +
            ", operationDate='" + operationDate + '\'' +
            '}';
  }
}
