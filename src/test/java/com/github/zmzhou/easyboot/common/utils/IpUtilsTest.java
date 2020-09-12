package com.github.zmzhou.easyboot.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * IpUtilsTest
 * 单元测试
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/12 15:56
 */
@Slf4j
class IpUtilsTest {

	@Test
	void getRealAddress() {
		String res = IpUtils.getRealAddress();
		log.info("getRealAddress: {}", res);
		Assertions.assertNotNull(res);
	}

	@Test
	void getRealIp() {
		String res = IpUtils.getRealIp();
		log.info("getRealIp: {}", res);
		Assertions.assertNotNull(res);
	}
}
