package com.github.zmzhou.easyboot.api.common;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.common.service.NonAuthService;
import com.github.zmzhou.easyboot.api.common.vo.EmailCodeVo;
import com.github.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 允许未经登录授权的请求控制层，包括忘记密码，用户注册等
 * @title NonAuthController
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/29 16:53
 */
@Api(tags = {"允许未经登录授权的请求"})
@Validated
@RequestMapping("/nonAuth")
@RestController
public class NonAuthController extends BaseController {
	@Resource
	private NonAuthService service;

	/**
	 * 获取邮箱验证码
	 * @param param 用户名和邮箱
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/12/29 17:17
	 */
	@ApiOperation(value = "获取邮箱验证码")
	@GetMapping("getEmailCode")
	public ApiResult<String> getEmailCode(EmailCodeVo param) {
		return ok(service.sendMail(param));
	}

	/**
	 * 验证用户邮箱验证码
	 * @param param 用户邮箱验证码信息
	 * @return Boolean 验证通过
	 * @author zmzhou
	 * @date 2020/12/31 21:10
	 */
	@ApiOperation(value = "验证用户邮箱验证码")
	@PostMapping("checkEmailCode")
	public ApiResult<Boolean> checkEmailCode(@RequestBody EmailCodeVo param) {
		return ok(service.checkEmailCode(param));
	}

	/**
	 * 重置密码
	 * @param uuid 重置密码会话id
	 * @param password 新密码
	 * @author zmzhou
	 * @date 2020/12/31 21:30
	 */
	@PutMapping("resetPwd")
	@ApiOperation(value = "重置密码")
	public ApiResult<Boolean> resetPwd(@ApiParam(name = "uuid", value = "重置密码会话id", required = true) String uuid,
						   @ApiParam(name = "password", value = "新密码", required = true) String password) {
		return ok(service.resetPwd(uuid, password));
	}

	/**
	 * 注册用户获取邮箱验证码
	 * @param param 注册用户信息
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/12/29 17:17
	 */
	@ApiOperation(value = "注册用户获取邮箱验证码")
	@GetMapping("getRegisterEmailCode")
	public ApiResult<String> getRegisterEmailCode(SysUserVo param) {
		return ok(service.sendRegisterMail(param));
	}

	/**
	 * 注册账号
	 * @param userVo 注册信息
	 * @return 注册成功
	 * @author zmzhou
	 * @date 2021/1/1 20:21
	 */
	@ApiOperation(value = "注册账号")
	@PostMapping("register")
	public ApiResult<Boolean> register(@RequestBody SysUserVo userVo) {
		return ok(service.register(userVo));
	}
}
