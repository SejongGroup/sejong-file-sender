/**
 * 
 */
package com.sejong.cdbiz.dbiz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sejong.cdbiz.cbiz.service.CBizMentService;
import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.dbiz.service.CalledMentService;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;

import sejong.api.common.util.APIConstants;
import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: BizController.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@Repository
public abstract class BizControllerAbstract {
	
	private static Logger logger;
	
	/* cbiz 메소드 및 변수 */
	@Autowired
	CBizMentService cheatService;
	
	public void dbizService(Logger logger, HttpServletRequest request, HttpServletResponse response, String logisticsCode, CheatMentVO ment, int DML_TYPE) {
		this.logger = logger;
		logger.info("Request Path : " + request.getContextPath() + " logiscode : " + logisticsCode);
		NBIZResponseBody responseVo = new NBIZResponseBody();
		
		/* 파라미터가 null 이면 */
		if (ment == null) {
			logger.info("ment is null");
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_PARAMETER));
		} else {
			ment.setLogisticsCode(logisticsCode);
			responseVo = cheatService.mentBehavior(ment, DML_TYPE);
		}
		setResponse(response, responseVo);
	}
	
	/* dbiz 메소드 및 변수 */
	@Autowired
	CalledMentService mentService;

	public void sendInsertService(Logger logger, HttpServletRequest request, HttpServletResponse response, String logisticsCode, CalledMentVO ment, int DML_TYPE) {
		this.logger = logger;
		logger.info("Request Path : " + request.getContextPath() + " logiscode : " + logisticsCode);
		NBIZResponseBody responseVo = new NBIZResponseBody(); 
		
		/* 파리미터가 null 이면 */
		if (ment == null) {
			logger.info("ment is null");
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_PARAMETER));
		} else {
		/* 파리미터가 null 이 아니면 전부 있는지 체크해서 있다고 판단되면 */
			if (ment.getMentKind() != null && ment.getUserPwd() != null && ment.getVnsNumber() != null) {
				ment.setLogisticsCode(logisticsCode);
				responseVo = mentService.mentBehavior(ment, DML_TYPE);
			} else {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_PARAMETER));
			}
		}
		setResponse(response, responseVo);
	}
	
	public void sendService(Logger logger, HttpServletRequest request, HttpServletResponse response, String logisticsCode, CalledMentVO ment, int DML_TYPE) {
		this.logger = logger;
		logger.info("Request Path : " + request.getContextPath() + " logiscode : " + logisticsCode);
		NBIZResponseBody responseVo = new NBIZResponseBody(); 
		
		/* 파리미터가 null 이면 */
		if (ment == null) {
			logger.info("ment is null");
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_PARAMETER));
		} else {
		/* 파리미터가 null 이 아니면 전부 있는지 체크해서 있다고 판단되면 */
			if (ment.getVnsNumber() != null && ment.getUserPwd() != null ) {
				ment.setLogisticsCode(logisticsCode);
				responseVo = mentService.mentBehavior(ment, DML_TYPE);
			} else {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_PARAMETER));
			}
		}
		setResponse(response, responseVo);
	}
	
	public static void setResponse(HttpServletResponse response, NBIZResponseBody responseVo){
		String responseMessage = responseVo.getResponseMessage(APIConstants.JSON);
		try {
			if(responseMessage != null && responseMessage.isEmpty() == false){
				response.setContentType(APIConstants.CONTENT_TYPE_JSON);
				response.getWriter().write(responseMessage);
			}else{
				throw new IOException();
			}
		} catch (IOException e) {
			logger.debug("Fail to write response message to response object");
		}
	}
}
