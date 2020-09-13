package com.github.zmzhou.easyboot.api.system.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.service.RoleService;
import com.github.zmzhou.easyboot.api.system.service.UserService;
import com.github.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.github.zmzhou.easyboot.api.system.vo.UserInfo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.ErrorCode;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.FileUploadUtils;
import com.github.zmzhou.easyboot.common.utils.FileUtil;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.common.utils.ServletUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zmzhou
 * @version 1.0
 * @title SysUserProfileController
 * @description 用户个人中心管理
 * @date 2020/9/5 22:03
 */
@Slf4j
@Api(tags = {"用户个人中心管理"})
@RestController
@RequestMapping("/system/user/profile")
public class SysUserProfileController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private FileUploadUtils fileUploadUtils;

	/**
	 * 获取用户个人信息
	 * @return ApiResult<JSONObject>
	 * @author zmzhou
	 * @date 2020/9/5 22:24
	 */
	@GetMapping
	@ApiOperation(value = "获取用户个人信息")
	public ApiResult<UserInfo> profile() {
		// 从redis缓存获取当前用户身份信息
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		UserInfo info = UserInfo.builder().user(user)
				// 获取用户角色名称集合
				.roleName(roleService.selectUserRole(user.getId())).build();
		return ok(info);
	}

	/**
	 * 修改用户
	 *
	 * @param user 用户信息
	 * @return {@link ApiResult}
	 * @author zmzhou
	 * @date 2020/9/5 22:25
	 */
	@PutMapping
	@ApiOperation(value = "修改用户个人信息")
	public ApiResult<SysUser> update(@Validated @RequestBody SysUserVo user) {
		ApiResult<SysUser> result = new ApiResult<>();
		if (null == user || null == user.getId()) {
			return result.error(ErrorCode.PARAM_ISNULL);
		}
		// 更新用户信息
		SysUser sysUser = userService.update(user.toEntity());
		result.setData(sysUser);
		// 更新缓存中的用户信息
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		loginUser.setUser(sysUser);
		tokenService.setLoginUser(loginUser);
		return result;
	}

	/**
	 * 修改密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @return {@link ApiResult}
	 * @author zmzhou
	 * @date 2020/9/5 22:43
	 */
	@PutMapping("/updatePwd")
	@ApiOperation(value = "修改用户密码")
	public ApiResult<SysUser> updatePwd(@ApiParam(name = "oldPassword",value="原密码",required=true)String oldPassword,
                    @ApiParam(name = "newPassword", value = "新密码", required = true) String newPassword) {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser sysUser = loginUser.getUser();
		String password = loginUser.getPassword();
		if (!SecurityUtils.matchesPassword(oldPassword, password)) {
			throw new BaseException(ErrorCode.PASSWD_ERROR, "修改密码失败，原密码错误");
		}
		if (SecurityUtils.matchesPassword(newPassword, password)) {
			throw new BaseException(ErrorCode.PASSWD_ERROR, "新密码不能与原密码相同");
		}
		sysUser = userService.resetPwd(sysUser.getId(), newPassword);
		// 更新缓存信息
		loginUser.setUser(sysUser);
		tokenService.setLoginUser(loginUser);
		return ok(sysUser);
	}

	/**
	 * 用户上传头像
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/5 22:45
	 */
	@PostMapping("/avatar")
	@ApiOperation(value = "用户上传头像")
	public ApiResult<SysUser> avatar() {
		HttpServletRequest request = ServletUtils.getRequest();
		LoginUser loginUser = tokenService.getLoginUser(request);
		// 头像文件
		FileItem avatar = fileUploadUtils.singleUpload(request);
		// 保存的文件名
		String fileName = loginUser.getUsername() + Constants.SEPARATOR
				+ Constants.AVATAR + FileUtil.getFileSuffix(avatar.getContentType());
		log.debug("上传头像文件：{}", fileName);
		SysUser user = loginUser.getUser();
		try {
			// 保存文件
			FileUtils.copyInputStreamToFile(avatar.getInputStream(),
					new File(fileUploadUtils.getAvatarPath(), fileName));
			user.setAvatar(fileName);
			// 更新用户信息
			user = userService.update(user);
		} catch (IOException e) {
			log.error("保存头像文件：{}异常", fileName, e);
			throw new BaseException(HttpStatus.BAD_REQUEST, "保存头像文件失败");
		}
		// 更新缓存用户头像
		loginUser.setUser(user);
		tokenService.setLoginUser(loginUser);
		return ok(user);
	}
}
