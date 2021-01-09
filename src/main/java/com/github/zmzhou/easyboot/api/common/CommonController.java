package com.github.zmzhou.easyboot.api.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.FileUploadUtils;
import com.github.zmzhou.easyboot.common.utils.FileUtil;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.security.LoginBody;
import com.github.zmzhou.easyboot.framework.web.BaseController;
import com.wf.captcha.ArithmeticCaptcha;

import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成验证码
 * CaptchaController
 * @author zmzhou
 * @date 2020/07/08 18:04
 */
@Slf4j
@Api(tags = {"通用工具接口"})
@RestController
@RequestMapping("common")
public class CommonController extends BaseController {
	@Resource
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
	@ApiOperation(value = "生成验证码")
	public ApiResult<LoginBody> captcha() {
		// png类型 https://gitee.com/whvse/EasyCaptcha
		ArithmeticCaptcha captcha = new ArithmeticCaptcha(width, height, digit);
		// 获取运算的结果
		String result = captcha.text();
		String uuid = IdUtil.simpleUUID();
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		redisUtils.set(verifyKey, result, expiration, TimeUnit.MINUTES);
		LoginBody body = new LoginBody();
		body.setUuid(uuid);
		body.setCode(captcha.toBase64());
		log.info("生成验证码：{}", result);
		return ok(body);
	}

	/**
	 * 通用文件上传
	 * @return 保存在服务器文件名称
	 * @author zmzhou
	 * @date 2020/11/19 15:37
	 */
	@PostMapping("upload")
	@ApiOperation(value = "上传文件")
	public ApiResult<String> upload() {
		ApiResult<String> result = new ApiResult<>();
		// 头像文件
		FileItem fileItem = FileUploadUtils.getInstance().singleUpload(SpringUtils.getRequest());
		// 上传文件路径
		String uuid = IdUtil.simpleUUID();
		String filePath = uploadPath + uuid;
		// 判断文件夹路径是否存在，不存在则创建
		FileUtil.existsAndMkdirs(filePath);
		// 上传并返回新文件名称
		String fileName = fileItem.getName();
		try {
			// 保存文件
			FileUtils.copyInputStreamToFile(fileItem.getInputStream(), new File(filePath, fileName));
			// 返回保存在服务器文件名称
			result.setData(uuid + Constants.SEPARATOR + fileName);
		} catch (IOException e) {
			log.error("保存文件：{}异常", fileName, e);
			throw new BaseException(HttpStatus.BAD_REQUEST.value(), "文件上传[0]失败", fileName);
		}
		return result;
	}

	/**
	 * 通用文件下载
	 *
	 * @param fileName 文件名称
	 * @param del 是否删除
	 */
	@GetMapping("/download")
	@ApiOperation(value = "下载文件")
	public void download(@ApiParam(name = "文件名", value = "fileName") String fileName, String del,
                 HttpServletResponse response, HttpServletRequest request){
		try {
			if (FileUtil.isAnIllegalFileName(fileName)) {
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
				FileUtil.deleteFile(filePath, true);
			}
		} catch (IOException e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * 访问用户头像
	 * @param response  HttpServletResponse
	 * @param request  HttpServletRequest
	 * @author zmzhou
	 * @date 2020/9/6 0:27
	 */
	@GetMapping("/avatar")
	@ApiOperation(value = "访问用户头像")
	public void avatar(HttpServletResponse response, HttpServletRequest request){
		// 文件相对路径
		String url = request.getParameter("url");
		try {
			if (FileUtil.isAnIllegalFileName(url)) {
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
