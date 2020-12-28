package com.github.zmzhou.easyboot.api.common;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.common.vo.CardVo;
import com.github.zmzhou.easyboot.api.monitor.entity.SysLoginLog;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.vo.PageParams;

/**
 * The type Dashboard controller test.
 *
 * @author zmzhou
 * @version 1.0
 * @title DashboardControllerTest
 * @date 2020 /12/28 15:09
 */
class DashboardControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private DashboardController controller;
	/**
	 * Card stat.
	 */
	@Test
	void cardStat() {
		ApiResult<CardVo> res = controller.cardStat();
		Assertions.assertNotNull(res);
	}

	/**
	 * User login stat.
	 */
	@Test
	void userLoginStat() {
		ApiResult<List<SysLoginLog>> res = controller.userLoginStat(new PageParams());
		Assertions.assertNotNull(res);
	}
}
