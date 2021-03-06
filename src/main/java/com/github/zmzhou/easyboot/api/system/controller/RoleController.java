package com.github.zmzhou.easyboot.api.system.controller;

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

import com.github.zmzhou.easyboot.api.system.entity.SysRole;
import com.github.zmzhou.easyboot.api.system.service.RoleService;
import com.github.zmzhou.easyboot.api.system.vo.SysRoleParams;
import com.github.zmzhou.easyboot.api.system.vo.SysRoleVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.vo.Params;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 * @author zmzhou
 * @version 1.0
 * date 2020/9/21 21:42
 */
@Api(tags = {"角色管理"})
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {
	@Resource
	private RoleService roleService;
	
	/**
	 * 获取角色列表
	 * @param params 查询参数
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * date 2020/08/28 17:54
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:list')")
	@PostMapping(path = "list")
	@ApiOperation(value = "获取角色列表")
	public ApiResult<TableDataInfo> list(@RequestBody(required = false) SysRoleParams params) {
		Pageable pageable = getPageable(params);
		Page<SysRole> list = roleService.findAll(params, pageable);
		return ok(list);
	}
	/**
	 * 导出excel
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/8/30 21:50
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:export')")
	@PostMapping("/export")
	@ApiOperation(value = "导出角色excel")
	public ApiResult<String> export(@RequestBody(required = false) SysRoleParams params) throws InterruptedException {
		return ok(roleService.export(params));
	}

	/**
	 * 根据id获取角色信息
	 * @param id 角色id
	 * @return ApiResult<SysUser>
	 * @author zmzhou
	 * @date 2020/08/28 18:57
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:query')")
	@GetMapping(value = {"getOne", "getOne/{id}"})
	@ApiOperation(value = "根据id获取角色信息")
	public ApiResult<SysRole> getOne(@PathVariable(value = "id", required = false) Long id) {
		return ok(roleService.getOne(id));
	}
	
	/**
	 * 根据id修改角色状态
	 * @param params 参数
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/07/07 14:02
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:changeStatus')")
	@PutMapping("/changeStatus")
	@ApiOperation(value = "根据id修改角色状态")
	public ApiResult<Integer> changeStatus(@RequestBody(required = false) Params params) {
		return ok(roleService.updateUserStatus(params.getId(), params.getStatus()));
	}
	
	/**
	 * 新增角色
	 * @param role 角色信息
	 * @return ApiResult<SysRole>
	 * @author zmzhou
	 * @date 2020/08/29 18:34
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:add')")
	@PostMapping
	@ApiOperation(value = "新增角色")
	public ApiResult<SysRole> add(@Validated @RequestBody SysRoleVo role) {
		ApiResult<SysRole> result = new ApiResult<>();
		// 校验角色名称，角色编码是否唯一
		if (roleService.checkRoleNameUnique(role)) {
			return result.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (roleService.checkRoleCodeUnique(role)) {
			return result.error("新增角色'" + role.getRoleName() + "'失败，角色编码已存在");
		}
		result.setData(roleService.insertRole(role.toEntity()));
		return result;
	}

	/**
	 * 修改角色信息
	 * @param role 角色信息
	 * @return ApiResult<Integer>
	 * @author zmzhou
	 * @date 2020/08/29 18:38
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:edit')")
	@PutMapping
	@ApiOperation(value = "修改角色信息")
	public ApiResult<Integer> update(@Validated @RequestBody SysRoleVo role) {
		ApiResult<Integer> result = new ApiResult<>();
		// 不允许操作超级管理员角色
		roleService.checkRoleAllowed(role);
		if (roleService.checkRoleNameUnique(role)) {
			return result.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
		} else if (roleService.checkRoleCodeUnique(role)) {
			return result.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
		}
		result.setData(roleService.updateRole(role.toEntity()));
		return result;
	}

	/**
	 * 删除角色
	 * @param roleIds 角色id集合
	 * @return 删除结果
	 * @author zmzhou
	 * @date 2020/08/29 18:36
	 */
	@PreAuthorize("@ebpe.hasPermission('system:role:remove')")
	@DeleteMapping("/{roleIds}")
	@ApiOperation(value = "删除角色")
	public ApiResult<Integer> remove(@PathVariable Long[] roleIds) {
		return ok(roleService.deleteRoleByIds(roleIds));
	}
}
