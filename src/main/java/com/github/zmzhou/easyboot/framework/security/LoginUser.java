package com.github.zmzhou.easyboot.framework.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;

/**
 * The type Login user.
 *
 * @author zmzhou
 * @title LoginUser
 * @Description 登录用户身份权限
 * @Date 2020 /07/10 9:53
 */
public class LoginUser implements UserDetails {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户唯一标识
	 */
	private String token;
	
	/**
	 * 登陆时间
	 */
	private Long loginTime;
	
	/**
	 * 过期时间
	 */
	private Long expireTime;
	
	/**
	 * 登录IP地址
	 */
	private String ipaddr;
	
	/**
	 * 登录地点
	 */
	private String loginLocation;
	
	/**
	 * 浏览器类型
	 */
	private String browser;
	
	/**
	 * 操作系统
	 */
	private String os;
	
	/**
	 * 权限列表
	 */
	private Set<String> permissions;
	
	/**
	 * 用户信息
	 */
	private SysUser user;
	
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
	 */
	public LoginUser(SysUser user, Set<String> permissions) {
		this.user = user;
		this.permissions = permissions;
	}
	
	/**
	 * Gets token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * Sets token.
	 *
	 * @param token the token
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * Gets login time.
	 *
	 * @return the login time
	 */
	public Long getLoginTime() {
		return loginTime;
	}
	
	/**
	 * Sets login time.
	 *
	 * @param loginTime the login time
	 */
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	
	/**
	 * Gets ipaddr.
	 *
	 * @return the ipaddr
	 */
	public String getIpaddr() {
		return ipaddr;
	}
	
	/**
	 * Sets ipaddr.
	 *
	 * @param ipaddr the ipaddr
	 */
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	
	/**
	 * Gets login location.
	 *
	 * @return the login location
	 */
	public String getLoginLocation() {
		return loginLocation;
	}
	
	/**
	 * Sets login location.
	 *
	 * @param loginLocation the login location
	 */
	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}
	
	/**
	 * Gets browser.
	 *
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}
	
	/**
	 * Sets browser.
	 *
	 * @param browser the browser
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	/**
	 * Gets os.
	 *
	 * @return the os
	 */
	public String getOs() {
		return os;
	}
	
	/**
	 * Sets os.
	 *
	 * @param os the os
	 */
	public void setOs(String os) {
		this.os = os;
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
		return null;
	}
}
