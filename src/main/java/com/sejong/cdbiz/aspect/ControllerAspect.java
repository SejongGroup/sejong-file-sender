package com.sejong.cdbiz.aspect;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.google.gson.Gson;
import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.common.ApplicationProperty;
import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;
import com.sejong.cdbiz.except.RegularExpressException;
import com.sejong.cdbiz.except.SessionTimeOutException;

@Aspect
public class ControllerAspect{
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
	//@Inject CommonService commonService; // body 암호화 할 때 적용
	//@Inject TaskExecutorExample taskExecutorExample;
	private Hashtable<String, Long > sessionsList; // 세션 목록
	
	public ControllerAspect(){
		sessionsList = new Hashtable<String, Long>(); // session list 객체 생성
	}
	
	/* dBIz */
	@Around(value="execution(* com.sejong.cdbiz.dbiz.controller.*.*(..)) && args(model, request, response, logisticsCode, ment)")
	public Object beforeControllerDBiz(ProceedingJoinPoint pjp, Model model, HttpServletRequest request, HttpServletResponse response, String logisticsCode, CalledMentVO ment) throws Throwable{
		String requestBody = toJson(ment);
		requestBody = decodeURI(requestBody);
		logger.info("[DBIZ] =================================================================");
		logger.info("==> AOP : this is in ControllerAspect.java before @DBizController ");
		logger.info("==> AOP : parameter value : " + requestBody);
		
		// body 암호화 적용 시 
		/* 
		requestBody = commonService.decryptRequestBody(request, logisCode, requestBody);
		if(requestBody == null){
			logger.error("beforeController decryptRequestBody Error : " + requestBody);
			throw new DecryptRequestBodyException();
		}
		else{
			logger.debug("decoded : " + requestBody);
		}
		*/
		
		// logistics code session delay checking
		if(sessionsList.get(logisticsCode) != null  && (System.currentTimeMillis () - sessionsList.get(logisticsCode)) <  ApplicationProperty.sessionDelay){
			throw new Exception();
		} else {
			sessionsList.put(logisticsCode, System.currentTimeMillis ());
		}
		
		// Regular Expression
		if (!isNumber(logisticsCode)) {
			throw new RegularExpressException();
		}
		
		ment = new ObjectMapper().readValue(requestBody, CalledMentVO.class);
		return pjp.proceed(new Object[] {model, request, response, logisticsCode, ment});
	}
	
	/* cBIz */
	@Around(value="execution(* com.sejong.cdbiz.cbiz.controller.*.*(..)) && args(model, request, response, logisticsCode, ment)")
	public Object beforeControllerCBiz(ProceedingJoinPoint pjp, Model model, HttpServletRequest request, HttpServletResponse response, String logisticsCode, CheatMentVO ment) throws Throwable{
		String requestBody = toJson(ment);
		requestBody = decodeURI(requestBody);
		logger.info("[CBIZ] =================================================================");
		logger.info("==> AOP : this is in ControllerAspect.java before @CBizController ");
		logger.info("==> AOP : parameter value : " + requestBody);
		
		// logistics code session delay checking
		if(sessionsList.get(logisticsCode) != null  && (System.currentTimeMillis () - sessionsList.get(logisticsCode)) <  ApplicationProperty.sessionDelay){
			throw new SessionTimeOutException();
		} else {
			sessionsList.put(logisticsCode, System.currentTimeMillis ());
		}
		
		ment = new ObjectMapper().readValue(requestBody, CheatMentVO.class);
		
		// Regular Expression
		if (!isNumber(logisticsCode)) {
			throw new RegularExpressException();
		}
		
		return pjp.proceed(new Object[] {model, request, response, logisticsCode, ment});
	}
	
	public static String decodeURI(String source) throws UnsupportedEncodingException{
		if(source == null){
			return "";
		}
		
		source = source.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		source = source.replaceAll("\\+", "%2B");
		
		String decoded = (String)URLDecoder.decode(source, Constants.CLIENT_CHARACTER_SET);
		if(decoded == null){
			return "";
		}
		return decoded;
	}
	
	public boolean isNumber(String input) {
		String regex = "^[0-9]+$";
		return Pattern.matches(regex, input);
	}
	
	public static <T> String toJson(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		return json;
	}
}
