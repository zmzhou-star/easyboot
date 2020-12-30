package com.github.zmzhou.easyboot.framework.security.service;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.service.TokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义权限评估器
 * @author zmzhou
 * @version 1.0
 * @title EasyBootPermissionEvaluator
 * @date 2020/12/9 17:18
 */
@Slf4j
@Service("ebpe")
public class EasyBootPermissionEvaluator {
	@Resource
	private TokenService tokenService;

	/**
	 * 判断是否拥有菜单权限
	 * @param permissions 菜单权限列表
	 * @return 是否拥有菜单权限
	 * @author zmzhou
	 * @date 2020/12/9 17:27
	 */
	public boolean hasPermission(String permissions) {
		LoginUser loginUser = tokenService.getLoginUser(SpringUtils.getRequest());
		// 菜单权限为空
		if (null == loginUser || CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		log.debug("菜单权限判断：{} {}", permissions, loginUser.getUsername());
		// 获取用户菜单权限
		Set<String> permissionsSet = loginUser.getPermissions();
		// 包含所有权限
		if (permissionsSet.contains(Constants.ALL_PERMISSION)) {
			return true;
		}
		if (StringUtils.isNotBlank(permissions)) {
			for (String permission : permissions.split(Constants.COMMA)) {
				// 包含指定权限
				if (StringUtils.isNotBlank(permission) && permissionsSet.contains(StringUtils.trim(permission))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否拥有角色权限
	 * @param roles 角色列表
	 * @return 是否拥有角色权限
	 * @author zmzhou
	 * @date 2020/12/9 17:31
	 */
	public boolean hasRole(String roles) {
		LoginUser loginUser = tokenService.getLoginUser(SpringUtils.getRequest());
		// 角色权限为空
		if (null == loginUser || CollectionUtils.isEmpty(loginUser.getRoles())) {
			return false;
		}
		log.debug("角色权限判断：{} {}", roles, loginUser.getUsername());
		// admin拥有所有角色权限
		if (loginUser.getUser().isAdmin()) {
			return true;
		}
		if (StringUtils.isNotBlank(roles)) {
			// 获取用户角色权限
			Set<String> roleSet = loginUser.getRoles();
			for (String role : roles.split(Constants.COMMA)) {
				// 包含指定角色
				if (StringUtils.isNotBlank(role) && roleSet.contains(StringUtils.trim(role))) {
					return true;
				}
			}
		}
		return false;
	}
}
