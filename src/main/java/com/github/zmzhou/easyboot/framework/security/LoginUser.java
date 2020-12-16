package com.github.zmzhou.easyboot.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zmzhou.easyboot.api.monitor.vo.SysUserOnlineVo;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.framework.vo.IpInfo;

import lombok.Data;

/**
 * The type Login user.
 *
 * @author zmzhou
 * @title LoginUser
 * @Description 登录用户身份权限
 * @Date 2020 /07/10 9:53
 */
@Data
public class LoginUser extends SysUserOnlineVo implements UserDetails {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 8529094256919171580L;
	/**
	 * 登录信息过期时间
	 */
	private Long expireTime;
	/**
	 * 权限列表
	 */
	private Set<String> permissions;
	/**
	 * 用户角色列表
	 */
	private Set<String> roles;
	/**
	 * 用户信息
	 */
	private SysUser user;

	/** 用户定位信息 */
	private IpInfo ipInfo;

	/**
	 * Instantiates a new Login user.
	 */
	public LoginUser() {
	}
	
	/**
	 * Instantiates a new Login user.
	 *
	 * @param user        the user
	 * @param permissions the permissions
	 * @param roles the roles
	 */
	public LoginUser(SysUser user, Set<String> permissions, Set<String> roles) {
		this.user = user;
		this.permissions = permissions;
		this.roles = roles;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	@JsonIgnore
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	/**
	 * Gets username.
	 *
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	/**
	 * 账户是否未过期,过期无法验证
	 *
	 * @return the boolean
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * 指定用户是否解锁,锁定的用户无法进行身份验证
	 *
	 * @return boolean
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
	 *
	 * @return boolean
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * 是否可用 ,禁用的用户不能身份验证
	 *
	 * @return boolean
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Gets expire time.
	 *
	 * @return the expire time
	 */
	public Long getExpireTime() {
		return expireTime;
	}
	
	/**
	 * Sets expire time.
	 *
	 * @param expireTime the expire time
	 */
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	
	/**
	 * Gets permissions.
	 *
	 * @return the permissions
	 */
	public Set<String> getPermissions() {
		return permissions;
	}
	
	/**
	 * Sets permissions.
	 *
	 * @param permissions the permissions
	 */
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
	
	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public SysUser getUser() {
		return user;
	}
	
	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(SysUser user) {
		this.user = user;
	}
	
	/**
	 * Gets authorities.
	 *
	 * @return the authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		Optional.ofNullable(roles).orElse(new HashSet<>()).forEach(role -> 
				authorities.add(new SimpleGrantedAuthority(role)));
		return authorities;
	}
}
