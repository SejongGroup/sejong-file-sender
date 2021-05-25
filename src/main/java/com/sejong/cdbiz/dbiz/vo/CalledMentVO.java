package com.sejong.cdbiz.dbiz.vo;

import org.simpleframework.xml.Default;

@Default
public class CalledMentVO  {
	private String userPwd;
	private String vnsNumber;
	private String mentKind;
	private String logisticsCode; // 귀찮아서 그냥 2개만들음..
	private String logisCode;
	private String modId;
	private String mentIdx;

	public String getMentIdx() {
		return mentIdx;
	}

	public void setMentIdx(String mentIdx) {
		this.mentIdx = mentIdx;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getLogisCode() {
		return logisCode;
	}

	public void setLogisCode(String logisCode) {
		this.logisCode = logisCode;
	}
	
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getVnsNumber() {
		return vnsNumber;
	}

	public void setVnsNumber(String vnsNumber) {
		this.vnsNumber = vnsNumber;
	}
	
	public String getMentKind() {
		return mentKind;
	}

	public void setMentKind(String mentKind) {
		this.mentKind = mentKind;
	}
	
	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	
}
