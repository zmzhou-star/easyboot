package com.github.zmzhou.easyboot.api.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * The type Sys role.
 *
 * @author zmzhou
 * @title SysRole
 * @Description 角色信息表
 * @Date 2020 /07/09 18:13
 */
@Data
@Entity
@Table(name = "SYS_ROLE")
public class SysRole extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = -3304319243957836923L;
	
	/**
	 * The Role code.
	 */
	@NotBlank(message = "角色编码不能为空")
	@Size(min = 4, max = 100, message = "角色编码长度不能超过100个字符，不能少于4个字符")
	private String roleCode;
	/**
	 * The Role name.
	 */
	@NotBlank(message = "角色名称不能为空")
	@Size(min = 1, max = 30, message = "角色名称长度不能超过30个字符")
	private String roleName;
	/**
	 * The Sort by.
	 */
	private long sortBy;
	/**
	 * The Data scope.
	 */
	private String dataScope;
	/**
	 * The Status.
	 */
	private String status;
	/** 角色对应的菜单组ids */
	@Transient
	private Long[] menuIds;

	/**
	 * Is admin boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAdmin() {
		return isAdmin(super.getId());
	}

	/**
	 * Is admin boolean.
	 * @param roleId the user id
	 * @return the boolean
	 */
	public static boolean isAdmin(Long roleId) {
		return roleId != null && 1L == roleId;
	}

	/**
	 * 构造器
	 */
	public SysRole() {
	}
	/**
	 * 构造器
	 */
	public SysRole(Long id) {
		super();
		super.setId(id);
	}
}
