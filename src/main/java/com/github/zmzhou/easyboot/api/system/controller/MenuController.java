package com.github.zmzhou.easyboot.api.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.api.system.service.MenuService;
import com.github.zmzhou.easyboot.api.system.vo.SysMenuVo;
import com.github.zmzhou.easyboot.common.utils.ServletUtils;
import com.github.zmzhou.easyboot.framework.entity.Params;
import com.github.zmzhou.easyboot.framework.entity.TreeSelect;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.page.TableDataInfo;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;
import com.github.zmzhou.easyboot.framework.web.BaseController;

/**
 *  @title MenuController
 *  @Description 菜单管理
 *  @author zmzhou
 *  @Date 2020/08/27 11:42
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 获取菜单列表
	 *
	 * @param params 查询参数
	 * @return ApiResult<TableDatInfo>
	 * @author zmzhou
	 * @date 2020/08/27 11:42
	 */
	@PostMapping(path = "list")
	public ApiResult<TableDataInfo> list(@RequestBody Params params) {
		Pageable pageable = getPageable(params);
		Page<SysMenu> list = menuService.findAll(params, pageable);
		return ok(list);
	}
	
	/**
	 * 获取菜单下拉树列表
	 * @param menu 参数
	 * @return ApiResult<List<TreeSelect>>
	 * @author zmzhou
	 * @date 2020/08/29 18:19
	 */
	@GetMapping("/treeSelect")
	public ApiResult<List<TreeSelect>> treeSelect(SysMenuVo menu) {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
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
	public ApiResult<JSONObject> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
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
	 * @param menu 菜单信息
	 * @return
	 * @author zmzhou
	 * @date 2020/08/29 18:30
	 */
//	@PostMapping
//	public AjaxResult add(@Validated @RequestBody SysMenu menu)
//	{
//		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
//		{
//			return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
//		}
//		menu.setCreateBy(SecurityUtils.getUsername());
//		return toAjax(menuService.insertMenu(menu));
//	}
}
