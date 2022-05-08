package com.github.zmzhou.easyboot.api.tool.util;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * VelocityEngine工厂初始化
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/20 17:32
 */
public final class VelocityInitializer {
	/**
	 * 私有构造器
     *
	 * @author zmzhou
	 * date 2020/9/20 17:36
	 */
	private VelocityInitializer() {
		// 私有构造器
	}

	/**
	 * 初始化代码生成使用模板vm
     *
	 * Init velocity.
	 */
	public static void initVelocity() {
		Properties p = new Properties();
		// 加载classpath目录下的vm文件
		p.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
		// 初始化Velocity引擎，指定配置Properties
		Velocity.init(p);
	}
}
