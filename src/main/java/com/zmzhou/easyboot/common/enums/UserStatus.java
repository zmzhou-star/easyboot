package com.zmzhou.easyboot.common.enums;

/**
 *  @title UserStatus
 *  @Description 用户状态
 *  @author zmzhou
 *  @Date 2020/07/21 10:54
 */
public enum UserStatus {
	/** 停用的用户 */
	DISABLE("0", "停用"),
	/** 正常的用户 */
	NORMAL("1", "正常"),
	/** 删除的用户 */
	DELETED("2", "删除");
	
	private final String code;
	private final String info;
	
	UserStatus(String code, String info) {
		this.code = code;
		this.info = info;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getInfo() {
		return info;
	}
}
