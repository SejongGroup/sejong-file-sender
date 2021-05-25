/**
 * 
 */
package com.sejong.cdbiz.cbiz.service.factory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.common.DmlInterface;
import com.sejong.cdbiz.service.DmlCommonService;

import sejong.api.common.model.ResultVO;
import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: CBizSelect.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.28	김준호			Delete를 지원하는 클래스
 * 2021.04.28	김준호			transactionManager2 = DBIZ 트랜젝션
 * 2021.04.28	김준호			private에서 트랜잭션 기능 수행x
 */
@Service
public class CBizDelete extends DmlCommonService implements DmlInterface {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor = Exception.class, value="transactionManager2")
	public NBIZResponseBody mainBehavior(Object ment) {
 		NBIZResponseBody responseVo = checkPrefix((CheatMentVO)ment, sqlSession_CBIZ);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = checkId((CheatMentVO) ment, sqlSession_CBIZ);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = callHash((CheatMentVO) ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = checkParam((CheatMentVO) ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = checkLevel((CheatMentVO) ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		responseVo = deleteMent((CheatMentVO) ment);
		
		return responseVo;
	}

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=false, rollbackFor=Exception.class)
	private NBIZResponseBody deleteMent(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();
		
		/* 기존 등록된 050번호를 조회 */
		List<String> sequenceNumber = cBizDao.sequenceNumber(ment);
		if (sequenceNumber.size() == 0) {
        	result.setResultCode("203");
        	result.setResultMessage("삭제 대상 번호가 존재하지 않습니다.");
        	responseVo.setResult(result);
        	return responseVo;
		}
		
		/* Delete 할 수 있는지 확인한다. */
		responseVo = canDelete(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		/*  TB_VNS_CHEAT_TEMP 테이블에 값을 INSERT함 */
		ment.setSeq(cBizDao.selectNextSequence(Constants.CHEAT_TEMP_SEQ));
		ment.setFlag("1"); // 삭제 요청 플래그
		ment.setLevel("0");
		responseVo = insertCheatNumTemp(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		/* CHEAT NUMBER 테이블에 Delete */
		responseVo = deleteCheatNumMain(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		/* UPDATE */
		ment.setSetFlag("D");
		responseVo = updateCheatNumTemp(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		return responseVo;
	}

}
