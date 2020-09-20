package com.github.zmzhou.easyboot.api.tool.util;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import com.github.zmzhou.easyboot.common.Constants;

/**
 * VelocityEngine工厂初始化
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/20 17:32
 */
public final class VelocityInitializer {
	/**
	 * 私有构造器
	 * @author zmzhou
	 * date 2020/9/20 17:36
	 */
	private VelocityInitializer() {
		// 私有构造器
	}

	/**
	 * 初始化代码生成使用模板vm
	 * Init velocity.
	 */
	public static void initVelocity() {
		Properties p = new Properties();
		// 加载classpath目录下的vm文件
		p.setProperty("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// 定义字符集
		p.setProperty(RuntimeConstants.ENCODING_DEFAULT, Constants.CHARSETS.displayName());
		p.setProperty(RuntimeConstants.OUTPUT_ENCODING, Constants.CHARSETS.displayName());
		// 初始化Velocity引擎，指定配置Properties
		Velocity.init(p);
	}
}
