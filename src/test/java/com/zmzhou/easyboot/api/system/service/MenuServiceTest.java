package com.zmzhou.easyboot.api.system.service;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zmzhou.easyboot.EasybootApplicationTests;
import com.zmzhou.easyboot.api.system.entity.SysMenu;
import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.api.system.vo.RouterVo;

/**
 * The type Menu service test.
 */
class MenuServiceTest extends EasybootApplicationTests {
	/**
	 * The Service.
	 */
	@Autowired
	private MenuService service;
	
	/**
	 * The Service.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Gets menu permission.
	 */
	@Test
	void getMenuPermission() {
		SysUser user = userService.getUser(1L);
		Set<String> res = service.getMenuPermission(user);
		log.info("findAll:{}", res);
		Assertions.assertNotNull(res);
		user = userService.getUser(2L);
		res = service.getMenuPermission(user);
		log.info("findAll:{}", res);
		Assertions.assertNotNull(res);
	}
	
	/**
	 * Select menu tree by user id.
	 */
	@Test
	void selectMenuTreeByUserId() {
		List<SysMenu> res = service.selectMenuTreeByUserId(1L);
		log.info("findAll:{}", res);
		Assertions.assertNotNull(res);
	}
	
	/**
	 * Build menus.
	 */
	@Test
	void buildMenus() {
		List<SysMenu> menus = service.selectMenuTreeByUserId(1L);
		List<RouterVo> res = service.buildMenus(menus);
		log.info("findAll:{}", res);
		Assertions.assertNotNull(res);
	}
}
