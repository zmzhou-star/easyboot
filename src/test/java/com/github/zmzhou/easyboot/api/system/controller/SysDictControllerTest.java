package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.vo.SysDictTypeVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

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

	/**
	 * Option select.
	 */
	@Test
	void optionSelect() {
		ApiResult<SysDictTypeVo> res = controller.optionSelect();
		Assertions.assertNotNull(res);
	}
}
