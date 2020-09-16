package com.github.zmzhou.easyboot.api.system.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.system.entity.SysUser;

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
	 * @return 修改结果
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
	 * @param realAddr 登录地址
	 * @author zmzhou
	 * @date 2020/08/27 14:23
	 */
	@Modifying
	@Query("update SysUser u set u.loginIp=?2,u.loginAddr=?3,u.online=1,u.loginDate=now() where u.id=?1")
	void updateLoginTime(Long userId, String realIp, String realAddr);

	/**
	 * 更新用户在线状态
	 * @param id 用户id
	 * @param online 在线状态
	 * @return 修改结果
	 * @author zmzhou
	 * @date 2020/9/6 23:13
	 */
	@Modifying
	@Query("update SysUser u set u.online=?2,u.loginDate=now() where u.id=?1")
	int updateOnline(Long id, String online);

	/**
	 * 更新不在线用户状态
	 * @param ids 在线用户id集合
	 * @return 更新结果
	 * @author zmzhou
	 * @date 2020/9/13 12:45
	 */
	@Modifying
	@Query("update SysUser u set u.online='0' where u.online<>'0' and u.id not in (?1)")
	int updateOffline(Set<Long> ids);
}
