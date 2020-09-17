package com.github.zmzhou.easyboot.api.tool.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.api.tool.service.CodeGenTableService;
import com.github.zmzhou.easyboot.api.tool.vo.CodeGenTableParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 代码生成工具控制层
 * @title CodeGenController
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:04
 */
@Api(tags = {"代码生成"})
@RestController
@RequestMapping("/tool/code/gen")
public class CodeGenController extends BaseController {
	@Resource
	private CodeGenTableService genTableService;

	/**
	 * 获取代码生成列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * @date 2020/9/16 23:08
	 */
	@GetMapping(path = "list")
	@ApiOperation(value = "获取代码生成列表")
	public ApiResult<TableDataInfo> list(CodeGenTableParams params) {
		Pageable pageable = getPageable(params);
		Page<CodeGenTable> list = genTableService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 查询数据库表列表
	 * @param tableName 表名称
	 * @param tableComment 表描述
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * @date 2020/9/17 20:28
	 */
	@GetMapping("/dbTable/list")
	public ApiResult<List<CodeGenTable>> dbTableList(@ApiParam(name = "tableName", value = "表名称") String tableName,
                                         @ApiParam(name = "tableComment", value = "表描述") String tableComment) {
		List<CodeGenTable> list = genTableService.selectDbTableList(tableName, tableComment);
		return ok(list);
	}

	/**
	 * 删除代码生成数据
	 * @param ids 代码生成ID数组
	 * @author zmzhou
	 * @date 2020/9/16 23:08
	 */
	@DeleteMapping("{ids}")
	@ApiOperation(value = "删除代码生成数据")
	public ApiResult<Object> delete(@PathVariable("ids")
	                                @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		genTableService.delete(ids);
		return new ApiResult<>();
	}
}
