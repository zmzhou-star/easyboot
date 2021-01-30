package com.github.zmzhou.easyboot.api.monitor.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统邮件记录请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2021-01-09 20:27:00
 */
@Data
@ApiModel(description = "系统邮件记录请求参数vo类")
public class SysMailParams extends PageParams {
    /** serialVersionUID */
    private static final long serialVersionUID = -8979618804939784381L;
    /** 邮件接收人 */
    @ApiModelProperty(value = "邮件接收人")
    private String to;

    /** 邮件主题 */
    @ApiModelProperty(value = "邮件主题")
    private String subject;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private String status;
}
