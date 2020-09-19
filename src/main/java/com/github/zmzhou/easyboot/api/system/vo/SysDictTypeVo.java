package com.github.zmzhou.easyboot.api.system.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 数据字典类型vo
 *
 * @author zmzhou
 * @version 1.0
 * date 2020/9/19 21:33
 */
@Data
@Builder
@ApiModel(description = "数据字典类型vo")
public class SysDictTypeVo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6648727379679131033L;

	/**
	 * 字典名称
	 */
	@ApiModelProperty(value = "字典名称")
	private String dictName;
	/**
	 * 字典类型.
	 */
	@ApiModelProperty(value = "字典类型")
	private String dictType;
}
