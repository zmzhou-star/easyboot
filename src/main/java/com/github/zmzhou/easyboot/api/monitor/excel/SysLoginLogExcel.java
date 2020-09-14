package com.github.zmzhou.easyboot.api.monitor.excel;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.api.monitor.entity.SysLoginLog;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.excel.converters.SuccessConverter;

import lombok.Data;

/**
 * 系统登录日志记录excel类
 *
 * @author zmzhou
 * @version 1.0
 * @title SysLoginLogExcel
 * @date 2020 /9/13 17:43
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysLoginLogExcel extends BaseExcel {
	/** serialVersionUID */
	private static final long serialVersionUID = 4022213065478226281L;
	/**
	 * 用户账号
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "用户名称"})
	private String userName;
	/**
	 * 登录IP地址
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "登录IP地址"})
	private String ipAddr;
	/**
	 * 登录地点
	 */
	@ColumnWidth(25)
	@ExcelProperty(value = {EXCEL_NAME, "", "登录地点"})
	private String loginLocation;
	/**
	 * 浏览器类型
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "浏览器类型"})
	private String browser;
	/**
	 * 操作系统
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "操作系统"})
	private String os;
	/**
	 * 登录状态（1成功 0失败）
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "登录状态"}, converter = SuccessConverter.class)
	private String status;
	/**
	 * 提示消息
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {EXCEL_NAME, "", "提示消息"})
	private String msg;
	/**
	 * 访问时间
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {EXCEL_NAME, "", "访问时间"})
	@DateTimeFormat(DATE_PATTERN)
	private Date loginTime;

	/**
	 * To entity sys login log.
	 *
	 * @return the sys login log
	 */
	public SysLoginLog toEntity(){
		SysLoginLog e = new SysLoginLog();
		BeanUtils.copyProperties(this, e);
		return e;
	}
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
