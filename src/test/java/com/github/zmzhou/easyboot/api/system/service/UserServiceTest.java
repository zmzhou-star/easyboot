package com.github.zmzhou.easyboot.api.system.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.vo.SysUserParams;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * The type User service test.
 */
@Slf4j
@SpringBootTest
class UserServiceTest {
	/**
	 * The Service.
	 */
	@Autowired
	private UserService service;
	
	/**
	 * Find all.
	 */
	@Test
	void findAll() {
		SysUserParams params = new SysUserParams();
		Pageable pageable = PageRequest.of(0, 5);
		Page<SysUser> res = service.findAll(params, pageable);
		log.info("findAll:{}", res.getContent());
		Assertions.assertNotNull(res);
	}
	
	/**
	 * Update user status.
	 */
	@Test
	void updateUserStatus() {
		int res = service.updateUserStatus(2L, "1");
		Assertions.assertEquals(1, res);
	}
	
	/**
	 * Gets user.
	 */
	@Test
	void getUser() {
		SysUser res = service.getUser(1L);
		log.info("getUser:{}", res);
		Assertions.assertNotNull(res);
		res = service.getUser("admin");
		log.info("getUser:{}", res);
		Assertions.assertNotNull(res);
	}
	
	/**
	 * Save.
	 */
	@Test
	void save() {
		SysUser user = service.getUser(2L);
		user.setId(0L);
		user.setUsername("junit");
		user = service.save(user);
		log.info("save user:{}", user);
		try {
			service.update(user);
		}catch (BaseException e){
			log.error("", e);
			Assertions.assertNotNull(e);
		}
		service.delete(new Long[]{user.getId()});
	}
	
	/**
	 * Exists.
	 */
	@Test
	void exists() {
		SysUser user = service.getUser(1L);
		boolean res = service.exists(user);
		Assertions.assertTrue(res);
	}
	
	/**
	 * Reset pwd.
	 */
	@Test
	void resetPwd() {
		SysUser res = service.resetPwd(2L, SecurityUtils.sha256Encrypt("Zmzhou.1324"));
		Assertions.assertNotNull(res);
	}
}
