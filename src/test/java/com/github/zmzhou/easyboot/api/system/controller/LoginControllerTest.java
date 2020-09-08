package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.common.CommonController;
import com.github.zmzhou.easyboot.api.system.vo.RouterVo;
import com.github.zmzhou.easyboot.api.system.vo.UserInfo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginBody;

/**
 * The type Login controller test.
 */
class LoginControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private LoginController controller;
	/**
	 * The Common controller.
	 */
	@Autowired
	private CommonController commonController;
	
	@Autowired
	private RedisUtils redisUtils;
	/**
	 * The Mock mvc.
	 */
	private MockMvc mockMvc;

	/**
	 * Sets .
	 */
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	/**
	 * Login.
	 */
	@Test
	void login() throws Exception {
		ApiResult<LoginBody> captcha = commonController.captcha();
		LoginBody loginBody = captcha.getData();
		ApiResult<JSONObject> res;
		try {
			controller.login(loginBody);
		} catch (BaseException e) {
			Assertions.assertEquals(ErrorCode.CAPTCHA_ERROR.getMsg(), e.getErrMsg());
		}
		// 重新生成验证码
		captcha = commonController.captcha();
		loginBody = captcha.getData();
		// 验证码key
		String captchaKey = Constants.CAPTCHA_CODE_KEY + loginBody.getUuid();
		// 从redis中获取验证码
		loginBody.setCode(redisUtils.get(captchaKey));
		loginBody.setUsername("admin");
		loginBody.setPassword(PD);
		res = controller.login(loginBody);
		Assertions.assertNotNull(res);
		try {
			// 验证码已过期
			loginBody.setCode("");
			loginBody.setUuid("");
			controller.login(loginBody);
		} catch (BaseException e) {
			Assertions.assertEquals(ErrorCode.CAPTCHA_EXPIRE.getMsg(), e.getErrMsg());
		}

		// 退出登录
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		log.info(mvcResult.getResponse().getContentAsString());
	}
	
	/**
	 * Gets user info.
	 */
	@Test
	void getUserInfo() {
		ApiResult<UserInfo> res = controller.getUserInfo();
		Assertions.assertNotNull(res);
	}
	
	/**
	 * Gets routers.
	 */
	@Test
	void getRouters() {
		ApiResult<RouterVo> res = controller.getRouters();
		Assertions.assertNotNull(res);
	}
}
