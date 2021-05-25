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
	
	public static String uploadMentBasePath;
	


	public String getUploadMentBasePath() {
		return uploadMentBasePath;
	}

	public void setUploadMentBasePath(String uploadMentBasePath) {
		if((uploadMentBasePath.lastIndexOf("/") + 1) != uploadMentBasePath.length()){
			uploadMentBasePath = uploadMentBasePath + "/";
		}
		ApplicationProperty.uploadMentBasePath = uploadMentBasePath;
	}
}