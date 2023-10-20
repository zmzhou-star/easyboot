/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 病人信息请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
@Data
@ApiModel(description = "病人信息请求参数vo类")
public class PatientParams extends PageParams {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4211527403404722281L;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码")
    private String tel;
}
