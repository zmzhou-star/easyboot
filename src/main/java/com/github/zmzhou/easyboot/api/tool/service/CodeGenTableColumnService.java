package com.github.zmzhou.easyboot.api.tool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.tool.dao.CodeGenTableColumnDao;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 代码生成业务表字段服务接口
 * @author zmzhou
 * @version 1.0
 * date 2020/9/16 23:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeGenTableColumnService {
	@Resource
	private CodeGenTableColumnDao tableColumnDao;

	/**
	 * 根据表名查询表字段信息
	 * Select db table columns list.
	 * @param tableName the table name
	 * @return the list
	 */
	public List<CodeGenTableColumn> selectDbTableColumns(String tableName) {
		// 查询数据库表列表
		List<String[]> list = tableColumnDao.selectDbTableColumns(tableName);
		return converter(list);
	}

	/**
	 * 数据库表字段信息转换成实体类
	 * @param list 数据库表字段信息(列名，列备注，字段类型，排序，是否必填，是否主键，是否自增)集合
	 * @return 实体类集合
	 * @author zmzhou
	 * date 2020/9/18 15:38
	 */
	private List<CodeGenTableColumn> converter(List<String[]> list) {
		List<CodeGenTableColumn> res = new ArrayList<>();
		// 遍历结果集设置返回结果
		Optional.ofNullable(list).orElse(new ArrayList<>()).forEach(obj -> {
			// 字段信息(列名，列备注，字段类型，排序，是否必填，是否主键，是否自增)
			CodeGenTableColumn column = CodeGenTableColumn.builder()
					.columnName(obj[0])
					.columnComment(obj[1])
					.columnType(obj[2])
					.sortBy(Integer.parseInt(obj[3]))
					.isRequired(obj[4])
					.isPk(obj[5])
					.isIncrement(obj[6])
					.build();
			res.add(column);
		});
		return res;
	}
	/**
	 * 批量保存
	 * Save all.
	 * @param codeGenTableColumns the code gen table columns
	 */
	public List<CodeGenTableColumn> saveAll(List<CodeGenTableColumn> codeGenTableColumns) {
		return tableColumnDao.saveAll(codeGenTableColumns);
	}

	/**
	 * 根据tableId查询所有的列
	 * Select columns list.
	 * @param tableId the table id
	 * @return the list
	 */
	public List<CodeGenTableColumn> selectColumns(Long tableId) {
		// 构造查询条件
		Specification<CodeGenTableColumn> spec = new SimpleSpecificationBuilder<CodeGenTableColumn>()
				.and("tableId", Operator.EQUAL, tableId)
				.build();
		return tableColumnDao.findAll(spec);
	}
}
