package com.github.zmzhou.easyboot.api.monitor.excel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.excel.converters.SuccessConverter;

import lombok.Data;

/**
 * 操作日志记录(SysLogOper)实体类
 *
 * @author makejava
 * @since 2020 -09-13 17:53:02
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(18)
public class SysOperLogExcel extends BaseExcel {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2832904587636386954L;
	/**
	 * 系统模块
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "系统模块"})
	private String title;
	/**
	 * 方法名称
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "方法名称"})
	private String method;
	/**
	 * 请求方式
	 */
	@ColumnWidth(12)
	@ExcelProperty(value = {EXCEL_NAME, "", "请求方式"})
	private String requestMethod;
	/**
	 * 操作人员
	 */
	@ColumnWidth(12)
	@ExcelProperty(value = {EXCEL_NAME, "", "操作人员"})
	private String operName;
	/**
	 * 请求URL
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "请求URL"})
	private String operUrl;
	/**
	 * 主机地址
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "主机地址"})
	private String operIp;
	/**
	 * 操作地点
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {EXCEL_NAME, "", "操作地点"})
	private String operLocation;
	/**
	 * 请求参数
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {EXCEL_NAME, "", "请求参数"})
	private String operParam;
	/**
	 * 操作状态（1正常 0异常）
	 */
	@ColumnWidth(12)
	@ExcelProperty(value = {EXCEL_NAME, "", "操作状态"}, converter = SuccessConverter.class)
	private String status;
	/**
	 * 错误消息
	 */
	@ExcelProperty(value = {EXCEL_NAME, "", "错误消息"})
	private String errorMsg;
	/**
	 * 操作时间
	 */
	@ColumnWidth(20)
	@ExcelProperty(value = {EXCEL_NAME, "", "操作时间"})
	@DateTimeFormat(DATE_PATTERN)
	private Date operTime;

	/**
	 * Gets oper time.
	 *
	 * @return the oper time
	 */
	public Date getOperTime() {
		if (null == this.operTime) {
			return null;
		}
		return (Date) (this.operTime).clone();
	}

	/**
	 * Sets oper time.
	 *
	 * @param operTime the oper time
	 */
	public void setOperTime(Date operTime) {
		if (null == operTime) {
			this.operTime = null;
		} else {
			this.operTime = (Date) (operTime).clone();
		}
	}
}
