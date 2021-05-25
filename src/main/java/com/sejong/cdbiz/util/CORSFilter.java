package com.sejong.cdbiz.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName	: CORSFilter.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 28.
 * @Author		: JH.KIM
 * @Description : HTTP 송수신 중 CORS 헤더의 처리를 함으로써 모든 접속의 허용이 생김
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.28	김준호		처음 작성
 */
public class CORSFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException { 
		HttpServletResponse response = (HttpServletResponse) res; 
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT"); 
		response.setHeader("Access-Control-Max-Age", "3600"); 
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept"); 
		chain.doFilter(req, res); 
		} 
	public void init(FilterConfig filterConfig) {} 
	public void destroy() {}
}
