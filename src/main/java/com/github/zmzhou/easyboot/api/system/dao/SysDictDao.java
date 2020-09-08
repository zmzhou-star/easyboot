package com.github.zmzhou.easyboot.api.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.system.entity.SysDict;

/**
 *  @title SysDictDao
 *  @Description 数据字典信息管理
 *  @author zmzhou
 *  @Date 2020/08/27 11:31
 */
public interface SysDictDao extends JpaRepository<SysDict, Long>, JpaSpecificationExecutor<SysDict> {
	/**
	 * 根据字典类型查询字典数据信息
	 * @param dictType 字典类型
	 * @return List<SysDict>
	 * @author zmzhou
	 * @date 2020/08/27 11:32
	 */
	@Query(value = "from SysDict where status = '1' and dictType = ?1 order by dictSort asc")
	List<SysDict> selectDictDataByType(String dictType);
}
