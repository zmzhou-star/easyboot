package com.github.zmzhou.easyboot.api.monitor.vo;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zmzhou.easyboot.api.monitor.entity.SysMail;
import com.github.zmzhou.easyboot.framework.entity.BaseIdEntity;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

import lombok.Data;

/**
 * 邮件信息类
 * @title MailVo
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/30 16:16
 */
@Data
public class MailVo extends SysMail implements BaseVo {
	/** serialVersionUID */
	private static final long serialVersionUID = 4106950771150413239L;
	/** 邮件附件 */
	@JsonIgnore
	private MultipartFile[] multipartFiles;

	/**
	 * vo转实体类
	 *
	 * @return {@link BaseIdEntity}
	 * date 2020/9/7 22:16
	 * @author zmzhou
	 */
	@Override
	public SysMail toEntity() {
		SysMail entity = new SysMail();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
