package com.github.zmzhou.easyboot.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.zmzhou.easyboot.EasybootApplicationTests;

/**
 * The type Security utils test.
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/19 19:09
 */
class SecurityUtilsTest extends EasybootApplicationTests {
	/**
	 * Gets username.
	 */
	@Test
	void getUsername() {
		String res = SecurityUtils.getUsername();
		Assertions.assertNull(res);
	}

	/**
	 * Encrypt password.
	 */
	@Test
	void encryptPassword() {
		String res = SecurityUtils.encryptPassword(null);
		Assertions.assertNotNull(res);
	}

	/**
	 * Matches password.
	 */
	@Test
	void matchesPassword() {
		Assertions.assertTrue(SecurityUtils.matchesPassword("Zmzhou.1324",
				SecurityUtils.encryptPassword(null)));
		Assertions.assertFalse(SecurityUtils.matchesPassword("abc", SecurityUtils.encryptPassword(null)));
	}

	/**
	 * Is admin.
	 */
	@Test
	void isAdmin() {
		Assertions.assertTrue(SecurityUtils.isAdmin(1L));
		Assertions.assertFalse(SecurityUtils.isAdmin(0L));
	}

	/**
	 * Sha 256 encrypt.
	 */
	@Test
	void sha256Encrypt() {
		String res = SecurityUtils.sha256Encrypt("abcd");
		Assertions.assertNotNull(res);
	}
}
