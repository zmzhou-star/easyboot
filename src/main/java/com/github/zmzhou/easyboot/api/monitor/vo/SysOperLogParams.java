package com.github.zmzhou.easyboot.api.monitor.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作日志管理请求参数vo类
 * @title SysOperLogParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/14 23:11
 */
@Data
@ApiModel(description = "操作日志管理请求参数vo类")
public class SysOperLogParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = -4039243324913033288L;

	/**
	 * 系统模块
	 */
	@ApiModelProperty(value = "系统模块")
	private String title;
	/**
	 * 请求方式
	 */
	@ApiModelProperty(value = "请求方式")
	private String requestMethod;
	/**
	 * 操作人员
	 */
	@ApiModelProperty(value = "操作人员")
	private String operName;
}
