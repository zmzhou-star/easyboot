package com.github.zmzhou.easyboot.api.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.BeanUtils;
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

import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.excel.SysUserExcel;
import com.github.zmzhou.easyboot.api.system.service.RoleService;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.api.system.vo.SysUserParams;
import com.github.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.excel.ExcelUtils;
import com.github.zmzhou.easyboot.common.utils.FileUploadUtils;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.vo.Params;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @title UserController
 * @description  用户管理
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/5 21:56
 */
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private FileUploadUtils fileUploadUtils;
	@Resource
	private ExcelUtils excelUtils;
	
	/**
	 * 根据id获取用户信息
	 * @param id 用户id
	 * @return ApiResult<SysUser>
	 * @author zmzhou
	 * @date 2020/07/08 11:50
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:query')")
	@GetMapping(value = {"getOne", "getOne/{id}"})
	@ApiOperation(value = "根据id获取用户信息")
	public ApiResult<SysUserVo> getUser(@PathVariable(value = "id", required = false)
                    @ApiParam(name = "id", value = "用户id") Long id) {
		SysUser sysUser = userService.getUser(id);
		SysUserVo user = new SysUserVo();
		BeanUtils.copyProperties(sysUser, user);
		// 根据用户id查询用户角色
		user.setRoles(roleService.getRoleIds(id));
		user.setRoleList(roleService.getRoleOptions());
		return ok(user);
	}
	
	/**
	 * 获取用户列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * @date 2020/07/02 18:53
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:list') or @ebpe.hasRole('admin')")
	@PostMapping(path = "list")
	@ApiOperation(value = "获取用户列表")
	public ApiResult<TableDataInfo> list(@RequestBody SysUserParams params) {
		Pageable pageable = getPageable(params);
		Page<SysUser> list = userService.findAll(params, pageable);
		return ok(list);
	}
	/**
	 * 导出excel
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/8/30 21:50
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出用户excel")
	public ApiResult<String> export(@RequestBody(required = false) SysUserParams params) throws InterruptedException {
		return ok(userService.export(params));
	}
	/**
	 * 下载excel导入模板
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/9/5 21:17
	 */
	@GetMapping("/excelTemplate")
	@ApiOperation(value = "下载excel导入模板")
	public ApiResult<String> excelTemplate() throws InterruptedException {
		return ok(userService.excelTemplate(SysUserExcel.class, "用户管理导入模板"));
	}

	/**
	 * 导入excel
	 * @param isUpdate 是否更新数据库的数据
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/6 13:54
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:import')")
	@PostMapping("/importExcel")
	@ApiOperation(value = "导入用户excel")
	public ApiResult<String> importExcel(boolean isUpdate) throws IOException {
		FileItem file = fileUploadUtils.singleUpload(SpringUtils.getRequest());
		// excel数据
		List<SysUserExcel> list = new ArrayList<>();
		// 读取excel数据
		excelUtils.simpleRead(file.getInputStream(), SysUserExcel.class, list);
		return ok(userService.importExcel(list, isUpdate));
	}
	/**
	 * 保存用户
	 * @param user 用户信息
	 * @return {@link ApiResult}
	 * @author zmzhou
	 * @date 2020/07/02 18:52
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:add')")
	@PostMapping(path = "save")
	@ApiOperation(value = "保存用户")
	public ApiResult<SysUser> save(@Validated @RequestBody SysUserVo user) {
		ApiResult<SysUser> result = new ApiResult<>();
		if (null == user) {
			return result.error(ErrorCode.PARAM_ISNULL);
		}
		if (userService.exists(user.toEntity())) {
			return result.error(ErrorCode.USER_EXISTS);
		}
		// 保存用户和用户的角色
		result.setData(userService.saveUserRole(user));
		return result;
	}
	/**
	 * 修改用户
	 * @param user 用户信息
	 * @return {@link ApiResult}
	 * @author zmzhou
	 * @date 2020/07/02 18:52
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:edit')")
	@PutMapping(path = "update")
	@ApiOperation(value = "修改用户信息")
	public ApiResult<SysUser> update(@Validated @RequestBody SysUserVo user) {
		ApiResult<SysUser> result = new ApiResult<>();
		if (null == user || null == user.getId()) {
			return result.error(ErrorCode.PARAM_ISNULL);
		}
		// 更新用户和用户的角色
		result.setData(userService.saveUserRole(user));
		return result;
	}
	
	/**
	 * 根据id修改用户状态
	 * @param params 参数
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/07/07 14:02
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:changeStatus')")
	@PutMapping("/changeStatus")
	@ApiOperation(value = "根据id修改用户状态")
	public ApiResult<Integer> changeStatus(@RequestBody Params params) {
		return ok(userService.updateUserStatus(params.getId(), params.getStatus()));
	}
	
	/**
	 * 删除用户
	 * @param ids 用户ID
	 * @author zmzhou
	 * @date 2020/07/02 18:51
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:remove')")
	@DeleteMapping("delete/{ids}")
	@ApiOperation(value = "删除用户")
	public ApiResult<Object> delete(@PathVariable("ids")
            @ApiParam(name = "ids", value = "id数组", required = true) Long[] ids) {
		userService.delete(ids);
		return new ApiResult<>();
	}
	/**
	 * 重置密码
	 * @param id 用户ID
	 * @param password 新密码
	 * @author zmzhou
	 * @date 2020/07/02 18:51
	 */
	@PreAuthorize("@ebpe.hasPermission('system:user:resetPwd')")
	@PutMapping("resetPwd")
	@ApiOperation(value = "修改密码")
	public ApiResult<SysUser> resetPwd(@ApiParam(name = "id", value = "用户ID", required = true) Long id,
                       @ApiParam(name = "password", value = "新密码", required = true) String password) {
		return ok(userService.resetPwd(id, password));
	}
}
