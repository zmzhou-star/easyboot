package com.github.zmzhou.easyboot.api.tool.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;

/**
 * 代码生成业务表(CodeGenTable)表数据库访问层
 * @title CodeGenTableDao
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:41
 */
public interface CodeGenTableDao extends JpaRepository<CodeGenTable, Long>, JpaSpecificationExecutor<CodeGenTable> {

	/**
	 * 查询数据库表列表
	 * <p>
	 *      排除code_gen_开头和quartz_开头的表
	 * </p>
	 * @param tableName 表名称
	 * @param tableComment 表描述
	 * @return 数据库表列表
	 * @author zmzhou
	 * @date 2020/9/17 20:52
	 */
	@Query(value = "select table_name, table_comment, create_time, update_time from information_schema.tables" +
			" where table_schema = (select database())" +
			" AND table_name NOT LIKE 'quartz_%' AND table_name NOT LIKE 'code_gen_%'" +
			" AND table_name NOT IN (select table_name from code_gen_table)" +
			" AND lower(table_name) like lower(concat('%', ?1, '%'))" +
			" AND lower(table_comment) like lower(concat('%', ?2, '%')) order by create_time desc",
			nativeQuery = true)
	List<Object[]> selectDbTableList(String tableName, String tableComment);

	/**
	 * 根据表名查询数据库表信息
	 * <p>
	 *      排除code_gen_开头和quartz_开头的表
	 * </p>
	 * @param tableNames 表名数组
	 * @return 数据库表信息
	 * @author zmzhou
	 * date 2020/9/18 21:42
	 */
	@Query(value = "select table_name, table_comment, create_time, update_time from information_schema.tables" +
			" where table_schema = (select database())" +
			" AND table_name NOT LIKE 'quartz_%' AND table_name NOT LIKE 'code_gen_%'" +
			" AND table_name IN (:tableNames)",
			nativeQuery = true)
	List<Object[]> selectDbTableList(@Param("tableNames") List<String> tableNames);
}
