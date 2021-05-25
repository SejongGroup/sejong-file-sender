/**
 * 
 */
package com.sejong.cdbiz.common;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sejong.api.nbiz.model.NBIZResponseBody;


/**
 * @FileName	: DmlInterface.java
 * @Project		: dBiz
 * @Date		: 2021. 4. 21.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
public interface DmlInterface extends DmlParrents {
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=false, rollbackFor=Exception.class)
	NBIZResponseBody mainBehavior(Object ment);
}
