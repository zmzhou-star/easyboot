package com.github.zmzhou.easyboot.api.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.github.zmzhou.easyboot.framework.entity.BaseIdEntity;

import lombok.Data;

/**
 * 邮件信息类
 * @title SysMail
 * @author zmzhou
 * @version 1.0
 * @date 2021/1/9 17:45
 */
@Data
@Entity
@Table(name = "SYS_MAIL")
public class SysMail extends BaseIdEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = -642265370008069770L;
	/** 邮件发送人 */
	@Column(name = "SEND_FROM")
	private String from;
	/** 邮件接收人（多个邮箱则用分号";"隔开） */
	@NotBlank(message = "邮件接收人不能为空")
	@Column(name = "SEND_TO")
	private String to;
	/** 邮件主题 */
	@NotBlank(message = "邮件主题不能为空")
	private String subject;
	/** 邮件内容 */
	@NotBlank(message = "邮件内容不能为空")
	@Column(name = "MAIL_TEXT")
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
}
