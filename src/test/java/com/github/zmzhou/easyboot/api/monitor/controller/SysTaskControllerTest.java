package com.github.zmzhou.easyboot.api.monitor.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskParams;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * The type Sys task controller test.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysTaskControllerTest
 * @date 2020 /12/28 17:32
 */
class SysTaskControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private SysTaskController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(new SysTaskParams());
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Find by id.
	 */
	@Test
	void findById() {
		ApiResult<SysTask> res = controller.findById(1L);
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Save.
	 */
	@Test
	void save() {
		ApiResult<SysTask> res = controller.findById(1L);
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
		SysTask config = res.getData();
		SysTaskVo vo = new SysTaskVo();
		BeanUtils.copyProperties(config, vo);
		controller.update(vo);
		vo.setId(null);
		vo.setCronExpression("0/33 * * * * ?");
		config = controller.save(vo).getData();
		controller.remove(new Long[]{ config.getId() });
	}

	/**
	 * Change status.
	 */
	@Test
	void changeStatus() {
		params.setId(1L);
		ApiResult<Integer> res = controller.changeStatus(params);
		log.info("结果：{}", res);
		res = controller.run(params);
		log.info("结果：{}", res);
		params.setId(null);
		Assertions.assertNotNull(res);
	}
}
