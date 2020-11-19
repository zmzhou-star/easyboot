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

import com.github.zmzhou.easyboot.api.system.entity.SysNotice;
import com.github.zmzhou.easyboot.api.system.service.SysNoticeService;
import com.github.zmzhou.easyboot.api.system.vo.SysNoticeParams;
import com.github.zmzhou.easyboot.api.system.vo.SysNoticeVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 通知公告信息Controller
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-19 14:15:47
 */
@Api(tags = {"通知公告信息"})
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
	@Resource
	private SysNoticeService sysNoticeService;

	/**
	 * 查询通知公告信息列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2020-11-19 14:15:47
	 */
	@ApiOperation(value = "查询通知公告信息列表")
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysNoticeParams params) {
		Pageable pageable = getPageable(params);
		Page<SysNotice> list = sysNoticeService.findAll(params, pageable);
		return ok(list);
	}

	/**
	 * 根据id获取通知公告信息详细信息
	 *
	 * @param id 通知公告信息id
	 * @return ApiResult<SysNotice>
	 * @author zmzhou
	 * date 2020-11-19 14:15:47
	 */
	@ApiOperation(value = "根据id获取通知公告信息详细信息")
	@GetMapping(value = "/{id}")
	public ApiResult<SysNotice> findById(@PathVariable(value = "id", required = false) Long id) {
		return ok(sysNoticeService.findById(id));
	}

	/**
	 * 新增通知公告信息
	 *
	 * @param vo 通知公告信息信息
	 * @return ApiResult<SysNotice>
	 * @author zmzhou
	 * date 2020-11-19 14:15:47
	 */
	@ApiOperation(value = "新增通知公告信息")
	@PostMapping
	public ApiResult<SysNotice> save(@Validated @RequestBody SysNoticeVo vo) {
		return ok(sysNoticeService.save(vo.toEntity()));
	}

	/**
	 * 修改通知公告信息
	 *
	 * @param vo 通知公告信息信息
	 * @return ApiResult<SysNotice>
	 * @author zmzhou
	 * date 2020-11-19 14:15:47
	 */
	@ApiOperation(value = "修改通知公告信息")
	@PutMapping
	public ApiResult<SysNotice> update(@Validated @RequestBody SysNoticeVo vo) {
		return ok(sysNoticeService.update(vo.toEntity()));
	}

	/**
	 * 删除通知公告信息
	 *
	 * @param ids 通知公告信息id集合
	 * @return 删除结果
	 * @author zmzhou
	 * date 2020-11-19 14:15:47
	 */
	@ApiOperation(value = "删除通知公告信息")
	@DeleteMapping("/{ids}")
	public ApiResult<Integer> remove(@PathVariable("ids")
			 @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		sysNoticeService.deleteByIds(ids);
		return new ApiResult<>();
	}
}
