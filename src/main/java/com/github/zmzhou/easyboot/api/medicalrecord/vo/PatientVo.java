/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.medicalrecord.entity.Patient;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 病人信息vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
@Data
@ApiModel(description = "病人信息vo类")
public class PatientVo extends Patient implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = -1671991268854563063L;
    
	/**
	 * vo转实体类.
	 *
	 * @return the Patient 实体类
	 */
	@Override
	public Patient toEntity() {
		Patient entity = new Patient();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
