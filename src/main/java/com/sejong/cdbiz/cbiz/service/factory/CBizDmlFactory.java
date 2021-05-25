/**
 * 
 */
package com.sejong.cdbiz.cbiz.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.common.DmlInterface;

/**
 * @FileName	: CBizDmlFacktory.java
 * @Project		: cdBiz
 * @Date		: 2021. 4. 26.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@Component
public class CBizDmlFactory {
	
	@Autowired
	public CBizSelect select;
	
	@Autowired
	public CBizInsert insert;
	
	@Autowired
	public CBizDelete delete;
	
	public DmlInterface createDml(int type) {
		DmlInterface dmlInterface = null;

		if (type == Constants.DML_SELECT) {
			dmlInterface = select;
		} else if (type == Constants.DML_INSERT) {
			dmlInterface = insert;
		} else {
			dmlInterface = delete;
		}
		return dmlInterface;
	}
	
}
