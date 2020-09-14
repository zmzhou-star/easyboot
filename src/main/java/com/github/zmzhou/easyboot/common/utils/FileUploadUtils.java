package com.github.zmzhou.easyboot.common.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @title FileUploadUtils
 * @description 文件上传工具类
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/6 15:32
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.servlet.multipart")
public class FileUploadUtils {
	/** 单个文件允许的最大值 */
	private String maxFileSize;
	/** 单次请求所有文件允许的最大值 */
	private String maxRequestSize;
	/** 文件保存路径 */
	private String location;
	/** 临时文件保存路径 */
	@Value("${server.file-path-temp}")
	private String tempFilePath;
	/**
	 * 用户头像路径
	 */
	@Value("${server.file-path-avatar}")
	private String avatarPath;

	/**
	 * @description 单文件上传
	 * @param request HttpServletRequest
	 * @return FileItem
	 * @author zmzhou
	 * @date 2020/9/6 15:54
	 */
	public FileItem singleUpload(HttpServletRequest request) {
		try {
			request.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//文件名中文乱码处理也可以如此写
			upload.setHeaderEncoding(StandardCharsets.UTF_8.displayName());
			//设置缓冲区大小与临时文件目录
			factory.setSizeThreshold(Constants.BUFFER_SIZE);
			File uploadTemp = new File(tempFilePath);
			FileUtil.existsAndMkdirs(uploadTemp.getAbsolutePath());
			factory.setRepository(uploadTemp);
			//设置单个文件大小限制
			upload.setFileSizeMax(DataSize.parse(maxFileSize).toBytes());
			//设置所有文件总和大小限制
			upload.setSizeMax(DataSize.parse(maxRequestSize).toBytes());
			List<FileItem> list = upload.parseRequest(request);
			if (!list.isEmpty()) {
				return list.get(0);
			}
		} catch (UnsupportedEncodingException | FileUploadException e) {
			log.error("文件上传异常", e);
			throw new BaseException(HttpStatus.BAD_REQUEST, "上传文件失败");
		}
		throw new BaseException(ErrorCode.NO_FILE);
	}

	/**
	 * 删除用户头像
	 * @param username 用户名
	 * @author zmzhou
	 * @date 2020/9/14 22:41
	 */
	public void deleteAvatar(String username) {
		String path = avatarPath + username;
		boolean flag = FileUtil.deleteFile(path, true);
		log.info("删除用户头像:{}，结果:{}", path, flag);
	}
}
