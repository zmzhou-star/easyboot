package com.github.zmzhou.easyboot.framework.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.config.CorsConfig;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

import lombok.extern.slf4j.Slf4j;

/**
 * CSRF - Cross-Site Request Forgery - 跨站请求伪造 过滤器
 * 验证 HTTP Referer 字段
 *
 * @author zmzhou
 * @version 1.0
 * @since 2021/5/11 16:45
 */
@Slf4j
public class MyCsrfFilter extends OncePerRequestFilter {
	/** 跨站资源共享配置 */
	private final CorsConfig corsConfig;

	public MyCsrfFilter(CorsConfig corsConfig) {
		this.corsConfig = corsConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {
		// 从 HTTP 头中取得 Referer 值
		final String referer = request.getHeader("Referer");
		// 判断 Referer 是否以 允许跨域请求的域名 开头
		final boolean anyMatch = Optional.ofNullable(referer).map(i ->
			Arrays.stream(corsConfig.getAllowedOriginPatterns())
				.anyMatch(origin -> ("*".equals(origin) || i.startsWith(origin)))).orElse(false);
		if (anyMatch) {
			chain.doFilter(request, response);
		} else {
			// 否则，抛出跨站请求伪造攻击异常
			SpringUtils.response(response, new ApiResult<>().error(50010,"Cross-Site Request Forgery Attack !!!"));
		}
	}
}
