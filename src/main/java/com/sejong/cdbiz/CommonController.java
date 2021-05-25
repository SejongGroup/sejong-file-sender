package com.sejong.cdbiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String errorMain() {
		logger.warn("404 error occurred !!, page is not exsist. ");
		return "error/404";
	}
	
	@RequestMapping(value = "/ment", method = RequestMethod.GET)
	public String mentHome() {
		return "error/404";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String checkRoot() {
		logger.warn("404 error occurred !!, page is not exsist. ");
		return "error/404";
	}
	
}
