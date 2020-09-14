package com.github.zmzhou.easyboot.common;

/**
 *  @title Constants
 *  @Description 通用常量信息
 *  @author zmzhou
 *  @Date 2020/07/03 15:24
 */
public final class Constants {
    /** 系统默认密码 */
    public static final String DEFAULT_PASSWORD = "Zmzhou.123";
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /** 字符 0 */
    public static final String ZERO = "0";
    /** 字符 1 */
    public static final String ONE = "1";
    /** http请求  */
    public static final String HTTP = "http://";
    /** https请求 */
    public static final String HTTPS = "https://";
    /**
     * 令牌
     */
    public static final String TOKEN = "token";
    /** Authorization */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户头像
     */
    public static final String AVATAR = "avatar";

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
    /** 点 . */
    public static final String DOT = ".";
    /** 下划线 _ */
    public static final String UNDERLINE = "_";
    /** 文件分隔符 / */
    public static final String SEPARATOR = "/";
    /** 百分号 % */
    public static final String PERCENT_SIGN = "%";
    /** username */
    public static final String USERNAME = "username";
    /** sortBy */
    public static final String SORT_BY = "sortBy";
    /** status */
    public static final String STATUS = "status";
    /** loginTime */
    public static final String LOGIN_TIME = "loginTime";
    /** 文件下载缓冲区大小 */
    public static final int BUFFER_SIZE = 2 * 1024 * 1024;

    /**
     * 私有构造器
     * @author zmzhou
     * @date 2020/08/29 14:12
     */
    private Constants() {
    }
}
