/**
 * 
 */
package com.sejong.filecontrol.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sejong.filecontrol.common.ApplicationProperty;

import onse.util.common.UtilConstants;
import onse.util.date.DateUtil;
import sejong.api.nbiz.error.NBIZErrorCode;

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
public class CommonService {

	/**
	 * @methodName	: uploadFile
	 * @author 		: JH.KIM
	 * @date		: 2021. 5. 25.
	 * @description : 
	 * @param file
	 */
	public void uploadFile(MultipartFile file) {
		
		if(file.getSize() == 0){
			System.out.println("파일사이즈가 없음");
			return;
		}
		
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			System.out.println("inputStream error");
			return;
		}
		
		// set ment path
		String today = DateUtil.getDate(UtilConstants.DEFAULT_DATE_FORMAT);
		String uploadPath = ApplicationProperty.uploadMentBasePath 
					+ today.substring(0, 4) + "/" + today.substring(4, 6)  + "/" + today.substring(6, 8) + "/" + "junho";
		File path = new File(uploadPath);
		
		/* 파일 위치 */
		if(path.exists() == false){
			if(path.mkdirs() == false){
				System.out.println("파일 생성 에러");
				return;
			}
		}
		
		String fileName = "test.txt";
		String fullFilePath = uploadPath + "/" + fileName;
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fullFilePath);
		} catch (FileNotFoundException e) {
			System.out.println("파일이없음");
			return;
		}
		try {
			byte[] buffer = new byte[10240]; // 10K
			int n = 0;
			while (-1 != (n = is.read(buffer))) {
				fos.write(buffer, 0, n);
			}
			
			fos.flush();
		} catch (IOException e) {
			System.out.println("실패");
			return;
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("실패");
				return;
			}
			try{
				is.close();
			}catch (IOException e) {
				System.out.println("실패");
				return;
			}
		}
		
	}
}
