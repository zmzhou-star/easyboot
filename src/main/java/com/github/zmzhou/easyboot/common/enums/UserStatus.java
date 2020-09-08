package com.github.zmzhou.easyboot.common.enums;

/**
 * The enum User status.
 *
 * @author zmzhou
 * @title UserStatus
 * @Description 用户状态
 * @Date 2020 /07/21 10:54
 */
public enum UserStatus {
	/**
	 * 停用的用户
	 */
	DISABLE("0", "停用"),
	/**
	 * 正常的用户
	 */
	NORMAL("1", "正常"),
	/**
	 * 删除的用户
	 */
	DELETED("2", "删除");

	/** 状态码 */
	private final String code;
	/** 描述 */
	private final String desc;
	
	UserStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
}
