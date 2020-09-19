package com.github.zmzhou.easyboot.api.tool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;

/**
 * 代码生成业务表字段(CodeGenTableColumn)表数据库访问层
 * @author zmzhou
 * @version 1.0
 * date 2020/9/16 23:42
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

	/**
	 * 根据表名查询表字段信息
	 * @param tableName 表名
	 * @return 表字段信息(列名，列备注，字段类型，排序，是否必填，是否主键，是否自增)
	 * @author zmzhou
	 * date 2020/9/19 15:04
	 */
	@Query(value = "select column_name, column_comment, column_type, ordinal_position sortBy, " +
			" (case when (is_nullable = 'no' and column_key != 'PRI') then '1' else '0' end) as is_required," +
			" (case when column_key = 'PRI' then '1' else '0' end) as is_pk," +
			" (case when extra = 'auto_increment' then '1' else '0' end) as is_increment" +
			" from information_schema.columns where table_schema = (select database()) and table_name =?1" +
			" order by ordinal_position",
			nativeQuery = true)
	List<String[]> selectDbTableColumns(String tableName);
}
