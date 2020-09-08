package com.github.zmzhou.easyboot.api.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.system.entity.SysMenu;

/**
 *  @title MenuDao
 *  @Description 描述
 *  @author zmzhou
 *  @Date 2020/07/21 10:26
 */
public interface MenuDao extends JpaRepository<SysMenu, Long>, JpaSpecificationExecutor<SysMenu> {
	/**
	 * 获取用户菜单权限
	 * @param id 用户id
	 * @return
	 * @author zmzhou
	 * @date 2020/07/21 10:37
	 */
	@Query(value ="select distinct m.perms from sys_menu m left join sys_role_menu rm on m.id = rm.menu_id " +
			"left join sys_user_role ur on rm.role_id = ur.role_id " +
			"left join sys_role r on r.id = ur.role_id " +
			"where m.status = '1' and r.status = '1' and ur.user_id = ?1", nativeQuery = true)
	List<String> findMenuPermission(Long id);
	
	/**
	 * 查询所有有效的菜单列表
	 * @return 有效的菜单列表
	 * @author zmzhou
	 * @date 2020/08/27 18:24
	 */
	@Query(value ="select distinct ID, MENU_NAME, PARENT_ID, SORT_BY, PATH, COMPONENT, IS_FRAME, MENU_TYPE," +
			" VISIBLE, STATUS, PERMS, ICON, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME, REMARK" +
			" from sys_menu m where m.menu_type in ('M', 'C') and m.status = 1" +
			" order by m.parent_id, m.sort_by", nativeQuery = true)
	List<SysMenu> selectMenuTreeAll();
	
	/**
	 * 根据用户ID查询菜单
	 * @param userId 用户ID
	 * @return 有效的菜单列表
	 * @author zmzhou
	 * @date 2020/08/27 18:26
	 */
	@Query(value ="select distinct m.ID, m.MENU_NAME, m.PARENT_ID, m.SORT_BY, m.PATH, m.COMPONENT, m.IS_FRAME," +
			" m.MENU_TYPE, m.VISIBLE, m.STATUS, m.PERMS, m.ICON, m.CREATE_BY, m.CREATE_TIME," +
			" m.UPDATE_BY, m.UPDATE_TIME, m.REMARK" +
			" from sys_menu m left join sys_role_menu rm on m.id = rm.menu_id" +
			" left join sys_user_role ur on rm.role_id = ur.role_id" +
			" left join sys_role r on ur.role_id = r.id" +
			" left join sys_user u on ur.user_id = u.id" +
			" where u.id = ?1 and m.menu_type in ('M', 'C') and m.status = 1 AND r.status = 1" +
			" order by m.parent_id, m.sort_by", nativeQuery = true)
	List<SysMenu> selectMenuTreeByUserId(Long userId);
	
	/**
	 * 根据用户查询系统菜单列表
	 * @param userId 用户ID
	 * @return 菜单列表
	 * @author zmzhou
	 * @date 2020/08/29 17:29
	 */
	@Query(value ="select distinct m.*" +
			" from sys_menu m" +
			" left join sys_role_menu rm on m.id = rm.menu_id" +
			" left join sys_user_role ur on rm.role_id = ur.role_id" +
			" left join sys_role r on ur.role_id = r.id" +
			" where ur.user_id = ?1 AND m.status = 1 AND m.VISIBLE = 1 AND r.status = 1" +
			" order by m.parent_id, m.sort_by", nativeQuery = true)
	List<SysMenu> selectMenuListByUserId(Long userId);
	
	/**
	 * 根据角色ID查询菜单树信息
	 * @param roleId 角色ID
	 * @return 选中菜单列表
	 * @author zmzhou
	 * @date 2020/08/29 17:29
	 */
	@Query(value ="select m.id from sys_menu m" +
			" left join sys_role_menu rm on m.id = rm.menu_id where rm.role_id = ?1" +
			" and m.id not in (select m.parent_id from sys_menu m " +
			" inner join sys_role_menu rm on m.id = rm.menu_id and rm.role_id = ?1)" +
			" order by m.parent_id, m.sort_by", nativeQuery = true)
	List<Integer> selectMenuListByRoleId(Long roleId);
}
