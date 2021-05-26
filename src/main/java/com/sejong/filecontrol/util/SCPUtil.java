package com.sejong.filecontrol.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;	
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SCPUtil {
	private Session session;
	private Channel channel;
	private ChannelSftp channelSftp;
	private boolean inVaild = false;

	/**
	 * @param serverIp : 아이피 주소
	 * @param serverId : 서버 아이디
	 * @param serverPw : 서버 패스워드
	 */
	public void init(String serverIp, String serverId, String serverPw) {
		/* JSch 라이브러리 호출 */
		JSch jsch = new JSch();
		try { 
			/* 세션 호출 */
			session = jsch.getSession(serverId, serverIp, 22);
	        session.setPassword(serverPw);

	        /* 세션 키 없이 호출 */
	        Properties config = new Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	        
	        /* 세션 연결 */
	        session.connect();
	        
	        /* 채널 방식을 설정한 후 채널을 이용하여 Upload, Download */
	        channel = session.openChannel("sftp");
	        channel.connect();
	        
	        channelSftp = (ChannelSftp) channel;
	        inVaild = true;
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param path : ls 명령어를 입력하려고 하는 path 저장소
	 * @return
	 */
	public Vector<ChannelSftp.LsEntry> getFileList(String path) {
		Vector<ChannelSftp.LsEntry> list = null;
		try {
			channelSftp.cd(path);
			list = channelSftp.ls(".");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
    /**
     * @param path : serverVO.path 를 통해 scp로 들어간 서버로 접속하여 upload한다. 
     * @param file : File file을 객체를 받음
     */
    public void upload(String path, String fileUri) {
    	File file = new File(fileUri);
    	
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            channelSftp.cd(path);
            channelSftp.put(in, file.getName());
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
            	if(in != null)
            		in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
 
    /**
     * @param path : serverVO.path 를 통해 scp로 들어간 서버로 접속하여 download한다.
     * @param fileName : 특정 파일의 이름을 찾아서 다운로드 한다.
     * @param userPath
     */
    public void download(String path, String fileName, String userPath) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            channelSftp.cd(path);
            in = channelSftp.get(fileName);
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
        	String fullpath = userPath + File.separator + fileName;
            out = new FileOutputStream(new File(fullpath));
            int i;

            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 서버와의 연결을 끊는다.
     */
    public void disconnection() {
        channelSftp.quit();
        channel.disconnect();
        session.disconnect();
        setInVaild(false);
    }
    
	public boolean isInVaild() {
		return inVaild;
	}

	public void setInVaild(boolean inVaild) {
		this.inVaild = inVaild;
	}

}
