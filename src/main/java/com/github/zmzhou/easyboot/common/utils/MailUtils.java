package com.github.zmzhou.easyboot.common.utils;

import java.util.Date;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.github.zmzhou.easyboot.api.common.vo.MailVo;
import com.github.zmzhou.easyboot.common.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 邮件工具类
 *
 * @author zmzhou
 * @version 1.0
 * @title MailUtils
 * @date 2020/12/30 15:58
 */
@Slf4j
@Component
public final class MailUtils {
	@Resource
	private JavaMailSenderImpl mailSender;

	/**
	 * 发送邮件
	 * @param mailVo 邮件信息
	 * @return mailVo 邮件信息
	 * @author zmzhou
	 * @date 2020/12/30 16:02
	 */
	public void sendMail() {
		//简单邮件
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(getMailSendFrom());
		simpleMailMessage.setTo("zmzhou8@gmail.com");
		simpleMailMessage.setSubject("Happy New Year");
		simpleMailMessage.setText("新年快乐！");
		mailSender.send(simpleMailMessage);
	}
	/**
	 * 发送邮件
	 * @param mailVo 邮件信息
	 * @return mailVo 邮件信息
	 * @author zmzhou
	 * @date 2020/12/30 16:19
	 */
	public MailVo sendMail(MailVo mailVo) {
		try {
			// 发送邮件
			sendMimeMail(mailVo);
			// 保存邮件信息
			return saveMail(mailVo);
		} catch (MessagingException e) {
			log.error("发送邮件失败:", e);
			mailVo.setStatus(Constants.ZERO);
			mailVo.setError(e.getMessage());
			return mailVo;
		}
	}

	/**
	 * 发送复杂邮件信息
	 * @param mailVo 邮件信息
	 * @author zmzhou
	 * @date 2020/12/30 16:22
	 */
	private void sendMimeMail(@Validated MailVo mailVo) throws MessagingException {
		//true表示支持复杂类型
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
		//邮件发信人从配置项读取
		mailVo.setFrom(getMailSendFrom());
		//邮件发信人
		messageHelper.setFrom(mailVo.getFrom());
		//邮件收信人
		messageHelper.setTo(mailVo.getTo().split(Constants.SEMICOLON));
		//邮件主题
		messageHelper.setSubject(mailVo.getSubject());
		//邮件内容
		messageHelper.setText(mailVo.getText());
		//抄送
		if (StringUtils.isNotBlank(mailVo.getCc())) {
			messageHelper.setCc(mailVo.getCc().split(Constants.SEMICOLON));
		}
		//密送
		if (StringUtils.isNotBlank(mailVo.getBcc())) {
			messageHelper.setCc(mailVo.getBcc().split(Constants.SEMICOLON));
		}
		//添加邮件附件
		if (mailVo.getMultipartFiles() != null) {
			for (MultipartFile multipartFile : mailVo.getMultipartFiles()) {
				if (null != multipartFile && null != multipartFile.getOriginalFilename()) {
					messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
				}
			}
		}
		//发送时间
		if (null == mailVo.getSendDate()) {
			mailVo.setSendDate(new Date());
			messageHelper.setSentDate(mailVo.getSendDate());
		}
		//正式发送邮件
		mailSender.send(messageHelper.getMimeMessage());
		mailVo.setStatus(Constants.ONE);
		log.info("发送邮件成功：{}->{}", mailVo.getFrom(), mailVo.getTo());
	}

	/**
	 * 保存邮件
	 * @param mailVo 邮件信息
	 * @return mailVo 邮件信息
	 * @author zmzhou
	 * @date 2020/12/30 17:40
	 */
	private MailVo saveMail(MailVo mailVo) {
		//将邮件保存到数据库..
		return mailVo;
	}
	/**
	 * 获取邮件发信人
	 * @return 邮件发信人
	 * @author zmzhou
	 * @date 2020/12/30 16:12
	 */
	private String getMailSendFrom() {
		return mailSender.getJavaMailProperties().getProperty("from");
	}
}
