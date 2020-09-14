package com.github.zmzhou.easyboot.framework.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 下拉框对象
 * OptionsVo
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/7 22:54
 */
@Data
@Builder
@ApiModel(description = "下拉框对象vo类")
public class OptionsVo implements Comparable<OptionsVo>, Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -5251288314137220163L;
	/**
	 * The Sort by.
	 */
	@ApiModelProperty(value = "显示顺序")
	private long sortBy;
	/** 值 */
	@ApiModelProperty(value = "值")
	private String value;
	/** 显示的值 */
	@ApiModelProperty(value = "显示的值")
	private String label;

	/**
	 * 比较排序
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(OptionsVo o) {
		if (null != o){
			return (int) (this.sortBy - o.getSortBy());
		}
		return 0;
	}
}
