/**
 * 
 */
package com.sejong.filecontrol.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.sejong.filecontrol.common.ApplicationProperty;
import com.sejong.filecontrol.error.ErrorCode;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.model.FileVO;

import onse.util.common.UtilConstants;
import onse.util.date.DateUtil;

/**
 * @FileName	: CommonUtil.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static void setResponse(HttpServletResponse response,  ResponseVO responseVO){
		String responseMessage = gsonToString(responseVO);
		try {
			if(responseMessage != null && responseMessage.isEmpty() == false){
				response.setContentType("json");
				response.getWriter().write(responseMessage);
			}
		} catch (IOException e) {
			logger.debug("Fail to write response message to response object");
		}
	}
	
	public static String gsonToString(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * @methodName	: uploadFile
	 * @author 		: JH.KIM
	 * @date		: 2021. 5. 25.
	 * @description : 
	 * @param file
	 */
	public static ResponseVO uploadFile(MultipartFile file, String userId) {
		ResponseVO responseVO = new ResponseVO();
		FileVO fileVO = new FileVO();
		
		if(file.getSize() == 0){
			responseVO.setResult(ErrorCode.getResultVO(ErrorCode.FILESIZE_ERROR));
			return responseVO;
		}
		
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			responseVO.setResult(ErrorCode.getResultVO(ErrorCode.IO_ERROR));
			return responseVO;
		}
		
		// set ment path
		String today = DateUtil.getDate(UtilConstants.DEFAULT_DATE_FORMAT);
		String uploadPath = ApplicationProperty.gateWayUploadPath
					+ today.substring(0, 8) + "/" + userId;
		File path = new File(uploadPath);
		
		/* 파일 위치 */
		if(path.exists() == false){
			if(path.mkdirs() == false){
				responseVO.setResult(ErrorCode.getResultVO(ErrorCode.DIRECTORY_ERROR));
				return responseVO;
			}
		}
		
		String fileName = file.getOriginalFilename();
		String fullFilePath = uploadPath + "/" + fileName;
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fullFilePath);
		} catch (FileNotFoundException e) {
			responseVO.setResult(ErrorCode.getResultVO(ErrorCode.FILEPATH_ERROR));
			return responseVO;
		}
		try {
			byte[] buffer = new byte[10240];
			int n = 0;
			while (-1 != (n = is.read(buffer))) {
				fos.write(buffer, 0, n);
			}
			fos.flush();
			
		} catch (IOException e) {
			responseVO.setResult(ErrorCode.getResultVO(ErrorCode.IO_ERROR));
			return responseVO;
		} finally {
			/* 파일이 다 생성되었으니까 파일을 VO객체에 집어넣어준다. */
			try {
				fileVO.setFileUri(fullFilePath);
				fileVO.setFileSize(file.getSize());
				fileVO.setFileName(fileName);
				
				ArrayList<FileVO> fileList = new ArrayList<FileVO>();
				fileList.add(fileVO);
				responseVO.setFile(fileList);
				fos.close();
			} catch (IOException e) {
				responseVO.setResult(ErrorCode.getResultVO(ErrorCode.IO_ERROR));
				return responseVO;
			}
			try{
				is.close();
			}catch (IOException e) {
				responseVO.setResult(ErrorCode.getResultVO(ErrorCode.IO_ERROR));
				return responseVO;
			}
		}
		responseVO.setResult(ErrorCode.getResultVO(ErrorCode.SUCCESS));
		return responseVO;
	}
}
