package com.github.zmzhou.easyboot.api.system.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.api.system.vo.SysMenuParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

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
		ApiResult<List<SysMenu>> res = controller.list(new SysMenuParams());
		Assertions.assertNotNull(res);
	}
}
