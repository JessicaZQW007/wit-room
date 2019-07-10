package com.yhxc.entity.send;

import javax.persistence.*;

/**
 * @Author: 张权威
 * @Date: 2019/6/14 16:53
 */
@Entity
@Table(name="s_receiveDataA011")
public class ReceiveDataA011 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号
    @Column(name = "NBsignal")   //NB信号强度
    private String NBsignal;
    @Column(name = "NBIsmi")   //NB卡号
    private String NBIsmi;
    @Column(name = "uuid")   //NBImei号
    private String uuid;
    @Column(name = "boardDataReportInterval")   //主板数据上报周期
    private String boardDataReportInterval;
    @Column(name = "snTemp")   //室内温度
    private String snTemp;
    @Column(name = "snHumidity")   //室内湿度
    private String snHumidity;
    @Column(name = "swTemp")   //室外温度
    private String swTemp;
    @Column(name = "status")   //空调状态
    private String status;
    @Column(name = "onTemp")   //开机温度
    private String onTemp;
    @Column(name = "offHumidity")   //关机湿度
    private String offHumidity;
    @Column(name = "setTemp")   //温度设定点
    private String setTemp;
    @Column(name = "tempDeviation")   //温度偏差
    private String tempDeviation;
    @Column(name = "setHumidity")   //湿度设定点
    private String setHumidity;
    @Column(name = "humidityDeviation")   //湿度偏差
    private String humidityDeviation;
    @Column(name = "userCount")   //用户自定义参数数量
    private String userCount;
    @Column(name = "version ")   //通讯协议版本
    private String version;
    @Column(name = "unitAddr")   //空调机组号
    private String unitAddr;
    @Column(name = "runByte")   //运行状态字节数
    private String runByte;
    @Column(name = "runStatus1")   //运行状态1
    private String runStatus1;
    @Column(name = "warningByte")   //报警状态字节数
    private String warningByte;
    @Column(name = "warningOne")   //报警状态1
    private String warningOne;
    @Column(name = "warningTwo")   //报警状态2
    private String warningTwo;
    @Column(name = "warningThree")   //报警状态3
    private String warningThree;
    @Column(name = "jizuStatus")   //机组属性和状态
    private String jizuStatus;
    @Column(name = "lockStatus")   //锁定状态
    private String lockStatus;
    private String receiveDate; // 接收时间
    @Column(name = "voltageA")
    private String voltageA; //A相电压
    @Column(name = "voltageB")
    private String voltageB; //B相电压
    @Column(name = "voltageC")
    private String voltageC; //C相电压
    @Column(name = "currentA")
    private String currentA; //A相电流
    @Column(name = "currentB")
    private String currentB; //B相电流
    @Column(name = "currentC")
    private String currentC; //C相电流
    @Column(name = "totalpower")
    private String totalpower; //正向有功电能
    @Column(name = "ktccurrent1")
    private String ktccurrent1; //空调1电流
    @Column(name = "ktccurrent2")
    private String ktccurrent2; //空调2电流

    public String getVoltageA() {
        return voltageA;
    }

    public void setVoltageA(String voltageA) {
        this.voltageA = voltageA;
    }

    public String getVoltageB() {
        return voltageB;
    }

    public void setVoltageB(String voltageB) {
        this.voltageB = voltageB;
    }

    public String getVoltageC() {
        return voltageC;
    }

    public void setVoltageC(String voltageC) {
        this.voltageC = voltageC;
    }

    public String getCurrentA() {
        return currentA;
    }

    public void setCurrentA(String currentA) {
        this.currentA = currentA;
    }

    public String getCurrentB() {
        return currentB;
    }

    public void setCurrentB(String currentB) {
        this.currentB = currentB;
    }

    public String getCurrentC() {
        return currentC;
    }

    public void setCurrentC(String currentC) {
        this.currentC = currentC;
    }

    public String getTotalpower() {
        return totalpower;
    }

    public void setTotalpower(String totalpower) {
        this.totalpower = totalpower;
    }

    public String getKtccurrent1() {
        return ktccurrent1;
    }

    public void setKtccurrent1(String ktccurrent1) {
        this.ktccurrent1 = ktccurrent1;
    }

    public String getKtccurrent2() {
        return ktccurrent2;
    }

    public void setKtccurrent2(String ktccurrent2) {
        this.ktccurrent2 = ktccurrent2;
    }

    public String getSyslock() {
        return syslock;
    }

    public void setSyslock(String syslock) {
        this.syslock = syslock;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    @Column(name = "syslock")

    private String syslock; //系统锁定
    @Column(name = "tempRange")
    private String tempRange; //温度偏差值

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "errorCodee")


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNBsignal() {
        return NBsignal;
    }

    public void setNBsignal(String NBsignal) {
        this.NBsignal = NBsignal;
    }

    public String getNBIsmi() {
        return NBIsmi;
    }

    public void setNBIsmi(String NBIsmi) {
        this.NBIsmi = NBIsmi;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBoardDataReportInterval() {
        return boardDataReportInterval;
    }

    public void setBoardDataReportInterval(String boardDataReportInterval) {
        this.boardDataReportInterval = boardDataReportInterval;
    }

    public String getSnTemp() {
        return snTemp;
    }

    public void setSnTemp(String snTemp) {
        this.snTemp = snTemp;
    }

    public String getSnHumidity() {
        return snHumidity;
    }

    public void setSnHumidity(String snHumidity) {
        this.snHumidity = snHumidity;
    }

    public String getSwTemp() {
        return swTemp;
    }

    public void setSwTemp(String swTemp) {
        this.swTemp = swTemp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnTemp() {
        return onTemp;
    }

    public void setOnTemp(String onTemp) {
        this.onTemp = onTemp;
    }

    public String getOffHumidity() {
        return offHumidity;
    }

    public void setOffHumidity(String offHumidity) {
        this.offHumidity = offHumidity;
    }

    public String getSetTemp() {
        return setTemp;
    }

    public void setSetTemp(String setTemp) {
        this.setTemp = setTemp;
    }

    public String getTempDeviation() {
        return tempDeviation;
    }

    public void setTempDeviation(String tempDeviation) {
        this.tempDeviation = tempDeviation;
    }

    public String getSetHumidity() {
        return setHumidity;
    }

    public void setSetHumidity(String setHumidity) {
        this.setHumidity = setHumidity;
    }

    public String getHumidityDeviation() {
        return humidityDeviation;
    }

    public void setHumidityDeviation(String humidityDeviation) {
        this.humidityDeviation = humidityDeviation;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUnitAddr() {
        return unitAddr;
    }

    public void setUnitAddr(String unitAddr) {
        this.unitAddr = unitAddr;
    }

    public String getRunByte() {
        return runByte;
    }

    public void setRunByte(String runByte) {
        this.runByte = runByte;
    }

    public String getRunStatus1() {
        return runStatus1;
    }

    public void setRunStatus1(String runStatus1) {
        this.runStatus1 = runStatus1;
    }

    public String getWarningByte() {
        return warningByte;
    }

    public void setWarningByte(String warningByte) {
        this.warningByte = warningByte;
    }

    public String getWarningOne() {
        return warningOne;
    }

    public void setWarningOne(String warningOne) {
        this.warningOne = warningOne;
    }

    public String getWarningTwo() {
        return warningTwo;
    }

    public void setWarningTwo(String warningTwo) {
        this.warningTwo = warningTwo;
    }

    public String getWarningThree() {
        return warningThree;
    }

    public void setWarningThree(String warningThree) {
        this.warningThree = warningThree;
    }

    public String getJizuStatus() {
        return jizuStatus;
    }

    public void setJizuStatus(String jizuStatus) {
        this.jizuStatus = jizuStatus;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    @Override
    public String toString() {
        return "ReceiveDataA011{" +
                "id=" + id +
                ", NBsignal='" + NBsignal + '\'' +
                ", NBIsmi='" + NBIsmi + '\'' +
                ", uuid='" + uuid + '\'' +
                ", boardDataReportInterval='" + boardDataReportInterval + '\'' +
                ", snTemp='" + snTemp + '\'' +
                ", snHumidity='" + snHumidity + '\'' +
                ", swTemp='" + swTemp + '\'' +
                ", status='" + status + '\'' +
                ", onTemp='" + onTemp + '\'' +
                ", offHumidity='" + offHumidity + '\'' +
                ", setTemp='" + setTemp + '\'' +
                ", tempDeviation='" + tempDeviation + '\'' +
                ", setHumidity='" + setHumidity + '\'' +
                ", humidityDeviation='" + humidityDeviation + '\'' +
                ", userCount='" + userCount + '\'' +
                ", version='" + version + '\'' +
                ", unitAddr='" + unitAddr + '\'' +
                ", runByte='" + runByte + '\'' +
                ", runStatus1='" + runStatus1 + '\'' +
                ", warningByte='" + warningByte + '\'' +
                ", warningOne='" + warningOne + '\'' +
                ", warningTwo='" + warningTwo + '\'' +
                ", warningThree='" + warningThree + '\'' +
                ", jizuStatus='" + jizuStatus + '\'' +
                ", lockStatus='" + lockStatus + '\'' +
                ", receiveDate='" + receiveDate + '\'' +
                ", voltageA='" + voltageA + '\'' +
                ", voltageB='" + voltageB + '\'' +
                ", voltageC='" + voltageC + '\'' +
                ", currentA='" + currentA + '\'' +
                ", currentB='" + currentB + '\'' +
                ", currentC='" + currentC + '\'' +
                ", totalpower='" + totalpower + '\'' +
                ", ktccurrent1='" + ktccurrent1 + '\'' +
                ", ktccurrent2='" + ktccurrent2 + '\'' +
                ", syslock='" + syslock + '\'' +
                ", tempRange='" + tempRange + '\'' +
                '}';
    }
}
