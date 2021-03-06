package com.github.zmzhou.easyboot.api.common.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.common.vo.EmailCodeVo;
import com.github.zmzhou.easyboot.api.monitor.vo.MailVo;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.EasyBootUtils;
import com.github.zmzhou.easyboot.common.utils.MailUtils;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.google.common.collect.Sets;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @title NonAuthService
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/31 15:49
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class NonAuthService {
	/** 邮箱验证码有效期 */
	private static final int EMAIL_CAPTCHA_EXPIRATION = 15;
	/** 项目名称 */
	@Value("${spring.application.name}")
	private String applicationName;
	@Resource
	private MailUtils mailUtils;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private UserService userService;

	/**
	 * 发送验证码到用户邮箱
	 * @param param 用户名和邮箱
	 * @return 邮箱验证码唯一标识
	 * @author zmzhou
	 * @date 2020/12/31 15:51
	 */
	public String sendMail(EmailCodeVo param) {
		SysUser user = userService.getUser(param.getUsername());
		// 用户不存在
		if (StringUtils.isBlank(user.getUsername())) {
			throw new BaseException(ErrorCode.USER_NOT_EXISTS);
		}
		if (!param.getEmail().equalsIgnoreCase(user.getEmail())){
			throw new BaseException("邮箱[0]与注册时的邮箱不一致", param.getEmail());
		}
		String uuid = IdUtil.simpleUUID();
		// 生成6位随机数为验证码
		String randomCode = EasyBootUtils.randomCode();
		param.setCode(randomCode);
		param.setUuid(uuid);
		String emailKey = this.emailCaptchaCodeKey(uuid);
		redisUtils.set(emailKey, param, EMAIL_CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
		MailVo mailVo = new MailVo();
		mailVo.setTo(param.getEmail());
		mailVo.setSubject(applicationName + " 修改密码验证码");
		mailVo.setText("<html><body style='max-width: 750px;'>尊敬的用户：<br>\t我们收到了您的修改密码请求，您的验证码为：" +
				"<div style=\"text-align: center;\">" +
				"<p><strong style=\"font-weight: bold;text-align:center;font-size: 26px;\">" +
				randomCode +
				"</strong></p></div><b>有效期15分钟</b>，如果您并未请求此验证码，" +
				"则可能是他人正在尝试修改您的 "+applicationName +" 账号：<b>" + param.getUsername() + "</b>的密码。" +
				"<p><b>请勿将此验证码转发给或提供给任何人。</b></p>此致<br>敬上</body></html>");
		mailVo = mailUtils.sendMail(mailVo);
		// 邮件发送失败
		if (Constants.ZERO.equals(mailVo.getStatus())) {
			uuid = null;
		}
		return uuid;
	}

	/**
	 * 验证用户邮箱验证码
	 * @param param 用户邮箱验证码信息
	 * @return Boolean 验证通过
	 * @author zmzhou
	 * @date 2020/12/31 21:11
	 */
	public boolean checkEmailCode(EmailCodeVo param) {
		String emailKey = this.emailCaptchaCodeKey(param.getUuid());
		EmailCodeVo emailCodeVo = redisUtils.get(emailKey);
		// 判断邮箱验证码是否过期
		if (StringUtils.isBlank(param.getUuid()) || null == emailCodeVo) {
			throw new BaseException(ErrorCode.CAPTCHA_EXPIRE);
		}
		if (!emailCodeVo.getCode().equals(param.getCode())) {
			throw new BaseException(ErrorCode.CAPTCHA_ERROR);
		}
		return true;
	}

	/**
	 * 重置密码
	 * @param uuid 重置密码会话id
	 * @param password 新密码
	 * @author zmzhou
	 * @date 2020/12/31 21:49
	 */
	public boolean resetPwd(String uuid, String password) {
		String emailKey = emailCaptchaCodeKey(uuid);
		EmailCodeVo emailCodeVo = redisUtils.get(emailKey);
		// 判断会话是否过期
		if (StringUtils.isBlank(uuid) || null == emailCodeVo) {
			throw new BaseException("本次重置密码会话已经超时，请重新操作");
		}
		SysUser user = userService.getUser(emailCodeVo.getUsername());
		// 用户不存在
		if (StringUtils.isBlank(user.getUsername())) {
			throw new BaseException(ErrorCode.USER_NOT_EXISTS);
		}
		// 删除缓存
		redisUtils.delete(emailKey);
		// 设置新密码
		user.setPassword(SecurityUtils.encryptPassword(password));
		user.setUpdateBy(emailCodeVo.getUsername());
		user = userService.saveAndFlush(user);
		log.info("用户：{}修改密码成功", user.getUsername());
		return true;
	}

	/**
	 * 获取邮箱验证码 redis key
	 * @param uuid 重置密码会话id
	 * @return 邮箱验证码 redis key
	 * @author zmzhou
	 * @date 2020/12/31 21:51
	 */
	private String emailCaptchaCodeKey(String uuid) {
		return Constants.EMAIL_CAPTCHA_CODE_KEY + uuid;
	}

	/**
	 * 注册用户获取邮箱验证码
	 * @param userVo 注册用户信息
	 * @return 注册验证码唯一标识
	 * @author zmzhou
	 * @date 2021/1/1 18:52
	 */
	public String sendRegisterMail(SysUserVo userVo) {
		SysUser user = userService.getUser(userVo.getUsername());
		// 用户已经存在
		if (StringUtils.isNotBlank(user.getUsername())) {
			throw new BaseException(ErrorCode.USER_EXISTS);
		}
		String uuid = IdUtil.simpleUUID();
		// 生成6位随机数为验证码
		String randomCode = EasyBootUtils.randomCode();
		userVo.setRemark(randomCode);
		String emailKey = this.emailCaptchaCodeKey(uuid);
		redisUtils.set(emailKey, userVo, EMAIL_CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
		MailVo mailVo = new MailVo();
		mailVo.setTo(userVo.getEmail());
		mailVo.setSubject(applicationName + " 注册用户验证码");
		mailVo.setText("<html><body style='max-width: 750px;'>尊敬的用户：<br>\t我们收到了您的注册用户请求，您的验证码为：" +
				"<div style=\"text-align: center;\">" +
				"<p><strong style=\"font-weight: bold;text-align:center;font-size: 26px;\">" +
				randomCode +
				"</strong></p></div><b>有效期15分钟</b>，如果您并未请求此验证码，" +
				"则可能是他人正在尝试用您的邮箱注册"+applicationName+ " 账号：<b>" + userVo.getUsername() + "</b>。" +
				"<p><b>请勿将此验证码转发给或提供给任何人。</b></p>此致<br>敬上</body></html>");
		mailVo = mailUtils.sendMail(mailVo);
		// 邮件发送失败
		if (Constants.ZERO.equals(mailVo.getStatus())) {
			uuid = null;
		}
		return uuid;
	}

	/**
	 * 注册账号
	 * @param userVo 注册信息
	 * @return 注册成功
	 * @author zmzhou
	 * @date 2021/1/1 20:23
	 */
	public boolean register(SysUserVo userVo) {
		String emailKey = emailCaptchaCodeKey(userVo.getNickName());
		SysUserVo vo = redisUtils.get(emailKey);
		// 判断会话是否超时
		if (StringUtils.isBlank(userVo.getNickName()) || null == vo) {
			throw new BaseException("本次注册账号会话已经超时，请重新操作");
		}
		if (!vo.getRemark().equals(userVo.getRemark())) {
			throw new BaseException(ErrorCode.CAPTCHA_ERROR);
		}
		// 删除缓存
		redisUtils.delete(emailKey);
		vo.setNickName(vo.getUsername());
		vo.setCreateBy(vo.getUsername());
		vo.setStatus(Constants.ONE);
		vo.setOnline(Constants.ZERO);
		// 设置默认角色为‘游客角色’
		vo.setRoles(Sets.newHashSet("3"));
		userService.saveUserRole(vo);
		return true;
	}
}
