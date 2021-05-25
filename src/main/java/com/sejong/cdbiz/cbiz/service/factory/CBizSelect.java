/**
 * 
 */
package com.sejong.cdbiz.cbiz.service.factory;

import org.springframework.stereotype.Service;

import com.sejong.cdbiz.common.DmlInterface;
import com.sejong.cdbiz.service.DmlCommonService;

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
public class CBizSelect extends DmlCommonService implements DmlInterface {

	@Override
	public NBIZResponseBody mainBehavior(Object ment) {
		NBIZResponseBody responseBody = new NBIZResponseBody();
		return responseBody;
	}

}
