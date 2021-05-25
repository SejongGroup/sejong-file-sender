package com.sejong.cdbiz.dbiz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sejong.cdbiz.common.DmlInterface;
import com.sejong.cdbiz.dbiz.service.factory.DBizDmlFactory;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;
import com.sejong.cdbiz.service.CommonService;

import sejong.api.common.model.ResultVO;
import sejong.api.nbiz.model.NBIZResponseBody;


@Service
public class CalledMentService extends CommonService {
	
	@Autowired
	DBizDmlFactory dmlfactory;
	
	/**
	 * @methodName	: mentBehavior
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : 서비스 메인 행동 처리
	 * @param ment
	 * @param type
	 * @return
	 */
	public NBIZResponseBody mentBehavior(CalledMentVO ment, int type) {
		
		DmlInterface dml = dmlfactory.createDml(type);
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
		
		logInfo(responseVo, ment.getVnsNumber(), ment.getMentKind());
		return responseVo;
	}
}
