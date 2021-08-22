package com.github.zmzhou.easyboot.common.utils;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The type Date utils test.
 *
 * @author zmzhou
 * @version 1.0 date 2020/9/19 19:00
 */
class DateUtilsTest {

	/**
	 * Gets date.
	 */
	@Test
	void getDate() {
		String res = DateUtils.getDate();
		Assertions.assertNotNull(res);
		res = DateUtils.getTime();
		Assertions.assertNotNull(res);
	}

	/**
	 * Parse date.
	 */
	@Test
	void parseDate() {
		Date res = DateUtils.parseDate(DateUtils.getDate());
		Assertions.assertNotNull(res);
		res = DateUtils.parseDate("abc");
		Assertions.assertNull(res);
		res = DateUtils.parseDate("");
		Assertions.assertNull(res);
	}
}
