
package com.sejong.cdbiz.except;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sejong.api.common.model.ResultVO;
import sejong.api.common.util.APIConstants;
import sejong.api.nbiz.model.NBIZResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String DEFAULT_ERROR_VIEW = "number";
    
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
   
    private void getResponseMessage(HttpServletResponse response, NBIZResponseBody responseVo){
    	setResponse(response, responseVo);
    }
	
    @ExceptionHandler(SessionTimeOutException.class)
    public void handleSessionTimeOutException(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("handleSessionTimeOutException : " + request.getRequestURI());
    	NBIZResponseBody responseVo = new NBIZResponseBody();
    	ResultVO result = new ResultVO();
		result.setResultCode("015");
        result.setResultMessage("세션 초기화가 되지 않았습니다.");
    	responseVo.setResult(result);
    	getResponseMessage(response, responseVo);
    }
    
    @ExceptionHandler(RegularExpressException.class)
    public void handleRegularExpressException(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("handleRegularExpressException : " + request.getRequestURI());
    	NBIZResponseBody responseVo = new NBIZResponseBody();
    	ResultVO result = new ResultVO();
		result.setResultCode("015");
        result.setResultMessage("파라미터가 잘못되었습니다.");
    	responseVo.setResult(result);
    	getResponseMessage(response, responseVo);
    }
}
