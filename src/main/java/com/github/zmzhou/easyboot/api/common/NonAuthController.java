package com.github.zmzhou.easyboot.api.common;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.common.vo.EmailCodeVo;
import com.github.zmzhou.easyboot.api.system.vo.UserInfo;
import com.github.zmzhou.easyboot.common.utils.MailUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 未经登录授权的请求控制层，包括忘记密码，用户注册等
 * @title NonAuthController
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/29 16:53
 */
@Api(tags = {"未经登录授权的请求"})
@Validated
@RequestMapping("/nonAuth")
@RestController
public class NonAuthController extends BaseController {
	@Resource
	private MailUtils mailUtils;

	/**
	 * 获取邮箱验证码
	 * @param param 用户名和邮箱
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/12/29 17:17
	 */
	@ApiOperation(value = "获取邮箱验证码")
	@GetMapping("getEmailCode")
	public ApiResult<UserInfo> getEmailCode(EmailCodeVo param) {
		System.out.println(param);
		mailUtils.sendMail();
		return ok(param);
	}
}
