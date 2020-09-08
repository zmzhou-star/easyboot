package com.github.zmzhou.easyboot.framework.security.handler;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.github.zmzhou.easyboot.common.utils.ServletUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;

import lombok.extern.slf4j.Slf4j;

/**
 *  @title AuthenticationEntryPointImpl
 *  @Description 认证失败处理类
 *  @author zmzhou
 *  @Date 2020/07/21 11:13
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 8522168914881679515L;
	
	/**
	 * 认证失败处理
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param e AuthenticationException
	 * @author zmzhou
	 * @date 2020/07/21 11:20
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
		// 访问uri
		String uri = request.getRequestURI();
		log.error("访问：{} 认证失败", uri, e);
		int code = HttpStatus.UNAUTHORIZED.value();
		String msg = String.format("请求访问：{%s}，认证失败，无权访问资源", uri);
		// token已失效
		if (e instanceof InsufficientAuthenticationException) {
			// 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired
			code = 50014;
			msg = "token已失效，请重新登录！";
		}
		ServletUtils.response(response, new ApiResult<>().error(code, msg));
	}
}
