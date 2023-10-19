/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.medicalrecord.entity.Prescript;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 药方vo类
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-19 21:15:54
 */
@Data
@ApiModel(description = "药方vo类")
public class PrescriptVo extends Prescript implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = -8677236564731017338L;
    
	/**
	 * vo转实体类.
	 *
	 * @return the Prescript 实体类
	 */
	@Override
	public Prescript toEntity() {
		Prescript entity = new Prescript();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
