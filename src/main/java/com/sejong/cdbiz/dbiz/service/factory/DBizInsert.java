/**
 * 
 */
package com.sejong.cdbiz.dbiz.service.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sejong.cdbiz.common.DmlInterface;
import com.sejong.cdbiz.dbiz.controller.DBizController;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;
import com.sejong.cdbiz.service.DmlCommonService;

import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: DmlSelect.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 21.
 * @Author		: JH.KIM
 * @Description : DBiz insert를 하기 위한 클래스
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.26	김준호				(1),(2) 공통부분 DmlCommonService 합병
 */
@Service
public class DBizInsert extends DmlCommonService implements DmlInterface {
	
	private static final Logger logger = LoggerFactory.getLogger(DBizController.class);
	
	// 메인 처리 부분
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor = Exception.class, value="transactionManager")
	public NBIZResponseBody mainBehavior(Object ment) {
		NBIZResponseBody responseVo = checkPrefix((CalledMentVO) ment, sqlSession_DBIZ);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = checkId((CalledMentVO) ment, sqlSession_DBIZ);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = insertMent((CalledMentVO) ment);
		return responseVo;
	}
	
	// 3.
	public NBIZResponseBody insertMent(CalledMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		try {
			/* 이 부분은 테스트 용입니다. */
			ment.setLogisCode(ment.getLogisticsCode());
			ment.setModId("dbiz");
			/* 이 부분은 테스트 용입니다. */
			
			/* MentKind : 2 일 때 */
			if (Integer.parseInt(ment.getMentKind()) == 2) {
				logger.info("MentKind value : 2, 테이블 삭제 처리 진행 중");
				calledDao.deleteCalledNumber(ment);
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
				return responseVo;
				
			/* MentKind : 0 일 때 */
			} else if (Integer.parseInt(ment.getMentKind()) == 0) {								
				logger.info("MentKind value : 0, 테이블 삽입 처리 진행 중 : MENT_IDX => 2, MENT_TYPE => 0");
				ment.setMentIdx("2");															// MENT_TYPE 변경
				int res = calledDao.selectCalledNumber(ment);
				if (res == 0) { 																// select count의 값이 없기 때문에 insert를 해줘야 한다.
					res = calledDao.insertCalledNumber(ment);
					if (res <= 0) {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
						return responseVo;
					} else {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
						return responseVo;
					}
				} else {																		// update 해줘야 한다.
					res = calledDao.updateCalledNumber(ment);
					if (res <= 0) {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
						return responseVo;
					} else {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
						return responseVo;
					}
				}
			
			/* MentKind : 1 일 때 */
			} else if (Integer.parseInt(ment.getMentKind()) == 1) {								// update/insert 해야하는지 select 로 확인
				logger.info("MentKind value : 1, 테이블 삽입 처리 진행 중 : MENT_IDX => 1, MENT_TYPE => 1");
				ment.setMentIdx("1");															// MENT_TYPE 변경
				int res = calledDao.selectCalledNumber(ment);
				if (res == 0) { 																// select count의 값이 없기 때문에 insert를 해줘야 한다.
					res = calledDao.insertCalledNumber(ment);
					if (res <= 0) {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
						return responseVo;
					} else {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
						return responseVo;
					}
				} else {																		// update 해줘야 한다.
					res = calledDao.updateCalledNumber(ment);
					if (res <= 0) {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
						return responseVo;
					} else {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
						return responseVo;
					}
					
				}
				
			/* MentKind : 3 일 때 */
			} else if (Integer.parseInt(ment.getMentKind()) == 3) {								// update/insert 해야하는지 select 로 확인
				logger.info("MentKind value : 1, 테이블 삽입 처리 진행 중 : MENT_IDX => 3, MENT_TYPE => 2");
				ment.setMentIdx("3");															// MENT_TYPE 변경
				int res = calledDao.selectCalledNumber(ment);
				if (res == 0) { 																// select count의 값이 없기 때문에 insert를 해줘야 한다.
					res = calledDao.insertCalledNumber(ment);
					if (res <= 0) {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
						return responseVo;
					} else {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
						return responseVo;
					}
				} else {																		// update 해줘야 한다.
					res = calledDao.updateCalledNumber(ment);
					if (res <= 0) {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
						return responseVo;
					} else {
						responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
						return responseVo;
					}
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			return responseVo;
		}
		return responseVo;
	}
	
}
