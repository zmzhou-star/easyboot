package com.github.zmzhou.easyboot.api.monitor.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * SysUserOnlineVo
 * 当前在线用户信息
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/12 17:12
 */
@Data
public class SysUserOnlineVo implements Comparable<SysUserOnlineVo>, Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 6331629193247607856L;
	/**
	 * 用户会话唯一标识
	 */
	private String token;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 登录IP地址
	 */
	private String ipAddr;
	/**
	 * 登录地址
	 */
	private String loginLocation;
	/**
	 * 所在城市中心经纬度坐标
	 */
	private String coordinate;
	/**
	 * 浏览器类型
	 */
	private String browser;
	/**
	 * 操作系统
	 */
	private String os;
	/**
	 * 登录时间
	 */
	private Long loginTime;

	/**
	 * @param vo the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */
	@Override
	public int compareTo(SysUserOnlineVo vo) {
		if (null != vo){
			// 按登陆时间倒序排序
			return (int) (vo.getLoginTime() - this.getLoginTime());
		}
		return 0;
	}
}
