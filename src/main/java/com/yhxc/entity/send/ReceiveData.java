package com.yhxc.entity.send;

import com.yhxc.entity.system.Menu;
import com.yhxc.entity.system.Role;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 分体空调接收数据实体类
 * @author yhxc
 *
 */
@Entity
@Table(name="s_receiveData")
public class ReceiveData implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 编号
	@Column(name = "uuid")
	private String uuid; // uuid
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
	private String totalpower; //总有功功率
	@Column(name = "sfktemp1")
	private String sfktemp1; //空调1送风口温度
	@Column(name = "sfktemp2")
	private String sfktemp2; //空调2送风口温度
	@Column(name = "setsfktemp1")
	private String setsfktemp1; //设置空调1送风口温度
	@Column(name = "setsfktemp2")
	private String setsfktemp2; //设置空调2送风口温度
	@Column(name = "hjtemp")
	private String hjtemp; //室内环境温度
	@Column(name = "sethjtemp")
	private String sethjtemp; //设置室内环境温度
	@Column(name = "ktccurrent1")
	private String ktccurrent1; //电流传感器1
	@Column(name = "ktccurrent2")
	private String ktccurrent2; //电流传感器2
	@Column(name = "relaycontrolmark")
	private String relaycontrolmark; //继电器标志位
	@Column(name = "swhumi")
	private String swhumi; //室外湿度
	@Column(name = "swtemp")
	private String swtemp; //室外温度
	@Column(name = "lmalarm1")
	private String lmalarm1; //空调1冷媒报警
	@Column(name = "lmalarm2")
	private String lmalarm2; //空调2冷媒报警
	@Column(name = "NBsignal")
	private String NBsignal; //NB信号强度
	@Column(name = "NBIsmi")
	private String NBIsmi; //NB卡号
	@Column(name = "NBImei")
	private String NBImei; //NBImei号
	@Column(name = "boardDataReportInterval")
	private String boardDataReportInterval;//主板数据上报周期
	@Column(name = "receiveDate")
	private String receiveDate; // 接收时间
	@Column(name = "errorCodee")
	private String errorCodee; //冷媒检测报警
	@Column(name = "KtSwitch1")
	private String KtSwitch1; //空调1开关
	@Column(name = "KtRunModel1")
	private String KtRunModel1; //空调1运行模式
	@Column(name = "KtSetTemp1")
	private String KtSetTemp1; //空调1设置温度
	@Column(name = "KtSetFs1")
	private String KtSetFs1; //空调1风速
	@Column(name = "KtSwitch2")
	private String KtSwitch2; //空调2开关
	@Column(name = "KtRunModel2")
	private String KtRunModel2; //空调2运行模式
	@Column(name = "KtSetTemp2")
	private String KtSetTemp2; //空调1设置温度
	@Column(name = "KtSetFs2")
	private String KtSetFs2; //空调2风速
	@Column(name = "KtSetFx2")
	private String KtSetFx2; //空调2风向
	@Column(name = "KtSetFx1")
	private String KtSetFx1; //空调1风向
	@Column(name = "hightTemp")
	private String hightTemp; //温度上限值
	@Column(name = "lowerTemp")
	private String lowerTemp; //温度下限值
	@Column(name = "runRule")
	private String runRule; //主板运行规则
	@Column(name = "xinFen")
	private String xinFen; //新风开关
	@Column(name = "sysLock")
	private String sysLock; //主板锁定

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public String getSfktemp1() {
		return sfktemp1;
	}

	public void setSfktemp1(String sfktemp1) {
		this.sfktemp1 = sfktemp1;
	}

	public String getSfktemp2() {
		return sfktemp2;
	}

	public void setSfktemp2(String sfktemp2) {
		this.sfktemp2 = sfktemp2;
	}

	public String getSetsfktemp1() {
		return setsfktemp1;
	}

	public void setSetsfktemp1(String setsfktemp1) {
		this.setsfktemp1 = setsfktemp1;
	}

	public String getSetsfktemp2() {
		return setsfktemp2;
	}

	public void setSetsfktemp2(String setsfktemp2) {
		this.setsfktemp2 = setsfktemp2;
	}

	public String getHjtemp() {
		return hjtemp;
	}

	public void setHjtemp(String hjtemp) {
		this.hjtemp = hjtemp;
	}

	public String getSethjtemp() {
		return sethjtemp;
	}

	public void setSethjtemp(String sethjtemp) {
		this.sethjtemp = sethjtemp;
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

	public String getRelaycontrolmark() {
		return relaycontrolmark;
	}

	public void setRelaycontrolmark(String relaycontrolmark) {
		this.relaycontrolmark = relaycontrolmark;
	}

	public String getSwhumi() {
		return swhumi;
	}

	public void setSwhumi(String swhumi) {
		this.swhumi = swhumi;
	}

	public String getSwtemp() {
		return swtemp;
	}

	public void setSwtemp(String swtemp) {
		this.swtemp = swtemp;
	}

	public String getLmalarm1() {
		return lmalarm1;
	}

	public void setLmalarm1(String lmalarm1) {
		this.lmalarm1 = lmalarm1;
	}

	public String getLmalarm2() {
		return lmalarm2;
	}

	public void setLmalarm2(String lmalarm2) {
		this.lmalarm2 = lmalarm2;
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

	public String getNBImei() {
		return NBImei;
	}

	public void setNBImei(String NBImei) {
		this.NBImei = NBImei;
	}

	public String getBoardDataReportInterval() {
		return boardDataReportInterval;
	}

	public void setBoardDataReportInterval(String boardDataReportInterval) {
		this.boardDataReportInterval = boardDataReportInterval;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getErrorCodee() {
		return errorCodee;
	}

	public void setErrorCodee(String errorCodee) {
		this.errorCodee = errorCodee;
	}

	public String getKtSwitch1() {
		return KtSwitch1;
	}

	public void setKtSwitch1(String ktSwitch1) {
		KtSwitch1 = ktSwitch1;
	}

	public String getKtRunModel1() {
		return KtRunModel1;
	}

	public void setKtRunModel1(String ktRunModel1) {
		KtRunModel1 = ktRunModel1;
	}

	public String getKtSetTemp1() {
		return KtSetTemp1;
	}

	public void setKtSetTemp1(String ktSetTemp1) {
		KtSetTemp1 = ktSetTemp1;
	}

	public String getKtSetFs1() {
		return KtSetFs1;
	}

	public void setKtSetFs1(String ktSetFs1) {
		KtSetFs1 = ktSetFs1;
	}

	public String getKtSwitch2() {
		return KtSwitch2;
	}

	public void setKtSwitch2(String ktSwitch2) {
		KtSwitch2 = ktSwitch2;
	}

	public String getKtRunModel2() {
		return KtRunModel2;
	}

	public void setKtRunModel2(String ktRunModel2) {
		KtRunModel2 = ktRunModel2;
	}

	public String getKtSetTemp2() {
		return KtSetTemp2;
	}

	public void setKtSetTemp2(String ktSetTemp2) {
		KtSetTemp2 = ktSetTemp2;
	}

	public String getKtSetFs2() {
		return KtSetFs2;
	}

	public void setKtSetFs2(String ktSetFs2) {
		KtSetFs2 = ktSetFs2;
	}

	public String getKtSetFx2() {
		return KtSetFx2;
	}

	public void setKtSetFx2(String ktSetFx2) {
		KtSetFx2 = ktSetFx2;
	}

	public String getKtSetFx1() {
		return KtSetFx1;
	}

	public void setKtSetFx1(String ktSetFx1) {
		KtSetFx1 = ktSetFx1;
	}

	public String getHightTemp() {
		return hightTemp;
	}

	public void setHightTemp(String hightTemp) {
		this.hightTemp = hightTemp;
	}

	public String getLowerTemp() {
		return lowerTemp;
	}

	public void setLowerTemp(String lowerTemp) {
		this.lowerTemp = lowerTemp;
	}

	public String getRunRule() {
		return runRule;
	}

	public void setRunRule(String runRule) {
		this.runRule = runRule;
	}

	public String getXinFen() {
		return xinFen;
	}

	public void setXinFen(String xinFen) {
		this.xinFen = xinFen;
	}

	public String getSysLock() {
		return sysLock;
	}

	public void setSysLock(String sysLock) {
		this.sysLock = sysLock;
	}

	@Override
	public String toString() {
		return "ReceiveData{" +
				"id=" + id +
				", uuid='" + uuid + '\'' +
				", voltageA='" + voltageA + '\'' +
				", voltageB='" + voltageB + '\'' +
				", voltageC='" + voltageC + '\'' +
				", currentA='" + currentA + '\'' +
				", currentB='" + currentB + '\'' +
				", currentC='" + currentC + '\'' +
				", totalpower='" + totalpower + '\'' +
				", sfktemp1='" + sfktemp1 + '\'' +
				", sfktemp2='" + sfktemp2 + '\'' +
				", setsfktemp1='" + setsfktemp1 + '\'' +
				", setsfktemp2='" + setsfktemp2 + '\'' +
				", hjtemp='" + hjtemp + '\'' +
				", sethjtemp='" + sethjtemp + '\'' +
				", ktccurrent1='" + ktccurrent1 + '\'' +
				", ktccurrent2='" + ktccurrent2 + '\'' +
				", relaycontrolmark='" + relaycontrolmark + '\'' +
				", swhumi='" + swhumi + '\'' +
				", swtemp='" + swtemp + '\'' +
				", lmalarm1='" + lmalarm1 + '\'' +
				", lmalarm2='" + lmalarm2 + '\'' +
				", NBsignal='" + NBsignal + '\'' +
				", NBIsmi='" + NBIsmi + '\'' +
				", NBImei='" + NBImei + '\'' +
				", boardDataReportInterval='" + boardDataReportInterval + '\'' +
				", receiveDate='" + receiveDate + '\'' +
				", errorCodee='" + errorCodee + '\'' +
				", KtSwitch1='" + KtSwitch1 + '\'' +
				", KtRunModel1='" + KtRunModel1 + '\'' +
				", KtSetTemp1='" + KtSetTemp1 + '\'' +
				", KtSetFs1='" + KtSetFs1 + '\'' +
				", KtSwitch2='" + KtSwitch2 + '\'' +
				", KtRunModel2='" + KtRunModel2 + '\'' +
				", KtSetTemp2='" + KtSetTemp2 + '\'' +
				", KtSetFs2='" + KtSetFs2 + '\'' +
				", KtSetFx2='" + KtSetFx2 + '\'' +
				", KtSetFx1='" + KtSetFx1 + '\'' +
				", hightTemp='" + hightTemp + '\'' +
				", lowerTemp='" + lowerTemp + '\'' +
				", runRule='" + runRule + '\'' +
				", xinFen='" + xinFen + '\'' +
				", sysLock='" + sysLock + '\'' +
				'}';
	}
}
