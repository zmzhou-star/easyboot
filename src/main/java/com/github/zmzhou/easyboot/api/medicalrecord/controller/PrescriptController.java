/*
 * Copyright © 2022-2022 zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.medicalrecord.controller;

import java.util.List;

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

import com.github.zmzhou.easyboot.api.medicalrecord.entity.Prescript;
import com.github.zmzhou.easyboot.api.medicalrecord.service.PrescriptService;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PrescriptParams;
import com.github.zmzhou.easyboot.api.medicalrecord.vo.PrescriptVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 药方Controller
 *
 * @author zmzhou
 * @version 1.0
 * @since 2023-10-19 21:15:54
 */
@Api(tags = {"药方"})
@RestController
@RequestMapping("/medicalrecord/prescript")
public class PrescriptController extends BaseController {
	@Resource
	private PrescriptService prescriptService;

	/**
	 * 查询药方列表
     *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2023-10-19 21:15:54
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:list')")
	@ApiOperation(value = "查询药方列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) PrescriptParams params) {
		Pageable pageable = getPageable(params);
		Page<Prescript> list = prescriptService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取药方详细信息
     *
	 * @param id 药方id
	 * @return ApiResult<Prescript>
	 * @author zmzhou
	 * date 2023-10-19 21:15:54
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:query')")
	@ApiOperation(value = "根据id获取药方详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<Prescript> findById(@PathVariable(value = "id" , required = false) Long id) {
		return ok(prescriptService.findById(id));
	}

    /**
     * 查询所有药方
     *
     * @return com.github.zmzhou.easyboot.framework.page.ApiResult<java.util.List<com.github.zmzhou.easyboot.api.medicalrecord.entity.Prescript>>
     * @since 2023/12/17 13:32
     */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:query')")
	@ApiOperation(value = "查询所有药方")
	@GetMapping(value = "findAllPrescript")
	public ApiResult<List<Prescript>> findAllPrescript() {
		return ok(prescriptService.findAllPrescript());
	}

	/**
	 * 新增药方
     *
	 * @param vo 药方信息
	 * @return ApiResult<Prescript>
	 * @author zmzhou
	 * date 2023-10-19 21:15:54
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:add')")
	@ApiOperation(value = "新增药方")
	@PostMapping
	public ApiResult<Prescript> save(@Validated @RequestBody PrescriptVo vo) {
		return ok(prescriptService.save(vo.toEntity()));
	}

	/**
	 * 修改药方
     *
	 * @param vo 药方信息
	 * @return ApiResult<Prescript>
	 * @author zmzhou
	 * date 2023-10-19 21:15:54
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:edit')")
	@ApiOperation(value = "修改药方")
	@PutMapping
	public ApiResult<Prescript> update(@Validated @RequestBody PrescriptVo vo) {
		return ok(prescriptService.update(vo.toEntity()));
	}

	/**
	 * 删除药方
     *
	 * @param ids 药方id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2023-10-19 21:15:54
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:remove')")
	@ApiOperation(value = "删除药方")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
			 @ApiParam(name = "ids" , value = "id数组" , required = true) Long[] ids) {
		prescriptService.deleteByIds(ids);
		return new ApiResult<>();
	}

	/**
	 * 导出excel
     *
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * date 2023-10-19 21:15:54
	 */
	@PreAuthorize("@ebpe.hasPermission('medicalrecord:prescript:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出药方excel")
	public ApiResult<String> export(@RequestBody PrescriptParams params) throws InterruptedException {
		return ok(prescriptService.export(params));
	}
}
