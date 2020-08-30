package com.zmzhou.easyboot.api.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zmzhou.easyboot.framework.entity.BaseEntity;

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
public class SysMenu extends BaseEntity {
	private static final long serialVersionUID = -3304319243957837933L;
	/**
	 * The Menu name.
	 */
	private String menuName;
	/**
	 * The Parent id.
	 */
	private Long parentId;
	/**
	 * The Sort by.
	 */
	private Long sortBy;
	/**
	 * The Path.
	 */
	private String path;
	/**
	 * The Component.
	 */
	private String component;
	/**
	 * The Is frame.
	 */
	private Long isFrame;
	/**
	 * The Menu type.
	 */
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
	private String perms;
	/**
	 * The Icon.
	 */
	private String icon;
	
	/** 子菜单 */
	@Transient
	private List<SysMenu> children = new ArrayList<>();
}
