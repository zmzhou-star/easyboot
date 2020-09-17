package com.github.zmzhou.easyboot.api.tool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;

/**
 * 代码生成业务表字段(CodeGenTableColumn)表数据库访问层
 * @title CodeGenTableColumnDao
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:42
 */
public interface CodeGenTableColumnDao extends JpaRepository<CodeGenTableColumn, Long>, JpaSpecificationExecutor<CodeGenTableColumn> {

	/**
	 * 删除表对应的字段数据
	 * @param tableIds 表id
	 * @author zmzhou
	 * @date 2020/9/17 20:23
	 */
	@Modifying
	@Query("delete from CodeGenTableColumn tc where tc.tableId in (?1)")
	void deleteByTableIds(Long[] tableIds);
}
