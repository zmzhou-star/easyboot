package com.github.zmzhou.easyboot.api.monitor.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskLogParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * The type Sys task log controller test.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysTaskLogControllerTest
 * @date 2020 /12/28 17:41
 */
class SysTaskLogControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private SysTaskLogController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(new SysTaskLogParams());
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Remove.
	 */
	@Test
	void remove() {
		ApiResult<Integer> res = controller.remove(new Long[]{1L});
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Clean.
	 */
	@Test
	void clean() {
		ApiResult<Integer> res = controller.clean();
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Export.
	 */
	@Test
	void export() {
		SysTaskLogParams param = new SysTaskLogParams();
		param.setExcelName("定时任务日志");
		try {
			ApiResult<String> res = controller.export(param);
			log.info("结果：{}", res);
			Assertions.assertNotNull(res);
		} catch (InterruptedException e) {
			Assertions.assertNotNull(e);
		}
	}
}
