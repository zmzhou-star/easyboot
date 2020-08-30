package com.zmzhou.easyboot.common;

import io.jsonwebtoken.Claims;

/**
 *  @title Constants
 *  @Description 通用常量信息
 *  @author zmzhou
 *  @Date 2020/07/03 15:24
 */
public final class Constants {
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userId";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";
    /** 逗号 , */
    public static final String COMMA = ",";
    /** 百分号 % */
    public static final String PERCENT_SIGN = "%";
    /** username */
    public static final String USERNAME = "username";
    /** sortBy */
    public static final String SORT_BY = "sortBy";
    
    /**
     * 私有构造器
     * @author zmzhou
     * @date 2020/08/29 14:12
     */
    private Constants() {
    }
}
