package com.github.zmzhou.easyboot.api.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.system.entity.SysRole;
/**
 *  @title RoleDao
 *  @Description 角色dao
 *  @author zmzhou
 *  @Date 2020/07/21 17:08
 */
public interface RoleDao extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {
	/**
	 * 获取用户角色权限
	 * @param id 用户id
	 * @return
	 * @author zmzhou
	 * @date 2020/07/21 17:08
	 */
	@Query(value="select distinct r.id,r.role_code,r.role_name,r.sort_by,r.data_scope," +
			"r.create_time,r.status,r.remark,r.create_by,r.update_by,r.UPDATE_TIME" +
			" from sys_role r left join sys_user_role ur on ur.role_id = r.id" +
			" left join sys_user u on u.id = ur.user_id " +
			"WHERE r.status = '1' and ur.user_id = ?1", nativeQuery = true)
	List<SysRole> findRolePermission(Long id);
	
	/**
	 * 根据id修改角色状态
	 * @param id 角色id
	 * @param status 角色状态
	 * @return 修改成功状态
	 * @author zmzhou
	 * @date 2020/08/28 14:51
	 */
	@Modifying
	@Query("update SysRole r set r.status=?2,r.updateTime=now() where r.id=?1")
	int updateUserStatus(Long id, String status);

	/**
	 * 通过角色ID查询角色
	 * @param roleId 角色ID
	 * @return 角色对象信息
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	@Query(value="select distinct r.* from sys_role r" +
			" left join sys_user_role ur on ur.role_id = r.id" +
			" left join sys_user u on u.id = ur.user_id where r.id= ?1", nativeQuery = true)
    SysRole selectRoleById(Long roleId);

}
