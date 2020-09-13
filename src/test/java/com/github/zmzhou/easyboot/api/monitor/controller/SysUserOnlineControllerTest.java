package com.github.zmzhou.easyboot.api.monitor.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.monitor.vo.SysUserOnlineVo;
import com.github.zmzhou.easyboot.common.utils.IpUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

import cn.hutool.core.util.IdUtil;

/**
 * 在线用户监控单元测试
 * SysUserOnlineControllerTest
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/13 11:38
 */
class SysUserOnlineControllerTest extends EasybootApplicationTests {

	/**
	 * The Controller.
	 */
	@Autowired
	private SysUserOnlineController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<List<SysUserOnlineVo>> res = controller.list("", "");
		Assertions.assertNotNull(res);
		res = controller.list("admin", "");
		Assertions.assertNotNull(res);
		res = controller.list("", IpUtils.LOCALHOST);
		Assertions.assertNotNull(res);
		res = controller.list("admin", IpUtils.LOCALHOST);
		Assertions.assertNotNull(res);
	}

	/**
	 * Force logout.
	 */
	@Test
	void forceLogout() {
		ApiResult<String> res = controller.forceLogout("");
		Assertions.assertNotNull(res);
		res = controller.forceLogout(IdUtil.simpleUUID());
		Assertions.assertNotNull(res);
	}
}
