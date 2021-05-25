package com.sejong.cdbiz.dbiz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.dbiz.service.CalledMentService;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;

/**
 * @FileName	: DBizController.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 20.
 * @Author		: JH.KIM
 * @Description : 사용자에게 멘트 서비스를 제공해줌
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.20	JH.KIM				using Sejong_API jar 
 * 2021.04.22   JS.PARK				check out
 */

@Controller
@RequestMapping(value="/dbiz/v1/set/called")
public class DBizController extends BizControllerAbstract {
	private static final Logger logger = LoggerFactory.getLogger(DBizController.class);
	
	@Autowired
	CalledMentService mentService;

	@RequestMapping(value="", method=RequestMethod.POST)
	public void insertDbiz(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value="logisCode", required=false) String logisticsCode,  @RequestBody CalledMentVO ment) {
		sendInsertService(logger, request, response, logisticsCode, ment, Constants.DML_INSERT);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public void deleteDbiz(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value="logisCode", required=false) String logisticsCode,  @RequestBody CalledMentVO ment) {
		sendService(logger, request, response, logisticsCode, ment, Constants.DML_DELETE);
	}
	
	@RequestMapping(value="/select", method=RequestMethod.POST)
	public void selectDbiz(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value="logisCode", required=false) String logisticsCode,  @RequestBody CalledMentVO ment ) {
		sendService(logger, request, response, logisticsCode, ment, Constants.DML_SELECT);
	}
}
