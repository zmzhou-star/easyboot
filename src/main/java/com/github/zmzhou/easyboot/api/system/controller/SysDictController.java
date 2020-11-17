package com.github.zmzhou.easyboot.api.system.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.service.SysDictService;
import com.github.zmzhou.easyboot.api.system.vo.SysDictParams;
import com.github.zmzhou.easyboot.api.system.vo.SysDictTypeVo;
import com.github.zmzhou.easyboot.api.system.vo.SysDictVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 数据字典Controller
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Api(tags = {"数据字典信息管理"})
@RestController
@RequestMapping("/system/dict")
public class SysDictController extends BaseController {
	@Resource
	private SysDictService sysDictService;

	/**
	 * 根据字典类型查询字典数据信息
	 *
	 * @param dictType 字典类型
	 * @return ApiResult<SysDict>
	 * @author zmzhou
	 * @date 2020/08/27 11:13
	 */
	@GetMapping(value = "/getDicts/{dictType}")
	@ApiOperation(value = "根据字典类型查询字典数据信息")
	public ApiResult<SysDict> getDicts(@PathVariable
		   @ApiParam(name = "dictType", value = "字典类型", required = true) String dictType) {
		return ok(sysDictService.selectDictDataByType(dictType));
	}

	/**
	 * 获取字典类型选择框列表
	 *
	 * @return the api result
	 */
	@ApiOperation(value = "获取字典类型选择框列表")
	@GetMapping("/type/optionSelect")
	public ApiResult<SysDictTypeVo> optionSelect() {
		return ok(sysDictService.selectDictTypes());
	}
	
	/**
	 * 查询数据字典列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2020-11-16 16:47:04
	 */
	@ApiOperation(value = "查询数据字典列表")
	@PostMapping(path = {"list", "type/list"})
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysDictParams params) {
		Pageable pageable = getPageable(params);
		Page<SysDict> list = sysDictService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取数据字典详细信息
	 *
	 * @param id 数据字典id
	 * @return ApiResult<SysDict>
	 * @author zmzhou
	 * date 2020-11-16 16:47:04
	 */
	@ApiOperation(value = "根据id获取数据字典详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<SysDict> findById(@PathVariable(value = "id", required = false) Long id) {
		return ok(sysDictService.findById(id));
	}

	/**
	 * 新增数据字典
	 *
	 * @param vo 数据字典信息
	 * @return ApiResult<SysDict>
	 * @author zmzhou
	 * date 2020-11-16 16:47:04
	 */
	@ApiOperation(value = "新增数据字典")
	@PostMapping
	public ApiResult<SysDict> save(@Validated @RequestBody SysDictVo vo) {
		return ok(sysDictService.save(vo.toEntity()));
	}

	/**
	 * 修改数据字典
	 *
	 * @param vo 数据字典信息
	 * @return ApiResult<SysDict>
	 * @author zmzhou
	 * date 2020-11-16 16:47:04
	 */
	@ApiOperation(value = "修改数据字典")
	@PutMapping
	public ApiResult<SysDict> update(@Validated @RequestBody SysDictVo vo) {
		return ok(sysDictService.update(vo.toEntity()));
	}

	/**
	 * 删除数据字典
	 *
	 * @param ids 数据字典id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2020-11-16 16:47:04
	 */
	@ApiOperation(value = "删除数据字典")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
					 @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		sysDictService.deleteByIds(ids);
		return new ApiResult<>();
	}
}
