package com.github.zmzhou.easyboot.common;

/**
 * The enum Error code.
 *
 * @author zmzhou
 * @title ErrorCode
 * @Description 错误码
 * @Date 2020/07/08 13:56
 */
public enum ErrorCode {
	/** 参数为空 */
	PARAM_ISNULL(1000, "参数为空"),
	/** 参数错误 */
	PARAM_ERROR(1001, "参数错误"),
	/** 用户已经存在 */
	USER_EXISTS(1002, "用户已经存在"),
	/** 用户不存在 */
	USER_NOT_EXISTS(1003, "用户不存在"),
	/** 验证码已过期 */
	CAPTCHA_EXPIRE(1004, "验证码已过期"),
	/** 验证码错误 */
	CAPTCHA_ERROR(1005, "验证码错误"),
	/** 用户已被删除 */
	USER_DELETED(1006, "对不起，您的账号[0]已被删除"),
	/** 用户已停用 */
	USER_DISABLE(1007, "对不起，您的账号[0]已停用"),
	/** 密码错误 */
	PASSWD_ERROR(1008, "密码错误"),
	/** 没有数据 */
	NO_DATA(1009, "没有数据"),
	/** 没有识别到文件 */
	NO_FILE(1010, "没有识别到文件"),
	/** 用户[0]无角色 */
	USER_HAS_NO_ROLE(1011, "用户[0]无角色"),
	/** 用户[0]无权限 */
	USER_HAS_NO_PERMISSIONS(1012, "用户[0]无权限"),
    /** 登录失败 */
    LOGIN_ERROR(1013, "用户名[0]或密码错误")
	;
	/** 错误码 */
	private final int code;
	/** 错误信息 */
	private final String msg;

	/**
	 * @description 构造器
	 * @author zmzhou
	 * @date 2020/9/6 15:59
	 */
	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
}
