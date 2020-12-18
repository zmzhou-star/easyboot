package com.github.zmzhou.easyboot.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.zmzhou.easyboot.common.utils.SpringUtils;

import lombok.Data;

/**
 * 读取代码生成相关配置
 * @author zmzhou
 * @version 1.0
 * date 2020/9/18 22:47
 */
@Data
@Component
@ConfigurationProperties(prefix = "gen")
public class CodeGenConfig {
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 生成包路径
	 */
	private String packageName;
	/**
	 * 表前缀(类名不会包含表前缀)
	 */
	private String tablePrefix;
	/**
	 * 自动去除表前缀，默认是true
	 */
	private boolean autoRemovePre;

	/**
	 * Get instance gen config.
	 *
	 * @return the gen config
	 */
	public static CodeGenConfig getInstance(){
		return SpringUtils.getBean(CodeGenConfig.class);
	}
}
