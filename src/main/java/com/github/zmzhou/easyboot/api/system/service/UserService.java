package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.github.zmzhou.easyboot.api.system.dao.UserDao;
import com.github.zmzhou.easyboot.api.system.dao.UserRoleDao;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.entity.SysUserRole;
import com.github.zmzhou.easyboot.api.system.excel.SysUserExcel;
import com.github.zmzhou.easyboot.api.system.vo.SysUserParams;
import com.github.zmzhou.easyboot.api.system.vo.SysUserVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.FileUploadUtils;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.common.utils.ThreadPoolUtils;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 *  @title UserService
 *  @Description 用户service类
 *  @author zmzhou
 *  @Date 2020/07/06 17:31
 */
@Slf4j
@Service
@CacheConfig(cacheNames = {"system:user"})
@Transactional(rollbackFor = Exception.class)
public class UserService extends BaseService<SysUserParams> {
	@Resource
	private UserDao userDao;
	@Resource
	private UserRoleDao userRoleDao;
	/**
	 * 用户头像删除
	 */
	@Resource
	private FileUploadUtils fileUploadUtils;
	/**
	 * 查询所有数据
	 * @param params 查询参数
	 * @param pageable 分页
	 * @return Page<SysUser>
	 * @author zmzhou
	 * @date 2020/07/07 14:52
	 */
	public Page<SysUser> findAll(SysUserParams params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.desc(Constants.STATUS), Sort.Order.asc(Constants.USERNAME));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysUser> spec = new SimpleSpecificationBuilder<SysUser>()
				.and(Constants.USERNAME, Operator.LIKE, params.getUsername())
				.and( Constants.STATUS, Operator.EQUAL, params.getStatus())
				.and("tel", Operator.R_LIKE, params.getTel())
				.between("and", "createTime", params.getBeginTime(), params.getEndTime())
				.build();
		return userDao.findAll(spec, page);
	}
	/**
	 * 根据id修改用户状态
	 * @param id 用户id
	 * @param status 用户状态
	 * @return int
	 * @author zmzhou
	 * @date 2020/07/07 14:52
	 */
	@CacheEvict(key="#id")
	public int updateUserStatus(Long id, String status) {
		return userDao.updateUserStatus(id, status);
	}
	/**
	 * 更新用户在线状态
	 * @param id 用户id
	 * @param online 在线状态
	 * @return 更新记录数
	 * @author zmzhou
	 * @date 2020/9/6 23:09
	 */
	public int updateOnline(Long id, String online){
		return userDao.updateOnline(id, online);
	}

	/**
	 * 更新不在线用户状态
	 * @param ids 在线用户id集合
	 * @return 更新记录数
	 * @author zmzhou
	 * @date 2020/9/13 12:45
	 */
	public int updateOffline(Set<Long> ids){
		if (ids.isEmpty()){
			ids.add(0L);
		}
		return userDao.updateOffline(ids);
	}

	/**
	 * 根据id获取用户信息
	 * @param id 用户id
	 * @return SysUser
	 * @author zmzhou
	 * @date 2020/07/08 11:52
	 */
	public SysUser getUser(Long id) {
		if (null == id) {
			return new SysUser();
		}
		return userDao.findById(id).orElse(new SysUser());
	}
	/**
	 * 根据username获取用户信息
	 * @param username 用户名
	 * @return SysUser
	 * @author zmzhou
	 * @date 2020/07/08 11:52
	 */
	public SysUser getUser(String username) {
		if (StringUtils.isBlank(username)) {
			return new SysUser();
		}
		Specification<SysUser> spec = new SimpleSpecificationBuilder<SysUser>(
				Constants.USERNAME, Operator.EQUAL, username).build();
		return userDao.findOne(spec).orElse(new SysUser());
	}
	/**
	 * 根据用户名、电话号码查询用户信息
	 * @param user 用户参数
	 * @return 用户信息
	 * @author zmzhou
	 * @date 2020/9/6 19:21
	 */
	public SysUser getUser(SysUser user) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher(Constants.USERNAME, ExampleMatcher.GenericPropertyMatchers.ignoreCase())
				.withMatcher("tel", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
				.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
				.withIgnorePaths("remark",  Constants.STATUS, "sex", "nickName", "password");
		Example<SysUser> example = Example.of(user ,matcher);
		return userDao.findOne(example).orElse(new SysUser());
	}
	/**
	 * 验证用户是否存在
	 * @param user 用户信息
	 * @return boolean
	 * @author zmzhou
	 * @date 2020/07/08 14:09
	 */
	public boolean exists(SysUser user) {
		return null != getUser(user).getId();
	}
	/**
	 * 保存用户角色信息
	 * @param userVo 用户信息
	 * @author zmzhou
	 * @date 2020/9/8 21:17
	 */
	public SysUser saveUserRole(SysUserVo userVo) {
		SysUser user;
		// 删除用户现有的角色关联数据
		SysUserRole userRole = new SysUserRole();
		userRole.setUserId(userVo.getId());
		// 新增用户不需要删除旧数据
		if (null == userVo.getId()){
			user = save(userVo.toEntity());
			userRole.setUserId(user.getId());
		} else {
			// 更新用户信息，先删除角色关联数据，再保存
			user = update(userVo.toEntity());
		    userRoleDao.deleteByUserId(userVo.getId());
		}
		if (null != userVo.getRoles() && !userVo.getRoles().isEmpty()) {
			// 保存新添加的角色关联数据
			userVo.getRoles().forEach(roleId -> {
			    userRole.setRoleId(Long.parseLong(roleId));
		        userRoleDao.save(userRole);
			});
		}
		return user;
	}
	/**
	 * 保存用户信息
	 * @param user 用户信息
	 * @return SysUser
	 * @author zmzhou
	 * @date 2020/07/08 11:52
	 */
	@CachePut(key="#user.id")
	public SysUser save(@Validated SysUser user) {
		user.setCreateTime(new Date());
		user.setCreateBy(SecurityUtils.getUsername());
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		return userDao.saveAndFlush(user);
	}
	/**
	 * 修改用户
	 * @param user 用户信息
	 * @return SysUser
	 * @author zmzhou
	 * @date 2020/07/08 16:15
	 */
	@CachePut(key="#user.id")
	public SysUser update(@Validated SysUser user) {
		// 从数据库查询密码
		SysUser sysUser = getUser(user.getId());
		user.setPassword(sysUser.getPassword());
		user.setUpdateTime(new Date());
		user.setUpdateBy(SecurityUtils.getUsername());
		return userDao.saveAndFlush(user);
	}
	/**
	 * 删除用户
	 * @param ids 用户id
	 * @author zmzhou
	 * @date 2020/07/08 16:15
	 */
	@CacheEvict
	public void delete(Long[] ids) {
		for (Long id: ids) {
			// 根据用户id删除用户角色关联数据
			userRoleDao.deleteByUserId(id);
			userDao.deleteById(id);
			// 删除用户头像
			ThreadPoolUtils.execute(()-> fileUploadUtils.deleteAvatar(getUser(id).getUsername()));
		}
	}
	
	/**
	 * 重置密码
	 * @param id 用户id
	 * @param password 密码
	 * @author zmzhou
	 * @date 2020/07/08 16:59
	 */
	@CachePut
	public SysUser resetPwd(Long id, String password) {
		// 非admin用户不能修改admin密码
		if (1 == id && !SecurityUtils.getLoginUser().getUser().isAdmin()) {
			throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "没有权限修改admin用户的密码");
		}
		SysUser user = getUser(id);
		// 用户不存在
		if (StringUtils.isBlank(user.getUsername())){
			throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "用户不存在");
		}
		// 密码加密，设置更新人，更新时间
