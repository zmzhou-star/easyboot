package com.github.zmzhou.easyboot.api.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.common.service.DashboardService;
import com.github.zmzhou.easyboot.api.common.vo.CardVo;
import com.github.zmzhou.easyboot.api.monitor.entity.SysLoginLog;
import com.github.zmzhou.easyboot.api.monitor.service.SysLoginLogService;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.vo.PageParams;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页监控面板
 * @author zmzhou
 * @version 1.0
 * @date 2020/12/21 10:36
 */
@Api(tags = {"首页监控面板"})
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {
	@Resource
	private DashboardService service;
	@Resource
	private SysLoginLogService loginLogService;

	/**
	 * 首页面板卡片统计
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/12/21 10:43
	 */
	@PreAuthorize("@ebpe.hasRole('admin')")
	@PostMapping("/cardStat")
	@ApiOperation(value = "首页面板卡片统计")
	public ApiResult<CardVo> cardStat() {
		return ok(service.cardStat());
	}
	/**
	 * 用户登录日志记录统计
	 * @param params 查询参数
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/12/20 16:51
	 */
	@PreAuthorize("@ebpe.hasRole('admin')")
	@PostMapping("/userLoginStat")
	@ApiOperation(value = "用户登录日志记录统计")
	public ApiResult<List<SysLoginLog>> userLoginStat(@RequestBody(required = false) PageParams params) {
		return ok(loginLogService.stat(params));
	}
}
