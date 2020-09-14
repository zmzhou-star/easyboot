package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.system.dao.RoleDao;
import com.github.zmzhou.easyboot.api.system.dao.RoleMenuDao;
import com.github.zmzhou.easyboot.api.system.dao.UserRoleDao;
import com.github.zmzhou.easyboot.api.system.entity.SysRole;
import com.github.zmzhou.easyboot.api.system.entity.SysRoleMenu;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;
import com.github.zmzhou.easyboot.api.system.entity.SysUserRole;
import com.github.zmzhou.easyboot.api.system.excel.SysRoleExcel;
import com.github.zmzhou.easyboot.api.system.vo.SysRoleParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecification;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;
import com.github.zmzhou.easyboot.framework.vo.OptionsVo;

import lombok.extern.slf4j.Slf4j;

/**
 *  @title RoleService
 *  @description 描述
 *  @author zmzhou
 *  @date 2020/07/21 17:05
 */
@Slf4j
@Service
@CacheConfig(cacheNames = {"system:role"})
@Transactional(rollbackFor = Exception.class)
public class RoleService extends BaseService<SysRoleParams> {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private UserRoleDao userRoleDao;
	/**
	 * 获取用户角色权限
	 * @param user 用户
	 * @return Set<String>
	 * @author zmzhou
	 * @date 2020/8/30 12:54
	 */
	public Set<String> getRolePermission(SysUser user) {
		Set<String> roles = new HashSet<>();
		// 管理员拥有所有权限
		if (user.isAdmin()) {
			roles.add("admin");
		} else {
			List<SysRole> perms = roleDao.findRolePermission(user.getId());
			for (SysRole role : perms) {
				if (null != role && StringUtils.isNotEmpty(role.getRoleCode())) {
					roles.addAll(Arrays.asList(role.getRoleCode().trim().split(Constants.COMMA)));
				}
			}
		}
		return roles;
	}
	/**
	 * 根据用户id查询用户角色
	 * @param userId 用户id
	 * @return  用户角色id列表
	 * @author zmzhou
	 * @date 2020/9/8 21:29
	 */
	public Set<String> getRoleIds(Long userId) {
		Set<String> roles = new HashSet<>();
		// 管理员拥有所有权限
		List<SysRole> perms = roleDao.findRolePermission(userId);
		for (SysRole role : perms) {
			if (null != role && null!=role.getId()) {
				roles.add(String.valueOf(role.getId()));
			}
		}
		return roles;
	}
	/**
	 * 查询所有的角色列表信息
	 * @return 角色列表
	 * @author zmzhou
	 * @date 2020/9/7 23:07
	 */
	public List<SysRole> findAll(){
		return roleDao.findAll();
	}
	/**
	 * 获取角色下拉框数据
	 * @return 角色下拉框数据
	 * @author zmzhou
	 * @date 2020/9/7 23:13
	 */
	public Set<OptionsVo> getRoleOptions(){
		List<SysRole> roles = findAll();
		Set<OptionsVo> optionsVos = new TreeSet<>();
		roles.forEach(r -> optionsVos.add(OptionsVo.builder()
				.label(r.getRoleName()).value(String.valueOf(r.getId()))
				.sortBy(r.getSortBy()).build()));
		return optionsVos;
	}

	/**
	 * 查询所有角色数据
	 * @param params 查询参数
	 * @param pageable 分页
	 * @return Page<SysUser>
	 * @author zmzhou
	 * @date 2020/08/28 17:56
	 */
	public Page<SysRole> findAll(SysRoleParams params, Pageable pageable) {
		// 构造分页和排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.desc( Constants.STATUS), Sort.Order.asc(Constants.SORT_BY));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysRole> spec = new SimpleSpecificationBuilder<SysRole>()
				.and("roleName", Operator.LIKE, params.getRoleName())
				.and("roleCode", Operator.LIKE, params.getRoleCode())
				.and( Constants.STATUS, Operator.EQUAL, params.getStatus())
				.between("and", "createTime", params.getBeginTime(), params.getEndTime())
				.build();
		return roleDao.findAll(spec, page);
	}
	
	/**
	 * 根据id获取角色信息
	 * @param id 角色id
	 * @return SysRole
	 * @author zmzhou
	 * @date 2020/08/28 18:58
	 */
	public SysRole getOne(Long id) {
		if (null == id) {
			return new SysRole();
		}
		return roleDao.findById(id).orElse(new SysRole());
	}
	
	/**
	 * 根据角色名称,角色编码获取角色信息
	 * @param roleName 角色名称
	 * @param roleCode 角色编码
	 * @return SysRole
	 * @author zmzhou
	 * @date 2020/08/28 18:58
	 */
	public SysRole findOne(String roleName, String roleCode) {
		// 构造查询条件
		Specification<SysRole> spec = new SimpleSpecificationBuilder<SysRole>()
				.and("roleName", Operator.EQUAL, roleName)
				.and("roleCode", Operator.EQUAL, roleCode).build();
		return roleDao.findOne(spec).orElse(null);
	}
	
