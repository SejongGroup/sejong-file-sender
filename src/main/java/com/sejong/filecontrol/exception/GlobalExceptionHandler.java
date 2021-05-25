/**
 * 
 */
package com.sejong.filecontrol.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sejong.filecontrol.error.ErrorCode;
import com.sejong.filecontrol.model.ResponseVO;
import com.sejong.filecontrol.util.CommonUtil;


/**
 * @FileName	: GlobalExceptionHandler.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(ParameterException.class)
    public void handleParameterException(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("handleParameterException : " + request.getRequestURI());
    	ResponseVO responseVo = new ResponseVO();
    	responseVo.setResult(ErrorCode.getResultVO(ErrorCode.KNOWNPARAM_ERROR));
    	CommonUtil.setResponse(response, responseVo);
    }
    
    @ExceptionHandler(AuthorizeException.class)
    public void handleAuthorizeException(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("handleAuthorizeException : " + request.getRequestURI());
    	ResponseVO responseVo = new ResponseVO();
    	responseVo.setResult(ErrorCode.getResultVO(ErrorCode.AUTHORIZE_ERROR));
    	CommonUtil.setResponse(response, responseVo);
    }
    
    @ExceptionHandler(SessionException.class)
    public void handleSessionException(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("handleSessionException : " + request.getRequestURI());
    	ResponseVO responseVo = new ResponseVO();
    	responseVo.setResult(ErrorCode.getResultVO(ErrorCode.SESSION_ERROR));
    	CommonUtil.setResponse(response, responseVo);
    }
    
    @ExceptionHandler(DeleteFilesException.class)
    public void handleDeleteFilesException(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	logger.info("handleDeleteFilesException : " + request.getRequestURI());
    	ResponseVO responseVo = new ResponseVO();
    	responseVo.setResult(ErrorCode.getResultVO(ErrorCode.DELETEFILE_ERROR));
    	CommonUtil.setResponse(response, responseVo);
    }
}
