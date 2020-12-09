package com.github.zmzhou.easyboot.framework.security.service;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.service.MenuService;
import com.github.zmzhou.easyboot.api.system.service.RoleService;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.enums.UserStatus;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.security.LoginUser;

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
	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService;
	@Resource
	private RoleService roleService;

	/**
	 * 用户登录验证
	 * @param username 用户名
	 * @return UserDetails
	 * @author zmzhou
	 * @date 2020/07/21 11:04
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		// 根据username获取用户信息
		SysUser user = userService.getUser(username);
		if (StringUtils.isBlank(user.getUsername())) {
			log.info("登录用户：{} 不存在.", username);
			// 用户不存在
			throw new BaseException(ErrorCode.USER_NOT_EXISTS);
		} else if (UserStatus.DELETED.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被删除.", username);
			throw new BaseException(ErrorCode.USER_DELETED, username);
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("登录用户：{} 已被停用.", username);
			throw new BaseException(ErrorCode.USER_DISABLE, username);
		}
		// 用户角色列表
		Set<String> roles = roleService.getRolePermission(user);
		if (roles.isEmpty()) {
			throw new BaseException(ErrorCode.USER_HAS_NO_ROLE, username);
		}
		// 用户菜单权限列表
		Set<String> permissions = menuService.getMenuPermission(user);
		if (roles.isEmpty()) {
			throw new BaseException(ErrorCode.USER_HAS_NO_PERMISSIONS, username);
		}
		return new LoginUser(user, permissions, roles);
	}
	
}
