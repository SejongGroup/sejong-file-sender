/**
 * 
 */
package com.sejong.filecontrol.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sejong.filecontrol.exception.AuthorizeException;
import com.sejong.filecontrol.exception.DeleteFilesException;
import com.sejong.filecontrol.exception.ParameterException;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.model.ServerVO;
import com.sejong.filecontrol.model.FileVO;
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
@RequestMapping(value="/upload", method=RequestMethod.GET)
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	UploadService uploadService; 
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="dupCheck",required=false) String dupCheck, @RequestParam(value="serverIp",required=false) String serverIp,
			@RequestParam(value="serverId",required=false) String serverId, @RequestParam(value="serverPw",required=false) String serverPw,
			@RequestParam(value="path",required=false) String path, @RequestParam(value="file", required=false) List<MultipartFile> file) {
		
		logger.info("?????? ???????????? ???????????? ???????????? ?????? ??? ?????????.");
		
		/* ?????? ??????????????? ?????? NULL ?????? ?????? */
		if (userId == null || dupCheck == null || serverIp == null || serverId == null
				|| serverPw == null || path == null || file == null) {
			logger.info("??????????????? ?????? NULL ??? ?????? Exception??? ???????????????.");
			throw new ParameterException();
		}
		
//		/* ?????? ?????? */
//		if (!userId.equals("root")) {
//			logger.info("????????? ?????? ???????????? ????????? ?????? Exception??? ???????????????.");
//			throw new AuthorizeException();
//		}
		
		/* VO ????????? ???????????? ??? ?????? */
		FileVO fileVO = new FileVO();
		fileVO.setUserId(userId);
		fileVO.setDupCheck(dupCheck);
		ServerVO serverVO = new ServerVO();
		serverVO.setServerIp(serverIp);
		serverVO.setServerId(serverId);
		serverVO.setServerPw(serverPw);
		serverVO.setPath(path);
		 
		ResponseVO responseVO = uploadService.uploadFiles(file, fileVO, serverVO);
		CommonUtil.setResponse(response, responseVO);
	}
}
