package com.github.zmzhou.easyboot.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.zmzhou.easyboot.EasybootApplicationTests;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Easy boot utils test.
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/19 18:55
 */
class EasyBootUtilsTest {

	/**
	 * Convert to camel case.
	 */
	@Test
	void convertToCamelCase() {
		String res = EasyBootUtils.convertToCamelCase("HELLO_WORLD");
		Assertions.assertEquals("HelloWorld", res);
		res = EasyBootUtils.convertToCamelCase("");
		Assertions.assertEquals("", res);
		res = EasyBootUtils.convertToCamelCase("hello");
		Assertions.assertEquals("Hello", res);
	}

	/**
	 * To camel case.
	 */
	@Test
	void toCamelCase() {
		String res = EasyBootUtils.toCamelCase("HELLO_WORLD");
		Assertions.assertEquals("helloWorld", res);
		res = EasyBootUtils.toCamelCase("HELLO");
		Assertions.assertEquals("hello", res);
		res = EasyBootUtils.toCamelCase("");
		Assertions.assertEquals("", res);
	}
}
