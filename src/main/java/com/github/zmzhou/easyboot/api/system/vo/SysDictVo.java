package com.github.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 数据字典vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Data
@ApiModel(description = "数据字典vo类")
public class SysDictVo extends SysDict implements BaseVo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4005607991251167227L;

	/**
	 * vo转实体类.
	 *
	 * @return the SysDict 实体类
	 */
	@Override
	public SysDict toEntity() {
		SysDict entity = new SysDict();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
