
package com.sejong.cdbiz.cbiz.service.factory;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
 *
 */
@Service
public class CBizInsert extends DmlCommonService implements DmlInterface {
	
	/*** 사용 가능한 Bean
	*
	*	CalledDao calledDao;
	*	DBizDao dBizDao;
	*	CommonDao commonDao;
	*	
	*	 
	***/

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

		responseVo = insertMent((CheatMentVO) ment);
		
		return responseVo;
	}

	/*	[ CBIZ INSERT 로직 ]
	 *  1. CETIME 기준 해당 월 CDR 테이블에서 발신번호 조회 => search_num 변수에 저장
	 *  2. search_num 변수에 값이 있으면, 해당번호 테이블 입력 => DB1
	 *  3. DB2에다가도 저장
	 *  4. 정상적으로 테이블 입력이 되면 결과 UPDATE
	 *  */
	private NBIZResponseBody insertMent(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();

		ment.setCdrNumber(getCDR());
		
		/* 해당 번호의 진짜 번호를 찾아내야 함 */
		
		String callNumber = cBizDao.getCallingNum(ment); // callnumber 를 찾아서 
		
		if (callNumber == null) {
        	result.setResultCode("018");
        	result.setResultMessage("입력된 값에 수정할 정보가 없습니다.");
        	responseVo.setResult(result);
        	return responseVo;
		}
		ment.setCallingNum(callNumber);

		/* INSERT 할수 있는 지를 확인 한다. */
		responseVo = canInsert(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		/* TB_VNS_CHEAT_TEMP 테이블에 값을 INSERT함 */
		ment.setSeq(cBizDao.selectNextSequence(Constants.CHEAT_TEMP_SEQ));	// 시퀀스 넘버가 db1 테이블에 조회를 함
		ment.setFlag("0"); // 입력 요청 플래그
		responseVo = insertCheatNumTemp(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		/* CHEAT_NUMBER 테이블에 INSERT/UPDATE */
		int insertOrupdate = insertOrupdate(ment);
		if (insertOrupdate >= 1) {
			/* Update 처리 */
			responseVo = updateCheatNumMain(ment);
			if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		} else {
			/* INSERT 처리 */
			responseVo = insertCheatNumMain(ment);
			if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		}
		
		/* TB_VNS_CHEAT_TEMP 테이블에 UPDATE */
		ment.setSetFlag("R");
		responseVo = updateCheatNumTemp(ment);
		if (responseVo.getResult().getResultCode().equals(NBIZErrorCode.SUCCESS) == false) return responseVo;
		
		return responseVo;
		
	}

}
