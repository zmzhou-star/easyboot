package com.github.zmzhou.easyboot.api.monitor.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.monitor.vo.SysLoginLogParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * @title SysLoginLogControllerTest
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/15 23:17
 */
class SysLoginLogControllerTest extends EasybootApplicationTests {
	/**
	 * The Param.
	 */
	SysLoginLogParams param = new SysLoginLogParams();

	/**
	 * Sets up.
	 */
	@BeforeEach
	void setUp() {
		param.setPageNum(1);
		param.setPageSize(10);
		param.setExcelName("LoginLog");
	}
	/**
	 * The Controller.
	 */
	@Autowired
	private SysLoginLogController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(null);
		Assertions.assertNotNull(res);
		res = controller.list(param);
		Assertions.assertNotNull(res);
	}

	/**
	 * Delete.
	 */
	@Test
	void delete() {
		ApiResult<Object> res = controller.delete(new Long[]{1L});
		Assertions.assertNotNull(res);
	}

	/**
	 * Clean.
	 */
	@Test
	void clean() {
		ApiResult<Object> res = controller.clean();
		Assertions.assertNotNull(res);
	}

	/**
	 * Export.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void export() throws InterruptedException {
		ApiResult<String> res = controller.export(param);
		Assertions.assertNotNull(res);
	}
}
