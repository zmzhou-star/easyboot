package com.zmzhou.easyboot.api.system.controller;

import java.util.Collections;
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
import com.zmzhou.easyboot.api.system.vo.UserInfo;
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
	 * @param loginBody 用户名密码
	 * @return {token:token}
	 * @author zmzhou
	 * @date 2020/07/03 14:54
	 */
	@PostMapping("login")
	public ApiResult<JSONObject> login(@RequestBody LoginBody loginBody) {
		// 登录验证并生成令牌返回
		String token = loginService.login(loginBody);
		return ok(Collections.singletonMap(Constants.TOKEN, token));
	}
	
	/**
	 * 获取用户信息
	 *
	 * @author zmzhou
	 * @date 2020/07/03 17:29
	 */
	@GetMapping("getUserInfo")
	public ApiResult<UserInfo> getUserInfo() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		// 用户信息、角色、菜单权限信息
		UserInfo userInfo = UserInfo.builder()
				.user(user)
				.roles(roleService.getRolePermission(user))
				.permissions(menuService.getMenuPermission(user))
				.build();
		return ok(userInfo);
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
