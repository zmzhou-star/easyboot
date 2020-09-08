package com.github.zmzhou.easyboot.api.system.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginBody;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

/**
 *  @title LoginService
 *  @Description 描述
 *  @author zmzhou
 *  @Date 2020/07/21 16:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private TokenService tokenService;
	@Resource
	private AuthenticationManager authenticationManager;
	
	/**
	 * 登录验证
	 *
	 * @param loginBody 用户名密码
	 * @return
	 * @author zmzhou
	 * @date 2020/07/21 14:05
	 */
	public String login(LoginBody loginBody) {
		// 验证码key
		String captchaKey = Constants.CAPTCHA_CODE_KEY + loginBody.getUuid();
		// 从redis中获取验证码
		String captcha = redisUtils.get(captchaKey);
		if (StringUtils.isBlank(captcha)) {
			throw new BaseException(ErrorCode.CAPTCHA_EXPIRE);
		}
		// 删除验证码
		redisUtils.delete(captchaKey);
		if (!loginBody.getCode().equalsIgnoreCase(captcha)) {
			throw new BaseException(ErrorCode.CAPTCHA_ERROR);
		}
		// 用户验证
		Authentication authentication;
		try {
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginBody.getUsername(), loginBody.getPassword()));
		} catch (BadCredentialsException e) {
			// 密码错误
			throw new BaseException(ErrorCode.PASSWD_ERROR);
		} catch (AuthenticationException e) {
			if (e.getCause() instanceof BaseException){
				throw (BaseException) e.getCause();
			} else {
				throw new BaseException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
		}
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		// 生成token
		return tokenService.createToken(loginUser);
	}
}
