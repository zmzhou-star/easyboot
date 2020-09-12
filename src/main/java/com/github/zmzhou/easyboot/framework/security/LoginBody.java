package com.github.zmzhou.easyboot.framework.security;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zmzhou
 * @title LoginBody
 * @Description 用户登录对象
 * @Date 2020/07/03 17:08
 */
@Data
@ApiModel(description = "用户登录参数信息")
public class LoginBody implements Serializable {
    private static final long serialVersionUID = 4580367148177497259L;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "验证码唯一标识")
    private String uuid = "";
}
