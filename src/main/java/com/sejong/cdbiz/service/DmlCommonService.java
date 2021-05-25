/**
 * 
 */
package com.sejong.cdbiz.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sejong.cdbiz.cbiz.vo.CheatMentVO;
import com.sejong.cdbiz.dbiz.vo.CalledMentVO;

import sejong.api.common.model.ResultVO;
import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

/**
 * @FileName	: DmlCommonService.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 * 2021.04.26	김준호				공통부분 (1),(2) 합병
 */
@Service
public class DmlCommonService extends CommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(DmlCommonService.class);
	
	/**
	 * @methodName	: checkPrefix
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : 1. prefix 확인 [ 공통 부분 처리를 위함 ] ==> cBiz, dBiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody checkPrefix(Object ment, SqlSession sqlSession) {
		ResultVO result = new ResultVO();
		NBIZResponseBody responseVo = new NBIZResponseBody();
		String vnsNumber = "";
		String logiscode = "";
	
		try{
			
			if (ment instanceof CalledMentVO) {
				vnsNumber = ((CalledMentVO) ment).getVnsNumber();
				logiscode = ((CalledMentVO) ment).getLogisticsCode();
			} else {
				vnsNumber = ((CheatMentVO) ment).getVnsNumber();
				logiscode = ((CheatMentVO) ment).getLogisticsCode();
			}
		

			result = checkVnsNumberPrefix(vnsNumber, logiscode, sqlSession);
			if(result.getResultCode().equals(NBIZErrorCode.SUCCESS) == false) {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			} else {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
			}
		} catch(DataAccessException e){
			logger.error(e.getMessage());
			logger.error("fail to select ment logisticsCode : " + vnsNumber + " , vnsIdx : " + logiscode);
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			return responseVo;
		}
		return responseVo;
	}

	/**
	 * @methodName	: checkId
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : Id 체크 ==> cBiz, dBiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody checkId(Object ment, SqlSession sqlSession) {
		ResultVO result = new ResultVO();
		NBIZResponseBody responseVo = new NBIZResponseBody();
		
		String logiscode = "";
		String userPwd = "";
		try {
			if (ment instanceof CalledMentVO) {
				userPwd = ((CalledMentVO) ment).getUserPwd();
				logiscode = ((CalledMentVO) ment).getLogisticsCode();
			} else {
				userPwd = ((CheatMentVO) ment).getUserPwd();
				logiscode = ((CheatMentVO) ment).getLogisticsCode();
			}
		

			result = isUsableIdAndPwd(logiscode, userPwd, sqlSession);
			if(result.getResultCode().equals(NBIZErrorCode.SUCCESS) == false) {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			} else {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
			}
		} catch(Exception e){
			logger.error(e.getMessage());
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			return responseVo;
		}
		return responseVo;
	}
	
	
	/**
	 * @methodName	: callHash
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : callingHash 값을 전송받아와서 값이 있는지 체크함 ==> cBiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody callHash(Object ment) {
		ResultVO result = new ResultVO();
		NBIZResponseBody responseVo = new NBIZResponseBody();
		
		String callinghash = "";
		try {
			
			if (ment instanceof CalledMentVO) {
				callinghash = "";
			} else {
				callinghash = ((CheatMentVO) ment).getCallingHash();
			}
		

			result = super.callHash(callinghash);
			if(result.getResultCode().equals(NBIZErrorCode.SUCCESS) == false) {
				responseVo.setResult(result);
			} else {
				responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
			}
			
		} catch(Exception e){
			logger.error(e.getMessage());
			responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL));
			return responseVo;
		}
		return responseVo;
	}
	
	/**
	 * @methodName	: checkParam
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : 나머지 파라미터를 체크하는 것 ==> cBiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody checkParam(Object ment) {
		ResultVO result = new ResultVO();
		NBIZResponseBody responseVo = new NBIZResponseBody();
		
		String cstime = "";
		String cetime = "";
		String callId = "";
		
		try {		
			
			if (ment instanceof CalledMentVO) {
				/* 해당 없음 */
			} else {
				cstime = ((CheatMentVO) ment).getCstime();
				cetime = ((CheatMentVO) ment).getCetime();
				callId = ((CheatMentVO) ment).getCallId();
			}

	        if (cstime == null || cstime.isEmpty()
	                || cetime == null || cetime.isEmpty()
	                || callId == null || callId.isEmpty()) {
	        	result.setResultCode("019");
	        	result.setResultMessage("개별 파라메터 값이 잘못되었습니다.");
	        	responseVo.setResult(result);
	        } else {
	        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
	        }
	        
		} catch(Exception e){
			logger.error(e.getMessage());
        	result.setResultCode("019");
        	result.setResultMessage("개별 파라메터 값이 잘못되었습니다.");
        	responseVo.setResult(result);
			return responseVo;
		}
		return responseVo;
	}

	/**
	 * @methodName	: checkLevel
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : 레벨의 length 를 체크하는 것 ==> cBiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody checkLevel(Object ment) {
		ResultVO result = new ResultVO();
		NBIZResponseBody responseVo = new NBIZResponseBody();
		
		String level = "";
		
		try {		
			
			if (ment instanceof CalledMentVO) {
				/* 해당 없음 */
			} else {
				level = ((CheatMentVO) ment).getLevel();
			}
			
	        if (level == null || level.isEmpty()) {
	            level = "0";
	            responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
	        }
	        else if (level.length() > 1) {
	            result.setResultCode("019");
	            result.setResultMessage("개별 파라메터 값이 잘못되었습니다.");
	            responseVo.setResult(result);
	        } else {
	        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
	        }	        
		} catch(Exception e){
			logger.error(e.getMessage());
        	result.setResultCode("019");
        	result.setResultMessage("개별 파라메터 값이 잘못되었습니다.");
        	responseVo.setResult(result);
			return responseVo;
		}
		return responseVo;
	}
	
	/**
	 * @methodName	: getCDR
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 27.
	 * @description : 최신 CDR 테이블의 name을 알아온다.
	 * @return
	 */
	public String getCDR() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		StringBuilder timeCDR = new StringBuilder();
		timeCDR.append("TB_CDR_");
		timeCDR.append(format.format(date));
		return timeCDR.toString();
	}
	
	/**
	 * @methodName	: canInsert
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 27.
	 * @description : INSERT 할 수 있는지 확인한다. ==> cbiz
	 * @param ment 
	 * @return
	 */
	public NBIZResponseBody canInsert(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("callingHash", ment.getCallingHash());
		
		String flag = cBizDao.selectCallingFlag(hm);
		
        if (flag == null || "1".equals(flag) || "D".equals(flag) || "R".equals(flag)) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
        } else {
            result.setResultCode("201");
            result.setResultMessage("이미 등록된 번호입니다.");
            responseVo.setResult(result);
            return responseVo;
        }
	}
	
	/**
	 * @methodName	: canDelete
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 27.
	 * @description : DELETE 할 수 있는지 확인한다. ==> cbiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody canDelete(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("callingHash", ment.getCallingHash());
		
		String flag = cBizDao.selectCallingFlag(hm);
		
        if (flag == null || "0".equals(flag) || "R".equals(flag)) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
        } else {
            result.setResultCode("202");
            result.setResultMessage("이미 삭제 요청한 번호입니다.");
            responseVo.setResult(result);
            return responseVo;
        }
	}
	
	/**
	 * @methodName	: insertCheatNumTemp
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : TEMP 테이블에 INSERT 한다. 기록만을 위함 ==> cbiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody insertCheatNumTemp(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();
		
		int res = cBizDao.insertCheatNumTemp(ment);
		
		if (res >= 1) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
		} else {
            result.setResultCode("015");
            result.setResultMessage("DB 작업에 실패했습니다.");
            responseVo.setResult(result);
            return responseVo;
		}
	}
	
	/**
	 * @methodName	: insertCheatNumMain
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : TB_VNS_CHEAT_NUMBER 테이블에 값을 insert한 후 잘 들어갔는지 체크 ==> cbiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody insertCheatNumMain(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();

		int res = cBizDao.insertCheatNumMain(ment);
		
		if (res >= 1) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
		} else {
            result.setResultCode("015");
            result.setResultMessage("DB 작업에 실패했습니다.");
            responseVo.setResult(result);
            return responseVo;
		}
	}
	
	/**
	 * @methodName	: updateCheatNumMain
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : TB_VNS_CHEAT_NUMBER 테이블에 값을 update한 후 잘 들어갔는지 체크 ==> cbiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody updateCheatNumMain(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();

		int res = cBizDao.updateCheatNumMain(ment);
		
		if (res >= 1) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
		} else {
            result.setResultCode("015");
            result.setResultMessage("DB 작업에 실패했습니다.");
            responseVo.setResult(result);
            return responseVo;
		}
	}
	
	/**
	 * @methodName	: updateCheatNumTemp
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : INSERT 한 TEMP 테이블의 값의 FLAG를 변경하는 함수 ==> cbiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody updateCheatNumTemp(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();
		int res = cBizDao.updateCheatNumTemp(ment);
		
		if (res >= 1) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
		} else {
            result.setResultCode("015");
            result.setResultMessage("DB 작업에 실패했습니다.");
            responseVo.setResult(result);
            return responseVo;
		}
	}

	/**
	 * @methodName	: deleteCheatNumMain
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : TB_VNS_CHEAT_NUMBER 테이블의 값을 삭제하는 함수 ==> cbiz
	 * @param ment
	 * @return
	 */
	public NBIZResponseBody deleteCheatNumMain(CheatMentVO ment) {
		NBIZResponseBody responseVo = new NBIZResponseBody();
		ResultVO result = new ResultVO();

		int res = cBizDao.deleteCheatNumMain(ment);
		
		if (res >= 1) {
        	responseVo.setResult(NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS));
            return responseVo;
		} else {
            result.setResultCode("015");
            result.setResultMessage("DB 작업에 실패했습니다.");
            responseVo.setResult(result);
            return responseVo;
		}
	}
	
	/**
	 * @methodName	: insertOrupdate
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : INSERT OR UPDATE 즉, 뭘 해야 하는지 알아내는 함수 ==> cbiz
	 * @param ment
	 * @return
	 */
	public Integer insertOrupdate(CheatMentVO ment) {
		return cBizDao.insertOrupdate(ment);
	}
}
