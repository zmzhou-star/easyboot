package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.api.system.vo.MetaVo;
import com.github.zmzhou.easyboot.api.system.vo.RouterVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.entity.Params;
import com.github.zmzhou.easyboot.framework.entity.TreeSelect;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;
import com.github.zmzhou.easyboot.api.system.dao.MenuDao;
import com.github.zmzhou.easyboot.api.system.entity.SysUser;

/**
 * @author zmzhou
 * @title MenuService
 * @Description 描述
 * @Date 2020/07/21 10:26
 */
@Service
@CacheConfig(cacheNames = {"system:menu"})
@Transactional(rollbackFor = Exception.class)
public class MenuService {
	
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 获取用户菜单权限
	 *
	 * @param user SysUser
	 * @return Set<String>
	 * @author zmzhou
	 * @date 2020/07/21 10:33
	 */
	@Cacheable
	public Set<String> getMenuPermission(SysUser user) {
		Set<String> permsSet = new HashSet<>();
		// 管理员拥有所有权限
		if (user.isAdmin()) {
			permsSet.add("*:*:*");
		} else {
			List<String> perms = menuDao.findMenuPermission(user.getId());
			for (String perm : perms) {
				if (StringUtils.isNotEmpty(perm)) {
					permsSet.addAll(Arrays.asList(perm.trim().split(Constants.COMMA)));
				}
			}
		}
		return permsSet;
	}
	
