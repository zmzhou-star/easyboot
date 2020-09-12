package com.github.zmzhou.easyboot.framework.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zmzhou
 * @title JwtAuthTokenFilter
 * @Description token认证过滤器(验证token是否有效)
 * @Date 2020/07/21 11:48
 */
@Slf4j
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private TokenService tokenService;
	/**
	 * 验证token是否有效
	 * @param request  HttpServletRequest
	 * @param response  HttpServletResponse
	 * @param chain  FilterChain
	 * @author zmzhou
	 * @date 2020/08/26 16:31
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// 获取用户身份信息
		LoginUser loginUser = tokenService.getLoginUser(request);
		// 有身份信息和认证信息，认证通过
		if (null != loginUser && null == SecurityUtils.getAuthentication()) {
			log.info("用户：{} ，tokenId：[{}] 认证通过", loginUser.getUsername(), loginUser.getToken());
			// 验证令牌有效期，相差不足20分钟，自动刷新缓存
			tokenService.verifyToken(loginUser);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					loginUser, null, loginUser.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		chain.doFilter(request, response);
	}
}
