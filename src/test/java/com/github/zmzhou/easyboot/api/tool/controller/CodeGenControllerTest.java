package com.github.zmzhou.easyboot.api.tool.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.zmzhou.easyboot.EasybootApplicationTests;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.api.tool.vo.CodeGenTableParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;

/**
 * 代码生成工具控制层单元测试
 *
 * @author zmzhou
 * @version 1.0
 * @title CodeGenControllerTest
 * @date 2020 /9/17 21:04
 */
class CodeGenControllerTest extends EasybootApplicationTests {
	/**
	 * The Controller.
	 */
	@Autowired
	private CodeGenController controller;
	/**
	 * The Mock mvc.
	 */
	private CodeGenTableParams params;

	/**
	 * Sets .
	 */
	@BeforeEach
	public void setup() {
		params = new CodeGenTableParams();
		params.setPageNum(1);
		params.setPageSize(10);
	}
	/**
	 * List.
	 */
	@Test
	void list() {
		ApiResult<TableDataInfo> res = controller.list(params);
		Assertions.assertNotNull(res);
	}

	/**
	 * Db table list.
	 */
	@Test
	void dbTableList() {
		ApiResult<List<CodeGenTable>> res = controller.dbTableList(null, null);
		Assertions.assertNotNull(res);
	}

	/**
	 * Import table.
	 */
	@Test
	void importTable() {
		ApiResult<Object> res = controller.importTable("SYS_ROLE");
		Assertions.assertNotNull(res);
		res = controller.importTable("");
		Assertions.assertNotNull(res);
	}

	/**
	 * Delete.
	 */
	@Test
	void delete() {
		ApiResult<Object> res = controller.delete(new Long[]{1L});
		Assertions.assertNotNull(res);
	}
}
