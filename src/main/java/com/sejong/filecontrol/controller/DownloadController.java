/**
 * 
 */
package com.sejong.filecontrol.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sejong.filecontrol.common.ApplicationProperty;
import com.sejong.filecontrol.error.ErrorCode;
import com.sejong.filecontrol.exception.AuthorizeException;
import com.sejong.filecontrol.exception.DeleteFilesException;
import com.sejong.filecontrol.exception.ParameterException;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.model.ServerVO;
import com.sejong.filecontrol.model.FileVO;
import com.sejong.filecontrol.service.DownloadService;
import com.sejong.filecontrol.service.UploadService;
import com.sejong.filecontrol.util.CommonUtil;

/**
 * @FileName	: CommonController.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@Controller 
@RequestMapping(value="/download", method=RequestMethod.GET)
public class DownloadController {
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	@Autowired
	DownloadService downloadService; 
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="userId",required=false) String userId, @RequestParam(value="fileName",required=false) String fileName,
			@RequestParam(value="serverIp",required=false) String serverIp, @RequestParam(value="serverId",required=false) String serverId, 
			@RequestParam(value="serverPw",required=false) String serverPw, @RequestParam(value="path",required=false) String path) {
		
		logger.info("다운로드를 진행하는 메소드를 수행 중 입니다.");
		
		/* 파일 파라미터의 값이 NULL 인지 체크 */
		if (userId == null || serverIp == null || serverId == null
				|| serverPw == null || path == null) {
			logger.info("파라미터의 값이 NULL 인 경우 Exception이 발생됩니다.");
			throw new ParameterException();
		}
		
		/* VO 객체에 파라미터 값 삽입 */
		FileVO fileVO = new FileVO();
		fileVO.setUserId(userId);
		fileVO.setDupCheck(fileName);
		fileVO.setFileName(fileName);
		ServerVO serverVO = new ServerVO();
		serverVO.setServerIp(serverIp);
		serverVO.setServerId(serverId);
		serverVO.setServerPw(serverPw);
		serverVO.setPath(path);
		
		/* SSH 로그인 후 파일을 받아와서 저장한 후 VO 객체에 뿌려줌 - 박종선 역할 */
		ResponseVO responseVO = downloadService.download(fileVO, serverVO, request);
		responseVO.setResult(ErrorCode.getResultVO(ErrorCode.SUCCESS));

		CommonUtil.setResponse(response, responseVO);
	}
	
	@RequestMapping(value="/file", method=RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="userId") String userId, @RequestParam(value="fileName") String fileName) throws IOException {
		
		logger.info("파일 다운로드를 진행하는 메소드를 수행 중 입니다.");
		
		/* 파일 파라미터의 값이 NULL 인지 체크 */
		if (userId == null || fileName == null) {
			logger.info("파라미터의 값이 NULL 인 경우 Exception이 발생됩니다.");
			throw new ParameterException();
		}
		
		File resource = downloadService.downloadFile(userId, fileName);
		String contentType = downloadService.getContentType(request, resource);
		
		/* 파일을 response객체에 보내줌 */
		CommonUtil.setDownloadResponse(response, resource, contentType, fileName);
	}
}
