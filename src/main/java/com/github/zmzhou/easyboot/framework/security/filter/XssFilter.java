package com.github.zmzhou.easyboot.framework.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 防御XSS(Cross Site Scripting)跨站脚本攻击过滤器
 * @author zmzhou
 * @version 1.0
 * date 2020/9/20 22:43
 */
public class XssFilter implements Filter {
	/**
	 * init
	 * The default implementation is a NO-OP.
	 * @param filterConfig The configuration information associated with the
	 *                     filter instance being initialised
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		// init
	}

	/**
	 * doFilter
	 * @param request  The request to process
	 * @param response The response associated with the request
	 * @param chain    Provides access to the next filter in the chain for this
	 *                 filter to pass the request and response to for further
	 *                 processing
	 * @throws IOException      if an I/O error occurs during this filter's
	 *                          processing of the request
	 * @throws ServletException if the processing fails for any other reason
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 过滤所有请求参数
		XssHttpServletRequestWrapper req = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(req, response);
	}

	/**
	 * destroy
	 * The default implementation is a NO-OP.
	 */
	@Override
	public void destroy() {
		// destroy
	}
}
