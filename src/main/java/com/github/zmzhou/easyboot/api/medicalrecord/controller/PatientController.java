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

import com.github.zmzhou.easyboot.api.medicalrecord.entity.Patient;
import com.github.zmzhou.easyboot.api.medicalrecord.service.PatientService;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PatientParams;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PatientVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 病人信息Controller
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-20 21:15:29
 */
@Api(tags = {"病人信息"})
@RestController
@RequestMapping("/medicalrecord/patient")
public class PatientController extends BaseController {
	@Resource
	private PatientService patientService;

	/**
	 * 查询病人信息列表
     *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2023-10-20 21:15:29
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:patient:list')")
	@ApiOperation(value = "查询病人信息列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) PatientParams params) {
		Pageable pageable = getPageable(params);
		Page<Patient> list = patientService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取病人信息详细信息
     *
	 * @param id 病人信息id
	 * @return ApiResult<Patient>
	 * @author zmzhou
	 * date 2023-10-20 21:15:29
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:patient:query')")
	@ApiOperation(value = "根据id获取病人信息详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<Patient> findById(@PathVariable(value = "id" , required = false) Long id) {
		return ok(patientService.findById(id));
	}

	/**
	 * 新增病人信息
     *
	 * @param vo 病人信息信息
	 * @return ApiResult<Patient>
	 * @author zmzhou
	 * date 2023-10-20 21:15:29
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:patient:add')")
	@ApiOperation(value = "新增病人信息")
	@PostMapping
	public ApiResult<Patient> save(@Validated @RequestBody PatientVo vo) {
		return ok(patientService.save(vo.toEntity()));
	}

	/**
	 * 修改病人信息
     *
	 * @param vo 病人信息信息
	 * @return ApiResult<Patient>
	 * @author zmzhou
	 * date 2023-10-20 21:15:29
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:patient:edit')")
	@ApiOperation(value = "修改病人信息")
	@PutMapping
	public ApiResult<Patient> update(@Validated @RequestBody PatientVo vo) {
		return ok(patientService.update(vo.toEntity()));
	}

	/**
	 * 删除病人信息
     *
	 * @param ids 病人信息id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2023-10-20 21:15:29
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:patient:remove')")
	@ApiOperation(value = "删除病人信息")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
			 @ApiParam(name = "ids" , value = "id数组" , required = true) Long[] ids) {
		patientService.deleteByIds(ids);
		return new ApiResult<>();
	}

	/**
	 * 导出excel
     *
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * date 2023-10-20 21:15:29
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:patient:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出病人信息excel")
	public ApiResult<String> export(@RequestBody PatientParams params) throws InterruptedException {
		return ok(patientService.export(params));
	}
}
