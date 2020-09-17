package com.github.zmzhou.easyboot.api.tool.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成请求参数vo类
 * @title CodeGenTableParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:45
 */
@Data
@ApiModel(description = "代码生成请求参数vo类")
public class CodeGenTableParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = -7523754498942829718L;
	/**
	 * 表名称
	 */
	@ApiModelProperty(value = "表名称")
	private String tableName;
	/**
	 * 表描述
	 */
	@ApiModelProperty(value = "表描述")
	private String tableComment;
}
