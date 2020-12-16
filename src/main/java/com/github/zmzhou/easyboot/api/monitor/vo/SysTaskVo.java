package com.github.zmzhou.easyboot.api.monitor.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 定时任务vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
@Data
@ApiModel(description = "定时任务vo类")
public class SysTaskVo extends SysTask implements BaseVo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6071683666861642968L;

	/**
	 * vo转实体类.
	 *
	 * @return the SysTask 实体类
	 */
	@Override
	public SysTask toEntity() {
		SysTask entity = new SysTask();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
