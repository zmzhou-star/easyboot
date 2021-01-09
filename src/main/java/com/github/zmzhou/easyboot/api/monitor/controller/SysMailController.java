package com.github.zmzhou.easyboot.api.monitor.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.entity.SysMail;
import com.github.zmzhou.easyboot.api.monitor.service.SysMailService;
import com.github.zmzhou.easyboot.api.monitor.vo.SysMailParams;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统邮件记录Controller
 * @author zmzhou
 * @version 1.0
 * date 2021-01-09 20:27:00
 */
@Api(tags = {"系统邮件记录"})
@RestController
@RequestMapping("/monitor/mail")
public class SysMailController extends BaseController {
	@Resource
	private SysMailService sysMailService;

	/**
	 * 查询系统邮件记录列表
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2021-01-09 20:27:00
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:mail:list')")
	@ApiOperation(value = "查询系统邮件记录列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysMailParams params) {
		Pageable pageable = getPageable(params);
		Page<SysMail> list = sysMailService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取系统邮件记录详细信息
	 * @param id 系统邮件记录id
	 * @return ApiResult<SysMail>
	 * @author zmzhou
	 * date 2021-01-09 20:27:00
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:mail:query')")
	@ApiOperation(value = "根据id获取系统邮件记录详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<SysMail> findById(@PathVariable(value = "id" , required = false) Long id) {
		return ok(sysMailService.findById(id));
	}

	/**
	 * 删除系统邮件记录
	 * @param ids 系统邮件记录id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2021-01-09 20:27:00
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:mail:remove')")
	@ApiOperation(value = "删除系统邮件记录")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
			 @ApiParam(name = "ids" , value = "id数组" , required = true) Long[] ids) {
		sysMailService.deleteByIds(ids);
		return new ApiResult<>();
	}

	/**
	 * 导出excel
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * date 2021-01-09 20:27:00
	 */
	@PreAuthorize("@ebpe.hasPermission('monitor:mail:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出系统邮件记录excel")
	public ApiResult<String> export(@RequestBody SysMailParams params) throws InterruptedException {
		return ok(sysMailService.export(params));
	}
}
