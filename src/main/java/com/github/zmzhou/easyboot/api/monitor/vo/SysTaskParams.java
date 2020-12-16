package com.github.zmzhou.easyboot.api.monitor.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
@Data
@ApiModel(description = "定时任务请求参数vo类")
public class SysTaskParams extends PageParams {
	/** serialVersionUID */
	private static final long serialVersionUID = -9061448969361082701L;

	/** 任务名称 */
	@ApiModelProperty(value = "任务名称")
	private String jobName;

	/** 任务分组 */
	@ApiModelProperty(value = "任务分组")
	private String jobGroup;

	/** 任务状态 */
	@ApiModelProperty(value = "任务状态")
	private String status;
}
