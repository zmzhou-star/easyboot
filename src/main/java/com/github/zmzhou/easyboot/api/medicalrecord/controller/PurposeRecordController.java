/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.controller;

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

import com.github.zmzhou.easyboot.api.medicalrecord.entity.PurposeRecord;
import com.github.zmzhou.easyboot.api.medicalrecord.service.PurposeRecordService;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PurposeRecordParams;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PurposeRecordVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.vo.Params;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 看诊记录Controller
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-24 22:22:23
 */
@Api(tags = {"看诊记录"})
@RestController
@RequestMapping("/medicalrecord/record")
public class PurposeRecordController extends BaseController {
	@Resource
	private PurposeRecordService purposeRecordService;

	/**
	 * 查询看诊记录列表
     *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2023-10-24 22:22:23
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:record:list')")
	@ApiOperation(value = "查询看诊记录列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) PurposeRecordParams params) {
		Pageable pageable = getPageable(params);
		Page<PurposeRecord> list = purposeRecordService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取看诊记录详细信息
     *
	 * @param id 看诊记录id
	 * @return ApiResult<PurposeRecord>
	 * @author zmzhou
	 * date 2023-10-24 22:22:23
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:record:query')")
	@ApiOperation(value = "根据id获取看诊记录详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<PurposeRecord> findById(@PathVariable(value = "id" , required = false) Long id) {
		return ok(purposeRecordService.findById(id));
	}

	/**
	 * 新增看诊记录
     *
	 * @param vo 看诊记录信息
	 * @return ApiResult<PurposeRecord>
	 * @author zmzhou
	 * date 2023-10-24 22:22:23
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:record:add')")
	@ApiOperation(value = "新增看诊记录")
	@PostMapping
	public ApiResult<PurposeRecord> save(@Validated @RequestBody PurposeRecordVo vo) {
		return ok(purposeRecordService.save(vo.toEntity()));
	}

	/**
	 * 修改看诊记录
     *
	 * @param vo 看诊记录信息
	 * @return ApiResult<PurposeRecord>
	 * @author zmzhou
	 * date 2023-10-24 22:22:23
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:record:edit')")
	@ApiOperation(value = "修改看诊记录")
	@PutMapping
	public ApiResult<PurposeRecord> update(@Validated @RequestBody PurposeRecordVo vo) {
		return ok(purposeRecordService.update(vo.toEntity()));
	}

	/**
	 * 删除看诊记录
     *
	 * @param ids 看诊记录id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2023-10-24 22:22:23
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:record:remove')")
	@ApiOperation(value = "删除看诊记录")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
			 @ApiParam(name = "ids" , value = "id数组" , required = true) Long[] ids) {
		purposeRecordService.deleteByIds(ids);
		return new ApiResult<>();
	}

	/**
	 * 导出excel
     *
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * date 2023-10-24 22:22:23
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:record:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出看诊记录excel")
	public ApiResult<String> export(@RequestBody PurposeRecordParams params) throws InterruptedException {
		return ok(purposeRecordService.export(params));
	}
}
