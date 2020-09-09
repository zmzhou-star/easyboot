package com.github.zmzhou.easyboot.common.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * The enum User status.
 *
 * @author zmzhou
 * @title UserStatus
 * @Description 用户状态
 * @Date 2020 /07/21 10:54
 */
@Getter
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
	public static String getCode(String desc) {
		// 为空，没有找到都返回删除
		if (StringUtils.isBlank(desc)){
			return DELETED.code;
		}
		for (UserStatus userStatus: UserStatus.values()){
			if (desc.equals(userStatus.getDesc())) {
				return userStatus.getCode();
			}
		}
		return DELETED.code;
	}

	/**
	 * Gets desc.
	 *
	 * @return the desc
	 */
	public static String getDesc(String code) {
		// 为空，没有找到都返回删除
		if (StringUtils.isBlank(code)){
			return DELETED.desc;
		}
		for (UserStatus userStatus: UserStatus.values()){
			if (code.equals(userStatus.getCode())) {
				return userStatus.getDesc();
			}
		}
		return DELETED.desc;
	}
}
