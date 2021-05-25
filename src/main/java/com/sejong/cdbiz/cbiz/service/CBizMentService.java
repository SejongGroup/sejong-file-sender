/**
 * 
 */
package com.sejong.cdbiz.cbiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sejong.cdbiz.cbiz.service.factory.CBizDmlFactory;
import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.common.DmlInterface;
import com.sejong.cdbiz.service.CommonService;

import sejong.api.common.model.ResultVO;
import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: CheatMentService.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.28	김준호			Exception이 일어나면 DB 작업 실패 기록
 * 2021.04.28	김준호			Transaction 처리를 위해 여기서 처리해야함
 */
@Service
public class CBizMentService extends CommonService {
	
	@Autowired
	CBizDmlFactory dmlFactory;

	public NBIZResponseBody mentBehavior(CheatMentVO ment, int DML_TYPE) {
		
		DmlInterface dml = dmlFactory.createDml(DML_TYPE);
		NBIZResponseBody responseVo = null;
		
		try {
			responseVo = dml.mainBehavior(ment);
		} catch (Exception e) {
			ResultVO result = new ResultVO();
			result.setResultCode("015");
            result.setResultMessage("DB 작업에 실패했습니다.");
			responseVo = new NBIZResponseBody();
			responseVo.setResult(result);
		}
		
		logInfo_c(responseVo, ment.getVnsNumber(), ment.getLevel());
		return responseVo;
	}
}
