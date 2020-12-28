package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysNotice;
import com.github.zmzhou.easyboot.api.system.vo.SysNoticeParams;
import com.github.zmzhou.easyboot.api.system.vo.SysNoticeVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * @title SysNoticeControllerTest
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/28 17:30
 */
class SysNoticeControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private SysNoticeController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(new SysNoticeParams());
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Find by id.
	 */
	@Test
	void findById() {
		ApiResult<SysNotice> res = controller.findById(1L);
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Save.
	 */
	@Test
	void save() {
		ApiResult<SysNotice> res = controller.findById(1L);
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
		SysNotice config = res.getData();
		SysNoticeVo vo = new SysNoticeVo();
		BeanUtils.copyProperties(config, vo);
		controller.update(vo);
		vo.setId(null);
		config = controller.save(vo).getData();
		controller.remove(new Long[]{ config.getId() });
	}
}
