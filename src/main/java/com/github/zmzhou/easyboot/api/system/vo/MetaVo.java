package com.github.zmzhou.easyboot.api.system.vo;

import lombok.Data;

/**
 * @author zmzhou
 * @title MetaVo
 * @Description 路由显示信息
 * @Date 2020/08/27 18:30
 */
@Data
public class MetaVo {
	/**
	 * 设置该路由在侧边栏和面包屑中展示的名字
	 */
	private String title;
	
	/**
	 * 设置该路由的图标，对应路径src/icons/svg
	 */
	private String icon;
	/**
	 * 无参构造
	 * @author zmzhou
	 * @date 2020/08/27 18:31
	 */
	public MetaVo() {
	}
	/**
	 * 带参构造
	 * @author zmzhou
	 * @date 2020/08/27 18:32
	 */
	public MetaVo(String title, String icon) {
		this.title = title;
		this.icon = icon;
	}
}
