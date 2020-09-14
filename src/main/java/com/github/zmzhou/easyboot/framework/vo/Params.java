package com.github.zmzhou.easyboot.framework.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *  @title Params
 *  @Description 查询参数接收实体类
 *  @author zmzhou
 *  @Date 2020/07/07 16:43
 */
@Data
@ApiModel(description = "请求参数vo类")
public class Params implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -1721448431500151181L;

	/** 状态 */
	@ApiModelProperty(value = "状态")
	private String status;
	/** id */
	@ApiModelProperty(value = "ID")
	private Long id;
}
