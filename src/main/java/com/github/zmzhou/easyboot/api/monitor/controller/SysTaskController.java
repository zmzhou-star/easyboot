package com.github.zmzhou.easyboot.api.monitor.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.entity.SysTask;
import com.github.zmzhou.easyboot.api.monitor.service.SysTaskService;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskParams;
import com.github.zmzhou.easyboot.api.monitor.vo.SysTaskVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.vo.Params;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 定时任务Controller
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
@Api(tags = {"定时任务"})
@Validated
@RestController
@RequestMapping("/monitor/job")
public class SysTaskController extends BaseController {
	@Resource
	private SysTaskService sysTaskService;

	/**
	 * 查询定时任务列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:job:list')")
	@ApiOperation(value = "查询定时任务列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysTaskParams params) {
		Pageable pageable = getPageable(params);
		Page<SysTask> list = sysTaskService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取定时任务详细信息
	 *
	 * @param id 定时任务id
	 * @return ApiResult<SysTask>
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:job:query')")
	@ApiOperation(value = "根据id获取定时任务详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<SysTask> findById(@PathVariable(value = "id", required = false) Long id) {
		return ok(sysTaskService.findById(id));
	}

	/**
	 * 新增定时任务
	 *
	 * @param vo 定时任务信息
	 * @return ApiResult<SysTask>
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:job:add')")
	@ApiOperation(value = "新增定时任务")
	@PostMapping
	public ApiResult<SysTask> save(@Validated @RequestBody SysTaskVo vo) {
		return ok(sysTaskService.save(vo.toEntity()));
	}

	/**
	 * 修改定时任务
	 *
	 * @param vo 定时任务信息
	 * @return ApiResult<SysTask>
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:job:edit')")
	@ApiOperation(value = "修改定时任务")
	@PutMapping
	public ApiResult<SysTask> update(@RequestBody SysTaskVo vo) {
		return ok(sysTaskService.update(vo.toEntity()));
	}

	/**
	 * 删除定时任务
	 *
	 * @param ids 定时任务id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2020-12-16 17:34:26
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:job:remove')")
	@ApiOperation(value = "删除定时任务")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
					 @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		sysTaskService.deleteByIds(ids);
		return new ApiResult<>();
	}
	/**
	 * 根据id修改任务状态
	 * @param params 参数
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/07/07 14:02
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:job:changeStatus')")
	@PutMapping("/changeStatus")
	@ApiOperation(value = "根据id修改任务状态")
	public ApiResult<Integer> changeStatus(@RequestBody Params params) {
		return ok(sysTaskService.changeStatus(params.getId(), params.getStatus()));
	}
}
