package com.github.zmzhou.easyboot.api.monitor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.zmzhou.easyboot.framework.entity.BaseIdEntity;

import lombok.Data;

/**
 * 系统登录日志记录表实体类
 *
 * @author zmzhou
 * @version 1.0
 * @title SysLogLogin
 * @date 2020 /9/13 17:43
 */
@Data
@Entity
@Table(name = "SYS_LOG_LOGIN")
public class SysLoginLog extends BaseIdEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 5381450624699389939L;
	/**
	 * 用户账号
	 */
	private String userName;
	/**
	 * 登录IP地址
	 */
	private String ipAddr;
	/**
	 * 登录地点
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
	 * 登录状态（1成功 0失败）
	 */
	private String status;
	/**
	 * 提示消息
	 */
	private String msg;
	/**
	 * 访问时间
	 */
	private Date loginTime;

	/**
	 * Gets login time.
	 *
	 * @return the login time
	 */
	public Date getLoginTime() {
		if (null == this.loginTime) {
			return null;
		}
		return (Date) (this.loginTime).clone();
	}

	/**
	 * Sets login time.
	 *
	 * @param loginTime the login time
	 */
	public void setLoginTime(Date loginTime) {
		if (null == loginTime) {
			this.loginTime = null;
		} else {
			this.loginTime = (Date) (loginTime).clone();
		}
	}
}
