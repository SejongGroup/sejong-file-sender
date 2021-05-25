/**
 * 
 */
package com.sejong.cdbiz.dbiz.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sejong.cdbiz.common.Constants;
import com.sejong.cdbiz.common.DmlInterface;

/**
 * @FileName	: CRUDFactory.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 21.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@Component
public class DBizDmlFactory {
	
	@Autowired
	private DBizSelect dmlSelect;
	@Autowired
	private DBizInsert dmlInsert;
	@Autowired
	private DBizDelete dmlDelete;
	
	public DmlInterface createDml(int type) {
		DmlInterface dmlInterface = null;

		if (type == Constants.DML_SELECT) {
			dmlInterface = dmlSelect;
		} else if (type == Constants.DML_INSERT) {
			dmlInterface = dmlInsert;
		} else {
			dmlInterface = dmlDelete;
		}
		return dmlInterface;
	}
}
