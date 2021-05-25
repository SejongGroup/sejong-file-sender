/**
 * 
 */
package com.sejong.cdbiz.dbiz.service.factory;

import org.springframework.stereotype.Service;

import com.sejong.cdbiz.common.DmlInterface;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;
import com.sejong.cdbiz.service.DmlCommonService;

import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: DmlSelect.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 21.
 * @Author		: JH.KIM
 * @Description : DBiz Select를 하기 위한 클래스
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.26	김준호				(1),(2) 공통부분 DmlCommonService 합병
 */
@Service
public class DBizSelect extends DmlCommonService implements DmlInterface {

	@Override
	public NBIZResponseBody mainBehavior(Object ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		int res = calledDao.selectCalledNumber((CalledMentVO) ment);
		if (res <= 0) {
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SELECT_FAIL));
		} else {
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
		}
		return responseVo;
	}
	
}
