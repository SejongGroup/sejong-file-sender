/**
 * 
 */
package com.sejong.cdbiz.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.common.ApplicationProperty;
import com.sejong.cdbiz.common.Constants;


/**
 * @FileName	: CommonDao.java
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
public class CommonDao {
	
	@Autowired protected SqlSession sqlSession_DBIZ;	// DB 1
	@Autowired protected SqlSession syncSqlSession_DBIZ;// DB 2
	
	@Autowired protected SqlSession sqlSession_CBIZ;	// DB 1
	@Autowired protected SqlSession syncSqlSession_CBIZ;// DB 2
	
	private Logger logger = LoggerFactory.getLogger(CommonDao.class);
	
	/**
	 * @methodName	: doCUDJobs
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : 쿼리문을 DB1과 DB2에 동시에 적용하기 위한 코드. 에러가 나면 런타임 에러처리를 하여 뒷단에 트렌잭션에게 맡기도록 설정해야 함
	 * @param query
	 * @param param
	 * @param DML_TYPE
	 * @param sqlsession
	 * @param sqlsession2
	 * @return
	 */
	protected Integer doCUDJobs(String query, Object param, Integer DML_TYPE, SqlSession sqlsession, SqlSession sqlsession2) {
		if(doRealDbCUDJobs(query, param, DML_TYPE, sqlsession) <= 0){
			logger.error("fail to cud to real db");
			throw new RuntimeException();
		}
		if(doSyncDbCUDJobs(query, param, DML_TYPE, sqlsession2) <= 0){
			logger.error("fail to cud to sync db");
			throw new RuntimeException();
		}
		return 1;
	}

	/**
	 * @methodName	: doRealDbCUDJobs
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : 쿼리문을 DB1에 INSERT, UPDATE, DELETE 하기 위한 함수
	 * @param query
	 * @param param
	 * @param dML_TYPE
	 * @param sqlSession
	 * @return
	 */
	private int doRealDbCUDJobs(String query, Object param, Integer dML_TYPE, SqlSession sqlSession) {
		try{
			if(dML_TYPE == Constants.DML_INSERT){
				if(sqlSession.insert(query, param) <= 0){
					logger.error("fail to insert data");
					return -1;
				}
			}
			else if(dML_TYPE == Constants.DML_UPDATE){
				if(sqlSession.update(query, param) < 0){
					logger.error("fail to update sync data");
					return -1;
				}
			}
			else if(dML_TYPE == Constants.DML_DELETE){
				if(sqlSession.delete(query, param) < 0){
					logger.error("fail to delete sync data");
					return -1;
				}
			}
			else {
				logger.error("invalid j ob mode : " + dML_TYPE);
				return -1;
			}
		}catch(Exception e){
			logger.error(" Exception : " + e.getMessage() + " " + query);
			return -1;
		}
		return 1;
	}
	
	/**
	 * @methodName	: doSyncDbCUDJobs
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : 쿼리문을 DB2에 INSERT, UPDATE, DELETE 하기 위한 함수
	 * @param query
	 * @param param
	 * @param dML_TYPE
	 * @param syncSqlSession
	 * @return
	 */
	private int doSyncDbCUDJobs(String query, Object param, Integer dML_TYPE, SqlSession syncSqlSession) {
		if(ApplicationProperty.dbSyncMode.equalsIgnoreCase(Constants.CHARACTER_OFF) == true){
			logger.info("sync mode is off");
			return 1;
		}

		try{
			if(dML_TYPE == Constants.DML_INSERT){
				if(syncSqlSession.insert(query, param) <= 0){
					logger.error("fail to insert data");
					return -1;
				}
			}
			else if(dML_TYPE == Constants.DML_UPDATE){
				if(syncSqlSession.update(query, param) < 0){
					logger.error("fail to update sync data");
					return -1;
				}
			}
			else if(dML_TYPE == Constants.DML_DELETE){
				if(syncSqlSession.delete(query, param) < 0){
					logger.error("fail to delete sync data");
					return -1;
				}
			}
			else {
				logger.error("invalid j ob mode : " + dML_TYPE);
				return -1;
			}
		}catch(Exception e){
			logger.error(" Exception : " + e.getMessage() + " " + query);
			return -1;
		}
		return 1;
	}

	public int selectVnsNumberPrefixCount(HashMap<String, String> hm, SqlSession sqlSession) {
		return (Integer)sqlSession.selectOne("common.selectVnsNumberPrefixCount", hm);
	}

	public int selectLogisticsUserPwdCheck(HashMap<String, String> map, SqlSession sqlSession) {
		return (Integer)sqlSession.selectOne("common.selectLogisticsUserPwdCheck", map);
	}
}
