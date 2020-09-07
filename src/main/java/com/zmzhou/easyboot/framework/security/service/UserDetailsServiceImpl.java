package com.zmzhou.easyboot.framework.security.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.api.system.service.MenuService;
import com.zmzhou.easyboot.api.system.service.UserService;
import com.zmzhou.easyboot.common.ErrorCode;
import com.zmzhou.easyboot.common.enums.UserStatus;
import com.zmzhou.easyboot.common.exception.BaseException;
import com.zmzhou.easyboot.framework.security.LoginUser;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zmzhou
 * @title UserDetailsServiceImpl
 * @Description 用户验证处理
 * @Date 2020/07/20 17:24
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	/**
	 * 用户登录验证
	 * @param username 用户名
	 * @return UserDetails
	 * @author zmzhou
	 * @date 2020/07/21 11:04
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据username获取用户信息
		SysUser user = userService.getUser(username);
		if (StringUtils.isBlank(user.getUsername())) {
			log.info("登录用户：{} 不存在.", username);
			throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
		} else if (UserStatus.DELETED.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被删除.", username);
			throw new BaseException(ErrorCode.USER_DELETED, username);
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被停用.", username);
			throw new BaseException(ErrorCode.USER_DISABLE, username);
		}
		// 更新用户登录时间和登录在线状态
		userService.updateLoginTime(user.getId());
		return new LoginUser(user, menuService.getMenuPermission(user));
	}
	
}
