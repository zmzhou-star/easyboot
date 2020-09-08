package com.github.zmzhou.easyboot.api.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.web.BaseController;
import com.wf.captcha.SpecCaptcha;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.FileUtil;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginBody;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zmzhou
 * @title CaptchaController
 * @Description 生成验证码
 * @Date 2020/07/08 18:04
 */
@Slf4j
@RestController
@RequestMapping("common")
public class CommonController extends BaseController {
	@Autowired
	private RedisUtils redisUtils;
	/**
	 * 验证码 宽度
	 */
	@Value("${captcha.width}")
	private Integer width;
	/**
	 * 验证码 高度
	 */
	@Value("${captcha.height}")
	private Integer height;
	/**
	 * 验证码 运算位数
	 */
	@Value("${captcha.digit}")
	private Integer digit;
	/**
	 * 验证码有效期（分钟）
	 */
	@Value("${captcha.expiration}")
	private Integer expiration;
	/**
	 * 用户头像路径
	 */
	@Value("${server.file-path-avatar}")
	private String avatarPath;
	/**
	 * 文件上传路径
	 */
	@Value("${server.file-path-upload}")
	private String uploadPath;
	/**
	 * 文件下载路径
	 */
	@Value("${server.file-path-download}")
	private String downloadPath;
	/**
	 * 生成验证码
	 * @return ApiResult<LoginBody>
	 * @author zmzhou
	 * @date 2020/08/29 16:09
	 */
	@GetMapping("/captcha")
	public ApiResult<LoginBody> captcha() {
		// png类型 https://gitee.com/whvse/EasyCaptcha
		SpecCaptcha captcha = new SpecCaptcha(width, height, digit);
		// 获取运算的结果
		String result = captcha.text();
		String uuid = IdUtil.simpleUUID();
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		redisUtils.set(verifyKey, result, expiration, TimeUnit.MINUTES);
		LoginBody body = new LoginBody();
		body.setUuid(uuid);
		body.setCode(captcha.toBase64());
		log.debug("生成验证码：{}", result);
		return ok(body);
	}

	/**
	 * 通用下载请求
	 *
	 * @param fileName 文件名称
	 * @param del 是否删除
	 */
	@GetMapping("/download")
	public void download(String fileName, String del, HttpServletResponse response, HttpServletRequest request){
		try {
			if (FileUtil.isValidFilename(fileName)) {
				throw new BaseException(HttpStatus.BAD_REQUEST.value(),
						String.format("文件名称(%s)非法，不允许下载。 ", fileName));
			}
			String filePath = downloadPath + fileName;
			// 文件不存在
			if (!FileUtil.exists(filePath)){
				log.error("文件不存在：{}", filePath);
				throw new BaseException(HttpStatus.NOT_FOUND, "文件不存在");
			}
			log.debug("下载文件：{}", filePath);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + FileUtil.setFileDownloadHeader(request, fileName));
			// 以流的形式发送给浏览器
			FileUtil.writeBytes(filePath, response.getOutputStream());
			// 下载之后是否要删除服务器上的文件
			if (Boolean.parseBoolean(del)) {
				FileUtil.deleteFile(filePath);
			}
		} catch (IOException e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * @description 访问用户头像
	 * @param response  HttpServletResponse
	 * @param request  HttpServletRequest
	 * @author zmzhou
	 * @date 2020/9/6 0:27
	 */
	@GetMapping("/avatar")
	public void avatar(HttpServletResponse response, HttpServletRequest request){
		// 文件相对路径
		String url = request.getParameter("url");
		try {
			if (FileUtil.isValidFilename(url)) {
				throw new BaseException(HttpStatus.BAD_REQUEST,
						String.format("文件名称(%s)非法，不允许下载。 ", url));
			}
			String avatar = avatarPath + url;
			log.debug("访问文件：{}", avatar);
			// 头像文件不存在
			if (!FileUtil.exists(avatar)){
				log.error("头像文件不存在：{}", avatar);
				throw new BaseException(HttpStatus.NOT_FOUND, "头像文件不存在");
			}
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setContentType("multipart/form-data");
			// 以流的形式发送给浏览器
			FileUtil.writeBytes(avatar, response.getOutputStream());
		} catch (IOException e) {
			log.error("下载文件:{}失败", url, e);
		}
	}
}
