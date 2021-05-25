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
 * @Description : DBiz Delete를 하기 위한 클래스
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.26	김준호				(1),(2) 공통부분 DmlCommonService 합병
 */
@Service
public class DBizDelete extends DmlCommonService implements DmlInterface {

	private static final Logger logger = LoggerFactory.getLogger(DBizController.class);
	
	// 메인 처리 부분
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor = Exception.class, value="transactionManager")
	public NBIZResponseBody mainBehavior(Object ment) {
		NBIZResponseBody responseVo = checkPrefix((CalledMentVO) ment, sqlSession_DBIZ);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = checkId((CalledMentVO) ment, sqlSession_DBIZ);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = deleteMent((CalledMentVO) ment);
		return responseVo;
	}
	
	// 3. delete 처리
	public NBIZResponseBody deleteMent(CalledMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		try {
			/* 이 부분은 테스트 용입니다. */
			ment.setLogisCode(ment.getLogisticsCode());
			/* 이 부분은 테스트 용입니다. */
			
			logger.info("테이블 삭제 처리 진행 중...");
			calledDao.deleteCalledNumber(ment);
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
			return responseVo;
			
		} catch (Exception e) {
			// TODO: handle exception
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			return responseVo;
		}
	}
}
