package com.github.zmzhou.easyboot.api.system.vo;

import java.io.Serializable;
import java.util.Set;

import com.github.zmzhou.easyboot.api.system.entity.SysUser;

import lombok.Builder;
import lombok.Data;

/**
 * UserInfo
 * 用户信息
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/7 22:20
 */
@Data
@Builder
public class UserInfo implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -5596543324343317337L;
	/** 用户个人信息 */
	private SysUser user;
	/** 用户角色信息 */
	private Set<String> roles;
	/** 用户菜单权限信息 */
	private Set<String> permissions;
}
