/**
 * 
 */
package com.sejong.filecontrol.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import com.sejong.filecontrol.common.ApplicationProperty;
import com.sejong.filecontrol.error.ErrorCode;
import com.sejong.filecontrol.exception.FileExistsException;
import com.sejong.filecontrol.exception.SCPException;
import com.sejong.filecontrol.model.FileVO;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.model.ServerVO;
import com.sejong.filecontrol.util.CommonUtil;
import com.sejong.filecontrol.util.SCPUtil;

/**
 * @FileName	: CommonService.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@Service
public class DownloadService {
	
	private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);


	public File downloadFile(String userId, String fileName) {
		/* 파일 경로 */
		String uploadPath = ApplicationProperty.gateWayDownloadPath + CommonUtil.fileSeparate() + CommonUtil.urlToday() + CommonUtil.fileSeparate() + userId;
		String fullFilePath = uploadPath + CommonUtil.fileSeparate() + fileName;		
		
		/* 파일 존재 유무 */
		File file = new File(fullFilePath);
		if(!file.exists()) {
			throw new FileExistsException();
		}
		
		/* 파일이 존재하면 리턴해줍니다. */
		return file;
	}

	public String getContentType(HttpServletRequest request, File resource) {
		String contentType = new String();
		contentType = request.getServletContext().getMimeType(resource.getAbsolutePath());
		return contentType;
	}

	/**
	 * @methodName	: download
	 * @author 		: JH.KIM
	 * @date		: 2021. 5. 26.
	 * @description : 
	 * @param fileVO
	 * @param serverVO
	 */
	public ResponseVO download(FileVO fileVO, ServerVO serverVO) {
		ResponseVO responseVO = new ResponseVO();
		SCPUtil scpUtil = new SCPUtil();
		scpUtil.init(serverVO.getServerIp(), serverVO.getServerId(), serverVO.getServerPw());
		
		/* SSH 접근 도중에 에러가 발생하면 */
		if(scpUtil.isInVaild()) {
			scpUtil.disconnection();
			throw new SCPException();
		}
		
		/* 에러가 발생하지 않으면 */
		String uploadPath = ApplicationProperty.gateWayUploadPath + CommonUtil.fileSeparate()
					+ CommonUtil.urlToday() + CommonUtil.fileSeparate() + fileVO.getUserId();
		File path = new File(uploadPath);
		
		/* 파일 위치 */
		if(path.exists() == false){
			if(path.mkdirs() == false){
				responseVO.setResult(ErrorCode.getResultVO(ErrorCode.DIRECTORY_ERROR));
				return responseVO;
			}
		}

		scpUtil.download(serverVO.getPath(), fileVO.getFileName(), uploadPath);
		scpUtil.disconnection();
		
		FileVO newFileVO = new FileVO();
		ArrayList<FileVO> list = new ArrayList<FileVO>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://localhost:8080/filecontrol/download/file?userId=");
		sb.append(fileVO.getUserId());
		sb.append("&fileName=");
		sb.append(fileVO.getFileName());
		newFileVO.setFileUri(sb.toString());
		
		responseVO.setFile(list);
		return responseVO;
	}
}
