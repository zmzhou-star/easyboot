package com.github.zmzhou.easyboot.common.utils;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.zmzhou.easyboot.EasybootApplicationTests;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Easy boot utils test.
 *
 * @author zmzhou
 * @version 1.0 date 2020/9/19 18:55
 */
@Slf4j
class EasyBootUtilsTest {

	/**
	 * Convert to camel case.
	 */
	@Test
	void convertToCamelCase() {
		String res = EasyBootUtils.convertToCamelCase("HELLO_WORLD");
		log.info("{}", res);
		Assertions.assertEquals("HelloWorld", res);
		res = EasyBootUtils.convertToCamelCase("");
		log.info("{}", res);
		Assertions.assertEquals("", res);
		res = EasyBootUtils.convertToCamelCase("hello");
		log.info("{}", res);
		Assertions.assertEquals("Hello", res);
	}

	/**
	 * To camel case.
	 */
	@Test
	void toCamelCase() {
		String res = EasyBootUtils.toCamelCase("HELLO_WORLD");
		log.info("{}", res);
		Assertions.assertEquals("helloWorld", res);
		res = EasyBootUtils.toCamelCase("HELLO");
		log.info("{}", res);
		Assertions.assertEquals("hello", res);
		res = EasyBootUtils.toCamelCase("");
		Assertions.assertEquals("", res);
	}

	/**
	 * Gets method.
	 */
	@Test
	void getMethod() {
		Method res = EasyBootUtils.getMethod(EasyBootUtilsTest.class, "");
		log.info("{}", res);
		Assertions.assertNull(res);
		res = EasyBootUtils.getMethod(EasyBootUtilsTest.class, "getMethod");
		log.info("{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Json string to map.
	 */
	@Test
	void jsonStringToMap() {
		Map<String, Object> res = EasyBootUtils.jsonStringToMap("");
		log.info("{}", res);
		Assertions.assertNotNull(res);
		res = EasyBootUtils.jsonStringToMap("{'abc':1}");
		log.info("{}", res);
		Assertions.assertNotNull(res);
		res = EasyBootUtils.jsonStringToMap("{'abc':{'abc':1}}");
		log.info("{}", res);
		Assertions.assertNotNull(res);
		res = EasyBootUtils.jsonStringToMap("{'abc':[{'abc':1}]}");
		log.info("{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Random code.
	 */
	@Test
	void randomCode() {
		String res = EasyBootUtils.randomCode();
		log.info("res:{}", res);
		Assertions.assertNotNull(res);
	}
}
