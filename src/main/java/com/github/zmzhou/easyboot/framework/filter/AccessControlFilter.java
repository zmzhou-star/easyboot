package com.github.zmzhou.easyboot.framework.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.service.TokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * 权限控制过滤器
 * 生产环境下guest角色用户不允许删除，修改，新增数据
 *
 * @author zmzhou
 * @version 1.0
 * @since 2021/5/7 17:57
 */
@Slf4j
public class AccessControlFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {
		LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(request);
		final String method = request.getMethod();
		final String requestUrl = request.getRequestURI();
		// 没登陆之前 loginUser 为空，允许通过
		boolean operationNotAllowed = Optional.ofNullable(loginUser).map(user ->
			// 游客角色，不允许操作新增，修改，删除数据
			loginUser.getRoles().contains("guest")
				&& (method.equals(HttpMethod.PUT.name()) || method.equals(HttpMethod.DELETE.name())
				|| (method.equals(HttpMethod.POST.name()) && !requestUrl.endsWith("/list")))
		).orElse(false);
		if (operationNotAllowed) {
			log.warn("游客：{}，访问[{}] {}", loginUser.getUsername(), method, requestUrl);
			SpringUtils.response(response, new ApiResult<>().error(50010, "游客角色，不允许操作!!!"));
		} else {
			chain.doFilter(request, response);
		}
	}
}
