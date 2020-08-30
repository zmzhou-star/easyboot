package com.zmzhou.easyboot.common.utils;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zmzhou.easyboot.common.exception.BaseException;
import com.zmzhou.easyboot.framework.security.LoginUser;

/**
 * @author zmzhou
 * @title SecurityUtils
 * @Description 安全服务工具类
 * @Date 2020/07/08 12:53
 */
public final class SecurityUtils {
	/**
	 * 私有构造器
	 * @author zmzhou
	 * @date 2020/08/29 14:18
	 */
	private SecurityUtils() {
	}
	/**
	 * 获取用户账户
	 **/
	public static String getUsername() {
		return getLoginUser().getUsername();
	}
	
	/**
	 * 获取用户
	 * @return LoginUser
	 * @author zmzhou
	 * @date 2020/08/26 16:37
	 */
	public static LoginUser getLoginUser() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new BaseException(HttpStatus.UNAUTHORIZED, "获取用户账户异常");
		}
	}
	
	/**
	 * 获取Authentication
	 * @return Authentication
	 * @author zmzhou
	 * @date 2020/08/26 16:37
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * 生成BCryptPasswordEncoder密码
	 *
	 * @param password 密码
	 * @return 加密字符串
	 */
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	/**
	 * 判断密码是否相同
	 *
	 * @param rawPassword     真实密码
	 * @param encodedPassword 加密后字符
	 * @return 结果
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	/**
	 * 是否为管理员
	 *
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}
}
