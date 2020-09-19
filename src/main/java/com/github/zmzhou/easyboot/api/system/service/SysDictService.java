package com.github.zmzhou.easyboot.api.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.system.dao.SysDictDao;
import com.github.zmzhou.easyboot.api.system.entity.SysDict;
import com.github.zmzhou.easyboot.api.system.vo.SysDictTypeVo;

/**
 *  @title SysDictService
 *  @Description 数据字典信息管理
 *  @author zmzhou
 *  @Date 2020/08/27 11:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictService {
	@Resource
	private SysDictDao dictDao;
	
	/**
	 * 根据字典类型查询字典数据信息
	 * @param dictType 字典类型
	 * @return List<SysDict>
	 * @author zmzhou
	 * @date 2020/08/27 11:28
	 */
	public List<SysDict> selectDictDataByType(String dictType) {
		return dictDao.selectDictDataByType(dictType);
	}

	/**
	 * 获取字典类型列表
	 * Select dict types list.
	 * @return the list
	 */
	public List<SysDictTypeVo> selectDictTypes() {
		// 获取字典类型列表
		List<String[]> typeList = dictDao.selectDictTypes();
		List<SysDictTypeVo> res = new ArrayList<>();
		typeList.forEach(obj -> res.add(SysDictTypeVo.builder().dictName(obj[0]).dictType(obj[1]).build()));
		return res;
	}
}
