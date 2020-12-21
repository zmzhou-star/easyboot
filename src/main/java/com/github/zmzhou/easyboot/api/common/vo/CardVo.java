package com.github.zmzhou.easyboot.api.common.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 首页面板卡片统计vo 
 * @title CardVo
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/21 10:47
 */
@Data
@Builder
@ApiModel(description = "首页面板卡片统计vo类")
public class CardVo implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -2117814648352731910L;
	/** Total Visitors */
	@ApiModelProperty(value = "用户总数")
	private long totalVisitors;
	/** Online Visitors */
	@ApiModelProperty(value = "在线用户")
	private long onlineVisitors;
	/** messages */
	@ApiModelProperty(value = "消息")
	private long messages;
	/** 订单 */
	@ApiModelProperty(value = "订单")
	private long orderForm;
	/** profit */
	@ApiModelProperty(value = "收益")
	private double profit;
}
