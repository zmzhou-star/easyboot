package com.github.zmzhou.easyboot.framework.specification;
/**
 *  @title Operator
 *  @Description 运算符枚举
 *  @author zmzhou
 *  @Date 2020/07/08 9:47
 */
public enum Operator {
	/** 等于 = */
	EQUAL,
	/** 不等于 <> */
	NOT_EQUAL,
	/** 大于等于 >= */
	GE,
	/** 小于等于 <= */
	LE,
	/** 大于> */
	GT,
	/** 小于 < */
	LT,
	/** 全模糊 %{username}% */
	LIKE,
	/** 右模糊 {username}% */
	R_LIKE,
	/** 左模糊 %{username} */
	L_LIKE,
	/** 为空 */
	ISNULL,
	/** 不为空 */
	IS_NOTNULL,
	/** between */
	BETWEEN
}
