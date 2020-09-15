package com.github.zmzhou.easyboot.api.monitor.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.zmzhou.easyboot.framework.entity.BaseIdEntity;

import lombok.Builder;
import lombok.Data;

/**
 * 操作日志记录(SysLogOper)实体类
 *
 * @author makejava
 * @since 2020 -09-13 17:53:02
 */
@Data
@Builder
@Entity
@Table(name = "SYS_LOG_OPER")
public class SysOperLog extends BaseIdEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2832904587636386954L;
	/**
	 * 模块标题
	 */
	private String title;
	/**
	 * 方法名称
	 */
	private String method;
	/**
	 * 方法描述
	 */
	private String methodDesc;
	/**
	 * 请求方式
	 */
	private String requestMethod;
	/**
	 * 操作人员
	 */
	private String operName;
	/**
	 * 请求URL
	 */
	private String operUrl;
	/**
	 * 主机地址
	 */
	private String operIp;
	/**
	 * 操作地点
	 */
	private String operLocation;
	/**
	 * 请求参数
	 */
	private String operParam;
	/**
	 * 返回参数
	 */
	private String operResult;
	/**
	 * 操作状态（1正常 0异常）
	 */
	private String status;
	/**
	 * 错误消息
	 */
	private String errorMsg;
	/**
	 * 操作时间
	 */
	private Date operTime;

	/**
	 * 构造器
	 * @author zmzhou
	 * @date 2020/9/14 23:37
	 */
	public SysOperLog() {
		super();
	}
	/**
	 * 构造器
	 * @author zmzhou
	 * @date 2020/9/14 23:37
	 */
	public SysOperLog(String title, String method, String methodDesc, String requestMethod, String operName,
	                  String operUrl, String operIp, String operLocation, String operParam, String operResult,
	                  String status, String errorMsg, Date operTime) {
		this.title = title;
		this.method = method;
		this.methodDesc = methodDesc;
		this.requestMethod = requestMethod;
		this.operName = operName;
		this.operUrl = operUrl;
		this.operIp = operIp;
		this.operLocation = operLocation;
		this.operParam = operParam;
		this.operResult = operResult;
		this.status = status;
		this.errorMsg = errorMsg;
		setOperTime(operTime);
	}

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
