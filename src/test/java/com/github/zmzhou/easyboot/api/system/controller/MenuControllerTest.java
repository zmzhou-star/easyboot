package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * The type Menu controller test.
 */
class MenuControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private MenuController controller;
	
	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(params);
		Assertions.assertNotNull(res);
	}
}
