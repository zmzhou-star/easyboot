package com.github.zmzhou.easyboot.api.monitor.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务日志请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
@Data
@ApiModel(description = "定时任务日志请求参数vo类")
public class SysTaskLogParams extends PageParams {
    /** serialVersionUID */
    private static final long serialVersionUID = -7643801480409935719L;
						
    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String jobName;
						
    /** 任务分组 */
    @ApiModelProperty(value = "任务分组")
    private String jobGroup;
						
    /** 执行状态 */
    @ApiModelProperty(value = "执行状态")
    private String status;
}
