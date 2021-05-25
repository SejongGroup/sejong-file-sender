/**
 * 
 */
package com.sejong.filecontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FileName	: CommonController.java
 * @Project		: windowTolinux
 * @Date		: 2021. 5. 25.
 * @Author		: JH.KIM
 * @Description : 
 * ==========================================================
 * DATE				AUTHOR				NOTE
 * ==========================================================
 *
 */
@Controller
@RequestMapping(value="/")
public class CommonController {
	
	@RequestMapping(value="")
	public void home() {
		System.out.println("hello wolrd");
	}
}
