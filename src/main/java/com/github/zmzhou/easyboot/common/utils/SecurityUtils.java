package com.github.zmzhou.easyboot.common.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zmzhou
 * @title SecurityUtils
 * @Description 安全服务工具类
 * @Date 2020/07/08 12:53
 */
@Slf4j
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
		LoginUser loginUser = new LoginUser();
		try {
			loginUser = (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			log.error("获取登录用户信息", e);
			throw new BaseException(HttpStatus.UNAUTHORIZED, "获取登录用户信息异常");
		}
		return loginUser;
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
		BCryptPasswordEncoder passwordEncoder = ServletUtils.getBean(BCryptPasswordEncoder.class);
		// 默认密码
		if (StringUtils.isBlank(password)){
			return passwordEncoder.encode(Constants.DEFAULT_PD);
		}
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
		BCryptPasswordEncoder passwordEncoder = ServletUtils.getBean(BCryptPasswordEncoder.class);
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

	/**
	 * @description SHA-256加密
	 * @param  plaintext 明文
	 * @return 密文
	 * @author zmzhou
	 * @date 2020/9/2 22:27
	 */
	public static String sha256Encrypt(String plaintext) {
		MessageDigest messageDigest;
		String ciphertext = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(plaintext.getBytes(StandardCharsets.UTF_8));
			ciphertext = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			log.info("SHA-256加密异常", e);
		}
		return ciphertext;
	}

	/**
	 * @description 字节数组转十六进制字符串
	 * @param bytes 字节数组
	 * @return 十六进制字符串
	 * @author zmzhou
	 * @date 2020/9/2 22:25
	 */
	private static String byte2Hex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		String temp;
		for (byte aByte : bytes) {
			temp = Integer.toHexString(aByte & 0xFF);
			if (temp.length() == 1) {
				//1得到一位的进行补0操作
				builder.append(Constants.ZERO);
			}
			builder.append(temp);
		}
		return builder.toString();
	}

}
