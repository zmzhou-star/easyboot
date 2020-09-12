package com.github.zmzhou.easyboot.api.system.vo;

import java.io.Serializable;
import java.util.Set;

import com.github.zmzhou.easyboot.api.system.entity.SysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -5596543324343317337L;
	/** 用户个人信息 */
	@ApiModelProperty(value = "用户个人信息实体")
	private SysUser user;
	@ApiModelProperty(value = "用户角色集合")
	/** 用户角色信息 */
	private Set<String> roles;
	/** 用户菜单权限信息 */
	@ApiModelProperty(value = "用户菜单权限集合")
	private Set<String> permissions;
}
