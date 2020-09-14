package com.github.zmzhou.easyboot.api.system.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.monitor.service.SysLoginLogService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginBody;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;

/**
 *  @title LoginService
 *  @Description 描述
 *  @author zmzhou
 *  @Date 2020/07/21 16:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginService {
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private TokenService tokenService;
	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private SysLoginLogService loginLogService;
	@Resource
	private UserService userService;
	
	/**
	 * 登录验证
	 *
	 * @param loginBody 用户名密码
	 * @return token
	 * @author zmzhou
	 * @date 2020/07/21 14:05
	 */
	public String login(LoginBody loginBody) {
		// 验证码key
		String captchaKey = Constants.CAPTCHA_CODE_KEY + loginBody.getUuid();
		// 从redis中获取验证码
		String captcha = redisUtils.get(captchaKey);
		String username = loginBody.getUsername();
		if (StringUtils.isBlank(captcha)) {
			// 保存登录失败日志
			loginLogService.saveFailLog(ErrorCode.CAPTCHA_EXPIRE.getMsg(), username);
			throw new BaseException(ErrorCode.CAPTCHA_EXPIRE);
		}
		// 删除验证码
		redisUtils.delete(captchaKey);
		if (!loginBody.getCode().equalsIgnoreCase(captcha)) {
			// 保存登录失败日志
			loginLogService.saveFailLog(ErrorCode.CAPTCHA_ERROR.getMsg(), username);
			throw new BaseException(ErrorCode.CAPTCHA_ERROR);
		}
		// 用户验证
		Authentication authentication;
		try {
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginBody.getUsername(), loginBody.getPassword()));
		} catch (BadCredentialsException e) {
			// 保存登录失败日志
			loginLogService.saveFailLog(ErrorCode.PASSWD_ERROR.getMsg(), username);
			// 密码错误
			throw new BaseException(ErrorCode.PASSWD_ERROR);
		} catch (AuthenticationException e) {
			if (e.getCause() instanceof BaseException){
				// 保存登录失败日志
				loginLogService.saveFailLog(((BaseException) e.getCause()).getErrMsg(), username);
				throw (BaseException) e.getCause();
			} else {
				// 保存登录失败日志
				loginLogService.saveFailLog(e.getMessage(), username);
				throw new BaseException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
		}
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		// 生成token
		String token = tokenService.createToken(loginUser);
		// 更新用户登录时间和登录在线状态
		userService.updateLoginTime(loginUser);
		// 保存登录成功日志
		loginLogService.saveSuccessLog(loginUser);
		return token;
	}
}
