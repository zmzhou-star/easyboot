package com.zmzhou.easyboot.api.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.api.system.service.UserService;
import com.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.zmzhou.easyboot.common.ErrorCode;
import com.zmzhou.easyboot.framework.entity.Params;
import com.zmzhou.easyboot.framework.page.ApiResult;
import com.zmzhou.easyboot.framework.page.TableDataInfo;
import com.zmzhou.easyboot.framework.web.BaseController;

/**
 * @author zmzhou
 * @description 用户管理
 * @date 2020/07/02 17:02
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	
	/**
	 * 根据id获取用户信息
	 * @param id 用户id
	 * @return ApiResult<SysUser>
	 * @author zmzhou
	 * @date 2020/07/08 11:50
	 */
	@GetMapping(value = {"getOne", "getOne/{id}"})
	public ApiResult<SysUser> getUser(@PathVariable(value = "id", required = false) Long id) {
		return ok(userService.getUser(id));
	}
	
	/**
	 * 获取用户列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * @date 2020/07/02 18:53
	 */
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody Params params) {
		Pageable pageable = getPageable(params);
		Page<SysUser> list = userService.findAll(params, pageable);
		return ok(list);
	}
	
	/**
	 * 保存用户
	 * @param user 用户信息
	 * @return {@link ApiResult}
	 * @author zmzhou
	 * @date 2020/07/02 18:52
	 */
	@PostMapping(path = "save")
	public ApiResult<SysUser> save(@Validated @RequestBody SysUserVo user) {
		ApiResult<SysUser> result = new ApiResult<>();
		if (null == user) {
			return result.error(ErrorCode.PARAM_ISNULL);
		}
		if (userService.exists(user)) {
			return result.error(ErrorCode.USER_EXISTS);
		}
		result.setData(userService.save(user));
		return result;
	}
	/**
	 * 修改用户
	 * @param user 用户信息
	 * @return {@link ApiResult}
	 * @author zmzhou
	 * @date 2020/07/02 18:52
	 */
	@PutMapping(path = "update")
	public ApiResult<SysUser> update(@Validated @RequestBody SysUserVo user) {
		ApiResult<SysUser> result = new ApiResult<>();
		if (null == user || null == user.getId()) {
			return result.error(ErrorCode.PARAM_ISNULL);
		}
		result.setData(userService.update(user));
		return result;
	}
	
	/**
	 * 根据id修改用户状态
	 * @param params 参数
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/07/07 14:02
	 */
	@PutMapping("/changeStatus")
	public ApiResult<Integer> changeStatus(@RequestBody Params params) {
		return ok(userService.updateUserStatus(params.getId(), params.getStatus()));
	}
	
	/**
	 * 删除用户
	 * @param ids 用户ID
	 * @author zmzhou
	 * @date 2020/07/02 18:51
	 */
	@DeleteMapping("delete/{ids}")
	public ApiResult<Object> delete(@PathVariable("ids") Long[] ids) {
		userService.delete(ids);
		return new ApiResult<>();
	}
	/**
	 * 重置密码
	 * @param params 用户ID和密码
	 * @author zmzhou
	 * @date 2020/07/02 18:51
	 */
	@PutMapping("resetPwd")
	public ApiResult<SysUser> resetPwd(@RequestBody Params params) {
		return ok(userService.resetPwd(params.getId(), params.getPassword()));
	}
}
