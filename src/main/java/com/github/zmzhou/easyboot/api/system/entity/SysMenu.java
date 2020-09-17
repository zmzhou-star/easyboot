package com.github.zmzhou.easyboot.api.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;
import lombok.ToString;

/**
 * The type Sys menu.
 *
 * @author zmzhou
 * @title SysMenu
 * @Description 菜单表实体类
 * @Date 2020 /07/09 18:10
 */
@Data
@ToString
@Entity
@Table(name = "SYS_MENU")
public class SysMenu extends BaseEntity implements Comparable<SysMenu> {
	/** serialVersionUID */
	private static final long serialVersionUID = -3304319243957837933L;
	/**
	 * The Menu name.
	 */
	@NotBlank(message = "菜单名称不能为空")
	@Size(min = 3, max = 50, message = "菜单名称长度在3~50个字符")
	@Column(name = "MENU_NAME")
	private String menuName;
	/**
	 * The Parent id.
	 */
	@Column(name = "PARENT_ID")
	private Long parentId;
	/**
	 * The Sort by.
	 */
	@Column(name = "SORT_BY")
	private Long sortBy;
	/**
	 * The Path.
	 */
	@Size(max = 200, message = "路由地址长度不能超过200个字符")
	private String path;
	/**
	 * The Component.
	 */
	@Size(max = 200, message = "组件路径不能超过255个字符")
	@Column(name = "COMPONENT")
	private String component;
	/**
	 * 是否为外链（1是 0否）
	 * The Is frame.
	 */
	@Column(name = "IS_FRAME")
	private String isFrame;
	/**
	 * The Menu type.
	 */
	@NotBlank(message = "菜单类型不能为空")
	@Column(name = "MENU_TYPE")
	private String menuType;
	/**
	 * The Visible.
	 */
	private String visible;
	/**
	 * The Status.
	 */
	private String status;
	/**
	 * The Perms.
	 */
	@Size(max = 100, message = "权限标识长度不能超过100个字符")
	private String perms;
	/**
	 * The Icon.
	 */
	private String icon;
	
	/** 子菜单 */
	@Transient
	private List<SysMenu> children = new ArrayList<>();

	/**
	 * 先按parentId排序，再按sortBy排序
	 * @param menu the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(SysMenu menu) {
		if (null != menu) {
			// 先按parentId排序
			int first = (int) (this.parentId - menu.getParentId());
			if (first == 0) {
				// 再按sortBy排序
				return (int) (this.sortBy - menu.getSortBy());
			}
			return first;
		}
		return 0;
	}
}
