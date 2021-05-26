/**
 * 
 */
package com.sejong.filecontrol.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sejong.filecontrol.controller.UploadController;
import com.sejong.filecontrol.error.ErrorCode;
import com.sejong.filecontrol.exception.DeleteFilesException;
import com.sejong.filecontrol.exception.SCPException;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.model.ServerVO;
import com.sejong.filecontrol.model.FileVO;
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
public class UploadService {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	public ResponseVO uploadFiles(List<MultipartFile> files, FileVO fileVO, ServerVO serverVO) {
		ResponseVO responseVO = new ResponseVO();
		ArrayList<FileVO> fileList = new ArrayList<FileVO>();
		
		for (MultipartFile file: files) {
			ResponseVO responseNew = CommonUtil.uploadFile(file, fileVO.getUserId());
			responseVO.setResult(responseNew.getResult());
			
			/* 파일을 저장하는데 성공하지 않으면 일단 내보내고 저장된 것만을 처리함 */
			if (responseNew.getResult().getResultCode() != ErrorCode.SUCCESS) {
				responseVO.setResult(responseNew.getResult());
				break;
			} else {
				fileList.add(responseNew.getFile().get(0));
			}
		}
		responseVO.setFile(fileList);		
		
		/* SCP로 저쪽 서버까지 파일 업로드 시작 및 끝났으면 파일을 삭제한다. */
		SCPUtil scpUtil = new SCPUtil();
		scpUtil.init(serverVO.getServerIp(), serverVO.getServerId(), serverVO.getServerPw());
		
		/* SSH 접근 도중에 에러가 발생하면 */
		if(scpUtil.isInVaild()) {
			/* 일단 모든 파일 삭제 */
			for (FileVO f : responseVO.getFile()) {
				if (!CommonUtil.deleteFile(f.getFileUri())) {
					logger.info("파일이 존재하지 않거나, 제대로 삭제되지 않았습니다.");
				}
			}
			/* 세선 종료 및 예외발생 */
			scpUtil.disconnection();
			throw new SCPException();
		}
		
		/* 에러가 발생하지 않으면 */
		try {
			if (responseVO.getFile() != null && responseVO.getFile().size() >= 1) {
				for (FileVO f : responseVO.getFile()) {
					scpUtil.upload(serverVO.getPath(), f.getFileUri());
					if (!CommonUtil.deleteFile(f.getFileUri())) {
						logger.info("파일이 존재하지 않거나, 제대로 삭제되지 않았습니다.");
					}
				}
			}
		} catch (Exception e) {
			scpUtil.disconnection();
			throw new DeleteFilesException();
		}
		
		scpUtil.disconnection();
		return responseVO;
	}
}
