package com.github.zmzhou.easyboot.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.security.filter.JwtAuthTokenFilter;
import com.github.zmzhou.easyboot.framework.security.handler.AuthenticationEntryPointImpl;
import com.github.zmzhou.easyboot.framework.security.handler.LogoutSuccessHandlerImpl;
import com.github.zmzhou.easyboot.framework.security.service.UserDetailsServiceImpl;

/**
 *  @title SecurityConfig
 *  @Description spring security配置
 *  @author zmzhou
 *  @Date 2020/07/21 14:33
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * 自定义用户认证逻辑
	 */
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	/**
	 * 认证失败处理类
	 */
	@Autowired
	private AuthenticationEntryPointImpl unauthorizedHandler;
	/**
	 * 退出处理类
	 */
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;
	/**
	 * token认证过滤器
	 */
	@Autowired
	private JwtAuthTokenFilter jwtAuthTokenFilter;
	
	/** 配置不需要认证的接口 */
	@Value("${token.antMatchers}")
	private String antMatchers;
	/**
	 * 方法描述
	 * @param httpSecurity HttpSecurity
	 * @author zmzhou
	 * @date 2020/07/21 14:37
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// 开启跨域
				.cors()
				.and()
				// CRSF禁用，因为不使用session
				.csrf().disable()
				// 认证失败处理类
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// 基于token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 过滤请求
				.authorizeRequests()
				// 用户可以任意访问
				.antMatchers(
						HttpMethod.GET,
						"**.html",
						"/**.htm",
						"/**.css",
						"/**.js",
						"/**.jpg",
						"/**.png",
						"/**.gif"
				).anonymous()
				// 允许匿名访问
				.antMatchers(antMatchers.split(Constants.COMMA)).anonymous()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				.and()
				// 配置登出地址
				.logout()
				.logoutUrl("/logout")
				// 配置用户登出自定义处理类
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.headers().frameOptions().disable();
		// 添加JWT filter
		httpSecurity.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// 禁用缓存
		httpSecurity.headers().cacheControl();
	}
	
	/**
	 * 强散列哈希加密实现
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 身份认证接口
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	/**
	 * 解决 无法直接注入 AuthenticationManager
	 *
	 * @return AuthenticationManager
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
