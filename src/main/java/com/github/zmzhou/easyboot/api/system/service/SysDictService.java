package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.system.dao.SysDictDao;
import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.excel.SysDictExcel;
import com.github.zmzhou.easyboot.api.system.excel.SysDictTypeExcel;
import com.github.zmzhou.easyboot.api.system.vo.SysDictParams;
import com.github.zmzhou.easyboot.api.system.vo.SysDictTypeVo;
import com.github.zmzhou.easyboot.api.system.vo.SysDictVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.common.utils.ServletUtils;
import com.github.zmzhou.easyboot.framework.redis.RedisUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据字典Service接口
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictService extends BaseService<SysDictParams> {
	/** dictLabel */
	private static final String DICT_LABEL = "dictLabel";
	/** /type/list */
	private static final String TYPE_LIST = "/type/list";
	/** /type/export */
	private static final String TYPE_EXPORT = "/type/export";
	
	@Resource
	private SysDictDao dao;
	@Resource
	private RedisUtils redisUtils;

	/**
	 * 项目启动时，加载所有数据字典到缓存 
	 * @author zmzhou
	 * @date 2020/11/18 11:50
	 */
	@PostConstruct
	public void initDict() {
		List<SysDictTypeVo> dictTypes = selectDictTypes();
		dictTypes.forEach(type -> {
			List<SysDict> dictList = selectDictDataByType(type.getDictType());
			redisUtils.set(getCacheKey(type.getDictType()), dictList);
			log.info("完成加载数据字典：{} [{}]", type.getDictType(), dictList);
		});
		log.info("完成加载数据字典：{}条到缓存", dictTypes.size());
	}
	
	/**
	 * 根据字典类型查询字典数据信息
	 *
	 * @param dictType 字典类型
	 * @return List<SysDict>
	 * @author zmzhou
	 * @date 2020/08/27 11:28
	 */
	public List<SysDict> selectDictDataByType(String dictType) {
		List<SysDict> dictList = redisUtils.get(getCacheKey(dictType));
		// 缓存中不存在，则查询数据库并存入缓存
		if (!Optional.ofNullable(dictList).isPresent()){
			dictList = dao.selectDictDataByType(dictType);
			redisUtils.set(getCacheKey(dictType), dictList);
		}
		return dictList;
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
		String uri = ServletUtils.getRequest().getRequestURI();
		if (uri.endsWith(TYPE_LIST) || uri.endsWith(TYPE_EXPORT)){
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
		// 删除缓存
		redisUtils.delete(getCacheKey(entity.getDictType()));
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
		// 字典标签为空是在字典类型管理页面
		if (StringUtils.isBlank(entity.getDictLabel())){
			SysDict old = findById(entity.getId());
			// 修改字典数据的字典类型
			dao.updateDictType(old.getDictType(), entity.getDictType(), entity.getDictName());
		}
		// 删除缓存
		redisUtils.delete(getCacheKey(entity.getDictType()));
		return dao.saveAndFlush(entity);
	}

	/**
	 * 校验参数键名是否唯一
	 * @param vo 参数信息
	 * @return 结果
	 * @author zmzhou
	 * @date 2020/11/18 15:38
	 */
	public boolean checkUnique(SysDictVo vo) {
		long dictId = null == vo.getId() ? -1L : vo.getId();
		// 根据参数键名获取参数信息
		SysDict dict = getOne(vo);
		return null != dict && dict.getId() != dictId;
	}

	/**
	 * 根据字典类型或字典标签查询数据 
	 * @param vo 查询参数
	 * @return SysDict
	 * @author zmzhou
	 * @date 2020/11/18 15:45
	 */
	private SysDict getOne(SysDictVo vo) {
		// 构造查询条件
		SimpleSpecificationBuilder<SysDict> spec = new SimpleSpecificationBuilder<>(
				"dictType", Operator.EQUAL, vo.getDictType());
		// 字典标签不为空是在字典数据页面
		if (StringUtils.isNotBlank(vo.getDictLabel())){
			spec.and(DICT_LABEL, Operator.EQUAL, vo.getDictLabel());
		} else {
			spec.and(DICT_LABEL, Operator.ISNULL, null);
		}
		return dao.findOne(spec.build()).orElse(null);
	}

	/**
	 * 批量删除数据字典
	 *
	 * @param ids 需要删除的数据字典ID
	 */
	public void deleteByIds(Long[] ids) {
		for (Long id : ids) {
			SysDict dict = findById(id);
			// 字典标签为空是在字典类型管理页面
			if (StringUtils.isBlank(dict.getDictLabel()) && !selectDictDataByType(dict.getDictType()).isEmpty()){
				throw new BaseException("字典类型[0]已使用，不能删除", dict.getDictType());
			}
			// 根据用户id删除数据
			dao.deleteById(id);
			// 删除缓存
			redisUtils.delete(getCacheKey(dict.getDictType()));
		}
	}

	/**
	 * 导出excel
	 *
	 * @param params 查询参数
	 * @return excel文件路径名
	 * @throws InterruptedException 异常信息
	 * @author zmzhou
	 * @date 2020/9/3 22:59
	 */
	@Override
	public String export(SysDictParams params) throws InterruptedException {
		Page<SysDict> list = findAll(params, getExcelPageable(params));
		List<BaseExcel> excelList = new ArrayList<>();
		// 判断是字典类型还是字典数据导出
		Class<? extends BaseExcel> clazz = SysDictExcel.class;
		if (ServletUtils.getRequest().getRequestURI().endsWith(TYPE_EXPORT)){
			clazz = SysDictTypeExcel.class;
		}
		// 判断是否还有下一页数据
		while (list.hasNext()) {
			dataConversion(list, excelList, clazz);
			list = findAll(params, list.nextPageable());
		}
		// 把最后一页数据加入
		dataConversion(list, excelList, clazz);
		return excelUtils.download(excelList, clazz, params.getExcelName());
	}

	/**
	 * 设置cache key
	 * @param dictType 字典类型
	 * @return 缓存键key
	 * @author zmzhou
	 * @date 2020/11/17 11:24
	 */
	private String getCacheKey(String dictType) {
		return Constants.SYS_DICT_KEY + dictType;
	}
}
