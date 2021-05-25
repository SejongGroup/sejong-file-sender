/**
 * 
 */
package com.sejong.cdbiz.cbiz.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.dao.CommonDao;

/**
 * @FileName	: DBizDao.java
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
public class CBizDao extends CommonDao {
	
	public String getCallingNum(CheatMentVO ment) {
		return sqlSession_CBIZ.selectOne("cbiz.getCallingNum", ment);
	} 

	public Integer insertCbiz(CheatMentVO ment) {
		return doCUDJobs("cbiz.insertCbizCheat", ment, Constants.DML_INSERT, sqlSession_CBIZ, syncSqlSession_CBIZ);
	}

	public Integer selectCbizCheat(CheatMentVO ment) {
		return sqlSession_CBIZ.selectOne("cbiz.selectCbizCheat", ment);
	}

	public String selectCallingFlag(HashMap<String, String> hm) {
		return sqlSession_CBIZ.selectOne("cbiz.selectCallingFlag", hm);
	}

	public Long selectNextSequence(String sequenceName) {
		return sqlSession_CBIZ.selectOne("cbiz.selectNextSequence", sequenceName);
	}

	public int insertCheatNumTemp(CheatMentVO ment) {
		return doCUDJobs("cbiz.insertCheatNumTemp", ment, Constants.DML_INSERT, sqlSession_CBIZ, syncSqlSession_CBIZ);
	}

	public int insertCheatNumMain(CheatMentVO ment) {
		return doCUDJobs("cbiz.insertCheatNumMain", ment, Constants.DML_INSERT, sqlSession_CBIZ, syncSqlSession_CBIZ);
	}

	public int updateCheatNumTemp(CheatMentVO ment) {
		return doCUDJobs("cbiz.updateCheatNumTemp", ment, Constants.DML_UPDATE, sqlSession_CBIZ, syncSqlSession_CBIZ);
	}

	public List<String> sequenceNumber(CheatMentVO ment) {
		return sqlSession_CBIZ.selectList("cbiz.sequenceNumber", ment);
	}

	public int deleteCheatNumMain(CheatMentVO ment) {
		return doCUDJobs("cbiz.deleteCheatNumMain", ment, Constants.DML_DELETE, sqlSession_CBIZ, syncSqlSession_CBIZ);
	}

	public Integer insertOrupdate(CheatMentVO ment) {
		return sqlSession_CBIZ.selectOne("cbiz.insertOrupdate", ment);
	}

	public int updateCheatNumMain(CheatMentVO ment) {
		return doCUDJobs("cbiz.updateCheatNumMain", ment, Constants.DML_UPDATE, sqlSession_CBIZ, syncSqlSession_CBIZ);
	}

}