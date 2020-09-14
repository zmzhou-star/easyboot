package com.github.zmzhou.easyboot.api.monitor.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.service.SysUserOnlineService;
import com.github.zmzhou.easyboot.api.monitor.vo.SysUserOnlineVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 在线用户监控
 * @title SysUserOnlineController
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/12 17:20
 */
@Api(tags = {"在线用户监控"})
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
	@Resource
	private SysUserOnlineService userOnlineService;
	@Resource
	private TokenService tokenService;

	/**
	 * 获取在线用户列表
	 *
	 * @param userName 用户名
	 * @param ipAddr   IP地址
	 * @return 在线用户列表
	 * @author zmzhou
	 * @date 2020/9/12 17:29
	 */
	@ApiOperation(value = "获取在线用户列表")
	@GetMapping("/list")
	public ApiResult<List<SysUserOnlineVo>> list(@ApiParam(name = "userName", value = "用户名") String userName,
	                                             @ApiParam(name = "ipAddr", value = "IP地址") String ipAddr) {
		return ok(userOnlineService.findAll(userName, ipAddr));
	}

	/**
	 * 强退用户
	 *
	 * @param tokenId tokenId
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/12 18:06
	 */
	@ApiOperation(value = "强退用户")
	@DeleteMapping("/{tokenId}")
	public ApiResult<String> forceLogout(@ApiParam(name = "tokenId", value = "tokenId", required = true)
	                                     @PathVariable() String tokenId) {
		tokenService.delLoginUser(tokenId);
		return ApiResult.builder();
	}
}
