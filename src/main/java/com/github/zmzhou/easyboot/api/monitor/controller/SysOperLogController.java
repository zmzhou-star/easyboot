package com.github.zmzhou.easyboot.api.monitor.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.entity.SysOperLog;
import com.github.zmzhou.easyboot.api.monitor.service.SysOperLogService;
import com.github.zmzhou.easyboot.api.monitor.vo.SysOperLogParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 操作日志记录控制层
 *
 * @author zmzhou
 * @version 1.0
 * @title SysOperLogController
 * @date 2020/9/15 21:03
 */
@Api(tags = {"操作日志记录管理"})
@RestController
@RequestMapping("monitor/operLog")
public class SysOperLogController extends BaseController {
	/**
	 * 服务对象
	 */
	@Resource
	private SysOperLogService operLogService;

	/**
	 * 获取操作日志记录列表
	 *
	 * @param params 查询参数
	 * @return 列表数据
	 * @author zmzhou
	 * @date 2020/9/15 21:03
	 */
	@PostMapping(path = "list")
	@ApiOperation(value = "获取操作日志记录列表")
	public ApiResult<TableDataInfo> list(@RequestBody SysOperLogParams params) {
		Pageable pageable = getPageable(params);
		Page<SysOperLog> list = operLogService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 删除操作日志
	 *
	 * @param ids id数组
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/15 21:03
	 */
	@DeleteMapping("/{ids}")
	@ApiOperation(value = "删除操作日志")
	public ApiResult<Object> delete(@PathVariable("ids")
	                                @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		operLogService.delete(ids);
		return new ApiResult<>();
	}

	/**
	 * 清空操作日志
	 *
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/15 21:03
	 */
	@DeleteMapping("/clean")
	@ApiOperation(value = "清空操作日志")
	public ApiResult<Object> clean() {
		operLogService.cleanLoginLog();
		return new ApiResult<>();
	}

	/**
	 * 导出操作日志excel
	 *
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/9/15 21:03
	 */
	@PostMapping("/export")
	@ApiOperation(value = "导出操作日志excel")
	public ApiResult<String> export(@Validated @RequestBody(required = false) SysOperLogParams params)
			throws InterruptedException {
		return ok(operLogService.export(params));
	}

}
