package com.zmzhou.easyboot.framework.specification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zmzhou.easyboot.common.Constants;

import lombok.ToString;
/**
 *  @title SimpleSpecificationBuilder
 *  @Description SimpleSpecification构造器
 *  @author zmzhou
 *  @Date 2020/07/08 9:40
 */
@ToString
public class SimpleSpecificationBuilder<T> {
	/** 查询操作符集合 */
	private final List<SpecificationOperator> operators;
	
	/**
	 * 查询规范构造函数，初始化的条件是and
	 * @author zmzhou
	 * @date 2020/08/27 11:51
	 */
	public SimpleSpecificationBuilder(String key, Operator operator, Object value) {
		SpecificationOperator so = new SpecificationOperator();
		so.setJoin("and");
		so.setKey(key);
		so.setOperator(operator);
		so.setValue(value);
		operators = new ArrayList<>();
		operators.add(so);
	}
	/**
	 * 查询条件构造函数
	 * @author zmzhou
	 * @date 2020/08/27 11:50
	 */
	public SimpleSpecificationBuilder() {
		operators = new ArrayList<>();
	}
	
	/**
	 * 完成条件的添加
	 *
	 * @return SimpleSpecificationBuilder
	 * @author zmzhou
	 * @date 2020/07/08 10:27
	 */
	public SimpleSpecificationBuilder<T> add(String join, String key, Operator operator, Object value) {
		if (null == value || StringUtils.isBlank(value.toString())) {
			return this;
		}
		SpecificationOperator so = new SpecificationOperator();
		so.setJoin(join);
		so.setKey(key);
		so.setValue(value);
		so.setOperator(operator);
		operators.add(so);
		return this;
	}
	
	/**
	 * 添加and的条件
	 *
	 * @return SimpleSpecificationBuilder
	 * @author zmzhou
	 * @date 2020/07/08 10:27
	 */
	public SimpleSpecificationBuilder<T> and(String key, Operator operator, Object value) {
		return this.add("and", key, operator, value);
	}
	
	/**
	 * 添加or条件的重载
	 *
	 * @return this，方便后续的链式调用
	 * @author zmzhou
	 * @date 2020/07/08 10:27
	 */
	public SimpleSpecificationBuilder<T> or(String key, Operator operator, Object value) {
		return this.add("or", key, operator, value);
	}
	
	/**
	 * between
	 * @param join 连接符
	 * @param key 字段名
	 * @param min 小数值
	 * @param max 大数值
	 * @return SimpleSpecificationBuilder
	 * @author zmzhou
	 * @date 2020/07/08 10:27
	 */
	public SimpleSpecificationBuilder<T> between(String join, String key, Object min, Object max) {
		Object value1 = min;
		Object value2 = max;
		if (null == value1 || StringUtils.isBlank(value1.toString())) {
			return this.add(join, key, Operator.LE, value2);
		} else {
			value1 = value1 + " 00:00:00";
		}
		if (null == value2 || StringUtils.isBlank(value2.toString())) {
			return this.add(join, key, Operator.GE, value1);
		} else {
			value2 = value2 + " 23:59:59";
		}
		return this.add(join, key, Operator.BETWEEN, value1 + Constants.COMMA + value2);
	}
	
	/**
	 * 查询规范构建
	 * @return SimpleSpecification<T>
	 * @author zmzhou
	 * @date 2020/08/27 11:51
	 */
	public SimpleSpecification<T> build() {
		return new SimpleSpecification<>(this.operators);
	}
}
