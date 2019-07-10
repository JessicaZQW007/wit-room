package com.yhxc.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: 刘俊涛
 * @Date: 2019/3/20 11:33
 */
@Entity
@Table(name = "t_operationlog")
public class OperationLog {

    @Id
    @GeneratedValue
    private Integer id;//编号

    @Column(length = 50)
    private String uName;//操作者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationTime;//操作时间

    private String state;//状态

}
