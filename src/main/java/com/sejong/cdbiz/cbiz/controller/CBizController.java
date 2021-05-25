/**
 * 
 */
package com.sejong.cdbiz.cbiz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.dbiz.controller.BizControllerAbstract;

/**
 * @FileName	: CBizController.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.26	김준호			CBIZ는 INSERT, DELETE만 지원
 * 2021.04.28	김준호			공통부분을 모아서 dbizService 실행
 */
@Controller
@RequestMapping(value="/cbiz/v1/set/cheat")
public class CBizController extends BizControllerAbstract {
	
	private static Logger logger = LoggerFactory.getLogger(CBizController.class);;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public void insertDbiz(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value="logisCode", required=false) String logisticsCode,  @RequestBody CheatMentVO ment) {
		dbizService(logger, request, response, logisticsCode, ment, Constants.DML_INSERT);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public void deleteDbiz(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value="logisCode", required=false) String logisticsCode,  @RequestBody CheatMentVO ment) {
		dbizService(logger, request, response, logisticsCode, ment, Constants.DML_DELETE);
	}
}
