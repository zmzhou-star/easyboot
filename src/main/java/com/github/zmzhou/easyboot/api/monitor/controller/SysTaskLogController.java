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

import com.github.zmzhou.easyboot.api.monitor.entity.SysTaskLog;
import com.github.zmzhou.easyboot.api.monitor.service.SysTaskLogService;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskLogParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 定时任务日志Controller
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
@Api(tags = {"定时任务日志"})
@RestController
@RequestMapping("/monitor/jobLog")
public class SysTaskLogController extends BaseController {
	@Resource
	private SysTaskLogService sysTaskLogService;

	/**
	 * 查询定时任务日志列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2020-12-17 19:40:43
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:jobLog:list')")
	@ApiOperation(value = "查询定时任务日志列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysTaskLogParams params) {
		Pageable pageable = getPageable(params);
		Page<SysTaskLog> list = sysTaskLogService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 删除定时任务日志
	 *
	 * @param ids 定时任务日志id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2020-12-17 19:40:43
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:jobLog:remove')")
	@ApiOperation(value = "删除定时任务日志")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
					 @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		sysTaskLogService.deleteByIds(ids);
		return new ApiResult<>();
	}

	/**
	 * 清空定时任务日志
	 * @return 删除结果
	 * @author zmzhou
	 * date 2020-12-17 19:40:43
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:jobLog:remove')")
	@ApiOperation(value = "清空定时任务日志")
	@DeleteMapping("/clean")
	public ApiResult<Integer> clean() {
		sysTaskLogService.clean();
		return new ApiResult<>();
	}

	/**
	 * 导出excel
	 *
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * date 2020-12-17 19:40:43
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:jobLog:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出定时任务日志excel")
	public ApiResult<String> export(@RequestBody SysTaskLogParams params) throws InterruptedException {
		return ok(sysTaskLogService.export(params));
	}
}
