package com.sejong.filecontrol.aspect;

import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.binding.MapperMethod.MethodSignature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sejong.filecontrol.exception.ParameterException;
import com.sejong.filecontrol.exception.SessionException;


@Aspect
public class ControllerAspect{
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
	private Hashtable<String, Long> sessionsList = null;
	
	public ControllerAspect(){
		sessionsList = new Hashtable<String, Long>();
	}
	
	@Around(value="execution(* com.sejong.filecontrol.controller.*.*(..))")
	public Object beforeController(ProceedingJoinPoint pjp) throws Throwable {
		Object[] paramValues = pjp.getArgs();
		HttpServletRequest request = null;
		
		if (paramValues == null) {
			throw new ParameterException();
		}
		
		try {
			for (int i = 0; i < paramValues.length; i++) {
				if (paramValues[i].getClass().getName().contains("HttpServletRequest")) {
					request = (HttpServletRequest) paramValues[i];
					break;
				}
			}
		}catch (Exception e) {
			throw new ParameterException();
		}

		/* 특정한 메소드는 내가 알아서 리턴한다 */
		if (pjp.getSignature().toShortString().contains("downloadFile")) {
			return pjp.proceed(paramValues);
		}
		
		String ip = request.getHeader("X-FORWARDED-FOR");
		Long lastSession = null;
		
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		
		if (sessionsList.get(ip) != null) {
			lastSession = sessionsList.get(ip);
			logger.info(ip + " 에서 Controller 접속을 시도하였습니다. 마지막으로 종료된 세션은 " + lastSession + " 입니다.");
		} else {
			logger.info(ip + " 에서 Controller 접속을 시도하였습니다.");
		}
		
		if(lastSession != null  && (System.currentTimeMillis () - lastSession) <  900){
			throw new SessionException();
		} else {
			sessionsList.put(ip, System.currentTimeMillis ());
		}
		return pjp.proceed(paramValues);
	}
	
}
