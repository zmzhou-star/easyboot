package com.github.zmzhou.easyboot.framework.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.service.TokenService;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

import lombok.extern.slf4j.Slf4j;

/**
 *  @title LogoutSuccessHandlerImpl
 *  @Description 退出成功处理类
 *  @author zmzhou
 *  @Date 2020/07/21 11:39
 */
@Slf4j
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserService userService;

	/**
	 * 退出处理
	 * @param request  HttpServletRequest
	 * @param response  HttpServletResponse
	 * @param auth Authentication
	 * @author zmzhou
	 * @date 2020/08/26 16:34
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		// 获取用户身份信息
		LoginUser loginUser = tokenService.getLoginUser(request);
		if (null != loginUser) {
			SysUser user = loginUser.getUser();
			int res = userService.updateOnline(user.getId(), "0");
			log.debug("更新用户：{}登录状态：{}成功", user.getUsername(), res);
			log.info("用户：{} 退出登录，删除token：{}", loginUser.getUsername(), loginUser.getToken());
			// 删除用户缓存记录
			tokenService.delLoginUser(loginUser.getToken());
		}
		SpringUtils.response(response, new ApiResult<>().info("退出成功"));
	}
}
