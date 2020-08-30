package com.zmzhou.easyboot.api.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zmzhou.easyboot.api.system.dao.SysDictDao;
import com.zmzhou.easyboot.api.system.entity.SysDict;

/**
 *  @title SysDictService
 *  @Description 数据字典信息管理
 *  @author zmzhou
 *  @Date 2020/08/27 11:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictService {
	@Autowired
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
}
