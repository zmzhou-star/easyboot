package com.github.zmzhou.easyboot.api.monitor.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.entity.SysLoginLog;
import com.github.zmzhou.easyboot.api.monitor.service.SysLoginLogService;
import com.github.zmzhou.easyboot.api.monitor.vo.SysLoginLogParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 登录日志记录控制层
 *
 * @author zmzhou
 * @version 1.0
 * @title SysLoginLogController
 * @date 2020/9/13 21:04
 */
@Api(tags = {"登录日志记录管理"})
@RestController
@RequestMapping("monitor/loginLog")
public class SysLoginLogController extends BaseController {
	/**
	 * service对象
	 */
	@Resource
	private SysLoginLogService loginLogService;

	/**
	 * 获取登录日志记录列表
	 *
	 * @param params 查询参数
	 * @return 列表数据
	 * @author zmzhou
	 * @date 2020/9/13 21:09
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:loginLog:list')")
	@PostMapping(path = "list")
	@ApiOperation(value = "获取登录日志记录列表")
	public ApiResult<TableDataInfo> list(@RequestBody SysLoginLogParams params) {
		Pageable pageable = getPageable(params);
		Page<SysLoginLog> list = loginLogService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 删除登录日志
	 * @param ids id数组
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/13 21:37
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:loginLog:remove')")
	@DeleteMapping("/{ids}")
	@ApiOperation(value = "删除登录日志")
	public ApiResult<Object> delete(@PathVariable("ids")
	                                @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		loginLogService.delete(ids);
		return new ApiResult<>();
	}

	/**
	 * 清空登录日志
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/13 21:38
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:loginLog:remove')")
	@DeleteMapping("/clean")
	@ApiOperation(value = "清空登录日志")
	public ApiResult<Object> clean() {
		loginLogService.cleanLoginLog();
		return new ApiResult<>();
	}

	/**
	 * 导出登录日志excel
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/9/13 21:38
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:loginLog:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出登录日志excel")
	public ApiResult<String> export(@RequestBody(required = false) SysLoginLogParams params)
			throws InterruptedException {
		return ok(loginLogService.export(params));
	}
}
