package com.github.zmzhou.easyboot.api.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.service.SysDictService;
import com.github.zmzhou.easyboot.framework.web.BaseController;

/**
 *  @title SysDictController
 *  @Description 数据字典信息管理
 *  @author zmzhou
 *  @Date 2020/08/27 11:29
 */
@RestController
@RequestMapping("/system/dict")
public class SysDictController extends BaseController {
	@Autowired
	private SysDictService dictService;
	
	/**
	 * 根据字典类型查询字典数据信息
	 * @param dictType 字典类型
	 * @return ApiResult<SysDict>
	 * @author zmzhou
	 * @date 2020/08/27 11:13
	 */
	@GetMapping(value = "/getDicts/{dictType}")
	public ApiResult<SysDict> getDicts(@PathVariable String dictType) {
		return ok(dictService.selectDictDataByType(dictType));
	}
}
