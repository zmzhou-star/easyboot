package com.zmzhou.easyboot.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zmzhou.easyboot.api.system.entity.SysUserRole;

/**
 * @description 用户角色关联关系dao
 * @author: zmzhou
 * @date: Created in 2020/8/30 0:02
 * @version: 1.0
 */
public interface UserRoleDao extends JpaRepository<SysUserRole, Long>, JpaSpecificationExecutor<SysUserRole>  {
}
