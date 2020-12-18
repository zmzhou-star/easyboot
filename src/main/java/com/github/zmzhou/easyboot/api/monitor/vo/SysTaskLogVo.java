package com.github.zmzhou.easyboot.api.monitor.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTaskLog;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 定时任务日志vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
@Data
@ApiModel(description = "定时任务日志vo类")
public class SysTaskLogVo extends SysTaskLog implements BaseVo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5383431161956820470L;

	/**
	 * vo转实体类.
	 *
	 * @return the SysTaskLog 实体类
	 */
	@Override
	public SysTaskLog toEntity() {
		SysTaskLog entity = new SysTaskLog();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
