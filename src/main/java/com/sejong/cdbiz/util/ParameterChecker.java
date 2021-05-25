package com.sejong.cdbiz.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SuppressWarnings("rawtypes")
public class ParameterChecker {
	private static Logger logger = LoggerFactory.getLogger(ParameterChecker.class);;
	
	public static String isNullStringInHashMap(HashMap hm){
		for(Iterator iter = hm.entrySet().iterator(); iter.hasNext();){
			Map.Entry entry = (Map.Entry)iter.next();
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();
			if(value == null || value.equals("") == true){
				return key;
			}
		}
		return null;
	}
	
	/**
	 * lengthHm에 최대길이 값이 등록된 요소의 길이만 확인한다.
	 * 원본의 내용이 null이거나 null string인 경우 최대 길이를 비교하지 않는다.(error를 return하지 않는다.)
	 * 원본의 내용이 null string이 아닌 경우에 최대 길이를 초과했는지만 확인하여 초과한 경우 해당 key의 이름을 return 한다.
	 * @param hm
	 * @param lengthHm
	 * @return
	 */
	public static String isOverMaxLengthStringInHashMap(HashMap hm, HashMap<String, Integer> lengthHm){
		for(Iterator iter = hm.entrySet().iterator(); iter.hasNext();){
			Map.Entry entry = (Map.Entry)iter.next();
			String key = (String)entry.getKey();
			
			Integer maxLength = lengthHm.get(key);
			if(maxLength == null || maxLength == 0){
				continue;
			}
			
			String value = (String)entry.getValue();
			if(value == null || value.isEmpty() == true){
				continue;
			}
			
			Integer length = value.length();
			if(length == null || length == 0){
				continue;
			}
			
			if(length > maxLength){
				return key;
			}
		}
		return null;
	}
	
	public static Boolean isValidValue(String value, Integer min, Integer max){
		Integer std = 0;
		if(value == null || value.isEmpty() == true){
			return false;
		}
		
		try{
			std = Integer.parseInt(value);
		}
		catch(NumberFormatException e){
			return false;
		}
		
		if(min <= std && std <= max){
			return true;
		}
		return false;
	}
	
	public static Boolean isValidLength(String value, Integer maxLength){
		if(value == null || value.isEmpty() == true){
			return false;
		}
		
		if(value.length() > maxLength){
			return false;
		}
		return true;
	}
	
	public static Boolean isYnType(String value){
		if(value == null){
			return false;
		}
		if(value.equals("Y") == true 
			|| value.equals("N") == true){
			return true;
		}
		return false;
	}
	

	public static Boolean isVaildPassword(String password){
		String reg = "^(?=([a-zA-Z]+[0-9]+[a-zA-Z0-9]*|[0-9]+[a-zA-Z]+[a-zA-Z0-9]*)$).{" + 10 +"," + 30 + "}";
		if(password == null || password.isEmpty() == true){
			logger.error("password is null");
			return false;
		}
		return Pattern.compile(reg).matcher(password).find();
	}
	
	public static Boolean isValidVnsNumber(String vnsNumber){
		String number = getOnlyDigitsString(vnsNumber);
		if(number == null || number.length() < 10 || number.length() > 12){
			logger.error("invalid vns number length  " + number.length());
			return false;
		}
		if(number.substring(0, 3).equals("050") == false){
			logger.error("invalid vns number prefix : " + number.substring(0,3));
			return false;
		}
		return true;
	}
	
	public static Boolean isValidCalledNumber(String phoneNumber){
		String number = getOnlyDigitsString(phoneNumber);
		if(number == null || number.length() < 3){
			logger.error("invalid called number length  " + number.length());
			return false;
		}
		if(number.substring(0, 3).equals("050") == true){
			logger.error("VNS number is not allowed for called number. number : " + number);
			return false;
		}		
		return isValidPhoneNumber(number);
	}
	
	public static Boolean isValidPhoneNumber(String phoneNumber){
		String number = getOnlyDigitsString(phoneNumber);
		if(number == null || number.length() <= 0){
			logger.error("invalid phoneNumber : " + phoneNumber);
			return false;
		}
		if(number.charAt(0) == '0'){
			if(number.charAt(1) == '1'){
				/*
				 * 01로 시작하는 번호는 핸드폰 번호(ex. 011-111-2222, 010-1111-2222)
				 * 10자리, 11자리 외의 핸드폰 번호는 잘못된 번호로 판단
				 */
				if(number.length() < 10 || number.length() > 12){
					logger.error("invalid cell phone number length. number : " + number + ", length : " + number.length());
					return false;
				}
				return true;
			}
			else if(number.charAt(1) == '7' || number.charAt(1) == '8'){
				/*
				 * 070으로 시작하는 인터넷 전화번호(11자리)
				 * 080으로 시작하는 대표번호(11자리)
				 */
				if(number.length() != 11){
					logger.error("invalid 070/080 number length. number : " + number + ", length : " + number.length());
					return false;
				}
				return true;
			}
			else{
				/*
				 * 02, 031 등으로 시작하는 집전화번호 (9~11자리)
				 */
				if(number.length() < 9 || number.length() > 12){
					logger.error("invalid PSTN phone number length. number : " + number + ", length : " + number.length());
					return false;
				}
				return true;
			}
		}
		else if(number.charAt(0) == '1'){
			/*
			 * 1로 시작하는 번호는 대표번호(ex. 1666-1111)
			 * 8자리 외의 대표번호는 잘못된 번호로 판단
			 */
			if(number.length() != 8){
				logger.error("invalid UAN number length. number : " + number + ", length : " + number.length());
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	/**
	 * SMS flag 0~9, *, #
	 * */
	public static Boolean isValidSmsFlag(String smsFlag){
		if(smsFlag == null || smsFlag.isEmpty() == true){
			logger.error("smsFlag is null");
			return false;
		}
		String reg = "^[0-9\\*\\#]${1}";
		return Pattern.compile(reg).matcher(smsFlag).find();
	}
	
	/* only digit String */
	public static String getOnlyDigitsString(String source){
		if(source == null || source.isEmpty()){
			return "";
		}
		return source.replaceAll("[^0-9]", "");
	}
}

