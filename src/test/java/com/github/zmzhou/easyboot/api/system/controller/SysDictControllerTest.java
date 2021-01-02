package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.vo.SysDictParams;
import com.github.zmzhou.easyboot.api.system.vo.SysDictTypeVo;
import com.github.zmzhou.easyboot.api.system.vo.SysDictVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * The type Sys dict controller test.
 */
class SysDictControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private SysDictController controller;

	/**
	 * Gets dicts.
	 */
	@Test
	void getDicts() {
		ApiResult<SysDict> res = controller.getDicts("sys_user_sex");
		Assertions.assertNotNull(res);
	}

	/**
	 * Option select.
	 */
	@Test
	void optionSelect() {
		ApiResult<SysDictTypeVo> res = controller.optionSelect();
		Assertions.assertNotNull(res);
	}

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(new SysDictParams());
		Assertions.assertNotNull(res);
	}

	/**
	 * Find by id.
	 */
	@Test
	void findById() {
		ApiResult<SysDict> res = controller.findById(1L);
		Assertions.assertNotNull(res);
	}

	/**
	 * Save.
	 */
	@Test
	void save() {
		ApiResult<SysDict> res = controller.findById(1L);
		Assertions.assertNotNull(res);
		SysDictVo vo = new SysDictVo();
		BeanUtils.copyProperties(res.getData(), vo);
		res = controller.save(vo);
		Assertions.assertNotNull(res);
		res = controller.update(vo);
		Assertions.assertNotNull(res);
	}

	/**
	 * Remove.
	 */
	@Test
	void remove() {
		ApiResult<Integer> res = controller.remove(new Long[]{ 1L });
		Assertions.assertNotNull(res);
	}

	/**
	 * Export.
	 */
	@Test
	void export() {
		SysDictParams param = new SysDictParams();
		param.setExcelName("数据字典");
		try {
			ApiResult<String> res = controller.export(param);
			log.info("结果：{}", res);
			Assertions.assertNotNull(res);
		} catch (InterruptedException e) {
			Assertions.assertNotNull(e);
		}
	}
}
