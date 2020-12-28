package com.github.zmzhou.easyboot.api.common.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.common.vo.CardVo;

/**
 * @title DashboardServiceTest
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/28 15:46
 */
class DashboardServiceTest extends EasybootApplicationTests {

	/**
	 * The Service.
	 */
	@Autowired
	private DashboardService service;

	@Test
	void cardStat() {
		CardVo res = service.cardStat();
		Assertions.assertNotNull(res);
	}
}
