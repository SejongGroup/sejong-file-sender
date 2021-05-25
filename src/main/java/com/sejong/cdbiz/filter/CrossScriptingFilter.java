package com.sejong.cdbiz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * @FileName	: CrossScriptingFilter.java
 * @Project		: cdBiz
 * @Date		: 2021. 5. 17.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */


public class CrossScriptingFilter implements Filter {
	public FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
	       this.filterConfig = filterConfig;
	   }
	
	   public void destroy() {
	       this.filterConfig = null;
	   }
	
	   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	       throws IOException, ServletException {
	
	       chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
	
	   }

}
