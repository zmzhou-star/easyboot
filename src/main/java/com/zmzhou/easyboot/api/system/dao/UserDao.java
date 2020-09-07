package com.zmzhou.easyboot.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zmzhou.easyboot.api.system.entity.SysUser;

/**
 * The interface User dao.
 *
 * @author zmzhou
 * @title UserDao
 * @Description 用户管理dao
 * @Date 2020 /07/06 18:24
 */
public interface UserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
	
	/**
	 * 根据id修改用户状态
	 * @param id 用户id
	 * @param status 用户状态
	 * @return
	 * @author zmzhou
	 * @date 2020/07/07 14:51
	 */
	@Modifying
	@Query("update SysUser u set u.status=?2,u.updateTime=now() where u.id=?1 and u.id <> 1")
	int updateUserStatus(Long id, String status);

	/**
	 * 更新用户登录时间
	 * @param userId 用户id
	 * @param realIp 登录IP
	 * @author zmzhou
	 * @date 2020/08/27 14:23
	 */
	@Modifying
	@Query("update SysUser u set u.loginIp=?2,u.online=1,u.loginDate=now() where u.id=?1")
	void updateLoginTime(Long userId, String realIp);

	/**
	 * 更新用户在线状态
	 * @param id 用户id
	 * @param online 在线状态
	 * @author zmzhou
	 * @date 2020/9/6 23:13
	 */
	@Modifying
	@Query("update SysUser u set u.online=?2,u.loginDate=now() where u.id=?1")
	int updateOnline(Long id, String online);
}
