/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 药方请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-19 21:15:54
 */
@Data
@ApiModel(description = "药方请求参数vo类")
public class PrescriptParams extends PageParams {
    /** serialVersionUID */
    private static final long serialVersionUID = 8544881567845237637L;

    /** 药方名 */
    @ApiModelProperty(value = "药方名")
    private String prescriptName;

    /** 用途 */
    @ApiModelProperty(value = "用途")
    private String purpose;

    /** 治法方药 */
    @ApiModelProperty(value = "治法方药")
    private String medicines;
}
