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
	/** 验证码已过期 */
	CAPTCHA_EXPIRE(1003, "验证码已过期"),
	/** 验证码错误 */
	CAPTCHA_ERROR(1004, "验证码错误"),
	/** 用户已被删除 */
	USER_DELETED(1005, "对不起，您的账号[0]已被删除"),
	/** 用户已停用 */
	USER_DISABLE(1006, "对不起，您的账号[0]已停用"),
	/** 密码错误 */
	PASSWD_ERROR(1007, "密码错误"),
	/** 没有数据 */
	NO_DATA(1008, "没有数据"),
	/** 没有识别到文件 */
	NO_FILE(1009, "没有识别到文件")
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
