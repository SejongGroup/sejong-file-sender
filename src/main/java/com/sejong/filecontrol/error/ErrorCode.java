/**
 * 
 */
package com.sejong.filecontrol.error;

import java.util.HashMap;

import com.sejong.filecontrol.model.ResultVO;
/**
 * @FileName	: ErrorCode.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public class ErrorCode {
	public static final String SUCCESS = "00";
	public static final String KNOWNPARAM_ERROR = "01";
	public static final String AUTHORIZE_ERROR = "02";
	public static final String SESSION_ERROR = "03";
	public static final String FILESIZE_ERROR = "04";
	public static final String IO_ERROR = "05";
	public static final String DIRECTORY_ERROR = "06";
	public static final String FILEPATH_ERROR = "07";
	public static final String DELETEFILE_ERROR = "08";
	public static final String UNKNOWN_ERROR = "99";
	
	@SuppressWarnings("unchecked")
	public static final HashMap<String, String> errorCode = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

		{
			put(SUCCESS, "성공");
			put(KNOWNPARAM_ERROR, "파라미터 형식 및 내용이 잘못되었습니다.");
			put(AUTHORIZE_ERROR, "권한이 없는 아이디로 로그인하였습니다.");
			put(UNKNOWN_ERROR, "알 수 없는 오류가 발생했습니다.");
			put(SESSION_ERROR, "연속적인 세션으로 인한 오류입니다. 잠시후에 다시 시도해주세요");
			put(FILESIZE_ERROR, "파일 사이즈의 값이 0 혹은 null 값입니다.");
			put(IO_ERROR, "파일을 읽는 도중에 오류가 발생하였습니다.");
			put(DIRECTORY_ERROR, "디렉토리를 생성하는 도중에 오류가 발생하였습니다.");
			put(FILEPATH_ERROR, "파일 생성 중 경로 오류가 발생하였습니다.");
			put(DELETEFILE_ERROR, "게이트웨이에 파일을 업로드 후 삭제하는 과정에서 예외가 발생하였습니다.");
		}
	};
	
	public static String getResultMessage(String code){
		return (String) errorCode.get(code);
	}

	public static ResultVO getResultVO(String code)
	{
		return getResultVO(code, null);
	}
	
	public static ResultVO getResultVO(String code, String addedMessage)
	{
		ResultVO result = new ResultVO();
		String message ="";
		
		message = (String) errorCode.get(code);
		if(message == null || message.equals("") == true){
			code = UNKNOWN_ERROR;
			message = (String) errorCode.get(code);
		}
		
		if(addedMessage != null && addedMessage.equals("") == false){
			message += " : " + addedMessage;
		}
		
		result.setResultCode(code);
		result.setResultMessage(message);
		return result;
	}
}
