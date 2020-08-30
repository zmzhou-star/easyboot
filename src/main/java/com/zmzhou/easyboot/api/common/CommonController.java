package com.zmzhou.easyboot.api.common;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.captcha.SpecCaptcha;
import com.zmzhou.easyboot.common.Constants;
import com.zmzhou.easyboot.framework.page.ApiResult;
import com.zmzhou.easyboot.framework.redis.RedisUtils;
import com.zmzhou.easyboot.framework.security.LoginBody;
import com.zmzhou.easyboot.framework.web.BaseController;

import cn.hutool.core.util.IdUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zmzhou
 * @title CaptchaController
 * @Description 生成验证码
 * @Date 2020/07/08 18:04
 */
@Slf4j
@RestController
@RequestMapping("common")
public class CommonController extends BaseController {
	@Autowired
	private RedisUtils redisUtils;
	/**
	 * 验证码 宽度
	 */
	@Value("${captcha.width}")
	private Integer width;
	/**
	 * 验证码 高度
	 */
	@Value("${captcha.height}")
	private Integer height;
	/**
	 * 验证码 运算位数
	 */
	@Value("${captcha.digit}")
	private Integer digit;
	/**
	 * 验证码有效期（分钟）
	 */
	@Value("${captcha.expiration}")
	private Integer expiration;
	
	/**
	 * 生成验证码
	 * @return ApiResult<LoginBody>
	 * @author zmzhou
	 * @date 2020/08/29 16:09
	 */
	@GetMapping("/captcha")
	public ApiResult<LoginBody> captcha() {
		// png类型 https://gitee.com/whvse/EasyCaptcha
		SpecCaptcha captcha = new SpecCaptcha(width, height, digit);
		// 获取运算的结果
		String result = captcha.text();
		String uuid = IdUtil.simpleUUID();
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		redisUtils.set(verifyKey, result, expiration, TimeUnit.MINUTES);
		LoginBody body = new LoginBody();
		body.setUuid(uuid);
		body.setCode(captcha.toBase64());
		log.info("生成验证码：{}", result);
		return ok(body);
	}
	
}
