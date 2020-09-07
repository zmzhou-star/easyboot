package com.zmzhou.easyboot.api.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
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
import com.zmzhou.easyboot.api.system.excel.SysUserExcel;
import com.zmzhou.easyboot.api.system.service.UserService;
import com.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.zmzhou.easyboot.common.ErrorCode;
import com.zmzhou.easyboot.common.excel.ExcelUtils;
import com.zmzhou.easyboot.common.utils.FileUploadUtils;
import com.zmzhou.easyboot.common.utils.ServletUtils;
import com.zmzhou.easyboot.framework.entity.Params;
import com.zmzhou.easyboot.framework.page.ApiResult;
import com.zmzhou.easyboot.framework.page.TableDataInfo;
import com.zmzhou.easyboot.framework.web.BaseController;

/**
 * @title UserController
 * @description  用户管理
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/5 21:56
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private FileUploadUtils fileUploadUtils;
	@Autowired
	private ExcelUtils excelUtils;
	
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
	 * @description 导出excel
	 * @param params 查询参数
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/8/30 21:50
	 */
	@PostMapping("/export")
	public ApiResult<String> export(@RequestBody(required = false) Params params) {
		return ok(userService.export(params));
	}
	/**
	 * @description 下载excel导入模板
	 * @return ApiResult<String> excel文件名
	 * @author zmzhou
	 * @date 2020/9/5 21:17
	 */
	@GetMapping("/excelTemplate")
	public ApiResult<String> excelTemplate() {
		return ok(userService.excelTemplate(SysUserExcel.class, "用户管理导入模板"));
	}

	/**
	 * 导入excel
	 * @param isUpdate 是否更新数据库的数据
	 * @return ApiResult
	 * @author zmzhou
	 * @date 2020/9/6 13:54
	 */
	@PostMapping("/importExcel")
	public ApiResult<String> importExcel(boolean isUpdate) throws IOException {
		FileItem file = fileUploadUtils.singleUpload(ServletUtils.getRequest());
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
	@PostMapping(path = "save")
	public ApiResult<SysUser> save(@Validated @RequestBody SysUserVo user) {
		ApiResult<SysUser> result = new ApiResult<>();
		if (null == user) {
			return result.error(ErrorCode.PARAM_ISNULL);
		}
		if (userService.exists(user.toEntity())) {
			return result.error(ErrorCode.USER_EXISTS);
		}
		result.setData(userService.save(user.toEntity()));
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
		result.setData(userService.update(user.toEntity()));
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
