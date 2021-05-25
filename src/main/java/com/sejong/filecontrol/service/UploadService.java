/**
 * 
 */
package com.sejong.filecontrol.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sejong.filecontrol.error.ErrorCode;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.model.FileVO;
import com.sejong.filecontrol.util.CommonUtil;

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

	public ResponseVO uploadFiles(List<MultipartFile> files, FileVO fileVO) {
		ResponseVO responseVO = new ResponseVO();
		ArrayList<FileVO> fileList = new ArrayList<FileVO>();
		
		for (MultipartFile file: files) {
			ResponseVO responseNew = CommonUtil.uploadFile(file, fileVO.getUserId());
			if (responseNew.getResult().getResultCode() != ErrorCode.SUCCESS) {
				return responseNew;
			} else {
				fileList.add(responseNew.getFile().get(0));
			}
		}
		responseVO.setResult(ErrorCode.getResultVO(ErrorCode.SUCCESS));
		responseVO.setFile(fileList);
		return responseVO;
	}
}
