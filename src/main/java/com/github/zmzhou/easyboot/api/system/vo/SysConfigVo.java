package com.github.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.system.entity.SysConfig;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 参数配置vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Data
@ApiModel(description = "参数配置vo类")
public class SysConfigVo extends SysConfig implements BaseVo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5830128793176708183L;

	/**
	 * vo转实体类.
	 *
	 * @return the SysConfig 实体类
	 */
	@Override
	public SysConfig toEntity() {
		SysConfig entity = new SysConfig();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
