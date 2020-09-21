package com.github.zmzhou.easyboot.framework.entity;

import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * Tree基类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020/9/21 20:04
 */
@Data
@MappedSuperclass
public class TreeEntity extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = -2551197326453009409L;
	/**
	 * 父菜单名称
	 */
	private String parentName;
	/**
	 * 父菜单ID
	 */
	private Long parentId;
	/**
	 * 显示顺序
	 */
	private Integer orderBy;
	/**
	 * 祖级列表
	 */
	private String ancestors;
}
