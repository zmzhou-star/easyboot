/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.vo;

import com.github.zmzhou.easyboot.framework.vo.PageParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 看诊记录请求参数vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
@Data
@ApiModel(description = "看诊记录请求参数vo类")
public class PurposeRecordParams extends PageParams {
    /** serialVersionUID */
    private static final long serialVersionUID = 1609007511536505190L;

    /** 病人ID */
    @ApiModelProperty(value = "病人ID")
    private Long patientId;

    /** 主诉 */
    @ApiModelProperty(value = "主诉")
    private String chiefComplaint;

    /** 治法方药 */
    @ApiModelProperty(value = "治法方药")
    private String medicines;
}
