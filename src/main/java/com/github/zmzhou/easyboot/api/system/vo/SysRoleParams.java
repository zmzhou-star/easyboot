package com.github.zmzhou.easyboot.api.system.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色管理请求参数vo类
 * @title SysRoleParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 22:33
 */
@Data
@ApiModel(description = "角色管理请求参数vo类")
public class SysRoleParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = -8923861500987964578L;

	/**
	 * The Role code.
	 */
	@ApiModelProperty(value = "角色编码")
	private String roleCode;
	/**
	 * The Role name.
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;
}