//		 user.setPassword(passwordEncoder.encode(password))
		user.setPassword(SecurityUtils.encryptPassword(password));
		user.setUpdateBy(SecurityUtils.getUsername());
		user.setUpdateTime(new Date());
		return userDao.saveAndFlush(user);
	}
	
	/**
	 * 更新用户登录时间
	 * @param loginUser 用户登录信息
	 * @author zmzhou
	 * @date 2020/08/27 14:05
	 */
	@CachePut
	public void updateLoginTime(LoginUser loginUser) {
		String realIp = loginUser.getIpAddr();
		String realAddr = loginUser.getLoginLocation();
		log.info("用户登录IP：{}，地址：{}", realIp, realAddr);
		userDao.updateLoginTime(loginUser.getUser().getId(), realIp, realAddr);
	}

	/**
	 * @param params 查询参数
	 * @return excel文件路径名
	 * @description 导出excel
	 * @author zmzhou
	 * @date 2020/9/3 22:59
	 */
	@Override
	public String export(SysUserParams params) throws InterruptedException {
		Page<SysUser> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		while (list.hasNext()) {
			dataConversion(list, excelList, SysUserExcel.class);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, SysUserExcel.class);
		return excelUtils.download(excelList, SysUserExcel.class, params.getExcelName());
	}
	/**
	 * 导入分析excel数据
	 * @param excelList excel数据
	 * @param isUpdate 是否要更新数据库中重复的数据
	 * @return 导入提示
	 * @author zmzhou
	 * @date 2020/9/6 18:19
	 */
	public String importExcel(List<SysUserExcel> excelList, boolean isUpdate) {
		if (null == excelList || excelList.isEmpty()) {
			throw new BaseException("导入的excel不能为空");
		}
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		int success = 0;
		int failure = 0;
		String br = "<br/>";
		for (SysUserExcel excel : excelList) {
			SysUser sysUser = this.getUser(excel.getUsername());
			// 数据库中没有重复数据
			if (null == sysUser.getId()) {
				sysUser = this.save(excel.toEntity());
				log.debug("新增数据成功：{}", sysUser);
				success++;
				successMsg.append(br).append(success).append("、账号[")
						.append(excel.getUsername()).append("]导入成功");
			} else if (isUpdate) {
				// 数据库中有但是需要更新
				BeanUtils.copyProperties(excel, sysUser);
				sysUser = this.update(sysUser);
				log.debug("更新数据成功：{}", sysUser);
				success++;
				successMsg.append(br).append(success).append("、账号[")
						.append(excel.getUsername()).append("]更新成功");
			} else {
				log.debug("数据重复：{}", excel);
				failure++;
				failureMsg.append(br).append(failure).append("、账号[")
						.append(excel.getUsername()).append("]已存在");
			}
		}
		if (failure > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failure + " 条数据格式不正确，错误如下：");
			throw new BaseException(failureMsg.toString());
		} else {
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + success + " 条，数据如下：");
		}
		return successMsg.toString();
	}

}