	/**
	 * 根据id修改角色状态
	 * @param id 角色id
	 * @param status 角色状态
	 * @return 修改成功状态
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	@CachePut(key="#id")
	public int updateUserStatus(Long id, String status) {
		return roleDao.updateUserStatus(id, status);
	}
	
	/**
	 * 校验角色名称是否唯一
	 * @param role 角色信息
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	public boolean checkRoleNameUnique(SysRole role) {
		long roleId = null == role.getId() ? -1L : role.getId();
		// 根据角色名称获取角色信息
		SysRole sysRole = findOne(role.getRoleName(), null);
		return null != sysRole && sysRole.getId() != roleId;
	}
	/**
	 * 校验角色编码是否唯一
	 * @param role 角色信息
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	public boolean checkRoleCodeUnique(SysRole role) {
		long roleId = null == role.getId() ? -1L : role.getId();
		// 根据角色编码获取角色信息
		SysRole sysRole = findOne(null, role.getRoleCode());
		return null != sysRole && sysRole.getId() != roleId;
	}
	
	/**
	 * 新增保存角色信息
	 * @param role 角色信息
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	@CachePut(key="#role.id")
	public SysRole insertRole(SysRole role) {
		// 新增角色信息
		role.setCreateTime(new Date());
		role.setCreateBy(SecurityUtils.getUsername());
		SysRole sysRole = roleDao.saveAndFlush(role);
		// 新增角色菜单信息
		sysRole.setMenuIds(role.getMenuIds());
		List<SysRoleMenu> sysRoleMenus = this.insertRoleMenu(sysRole);
		log.info("保存了[{}]条角色菜单信息:{}", sysRoleMenus.size(), sysRoleMenus);
		return sysRole;
	}
	
	/**
	 * 新增角色菜单对应信息
	 * @param role 角色对象
	 * @return 保存结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	private List<SysRoleMenu> insertRoleMenu(SysRole role) {
		// 新增用户与角色管理
		List<SysRoleMenu> list = new ArrayList<>();
		for (Long menuId : role.getMenuIds()) {
			SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getId());
			rm.setMenuId(menuId);
			list.add(rm);
		}
		if (!list.isEmpty()) {
			list = roleMenuDao.saveAll(list);
		}
		return list;
	}

	/**
	 * 校验角色是否允许操作
	 *
	 * @param role 角色信息
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	public void checkRoleAllowed(SysRole role) {
		// 不允许操作超级管理员角色
		if (role.isAdmin()) {
			throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "不允许操作超级管理员角色");
		}
	}

	/**
	 * 修改保存角色信息
	 *
	 * @param role 角色信息
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	@CachePut(key="#role.id")
	public int updateRole(SysRole role) {
		// 修改角色信息
		role.setUpdateBy(SecurityUtils.getUsername());
		role.setUpdateTime(new Date());
		roleDao.saveAndFlush(role);
		// 删除角色与菜单关联
		roleMenuDao.deleteAll(findAll(role.getId()));
		return insertRoleMenu(role).size();
	}

	/**
	 *根据角色id查询角色与菜单关联的数据
	 * @param roleId 角色id
	 * @return 角色与菜单关联的数据
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	@Cacheable(key="#roleId")
	public List<SysRoleMenu> findAll(long roleId){
		SimpleSpecification<SysRoleMenu> spec = new SimpleSpecificationBuilder<SysRoleMenu>()
				.and("roleId", Operator.EQUAL, roleId)
				.build();
		return roleMenuDao.findAll(spec);
	}

	/**
	 * 批量删除角色信息
	 *
	 * @param roleIds 需要删除的角色ID
	 * @return 结果
	 */
	public int deleteRoleByIds(Long[] roleIds) {
		int count = 0;
		for (Long roleId : roleIds) {
			// 校验角色是否允许操作
			checkRoleAllowed(new SysRole(roleId));
			SysRole role = selectRoleById(roleId);
			// 通过角色ID查询角色使用数量
			if (countUserRoleByRoleId(roleId) > 0) {
				throw new BaseException(HttpStatus.UNAUTHORIZED.value(),
						String.format("%1$s已分配,不能删除", role.getRoleName()));
			}
			// 删除角色与菜单关联
			roleMenuDao.deleteAll(findAll(role.getId()));
			roleDao.deleteById(roleId);
			// 删除计数
			count ++;
		}
		return count;
	}
	/**
	 * 通过角色ID查询角色使用数量
	 * @param roleId 角色ID
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	private long countUserRoleByRoleId(Long roleId) {
		SimpleSpecification<SysUserRole> spec = new SimpleSpecificationBuilder<SysUserRole>()
				.and("roleId", Operator.EQUAL, roleId)
				.build();
		return userRoleDao.count(spec);
	}

	/**
	 * 通过角色ID查询角色
	 * @param roleId 角色ID
	 * @return 角色对象信息
	 * @author zmzhou
	 * @date 2020/08/28 18:56
	 */
	private SysRole selectRoleById(Long roleId) {
		return roleDao.selectRoleById(roleId);
	}
	/**
	 * @description 导出excel
	 * @param params 查询参数
	 * @return excel文件路径名
	 * @author zmzhou
	 * @date 2020/9/3 22:18
	 */
    @Override
	public String export(SysRoleParams params) throws InterruptedException {
		Page<SysRole> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		while (list.hasNext()) {
			dataConversion(list, excelList, SysRoleExcel.class);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, SysRoleExcel.class);
		return excelUtils.download(excelList, SysRoleExcel.class, params.getExcelName());
    }
	/**
	 * @description 获取用户角色
	 * @param userId 用户id
	 * @return 用户角色名（多个角色以,隔开）
	 * @author zmzhou
	 * @date 2020/9/5 22:12
	 */
	public String selectUserRole(Long userId) {
		List<SysRole> roles = roleDao.findRolePermission(userId);
		StringBuilder roleNames = new StringBuilder();
		for (SysRole role : roles) {
			roleNames.append(role.getRoleName()).append(Constants.COMMA);
		}
		if (StringUtils.isNotEmpty(roleNames.toString())) {
			return roleNames.substring(0, roleNames.length() - 1);
		}
		return roleNames.toString();
	}
}
