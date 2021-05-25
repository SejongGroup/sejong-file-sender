package com.sejong.cdbiz.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sejong.cdbiz.cbiz.dao.CBizDao;
import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.dao.CommonDao;
import com.sejong.cdbiz.dbiz.dao.CalledDao;
import com.sejong.cdbiz.util.ParameterChecker;

import sejong.api.common.model.ResultVO;
import sejong.api.nbiz.error.NBIZErrorCode;
import sejong.api.nbiz.model.NBIZResponseBody;

@Service
public class CommonService {
	
	@Autowired
	protected
	CalledDao calledDao;
	
	@Autowired
	protected
	CBizDao cBizDao;
	
	@Autowired
	protected
	CommonDao commonDao;
	
	@Resource protected SqlSession sqlSession_CBIZ;
	@Resource protected SqlSession syncSqlSession_CBIZ;
	@Resource protected SqlSession sqlSession_DBIZ;
	@Resource protected SqlSession syncSqlSession_DBIZ;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);
	private static final Logger logger_info_cbiz = LoggerFactory.getLogger("INFO_LOGGER_cBiz");
	private static final Logger logger_info_dbiz = LoggerFactory.getLogger("INFO_LOGGER_dBiz");
	
	/**
	 * @methodName	: checkVnsNumberPrefix
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : Vns 넘버가 맞는지 확인 하기 ==> cbiz, dbiz
	 * @param vnsNumber
	 * @param logisticsCode
	 * @return
	 */
	public ResultVO checkVnsNumberPrefix(String vnsNumber, String logisticsCode, SqlSession sqlSession){
		if(ParameterChecker.isValidVnsNumber(vnsNumber) == false){
			return NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_VNS_NUMBER_FORMAT);
		}
		
		try {
			String prefix = vnsNumber.substring(3, vnsNumber.length() - 4); // 050번호 substring
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("prefix", prefix);
			hm.put("logisticsCode", logisticsCode);
			// prefix 확인  ==> 자기번호 확인
			if(commonDao.selectVnsNumberPrefixCount(hm, sqlSession) <= 0){
				logger.info("존재 하지 않은 VNS 넘버로 인한 오류입니다. 사용한 VNS NUMBER : " + vnsNumber);
				return NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_VNS_NUMBER_PREFIX);
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			logger.error("fail to checking gbiz vns number : " + vnsNumber);
			return NBIZErrorCode.getResultVO(NBIZErrorCode.INSERT_FAIL);
		}
		return NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS);	
	}

	/**
	 * @methodName	: isUsableIdAndPwd
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : 아이디와 패스워드가 등록되어져 있는지 확인 ==> cbiz, dbiz
	 * @param logisticsCode
	 * @param userPwd
	 * @return
	 */
	public ResultVO isUsableIdAndPwd(String logisticsCode, String userPwd, SqlSession sqlSession) {
		logger.info("check userPwd in logisticsCode : " + logisticsCode);
		
		try{
            HashMap<String,String> map = new HashMap();
            map.put(Constants.LOGISTICS_CODE, logisticsCode);
            map.put(Constants.USER_PWD, userPwd);
            map.put(Constants.USE_YN, Constants.CHARACTER_YES);
            map.put(Constants.BIZ_ID, Constants.ID_DBIZ);
            int result = commonDao.selectLogisticsUserPwdCheck(map, sqlSession);
        
            if (result <= 0) {
            	return NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_LOGIS_AUTHENTICATION);
            }
		} catch (Exception e) {
			return NBIZErrorCode.getResultVO(NBIZErrorCode.INVALID_LOGIS_AUTHENTICATION);	
		}
		return NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS);
	}

	/**
	 * @methodName	: callHash
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 26.
	 * @description : 발신번호 해시값의 누락 체크 ==> cbiz
	 * @param logiscode
	 * @param userPwd
	 * @return
	 */
	public ResultVO callHash(String callinghash) {
		logger.info("check callHash : " + callinghash);
		ResultVO rvo = new ResultVO();
        if (callinghash == null || callinghash.isEmpty()) {
            rvo.setResultCode("103");
            rvo.setResultMessage("발신번호 해시값이 누락되었습니다.");
        } else {
        	return NBIZErrorCode.getResultVO(NBIZErrorCode.SUCCESS);
        }
		return rvo;
	}
	
	
	/**
	 * @methodName	: logInfo
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 27.
	 * @description : 결과 로그를 저장하기 위함 ==> dbiz
	 * @param responseVo
	 * @param vnsNumber
	 * @param mentType
	 */
	public void logInfo(NBIZResponseBody responseVo, String vnsNumber, String mentType) {
        StringBuffer sb = new StringBuffer();
        
        ResultVO result = responseVo.getResult();
        sb.append(result.getResultCode());
        sb.append("|");
        sb.append(result.getResultMessage());
        sb.append("|");
        sb.append(vnsNumber);
        sb.append("|");
        sb.append(mentType);
        logger_info_dbiz.info(sb.toString());
    }
	
	/**
	 * @methodName	: logInfo_c
	 * @author 		: JH.KIM
	 * @date		: 2021. 4. 28.
	 * @description : 결과 로그를 저장하기 위함 ==> cbiz
	 * @param responseVo
	 * @param vnsNumber
	 * @param mentType
	 */
	public void logInfo_c(NBIZResponseBody responseVo, String vnsNumber, String mentType) {
        StringBuffer sb = new StringBuffer();
        
        ResultVO result = responseVo.getResult();
        sb.append(result.getResultCode());
        sb.append("|");
        sb.append(result.getResultMessage());
        sb.append("|");
        sb.append(vnsNumber);
        sb.append("|");
        sb.append(mentType);
        logger_info_cbiz.info(sb.toString());
    }
}
