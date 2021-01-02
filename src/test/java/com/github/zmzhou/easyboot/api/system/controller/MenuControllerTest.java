package com.github.zmzhou.easyboot.api.system.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.api.system.vo.SysMenuParams;
import com.github.zmzhou.easyboot.api.system.vo.SysMenuVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.vo.TreeSelect;

/**
 * The type Menu controller test.
 */
class MenuControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private MenuController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<List<SysMenu>> res = controller.list(new SysMenuParams());
		Assertions.assertNotNull(res);
	}

	/**
	 * Tree select.
	 */
	@Test
	void treeSelect() {
		SysMenuVo menu = new SysMenuVo();
		ApiResult<SysMenu> sysMenu = controller.getOne(1L);
		BeanUtils.copyProperties(sysMenu.getData(), menu);
		ApiResult<List<TreeSelect>> res = controller.treeSelect(menu);
		Assertions.assertNotNull(res);
	}

	/**
	 * Role menu tree select.
	 */
	@Test
	void roleMenuTreeSelect() {
		ApiResult<JSONObject> res = controller.roleMenuTreeSelect(1L);
		Assertions.assertNotNull(res);
	}

	/**
	 * Add.
	 */
	@Test
	void add() {
		SysMenuVo menu = new SysMenuVo();
		ApiResult<SysMenu> sysMenu = controller.getOne(1L);
		BeanUtils.copyProperties(sysMenu.getData(), menu);
		ApiResult<SysMenu> res = controller.add(menu);
		Assertions.assertNotNull(res);
		res = controller.update(menu);
		Assertions.assertNotNull(res);
		ApiResult<Integer> res2 = controller.remove(1L);
		Assertions.assertNotNull(res2);
	}
}
