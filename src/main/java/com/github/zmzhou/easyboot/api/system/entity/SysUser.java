package com.github.zmzhou.easyboot.api.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * The type Sys user.
 *
 * @author zmzhou
 * @title SysUser
 * @Description 用户信息实体类
 * @Date 2020 /07/06 11:19
 */
@Data
@Entity
@Table(name = "SYS_USER")
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = -3304319213957837923L;
	/**
	 * The Username.
	 */
	@NotBlank(message = "用户账号不能为空")
	@Size(min = 4, max = 30, message = "用户账号长度不符")
	@Column(name = "USER_NAME")
	private String username;
	/**
	 * The Password.
	 */
	@Column(name = "PASSWORD")
	private String password;
	/**
	 * The Avatar.
	 */
	@Column(name = "avatar")
	private String avatar;
	/**
	 * The Nick name.
	 */
	@Column(name = "NICK_NAME")
	private String nickName;
	/**
	 * The Email.
	 */
	@Email(message = "邮箱格式不正确")
	@Size(min = 6, max = 50, message = "邮箱长度不能超过50个字符")
	@Column(name = "EMAIL")
	private String email;
	/**
	 * The Tel.
	 */
	@Size(min = 11, max = 11, message = "手机号码格式不正确")
	@Column(name = "TEL")
	private String tel;
	/**
	 * The Sex.
	 */
	@Column(name = "SEX")
	private String sex;
	/**
	 * The Status.
	 */
	@Column(name = "STATUS")
	private String status;
	/**
	 * The Status.
	 */
	@Column(name = "ONLINE")
	private String online;
	/**
	 * The Login ip.
	 */
	@Column(name = "LOGIN_IP")
	private String loginIp;
	/**
	 * The Login address.
	 */
	@Column(name = "LOGIN_ADDR")
	private String loginAddr;
	/**
	 * The Login date.
	 */
	@Column(name = "LOGIN_DATE")
	private Date loginDate;

//	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Gets login date.
	 *
	 * @return the login date
	 */
	public Date getLoginDate() {
		if (null == this.loginDate) {
			return null;
		}
		return (Date) (this.loginDate).clone();
	}
	
	/**
	 * Sets login date.
	 *
	 * @param loginDate the login date
	 */
	public void setLoginDate(Date loginDate) {
		if (null == loginDate) {
			this.loginDate = null;
		} else {
			this.loginDate = (Date) (loginDate).clone();
		}
	}
	
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
	 *
	 * @param userId the user id
	 * @return the boolean
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}
}
