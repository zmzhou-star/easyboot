package com.github.zmzhou.easyboot.api.tool.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;

/**
 * The type Code gen table column service test.
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/19 18:36
 */
class CodeGenTableColumnServiceTest extends EasybootApplicationTests {

	/**
	 * The Service.
	 */
	@Autowired
	private CodeGenTableColumnService service;

	/**
	 * Select db table columns.
	 */
	@Test
	void selectDbTableColumns() {
		List<CodeGenTableColumn> res = service.selectDbTableColumns("SYS_ROLE");
		Assertions.assertNotNull(res);
	}

}
