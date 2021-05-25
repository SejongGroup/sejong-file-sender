package com.sejong.cdbiz.dbiz.dao;

import org.springframework.stereotype.Repository;

import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.dao.CommonDao;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;

@Repository
public class CalledDao extends CommonDao {
	
	public int deleteCalledNumber(CalledMentVO ment) {
		return doCUDJobs("ment.deleteCalledNumber", ment, Constants.DML_DELETE, sqlSession_DBIZ, syncSqlSession_DBIZ);
	}

	public int selectCalledNumber(CalledMentVO ment) {
		return (Integer) sqlSession_DBIZ.selectOne("ment.selectCalledNumber", ment);
	}

	public int updateCalledNumber(CalledMentVO ment) {
		return doCUDJobs("ment.updateCalledNumber", ment, Constants.DML_UPDATE, sqlSession_DBIZ, syncSqlSession_DBIZ);
	}

	public int insertCalledNumber(CalledMentVO ment) {
		return doCUDJobs("ment.insertCalledNumber", ment, Constants.DML_INSERT, sqlSession_DBIZ, syncSqlSession_DBIZ);
	}
}
