package com.github.zmzhou.easyboot.common.utils;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.common.vo.MailVo;

/**
 * The type Mail utils test.
 *
 * @author zmzhou
 * @version 1.0
 * @title MailUtilsTest
 * @date 2020 /12/31 19:11
 */
class MailUtilsTest extends EasybootApplicationTests {
	@Resource
	private MailUtils mailUtils;
	private MailVo mailVo;

	/**
	 * Sets up.
	 */
	@BeforeEach
	void setUp() {
		mailVo = MailVo.builder()
				.to("zmzhou8@gmail.com")
				.subject("Easy Boot 验证码")
				.text("<html><body>尊敬的用户：<br>\t我们收到了您的修改密码请求，您的验证码为：" +
						"<p><strong style=\"font-weight: bold;text-align:center;font-size: 26px;\">" +
						EasyBootUtils.randomCode() +
						"</strong></p>如果您并未请求此验证码，则可能是他人正在尝试修改您的 Easy Boot 密码。" +
						"<p><b>请勿将此验证码转发给或提供给任何人。</b></p>此致<br>敬上</body></html>")
				.build();
	}

	/**
	 * Send mail.
	 */
	@Test
	void sendMail() {
		mailUtils.sendMail(mailVo);
	}

	/**
	 * Send simple mail.
	 */
	@Test
	void sendSimpleMail() {
		mailUtils.sendSimpleMail(mailVo);
	}
}
