/**
 * 
 */
package com.sejong.filecontrol.common;


/**
 * @FileName	: ApplicationProperty.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public class ApplicationProperty { 
	public static String gateWayUploadPath;
	public static String gateWayDownloadPath;
	
	public String getGateWayUploadPath() {
		return gateWayUploadPath;
	}
	public void setGateWayUploadPath(String gateWayUploadPath) {
		ApplicationProperty.gateWayUploadPath = gateWayUploadPath;
	}
	public String getGateWayDownloadPath() {
		return gateWayDownloadPath;
	}
	public void setGateWayDownloadPath(String gateWayDownloadPath) {
		ApplicationProperty.gateWayDownloadPath = gateWayDownloadPath;
	}
}