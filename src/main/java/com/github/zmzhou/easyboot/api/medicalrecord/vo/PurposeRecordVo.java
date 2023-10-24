/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.medicalrecord.entity.PurposeRecord;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 看诊记录vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
@Data
@ApiModel(description = "看诊记录vo类")
public class PurposeRecordVo extends PurposeRecord implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = -5752393314625733766L;

	/**
	 * vo转实体类.
	 *
	 * @return the PurposeRecord 实体类
	 */
	@Override
	public PurposeRecord toEntity() {
		PurposeRecord entity = new PurposeRecord();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
