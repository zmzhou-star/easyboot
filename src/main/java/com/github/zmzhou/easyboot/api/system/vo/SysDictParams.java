package com.github.zmzhou.easyboot.api.system.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据字典请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Data
@ApiModel(description = "数据字典请求参数vo类")
public class SysDictParams extends PageParams {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8810545371715286755L;

	/** 字典标签 */
	@ApiModelProperty(value = "字典标签")
	private String dictLabel;
	/** 字典名称 */
	@ApiModelProperty(value = "字典名称")
	private String dictName;
	/** 字典类型 */
	@ApiModelProperty(value = "字典类型")
	private String dictType;
}
