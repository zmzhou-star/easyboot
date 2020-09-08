package com.github.zmzhou.easyboot.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.system.entity.SysUserRole;

/**
 * 用户角色关联关系dao
 * UserRoleDao
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/8 21:58
 */
public interface UserRoleDao extends JpaRepository<SysUserRole, Long>, JpaSpecificationExecutor<SysUserRole>  {

	/**
	 * 根据用户id删除用户角色关联数据
	 * @param userId 用户id
	 * @author zmzhou
	 * @date 2020/9/8 21:57
	 */
	@Modifying
	@Query("delete from SysUserRole ur where ur.userId=?1")
	void deleteByUserId(Long userId);
}
