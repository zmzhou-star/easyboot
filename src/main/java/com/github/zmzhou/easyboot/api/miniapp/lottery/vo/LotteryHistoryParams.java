/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.miniapp.lottery.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 彩票历史数据请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022-07-03 21:04:47
 */
@Data
@ApiModel(description = "彩票历史数据请求参数vo类")
public class LotteryHistoryParams extends PageParams {
    /** serialVersionUID */
    private static final long serialVersionUID = 3630796229075765674L;

    /** 期号 */
    @ApiModelProperty(value = "期号")
    private Long lotteryId;

    /** 彩票类型 */
    @ApiModelProperty(value = "彩票类型")
    private String lotteryType;
}
