/**
 * 
 */
package com.sejong.filecontrol.model;

/**
 * @FileName	: fileVO.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public class FileVO {
	private String fileName;
	private String fileType;
	private Long fileSize;
	private String fileUri;
	private String userId;
	private String dupCheck;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUri() {
		return fileUri;
	}
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}
	public String getDupCheck() {
		return dupCheck;
	}
	public void setDupCheck(String dupCheck) {
		this.dupCheck = dupCheck;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
