package com.github.zmzhou.easyboot.api.system.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.system.entity.SysConfig;
import com.github.zmzhou.easyboot.api.system.vo.SysConfigParams;
import com.github.zmzhou.easyboot.api.system.vo.SysConfigVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * The type Sys config controller test.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysConfigControllerTest
 * @date 2020 /12/28 16:30
 */
@Slf4j
class SysConfigControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private SysConfigController controller;

	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(new SysConfigParams());
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Find by id.
	 */
	@Test
	void findById() {
		ApiResult<SysConfig> res = controller.findById(1L);
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Gets config key.
	 */
	@Test
	void getConfigKey() {
		ApiResult<String> res = controller.getConfigKey("sys.index.skin");
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
	}

	/**
	 * Save.
	 */
	@Test
	void save() {
		ApiResult<SysConfig> res = controller.findById(3L);
		log.info("结果：{}", res);
		Assertions.assertNotNull(res);
		SysConfig config = res.getData();
		SysConfigVo vo = new SysConfigVo();
		BeanUtils.copyProperties(config, vo);
		controller.update(vo);
		vo.setId(null);
		vo.setConfigKey("junit.test");
		config = controller.save(vo).getData();
		controller.remove(new Long[]{ config.getId() });
	}

	/**
	 * Export.
	 */
	@Test
	void export() {
		SysConfigParams param = new SysConfigParams();
		param.setExcelName("参数配置");
		try {
			ApiResult<String> res = controller.export(param);
			log.info("结果：{}", res);
			Assertions.assertNotNull(res);
		} catch (InterruptedException e) {
			Assertions.assertNotNull(e);
		}
	}
}
