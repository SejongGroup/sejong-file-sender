/**
 * 
 */
package com.sejong.cdbiz.cbiz.vo;

/**
 * @FileName	: CheatMentVo.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */

/* 사기 의심 발신번호 등록 */
public class CheatMentVO {
	private Long seq;
	private String userPwd;
    private String vnsNumber;
    private String callingHash;
    private String cstime;
    private String cetime;
    private String callId;
    private String level;
	private String logisticsCode; // 나중에 통합 할 예정
	private String logisCode;	// 나중에 통합 할 예정
	private String flag;
	private String setFlag;
	private String cdrNumber;
	private String callingNum;
	
	public String getSetFlag() {
		return setFlag;
	}
	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getCallingNum() {
		return callingNum;
	}
	public void setCallingNum(String callingNum) {
		this.callingNum = callingNum;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getCdrNumber() {
		return cdrNumber;
	}
	public void setCdrNumber(String cdrNumber) {
		this.cdrNumber = cdrNumber;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLogisticsCode() {
		return logisticsCode;
	}
	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
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
	public String getCallingHash() {
		return callingHash;
	}
	public void setCallingHash(String callingHash) {
		this.callingHash = callingHash;
	}
	public String getCstime() {
		return cstime;
	}
	public void setCstime(String cstime) {
		this.cstime = cstime;
	}
	public String getCetime() {
		return cetime;
	}
	public void setCetime(String cetime) {
		this.cetime = cetime;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
