package com.github.zmzhou.easyboot.api.common.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title EmailCodeVo
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/29 17:18
 */
@Data
@ApiModel(description = "邮箱验证码vo类")
public class EmailCodeVo implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -5309687732541558329L;
	/** 用户名 */
	@NotBlank(message = "用户名不能为空")
	@ApiModelProperty(value = "用户名")
	private String username;
	/** 用户邮箱 */
	@Email(message = "邮箱格式不正确")
	@ApiModelProperty(value = "用户邮箱")
	private String email;
	/** 邮箱验证码 */
	@ApiModelProperty(value = "邮箱验证码")
	private String code;
	/**
	 * 邮箱验证码唯一标识
	 */
	@ApiModelProperty(value = "邮箱验证码唯一标识")
	private String uuid = "";
}
