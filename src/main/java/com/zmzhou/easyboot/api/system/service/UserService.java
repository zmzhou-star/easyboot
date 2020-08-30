package com.zmzhou.easyboot.api.system.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmzhou.easyboot.api.system.dao.UserDao;
import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.common.Constants;
import com.zmzhou.easyboot.common.exception.BaseException;
import com.zmzhou.easyboot.common.utils.IpUtils;
import com.zmzhou.easyboot.common.utils.SecurityUtils;
import com.zmzhou.easyboot.common.utils.ServletUtils;
import com.zmzhou.easyboot.framework.entity.Params;
import com.zmzhou.easyboot.framework.specification.Operator;
import com.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 *  @title UserService
 *  @Description 用户service类
 *  @author zmzhou
 *  @Date 2020/07/06 17:31
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	/**
	 * 查询所有数据
	 * @param params 查询参数
	 * @param pageable 分页
	 * @return Page<SysUser>
	 * @author zmzhou
	 * @date 2020/07/07 14:52
	 */
	public Page<SysUser> findAll(Params params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.desc("status"), Sort.Order.asc(Constants.USERNAME));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysUser> spec = new SimpleSpecificationBuilder<SysUser>()
				.and(Constants.USERNAME, Operator.LIKE, params.getUsername())
				.and("status", Operator.EQUAL, params.getStatus())
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
	public int updateUserStatus(Long id, String status) {
		return userDao.updateUserStatus(id, status);
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
	 * 保存用户信息
	 * @param user 用户信息
	 * @return SysUser
	 * @author zmzhou
	 * @date 2020/07/08 11:52
	 */
	public SysUser save(SysUser user) {
		user.setCreateTime(new Date());
		user.setCreateBy(SecurityUtils.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.saveAndFlush(user);
	}
	/**
	 * 验证用户是否存在
	 * @param user 用户信息
	 * @return boolean
	 * @author zmzhou
	 * @date 2020/07/08 14:09
	 */
	public boolean exists(SysUser user) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher(Constants.USERNAME, ExampleMatcher.GenericPropertyMatchers.ignoreCase())
				.withMatcher("tel", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching())
				.withIgnorePaths("remark", "status", "email", "sex", "nickName", "password");
		Example<SysUser> example = Example.of(user ,matcher);
		return userDao.exists(example);
	}
	/**
	 * 修改用户
	 * @param user 用户信息
	 * @return SysUser
	 * @author zmzhou
	 * @date 2020/07/08 16:15
	 */
	public SysUser update(SysUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
	public void delete(Long[] ids) {
		for (Long id: ids) {
			userDao.deleteById(id);
		}
	}
	
	/**
	 * 重置密码
	 * @param id 用户id
	 * @param password 密码
	 * @author zmzhou
	 * @date 2020/07/08 16:59
	 */
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
		user.setPassword(passwordEncoder.encode(password));
		user.setUpdateBy(SecurityUtils.getUsername());
		user.setUpdateTime(new Date());
		return userDao.saveAndFlush(user);
	}
	
	/**
	 * 更新用户登录时间
	 * @param userId 用户id
	 * @author zmzhou
	 * @date 2020/08/27 14:05
	 */
	public void updateLoginTime(Long userId) {
		String realIp = IpUtils.getRealAddressByIP(IpUtils.getIpAddr(ServletUtils.getRequest()));
		log.info("用户登录IP：{}", realIp);
		userDao.updateLoginTime(userId, realIp);
	}
}
