package com.github.zmzhou.easyboot.api.system.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数配置请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Data
@ApiModel(description = "参数配置请求参数vo类")
public class SysConfigParams extends PageParams {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7334582941399324803L;

	/** 参数名称 */
	@ApiModelProperty(value = "参数名称")
	private String configName;

	/** 参数键名 */
	@ApiModelProperty(value = "参数键名")
	private String configKey;

	/** 系统内置（Y是 N否） */
	@ApiModelProperty(value = "系统内置（Y是 N否）")
	private String configType;
}
