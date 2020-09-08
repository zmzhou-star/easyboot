package com.github.zmzhou.easyboot.api.system.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * The type Sys role menu.
 *
 * @author zmzhou
 * @title SysRoleMenu
 * @Description 角色和菜单关联表
 * @Date 2020 /07/09 18:15
 */
@Data
@Entity
@IdClass(SysRoleMenuJointPrimaryKey.class)
@Table(name = "SYS_ROLE_MENU")
public class SysRoleMenu implements Serializable {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = -3304319243957837923L;
	
	/**
	 * The Role id.
	 */
	@Id
	private Long roleId;
	/**
	 * The Menu id.
	 */
	@Id
	private Long menuId;
	
	/**
	 * Equals boolean.
	 *
	 * @param o the o
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SysRoleMenu)) {
			return false;
		}
		SysRoleMenu that = (SysRoleMenu) o;
		return getRoleId().equals(that.getRoleId()) &&
				getMenuId().equals(that.getMenuId());
	}
	
	/**
	 * Hash code int.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getRoleId(), getMenuId());
	}
}

/**
 * @author zmzhou
 * @title SysRoleMenu
 * @Description SysRoleMenu联合主键
 * @Date 2020 /08/29 19:24
 */
@Data
class SysRoleMenuJointPrimaryKey implements Serializable {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 6368329330115693307L;
	/**
	 * The Role id.
	 */
	@Id
	private Long roleId;
	/**
	 * The Menu id.
	 */
	@Id
	private Long menuId;
	
	/**
	 * Equals boolean.
	 *
	 * @param o the o
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SysRoleMenu)) {
			return false;
		}
		SysRoleMenu that = (SysRoleMenu) o;
		return getRoleId().equals(that.getRoleId()) &&
				getMenuId().equals(that.getMenuId());
	}
	
	/**
	 * Hash code int.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getRoleId(), getMenuId());
	}
}
