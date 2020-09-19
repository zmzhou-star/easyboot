package com.github.zmzhou.easyboot.common.utils;

import java.util.Collections;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.zmzhou.easyboot.framework.vo.AmapAddressInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 高德地图 IP定位服务
 * @title AmapUtils
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 21:18
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "amap")
public class AmapUtils {
	/** 高德地图 IP定位API服务地址 */
	private String ipUrl;
	/** 高德地图 Web服务API类型KEY */
	private String webKey;

	/**
	 * Get instance amap utils.
	 *
	 * @return the amap utils
	 */
	public static AmapUtils getInstance(){
		return ServletUtils.getBean(AmapUtils.class);
	}

	/**
	 * 获取用户高德地图ip定位信息
	 * @return 高德地图定位信息
	 * @author zmzhou
	 * @date 2020/9/16 21:22
	 */
	public AmapAddressInfo getAddressInfo() {
		// 获取用户地理位置
		AmapAddressInfo addressInfo = HttpUtils.get(ipUrl, Collections.singletonMap("key", webKey), AmapAddressInfo.class);
		log.info("用户ip信息：{}", addressInfo);
		return addressInfo;
	}
}
