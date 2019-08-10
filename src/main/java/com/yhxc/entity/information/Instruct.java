package com.yhxc.entity.information;


import javax.persistence.*;

/**
 * @Author: 陈霖炎
 * @Date: 2019/08/10 9:29
 * 监控口令实体类
 */
@Entity
@Table(name = "instruct")
public class Instruct {
    @Id
    @GeneratedValue
    private Integer id;         // 唯一主键


    @Column(length = 50)
    private String password;    // 口令密码


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Instruct{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }



}
