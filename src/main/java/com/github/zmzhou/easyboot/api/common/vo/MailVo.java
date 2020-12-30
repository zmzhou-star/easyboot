package com.github.zmzhou.easyboot.api.common.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 邮件信息类
 * @title MailVo
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/30 16:16
 */
@Data
public class MailVo {
	/** 邮件id */
	private Long id;
	/** 邮件发送人 */
	private String from;
	/** 邮件接收人（多个邮箱则用分号";"隔开） */
	@NotBlank(message = "邮件接收人不能为空")
	private String to;
	/** 邮件主题 */
	@NotBlank(message = "邮件主题不能为空")
	private String subject;
	/** 邮件内容 */
	@NotBlank(message = "邮件内容不能为空")
	private String text;
	/** 发送时间 */
	private Date sendDate;
	/** 抄送（多个邮箱则用分号";"隔开） */
	private String cc;
	/** 密送（多个邮箱则用分号";"隔开） */
	private String bcc;
	/** 状态 */
	private String status;
	/** 报错信息 */
	private String error;
	/** 邮件附件 */
	@JsonIgnore
	private MultipartFile[] multipartFiles;
}
