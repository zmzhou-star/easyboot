package com.zmzhou.easyboot.framework.entity;

import java.io.Serializable;

import com.zmzhou.easyboot.api.system.entity.SysMenu;

import lombok.Data;
/**
 *  @title Params
 *  @Description 查询参数接收实体类
 *  @author zmzhou
 *  @Date 2020/07/07 16:43
 */
@Data
public class Params implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 243622579032240361L;
	/** 页码 */
	private Integer pageNum;
	/** 每页显示记录数 */
	private Integer pageSize;
	/** 开始时间 */
	private String beginTime;
	/** 结束时间 */
	private String endTime;
	/** 用户名称 */
	private String username;
	/** 手机号码 */
	private String tel;
	/** 用户状态 */
	private String status;
	/** 用户id */
	private Long id;
	/** 用户密码 */
	private String password;
	/** 排序列 */
	private String prop;
	/** 排序的方向 "descending" 或者 "ascending". */
	private String order;
	/** 菜单名称 */
	private String menuName;
	/**
	 * The Role code.
	 */
	private String roleCode;
	/**
	 * The Role name.
	 */
	private String roleName;
	/**
	 * The Visible.
	 */
	private String visible;
	/** 导出excel的文件名 */
	private String excelName;

	/**
	 * Instantiates a new Params.
	 */
	public Params() {
	}
	/**
	 * Instantiates a new Params.
	 *
	 * @param menu the menu
	 */
	public Params(SysMenu menu) {
		this.menuName = menu.getMenuName();
		this.visible = menu.getVisible();
		this.status = menu.getStatus();
	}
}
