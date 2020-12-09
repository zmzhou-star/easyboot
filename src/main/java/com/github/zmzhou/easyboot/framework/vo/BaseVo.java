package com.github.zmzhou.easyboot.framework.vo;

import java.io.Serializable;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

/**
 * vo类接口
 * BaseVo
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/7 22:18
 */
public interface BaseVo extends Serializable {
	/**
	 * vo转实体类
	 * @author zmzhou
	 * @return {@link BaseEntity}
	 * date 2020/9/7 22:16
	 */
	BaseEntity toEntity();
}
