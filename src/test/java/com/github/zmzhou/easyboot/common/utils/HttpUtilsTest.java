package com.github.zmzhou.easyboot.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.zmzhou.easyboot.framework.entity.IpInfo;

/**
 * HttpUtilsTest
 * 单元测试
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020 /9/12 15:46
 */
class HttpUtilsTest {

	/**
	 * Get.
	 */
	@Test
	void get() {
		String res = HttpUtils.get(IpInfo.GET_IP_URL, null);
		Assertions.assertNotNull(res);
		Map<String, String> param = new HashMap<>();
		res = HttpUtils.get(IpInfo.GET_IP_URL, param);
		Assertions.assertNotNull(res);
		param.put("ip", "14.25.181.135");
		param.put("json", "true");
		res = HttpUtils.get(IpInfo.GET_IP_URL, param);
		Assertions.assertNotNull(res);
	}
}
