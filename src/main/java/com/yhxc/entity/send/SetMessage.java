package com.yhxc.entity.send;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 空调信息实体类
 */
@Entity
@Table(name="set_message")
public class SetMessage implements Serializable {

    @Id
    @GeneratedValue
    private Integer id; //编号
    @Column(length=255)
    private String runmodel; //运行模式
    @Column(length=255)
    private String temp; //温度
    @Column(length=255)
    private String uuid; //uuid
    @Column(length=255)
    private String winddirection; //风向
    @Column(length=255)
    private String windspeed; //风速
    @Column(length=255)
    private String num; //空调编号
    @Column(length=255)
    private String createtime; //创建时间

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

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

    @Override
    public String toString() {
        return "SetMessage{" +
                "id=" + id +
                ", runmodel='" + runmodel + '\'' +
                ", temp='" + temp + '\'' +
                ", uuid='" + uuid + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", windspeed='" + windspeed + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
