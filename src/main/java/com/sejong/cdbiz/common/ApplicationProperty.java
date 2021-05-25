package com.sejong.cdbiz.common;

/**
 * @FileName	: ApplicationProperty.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 28.
 * @Author		: JH.KIM
 * @Description : Database 이중화 및 세션 초과 시간, Body 암호화를 할 지 구성하는 VO 클래스
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.28	김준호			처음 작성
 */
public class ApplicationProperty{ 
	public static String requestBodyEncryptMode;
	public static String dbSyncMode;
	public static long sessionDelay;
	
	public String getRequestBodyEncryptMode() {
		return requestBodyEncryptMode;
	}
	public void setRequestBodyEncryptMode(String requestBodyEncryptMode) {
		ApplicationProperty.requestBodyEncryptMode = requestBodyEncryptMode;
	}
	public String getDbSyncMode() {
		return dbSyncMode;
	}
	public void setDbSyncMode(String dbSyncMode) {
		ApplicationProperty.dbSyncMode = dbSyncMode;
	}
	public long getSessionDelay() {
		return sessionDelay;
	}
	public void setSessionDelay(long sessionDelay) {
		ApplicationProperty.sessionDelay = sessionDelay;
	}

}