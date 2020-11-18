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

import com.github.zmzhou.easyboot.api.system.entity.SysConfig;
import com.github.zmzhou.easyboot.api.system.service.SysConfigService;
import com.github.zmzhou.easyboot.api.system.vo.SysConfigParams;
import com.github.zmzhou.easyboot.api.system.vo.SysConfigVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 参数配置Controller
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Api(tags = {"参数配置"})
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
	@Resource
	private SysConfigService sysConfigService;

	/**
	 * 查询参数配置列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2020-11-16 21:51:23
	 */
	@ApiOperation(value = "查询参数配置列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysConfigParams params) {
		Pageable pageable = getPageable(params);
		Page<SysConfig> list = sysConfigService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取参数配置详细信息
	 *
	 * @param id 参数配置id
	 * @return ApiResult<SysConfig>
	 * @author zmzhou
	 * date 2020-11-16 21:51:23
	 */
	@ApiOperation(value = "根据id获取参数配置详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<SysConfig> findById(@PathVariable(value = "id", required = false) Long id) {
		return ok(sysConfigService.findById(id));
	}
	
	/**
	 * 根据参数键名查询参数值 
	 * @param configKey 参数键名
	 * @return ApiResult<SysConfig>
	 * @author zmzhou
	 * @date 2020/11/17 11:16
	 */
	@ApiOperation(value = "根据参数键名查询参数值")
	@GetMapping(value = "/configKey/{configKey}")
	public ApiResult<String> getConfigKey(@PathVariable(value = "configKey") String configKey) {
		return ok(sysConfigService.findByKey(configKey));
	}

	/**
	 * 新增参数配置
	 *
	 * @param vo 参数配置信息
	 * @return ApiResult<SysConfig>
	 * @author zmzhou
	 * date 2020-11-16 21:51:23
	 */
	@ApiOperation(value = "新增参数配置")
	@PostMapping
	public ApiResult<SysConfig> save(@Validated @RequestBody SysConfigVo vo) {
		ApiResult<SysConfig> result = new ApiResult<>();
		if (sysConfigService.checkConfigKeyUnique(vo)) {
			return result.error("新增参数配置'" + vo.getConfigKey() + "'失败，参数键名已存在");
		}
		result.setData(sysConfigService.save(vo.toEntity()));
		return result;
	}

	/**
	 * 修改参数配置
	 *
	 * @param vo 参数配置信息
	 * @return ApiResult<SysConfig>
	 * @author zmzhou
	 * date 2020-11-16 21:51:23
	 */
	@ApiOperation(value = "修改参数配置")
	@PutMapping
	public ApiResult<SysConfig> update(@Validated @RequestBody SysConfigVo vo) {
		ApiResult<SysConfig> result = new ApiResult<>();
		if (sysConfigService.checkConfigKeyUnique(vo)) {
			return result.error("修改参数配置'" + vo.getConfigKey() + "'失败，参数键名已存在");
		}
		result.setData(sysConfigService.update(vo.toEntity()));
		return result;
	}

	/**
	 * 删除参数配置
	 *
	 * @param ids 参数配置id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2020-11-16 21:51:23
	 */
	@ApiOperation(value = "删除参数配置")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
						 @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		sysConfigService.deleteByIds(ids);
		return new ApiResult<>();
	}
	
	/**
	 * 导出excel
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * date 2020-11-16 21:51:23
	 */
	@PostMapping("/export")
	@ApiOperation(value = "导出参数配置excel")
	public ApiResult<String> export(@RequestBody SysConfigParams params) throws InterruptedException {
		return ok(sysConfigService.export(params));
	}
}
