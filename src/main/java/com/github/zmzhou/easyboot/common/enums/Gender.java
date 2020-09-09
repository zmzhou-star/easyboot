package com.github.zmzhou.easyboot.common.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.ToString;

/**
 * Gender
 * 性别枚举
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/9 19:54
 */
@Getter
@ToString
public enum Gender {
	/** 女 */
	WOMAN("0", "女"),
	/** 男 */
	MAN("1", "男"),
	/** 未知 */
	UNKNOWN("2", "未知");

	/** 性别数字 */
	private final String code;
	/** 性别中文 */
	private final String zh;

	Gender(String code, String zh) {
		this.code = code;
		this.zh = zh;
	}

	/**
	 * 根据中文获取code
	 *
	 * @param zh 中文
	 * @return code string
	 * @author zmzhou
	 * @date 2020 /9/9 20:00
	 */
	public static String getCode(String zh){
		// 为空，没有找到都返回未知
		if (StringUtils.isBlank(zh)){
			return UNKNOWN.code;
		}
		for (Gender gender: Gender.values()){
			if (zh.equals(gender.getZh())) {
				return gender.getCode();
			}
		}
		return UNKNOWN.code;
	}

	/**
	 * 根据code获取中文
	 *
	 * @param code 中文
	 * @return zh string
	 * @author zmzhou
	 * @date 2020 /9/9 20:00
	 */
	public static String getZh(String code){
		// 为空，没有找到都返回未知
		if (StringUtils.isBlank(code)){
			return UNKNOWN.zh;
		}
		for (Gender gender: Gender.values()){
			if (code.equals(gender.getCode())) {
				return gender.getZh();
			}
		}
		return UNKNOWN.getZh();
	}
}