	/**
	 * 获取菜单列表
	 *
	 * @param params 查询参数
	 * @param pageable 分页条件
	 * @return ApiResult<TableDataInfo>
	 * @author zmzhou
	 * @date 2020/08/27 11:45
	 */
	@Cacheable(key="#params")
	public Page<SysMenu> findAll(Params params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("sortBy"));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<SysMenu> spec = menuListSpec(params);
		return menuDao.findAll(spec, page);
	}
	
	/**
	 * 构造菜单查询条件
	 * @param params 查询参数
	 * @return Specification<SysMenu>
	 * @author zmzhou
	 * @date 2020/08/29 17:37
	 */
	private Specification<SysMenu> menuListSpec(Params params){
		// 构造查询条件
		return new SimpleSpecificationBuilder<SysMenu>()
				.and("menuName", Operator.LIKE, params.getMenuName())
				.and("visible", Operator.EQUAL, params.getVisible())
				.and(Constants.STATUS, Operator.EQUAL, params.getStatus())
				.build();
	}
	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户名称
	 * @return 菜单列表
	 * @author zmzhou
	 * @date 2020/08/27 17:55
	 */
	public List<SysMenu> selectMenuTreeByUserId(Long userId) {
		List<SysMenu> menus;
		if (SecurityUtils.isAdmin(userId)) {
			menus = menuDao.selectMenuTreeAll();
		} else {
			menus = menuDao.selectMenuTreeByUserId(userId);
		}
		return getChildPerms(menus);
	}
	
	/**
	 * 根据父节点的ID获取所有子节点
	 *
	 * @param list 分类表
	 * @return 所有子节点菜单
	 * @author zmzhou
	 * @date 2020/08/27 18:06
	 */
	private List<SysMenu> getChildPerms(List<SysMenu> list) {
		List<SysMenu> returnList = new ArrayList<>();
		for (SysMenu menu : list) {
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点,父节点为0表示最上层菜单节点
			if (menu.getParentId() == 0L) {
				// 递归列表得到子节点列表
				recursionFn(list, menu);
				returnList.add(menu);
			}
		}
		return returnList;
	}
	
	/**
	 * 递归列表得到子节点列表
	 *
	 * @param list 菜单集合
	 * @param menu 菜单节点
	 * @author zmzhou
	 * @date 2020/08/27 18:08
	 */
	private void recursionFn(List<SysMenu> list, SysMenu menu) {
		// 得到子节点列表
		List<SysMenu> childList = getChildList(list, menu);
		for (SysMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				for (SysMenu n : childList) {
					recursionFn(list, n);
				}
			}
		}
	}
	
	/**
	 * 得到子节点列表
	 *
	 * @param list 菜单集合
	 * @param menu 菜单节点
	 * @return 子节点列表
	 * @author zmzhou
	 * @date 2020/08/27 18:00
	 */
	private List<SysMenu> getChildList(List<SysMenu> list, SysMenu menu) {
		List<SysMenu> childList = new ArrayList<>();
		for (SysMenu n : list) {
			if (n.getParentId().longValue() == menu.getId().longValue()) {
				childList.add(n);
			}
		}
		// 保存该节点的子节点菜单
		menu.setChildren(childList);
		return childList;
	}
	
	/**
	 * 判断是否有子节点
	 *
	 * @param list 菜单集合
	 * @param menu 菜单节点
	 * @return 是否有子节点
	 * @author zmzhou
	 * @date 2020/08/27 17:58
	 */
	private boolean hasChild(List<SysMenu> list, SysMenu menu) {
		return !getChildList(list, menu).isEmpty();
	}
	
	/**
	 * 构建前端路由所需要的菜单
	 *
	 * @param menus 菜单列表
	 * @return 路由列表
	 * @author zmzhou
	 * @date 2020/08/27 18:31
	 */
	public List<RouterVo> buildMenus(List<SysMenu> menus) {
		List<RouterVo> routers = new LinkedList<>();
		for (SysMenu menu : menus) {
			RouterVo router = new RouterVo();
			// 菜单是否可见
			router.setHidden("0".equals(menu.getVisible()));
			router.setName(StringUtils.capitalize(menu.getPath()));
			router.setPath(getRouterPath(menu));
			router.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
			router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
			List<SysMenu> cMenus = menu.getChildren();
			if (!cMenus.isEmpty() && "M".equals(menu.getMenuType())) {
				router.setAlwaysShow(true);
				router.setRedirect("noRedirect");
				router.setChildren(buildMenus(cMenus));
			}
			routers.add(router);
		}
		return routers;
	}
	
	/**
	 * 获取路由地址
	 *
	 * @param menu 菜单信息
	 * @return 路由地址
	 * @author zmzhou
	 * @date 2020/08/27 18:35
	 */
	private String getRouterPath(SysMenu menu) {
		String routerPath = menu.getPath();
		// 非外链并且是一级目录
		if (0L == menu.getParentId() && 0L == menu.getIsFrame()) {
			routerPath = Constants.SEPARATOR + menu.getPath();
		}
		return routerPath;
	}
	
	/**
	 * 根据用户查询系统菜单列表
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 * @author zmzhou
	 * @date 2020/08/29 17:15
	 */
	@Cacheable
	public List<SysMenu> selectMenuList(Long userId) {
		// 查询有效可见的菜单
		SysMenu menu = new SysMenu();
		menu.setStatus("1");
		menu.setVisible("1");
		return selectMenuList(menu, userId);
	}
	
	/**
	 * 查询系统菜单列表
	 *
	 * @param menu 菜单信息
	 * @return 菜单列表
	 * @author zmzhou
	 * @date 2020/08/29 17:15
	 */
	public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
		Params params = new Params(menu);
		List<SysMenu> menuList;
		// 管理员显示所有菜单信息
		if (SysUser.isAdmin(userId)) {
			// 构造查询条件
			Specification<SysMenu> spec = menuListSpec(params);
			menuList = menuDao.findAll(spec);
		} else {
			menuList = menuDao.selectMenuListByUserId(userId);
		}
		return menuList;
	}
	
	/**
	 * 根据角色ID查询已选中的菜单树id列表
	 * @param roleId 角色ID
	 * @return 选中菜单列表id
	 * @author zmzhou
	 * @date 2020/08/29 17:15
	 */
	@Cacheable
	public List<Integer> selectMenuListByRoleId(Long roleId) {
		return menuDao.selectMenuListByRoleId(roleId);
	}
	
	/**
	 * 构建前端所需要下拉树结构
	 * @param menus 菜单列表
	 * @return 下拉树结构列表
	 * @author zmzhou
	 * @date 2020/08/29 17:15
	 */
	public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
		List<SysMenu> menuTrees = buildMenuTree(menus);
		return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}
	
	/**
	 * 构建前端所需要树结构
	 * @param menus 菜单列表
	 * @return 树结构列表
	 * @author zmzhou
	 * @date 2020/08/29 17:15
	 */
	private List<SysMenu> buildMenuTree(List<SysMenu> menus) {
		List<SysMenu> returnList = new ArrayList<>();
		for (SysMenu t : menus) {
			// 根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == 0) {
				recursionFn(menus, t);
				returnList.add(t);
			}
		}
		if (returnList.isEmpty()) {
			returnList = menus;
		}
		return returnList;
	}
}
