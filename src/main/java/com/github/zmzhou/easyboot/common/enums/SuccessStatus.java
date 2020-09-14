package com.github.zmzhou.easyboot.common.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * 成功失败枚举
 * @title SuccessStatus
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 23:19
 */
@Getter
public enum SuccessStatus {
	/**
	 * 失败
	 */
	FAILURE("0", "失败"),
	/**
	 * 成功
	 */
	SUCCESS("1", "成功");

	/** 状态码 */
	private final String code;
	/** 描述 */
	private final String desc;

	/**
	 * 构造器
	 * @author zmzhou
	 * @date 2020/9/13 23:21
	 */
	SuccessStatus(String code, String desc) {
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
			return FAILURE.code;
		}
		for (SuccessStatus status: SuccessStatus.values()){
			if (desc.equals(status.getDesc())) {
				return status.getCode();
			}
		}
		return FAILURE.code;
	}

	/**
	 * Gets desc.
	 *
	 * @return the desc
	 */
	public static String getDesc(String code) {
		// 为空，没有找到都返回删除
		if (StringUtils.isBlank(code)){
			return FAILURE.desc;
		}
		for (SuccessStatus status: SuccessStatus.values()){
			if (code.equals(status.getCode())) {
				return status.getDesc();
			}
		}
		return FAILURE.desc;
	}
}
