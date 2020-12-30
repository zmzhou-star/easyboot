package com.github.zmzhou.easyboot.api.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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

import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.api.system.service.MenuService;
import com.github.zmzhou.easyboot.api.system.vo.SysMenuParams;
import com.github.zmzhou.easyboot.api.system.vo.SysMenuVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SpringUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.service.TokenService;
import com.github.zmzhou.easyboot.framework.vo.TreeSelect;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *  @title MenuController
 *  @Description 菜单管理
 *  @author zmzhou
 *  @Date 2020/08/27 11:42
 */
@Api(tags = {"菜单管理"})
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {
	@Resource
	private MenuService menuService;
	@Resource
	private TokenService tokenService;

	/**
	 * 获取菜单列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDatInfo>
	 * @author zmzhou
	 * @date 2020/08/27 11:42
	 */
	@PreAuthorize("@ebpe.hasPermission('system:menu:list')")
	@PostMapping(path = "list")
	@ApiOperation(value = "获取菜单列表")
	public ApiResult<List<SysMenu>> list(@RequestBody(required = false) SysMenuParams params) {
		List<SysMenu> list = menuService.findAll(params);
		return ok(list);
	}

	/**
	 * 根据菜单id获取详细信息
	 * @param id 菜单id
	 * @return 菜单信息
	 * @author zmzhou
	 * @date 2020/9/8 22:59
	 */
	@PreAuthorize("@ebpe.hasPermission('system:menu:query')")
	@GetMapping(value = {"getOne", "getOne/{id}"})
	@ApiOperation(value = "根据菜单id获取详细信息")
	public ApiResult<SysMenu> getOne(@PathVariable(value = "id", required = false) Long id) {
		return ok(menuService.getOne(id));
	}

	/**
	 * 获取菜单下拉树列表
	 * @param menu 参数
	 * @return ApiResult<List<TreeSelect>>
	 * @author zmzhou
	 * @date 2020/08/29 18:19
	 */
	@GetMapping("/treeSelect")
	@ApiOperation(value = "获取菜单下拉树列表")
	public ApiResult<List<TreeSelect>> treeSelect(SysMenuVo menu) {
		LoginUser loginUser = tokenService.getLoginUser(SpringUtils.getRequest());
		// 当前登录用户id
		Long userId = loginUser.getUser().getId();
		List<SysMenu> menus = menuService.selectMenuList(menu.toEntity(), userId);
		return ok(menuService.buildMenuTreeSelect(menus));
	}

	/**
	 * 加载对应角色菜单列表树
	 * @param roleId 角色id
	 * @return ApiResult<JSONObject>
	 * @author zmzhou
	 * @date 2020/08/29 17:04
	 */
	@GetMapping(value = "/roleMenuTreeSelect/{roleId}")
	@ApiOperation(value = "加载对应角色菜单列表树")
	public ApiResult<JSONObject> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
		LoginUser loginUser = tokenService.getLoginUser(SpringUtils.getRequest());
		// 根据用户查询系统菜单列表
		List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getId());
		JSONObject res = new JSONObject();
		// 根据角色ID查询已选中的菜单树id列表
		res.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
		// 构建前端所需要下拉树结构
		res.put("menus", menuService.buildMenuTreeSelect(menus));
		return ok(res);
	}

	/**
	 * 新增菜单
	 *
	 * @param menuVo 菜单信息
	 * @return ApiResult<SysMenu>
	 * @author zmzhou
	 * @date 2020/9/8 23:23
	 */
	@PreAuthorize("@ebpe.hasPermission('system:menu:add')")
	@PostMapping
	@ApiOperation(value = "新增菜单")
	public ApiResult<SysMenu> add(@Validated @RequestBody SysMenuVo menuVo) {
		ApiResult<SysMenu> result = new ApiResult<>();
		// 校验菜单名称是否唯一
		if (menuService.checkMenuNameUnique(menuVo)) {
			return result.error("新增菜单'" + menuVo.getMenuName() + "'失败，菜单名称已存在");
		} else if (Constants.ONE.equals(menuVo.getIsFrame())
				&& !StringUtils.startsWithAny(menuVo.getPath(), Constants.HTTP, Constants.HTTPS)) {
			return result.error("新增菜单'" + menuVo.getMenuName() + "'失败，地址必须以http(s)://开头");
		}
		result.setData(menuService.save(menuVo.toEntity()));
		return result;
	}

	/**
	 * 修改菜单信息
	 *
	 * @param menuVo 菜单信息
	 * @return ApiResult<SysMenu>
	 * @author zmzhou
	 * @date 2020/9/9 22:06
	 */
	@PreAuthorize("@ebpe.hasPermission('system:menu:edit')")
	@PutMapping
	@ApiOperation(value = "修改菜单信息")
	public ApiResult<SysMenu> update(@Validated @RequestBody SysMenuVo menuVo) {
		ApiResult<SysMenu> result = new ApiResult<>();
		// 校验菜单名称是否唯一
		if (menuService.checkMenuNameUnique(menuVo)) {
			return result.error("修改菜单'" + menuVo.getMenuName() + "'失败，菜单名称已存在");
		} else if (Constants.ONE.equals(menuVo.getIsFrame())
				&& !StringUtils.startsWithAny(menuVo.getPath(), Constants.HTTP, Constants.HTTPS)) {
			return result.error("修改菜单'" + menuVo.getMenuName() + "'失败，地址必须以http(s)://开头");
		}
		result.setData(menuService.update(menuVo.toEntity()));
		return result;
	}

	/**
	 * 删除菜单
	 *
	 * @param menuId 菜单id
	 * @return ApiResult<Integer>
	 * @author zmzhou
	 * @date 2020/9/9 22:13
	 */
	@PreAuthorize("@ebpe.hasPermission('system:menu:remove')")
	@DeleteMapping("/{menuId}")
	@ApiOperation(value = "删除菜单")
	public ApiResult<Integer> remove(@PathVariable("menuId") Long menuId) {
		ApiResult<Integer> result = new ApiResult<>();
		// 判断是否存在菜单子节点
		if (menuService.hasChildByMenuId(menuId)) {
			return result.error("存在子菜单,不允许删除");
		}
		if (menuService.checkMenuExistRole(menuId)) {
			return result.error("菜单已分配,不允许删除");
		}
		menuService.deleteMenuById(menuId);
		return result;
	}
}
