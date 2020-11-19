package com.github.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.system.entity.SysNotice;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 通知公告信息vo类
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-19 14:15:47
 */
@Data
@ApiModel(description = "通知公告信息vo类")
public class SysNoticeVo extends SysNotice implements BaseVo {
	/** serialVersionUID */
	private static final long serialVersionUID = -5355751900145256467L;
	/**
	 * vo转实体类.
	 *
	 * @return the SysNotice 实体类
	 */
	@Override
	public SysNotice toEntity() {
		SysNotice entity = new SysNotice();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
