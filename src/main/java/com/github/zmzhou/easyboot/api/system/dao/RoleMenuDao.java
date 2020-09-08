package com.github.zmzhou.easyboot.api.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.zmzhou.easyboot.api.system.entity.SysRoleMenu;

/**
 *  @title RoleMenuDao
 *  @Description 角色菜单对应关系dao
 *  @author zmzhou
 *  @Date 2020/08/29 19:16
 */
public interface RoleMenuDao extends JpaRepository<SysRoleMenu, Long>, JpaSpecificationExecutor<SysRoleMenu> {

}
