package com.github.zmzhou.easyboot.api.system.vo;

import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.framework.vo.Params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单管理请求参数vo类
 * @title SysMenuParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 22:33
 */
@Data
@ApiModel(description = "菜单管理请求参数vo类")
public class SysMenuParams extends Params {
	/** serialVersionUID */
	private static final long serialVersionUID = -4802983359493595483L;

	/** 菜单名称 */
	@ApiModelProperty(value = "菜单名称")
	private String menuName;
	/**
	 * The Visible.
	 */
	@ApiModelProperty(value = "菜单可见")
	private String visible;

	/**
	 * Instantiates a new Sys menu params.
	 */
	public SysMenuParams() {
		super();
	}
	/**
	 * Instantiates a new Params.
	 *
	 * @param menu the menu
	 */
	public SysMenuParams(SysMenu menu) {
		this.menuName = menu.getMenuName();
		setVisible(menu.getVisible());
		setStatus(menu.getStatus());
	}

}
