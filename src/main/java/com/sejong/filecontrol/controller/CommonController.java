/**
 * 
 */
package com.sejong.filecontrol.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sejong.filecontrol.service.CommonService;

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
@RequestMapping(value="/", method=RequestMethod.GET)
public class CommonController {
	
	@Autowired
	CommonService commonService; 
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public void home2(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("gdgd");
	}
	
	@RequestMapping(value="upload", method=RequestMethod.POST)
	public void home(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="file", required=false) MultipartFile file) {
		commonService.uploadFile(file);
	}
}
