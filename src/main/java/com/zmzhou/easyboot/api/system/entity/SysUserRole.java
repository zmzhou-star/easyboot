package com.zmzhou.easyboot.api.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * The type Sys user role.
 *
 * @author zmzhou
 * @title SysUserRole
 * @Description 用户和角色关联表
 * @Date 2020 /07/09 18:16
 */
@Data
@Entity
@IdClass(SysUserRoleJointPrimaryKey.class)
@Table(name = "SYS_USER_ROLE")
public class SysUserRole implements Serializable {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = -3304319243957837925L;
	/**
	 * The User id.
	 */
	@Id
	private Long userId;
	/**
	 * The Role id.
	 */
	@Id
	private Long roleId;
	
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
		if (!(o instanceof SysUserRole)) {
			return false;
		}
		SysUserRole that = (SysUserRole) o;
		return getUserId().equals(that.getUserId()) &&
				getRoleId().equals(that.getRoleId());
	}
	
	/**
	 * Hash code int.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getUserId(), getRoleId());
	}
}

/**
 * @author zmzhou
 * @title SysRoleMenu
 * @Description SysUserRole联合主键
 * @Date 2020 /08/29 19:24
 */
@Data
class SysUserRoleJointPrimaryKey implements Serializable {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2495126057872583256L;
	
	/**
	 * The User id.
	 */
	@Id
	private Long userId;
	/**
	 * The Role id.
	 */
	@Id
	private Long roleId;
	
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
		if (!(o instanceof SysUserRole)) {
			return false;
		}
		SysUserRole that = (SysUserRole) o;
		return getUserId().equals(that.getUserId()) &&
				getRoleId().equals(that.getRoleId());
	}
	
	/**
	 * Hash code int.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getUserId(), getRoleId());
	}
}
