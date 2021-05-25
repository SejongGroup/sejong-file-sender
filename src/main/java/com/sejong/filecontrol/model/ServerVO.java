/**
 * 
 */
package com.sejong.filecontrol.model;

/**
 * @FileName	: ServerVO.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public class ServerVO {
	private String serverIp;
	private String serverId;
	private String serverPw;
	private String path;
	
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getServerPw() {
		return serverPw;
	}
	public void setServerPw(String serverPw) {
		this.serverPw = serverPw;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
