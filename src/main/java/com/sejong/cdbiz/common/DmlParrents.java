/**
 * 
 */
package com.sejong.cdbiz.common;

import org.apache.ibatis.session.SqlSession;

import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: DmlParrents.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 21.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public interface DmlParrents {
	// 1. prefix 확인
	public NBIZResponseBody checkPrefix(Object ment, SqlSession sqlSession);
	
	// 2. Id 체크
	public NBIZResponseBody checkId(Object ment, SqlSession sqlSession);
}
