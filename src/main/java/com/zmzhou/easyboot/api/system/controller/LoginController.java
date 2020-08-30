package com.zmzhou.easyboot.api.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zmzhou.easyboot.api.system.entity.SysMenu;
import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.api.system.service.LoginService;
import com.zmzhou.easyboot.api.system.service.MenuService;
import com.zmzhou.easyboot.api.system.service.RoleService;
import com.zmzhou.easyboot.api.system.vo.RouterVo;
import com.zmzhou.easyboot.common.Constants;
import com.zmzhou.easyboot.common.utils.ServletUtils;
import com.zmzhou.easyboot.framework.page.ApiResult;
import com.zmzhou.easyboot.framework.security.LoginBody;
import com.zmzhou.easyboot.framework.security.LoginUser;
import com.zmzhou.easyboot.framework.security.service.TokenService;
import com.zmzhou.easyboot.framework.web.BaseController;

/**
 * @author zmzhou
 * @title LoginController
 * @Description 用户登录
 * @Date 2020/07/10 9:36
 */
@RestController
public class LoginController extends BaseController {
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 用户登录
	 *
	 * @param loginBody 用户名密码
	 * @author zmzhou
	 * @date 2020/07/03 14:54
	 */
	@PostMapping("login")
	public ApiResult<JSONObject> login(@RequestBody LoginBody loginBody) {
		ApiResult<JSONObject> result = new ApiResult<>();
		// 生成令牌
		String token = loginService.login(loginBody);
		JSONObject json = new JSONObject();
		json.put(Constants.TOKEN, token);
		result.setData(json);
		return result;
	}
	
	/**
	 * 方法描述
	 *
	 * @author zmzhou
	 * @date 2020/07/03 17:29
	 */
	@GetMapping("getUserInfo")
	public ApiResult<JSONObject> getUserInfo() {
		ApiResult<JSONObject> result = new ApiResult<>();
		JSONObject json = new JSONObject();
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		json.put("user", user);
		// 角色集合
		json.put("roles", roleService.getRolePermission(user));
		// 菜单权限集合
		json.put("permissions", menuService.getMenuPermission(user));
		result.setData(json);
		return result;
	}
	
	/**
	 * 获取路由信息
	 *
	 * @return ApiResult<SysMenu>
	 * @author zmzhou
	 * @date 2020/07/10 9:50
	 */
	@GetMapping("getRouters")
	public ApiResult<RouterVo> getRouters() {
		// 获取当前登录用户信息
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		// 用户信息
		SysUser user = loginUser.getUser();
		// 根据用户ID查询菜单
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getId());
		// 构建前端路由所需要的菜单
		return ok(menuService.buildMenus(menus));
	}
}
