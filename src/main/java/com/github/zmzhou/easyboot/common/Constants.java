package com.github.zmzhou.easyboot.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *  通用常量信息
 *  @title Constants
 *  @author zmzhou
 *  Date 2020/07/03 15:24
 */
public final class Constants {
    /** 系统默认编码 */
    public static final Charset CHARSETS = StandardCharsets.UTF_8;
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /** 参数管理 cache key */
    public static final String SYS_CONFIG_KEY = "sys_config:";
    /** 数据字典 cache key */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /** 字符 0 */
    public static final String ZERO = "0";
    /** 字符 1 */
    public static final String ONE = "1";
    /** 是 */
    public static final String YES = "Y";
    /** 数字 3 */
    public static final int INT_THREE = 3;
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

    /** 逗号 , */
    public static final String COMMA = ",";
    /** 点 . */
    public static final String DOT = ".";
    /** 分号 ; */
    public static final String SEMICOLON = ";";
    /** 下划线 _ */
    public static final String UNDERLINE = "_";
    /** 文件分隔符 / */
    public static final String SEPARATOR = "/";
    /** 左括号( */
    public static final String LEFT_PARENTHESIS = "(";
    /** 右括号) */
    public static final String RIGHT_PARENTHESIS = ")";
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
    /** tableName */
	public static final String TABLE_NAME = "tableName";
    /** createTime */
	public static final String CREATE_TIME = "createTime";
    /** 文件下载缓冲区大小 */
    public static final int BUFFER_SIZE = 2 * 1024 * 1024;
    
    /** 所有权限标识 */
    public static final String ALL_PERMISSION = "*:*:*";

    /** 定时任务执行类 */
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";
    /** 执行目标key */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";
    /** 立即触发执行 */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";
    /** 触发一次执行 */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";
    /** 不触发立即执行 */
    public static final String MISFIRE_DO_NOTHING = "3";
    /** 定时任务暂停 */
    public static final String PAUSE = "0";
    
	/**
     * 私有构造器
     * @author zmzhou
     * @date 2020/08/29 14:12
     */
    private Constants() {
    }
}
