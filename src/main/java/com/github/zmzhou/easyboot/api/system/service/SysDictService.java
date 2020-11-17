package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.system.dao.SysDictDao;
import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.vo.SysDictParams;
import com.github.zmzhou.easyboot.api.system.vo.SysDictTypeVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.common.utils.ServletUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 数据字典Service接口
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictService {
	private static final String DICT_LABEL = "dictLabel";
	@Resource
	private SysDictDao dao;

	/**
	 * 根据字典类型查询字典数据信息
	 *
	 * @param dictType 字典类型
	 * @return List<SysDict>
	 * @author zmzhou
	 * @date 2020/08/27 11:28
	 */
	public List<SysDict> selectDictDataByType(String dictType) {
		return dao.selectDictDataByType(dictType);
	}

	/**
	 * 获取字典类型列表
	 * Select dict types list.
	 *
	 * @return the list
	 */
	public List<SysDictTypeVo> selectDictTypes() {
		// 获取字典类型列表
		List<String[]> typeList = dao.selectDictTypes();
		List<SysDictTypeVo> res = new ArrayList<>();
		typeList.forEach(obj -> res.add(SysDictTypeVo.builder().dictName(obj[0]).dictType(obj[1]).build()));
		return res;
	}

	/**
	 * 分页查询数据字典数据
	 *
	 * @param params   查询参数
	 * @param pageable 分页
	 * @return Page<SysDict>
	 */
	public Page<SysDict> findAll(SysDictParams params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.asc("dictSort"));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 空指针处理
		params = Optional.ofNullable(params).orElse(new SysDictParams());
		// 构造查询条件
		SimpleSpecificationBuilder<SysDict> spec = new SimpleSpecificationBuilder<SysDict>()
				.and(Constants.STATUS, Operator.EQUAL, params.getStatus())
				.and(DICT_LABEL, Operator.LIKE, params.getDictLabel())
				.and("dictName", Operator.LIKE, params.getDictName())
				.and("dictType", Operator.LIKE, params.getDictType())
				.between(Constants.CREATE_TIME, params.getBeginTime(), params.getEndTime());
		// 查询字典类型
		if (ServletUtils.getRequest().getRequestURI().endsWith("/type/list")){
			spec.and(DICT_LABEL, Operator.ISNULL, null);
		} else {
			spec.and(DICT_LABEL, Operator.IS_NOTNULL, null);
		}
		return dao.findAll(spec.build(), page);
	}

	/**
	 * 根据id查询数据字典
	 *
	 * @param id 数据字典id
	 * @return SysDict对象
	 */
	public SysDict findById(Long id) {
		if (null == id) {
			return new SysDict();
		}
		return dao.findById(id).orElse(new SysDict());
	}

	/**
	 * 新增数据字典
	 *
	 * @param entity 数据字典
	 * @return 结果
	 */
	public SysDict save(SysDict entity) {
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUsername());
		return dao.saveAndFlush(entity);
	}

	/**
	 * 修改数据字典
	 *
	 * @param entity 数据字典
	 * @return 结果
	 */
	public SysDict update(SysDict entity) {
		entity.setUpdateTime(new Date());
		entity.setUpdateBy(SecurityUtils.getUsername());
		return dao.saveAndFlush(entity);
	}

	/**
	 * 批量删除数据字典
	 *
	 * @param ids 需要删除的数据字典ID
	 */
	public void deleteByIds(Long[] ids) {
		for (Long id : ids) {
			// 根据用户id删除数据
			dao.deleteById(id);
		}
	}
}
