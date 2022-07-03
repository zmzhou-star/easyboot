package com.github.zmzhou.easyboot.framework.vo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询参数vo父类
 * @title PageParams
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/14 21:10
 */
@Data
@ApiModel(description = "分页查询参数vo父类")
public class PageParams extends Params {
	/** serialVersionUID */
	private static final long serialVersionUID = 1279585296256930218L;
	/** 页码 */
	@ApiModelProperty(value = "页码")
	private Integer pageNum = 1;

	/** 每页显示记录数 */
	@ApiModelProperty(value = "每页记录数")
	private Integer pageSize = 20;

	/** 开始时间 */
	@ApiModelProperty(value = "开始时间")
	private String beginTime;

	/** 结束时间 */
	@ApiModelProperty(value = "结束时间")
	private String endTime;

	/** 排序列 */
	@ApiModelProperty(value = "排序列")
	private String prop;

	/** 排序的方向 "descending" 或者 "ascending". */
	@ApiModelProperty(value = "排序规则")
	private String order;

	/** 导出excel的文件名 */
	@NotNull(message = "导出excel的文件名不能为空")
	@ApiModelProperty(value = "导出excel的文件名")
	private String excelName;
}
