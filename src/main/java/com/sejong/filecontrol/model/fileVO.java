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
public class fileVO {
	private String fileName;
	private String fileType;
	private Integer fileSize;
	private String fileUri;
	private String dupCheck;
	private String pathServer;
	private String pathClient;
	
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
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
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
	public String getPathServer() {
		return pathServer;
	}
	public void setPathServer(String pathServer) {
		this.pathServer = pathServer;
	}
	public String getPathClient() {
		return pathClient;
	}
	public void setPathClient(String pathClient) {
		this.pathClient = pathClient;
	}
}
