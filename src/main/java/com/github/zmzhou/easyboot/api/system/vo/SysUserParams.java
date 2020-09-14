package com.github.zmzhou.easyboot.api.system.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理请求参数vo类
 * @title SysUserParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 22:33
 */
@Data
@ApiModel(description = "用户管理请求参数vo类")
public class SysUserParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = 1682752116245400536L;

	/** 用户名称 */
	@ApiModelProperty(value = "用户名称")
	private String username;
	/** 用户密码 */
	@ApiModelProperty(value = "用户密码")
	private String password;
	/** 手机号码 */
	@ApiModelProperty(value = "手机号码")
	private String tel;
}
