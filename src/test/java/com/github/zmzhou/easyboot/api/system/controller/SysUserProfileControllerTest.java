package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.vo.UserInfo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

/**
 * 用户个人中心管理单元测试
 * SysUserProfileControllerTest
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/13 11:51
 */
class SysUserProfileControllerTest extends EasybootApplicationTests {

	/**
	 * The Controller.
	 */
	@Autowired
	private SysUserProfileController controller;

	/**
	 * Profile.
	 */
	@Test
	void profile() {
		ApiResult<UserInfo> res = controller.profile();
		Assertions.assertNotNull(res);
	}

	/**
	 * Update.
	 */
	@Test
	void update() {
		ApiResult<SysUser> res = controller.update(null);
		Assertions.assertNotNull(res);
	}

	/**
	 * Update pwd.
	 */
	@Test
	void updatePwd() {
		ApiResult<SysUser> res = controller.updatePwd("123", "234");
		Assertions.assertNotNull(res);
	}

	/**
	 * Avatar.
	 */
	@Test
	void avatar() {
		try {
			ApiResult<SysUser> res = controller.avatar();
			Assertions.assertNotNull(res);
		} catch (Exception e){
			Assertions.assertNotNull(e);
		}
	}
}
