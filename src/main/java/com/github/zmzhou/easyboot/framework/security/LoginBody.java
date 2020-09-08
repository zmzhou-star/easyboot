package com.github.zmzhou.easyboot.framework.security;

import java.io.Serializable;

import lombok.Data;

/**
 * @author zmzhou
 * @title LoginBody
 * @Description 用户登录对象
 * @Date 2020/07/03 17:08
 */
@Data
public class LoginBody implements Serializable {
    private static final long serialVersionUID = 4580367148177497259L;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";
}
