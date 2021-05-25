package com.sejong.filecontrol.aspect;

import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		for (int i = 0; i < paramValues.length; i++) {
			System.out.println(paramValues[i].getClass().getName());
			if (paramValues[i].getClass().getName().contains("HttpServletRequest")) {
				request = (HttpServletRequest) paramValues[i];
				break;
			}
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
