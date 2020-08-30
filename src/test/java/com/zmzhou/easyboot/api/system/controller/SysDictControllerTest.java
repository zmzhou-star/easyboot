package com.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zmzhou.easyboot.EasybootApplicationTests;
import com.zmzhou.easyboot.api.system.entity.SysDict;
import com.zmzhou.easyboot.framework.page.ApiResult;

/**
 * The type Sys dict controller test.
 */
class SysDictControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private SysDictController controller;
	
	/**
	 * Gets dicts.
	 */
	@Test
	void getDicts() {
		ApiResult<SysDict> res = controller.getDicts("sys_user_sex");
		Assertions.assertNotNull(res);
	}
}
