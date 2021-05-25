/**
 * 
 */
package com.sejong.filecontrol.model;

import java.util.ArrayList;

/**
 * @FileName	: ResponseVO.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public class ResponseVO {
	private ResultVO result;
	private ArrayList<fileVO> file;
	private ServerVO server;
	
	public ResultVO getResult() {
		return result;
	}
	public void setResult(ResultVO result) {
		this.result = result;
	}
	public ArrayList<fileVO> getFile() {
		return file;
	}
	public void setFile(ArrayList<fileVO> file) {
		this.file = file;
	}
	public ServerVO getServer() {
		return server;
	}
	public void setServer(ServerVO server) {
		this.server = server;
	}
}
