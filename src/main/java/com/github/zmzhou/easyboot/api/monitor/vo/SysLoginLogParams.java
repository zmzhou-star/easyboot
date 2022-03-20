package com.github.zmzhou.easyboot.api.monitor.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录日志管理请求参数vo类
 * @title SysLoginLogParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 21:12
 */
@Data
@ApiModel(description = "登录日志管理请求参数vo类")
public class SysLoginLogParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = -1741677165845334218L;

	/** 每页显示记录数 */
	@ApiModelProperty(value = "登录IP")
	private String ipAddr;

	/** 每页显示记录数 */
	@ApiModelProperty(value = "用户名称")
	private String userName;
}
