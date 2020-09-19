package com.github.zmzhou.easyboot.api.tool.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.zmzhou.easyboot.api.tool.dao.CodeGenTableColumnDao;
import com.github.zmzhou.easyboot.api.tool.dao.CodeGenTableDao;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;
import com.github.zmzhou.easyboot.api.tool.util.CodeGenUtils;
import com.github.zmzhou.easyboot.api.tool.vo.CodeGenTableParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成业务表服务接口
 * @title CodeGenTableService
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:28
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeGenTableService {

	@Resource
	private CodeGenTableDao genTableDao;
	@Resource
	private CodeGenTableColumnDao columnDao;
	@Resource
	private CodeGenTableColumnService tableColumnService;

	/**
	 * 获取代码生成列表
	 *
	 * @param params 查询参数
	 * @return Page<CodeGenTable>
	 * @author zmzhou
	 * @date 2020/9/16 23:47
	 */
	public Page<CodeGenTable> findAll(CodeGenTableParams params, Pageable pageable) {
		// 构造分页排序条件
		Pageable page = pageable;
		if (pageable.getSort().equals(Sort.unsorted())) {
			Sort sort = Sort.by(Sort.Order.asc(Constants.TABLE_NAME));
			page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
		// 构造查询条件
		Specification<CodeGenTable> spec = new SimpleSpecificationBuilder<CodeGenTable>()
				.and(Constants.TABLE_NAME, Operator.LIKE, params.getTableName())
				.and("tableComment", Operator.LIKE, params.getTableComment())
				.between("and", Constants.CREATE_TIME, params.getBeginTime(), params.getEndTime())
				.build();
		return genTableDao.findAll(spec, page);
	}

	/**
	 * 删除代码生成数据
	 * @param ids 代码生成ID
	 * @author zmzhou
	 * @date 2020/9/17 0:03
	 */
	public void delete(Long[] ids) {
		for (Long id: ids) {
			// 删除表对应的字段数据
			columnDao.deleteByTableIds(ids);
			// 根据id删除代码生成数据
			genTableDao.deleteById(id);
		}
	}

	/**
	 * 查询数据库表列表
	 * @param tableName 表名称
	 * @param tableComment 表描述
	 * @return 数据库表列表
	 * @author zmzhou
	 * @date 2020/9/17 20:31
	 */
	public List<CodeGenTable> selectDbTableList(String tableName, String tableComment) {
		// 查询数据库表列表
		List<Object[]> list = genTableDao.selectDbTableList(StringUtils.trimToEmpty(tableName),
				StringUtils.trimToEmpty(tableComment));
		return converter(list);
	}

	/**
	 * 根据表名查询数据库表信息
	 * @param tableNames 表名数组
	 * @return 实体类集合
	 * @author zmzhou
	 * date 2020/9/18 21:40
	 */
	public List<CodeGenTable> selectDbTableList(String tableNames) {
		if (StringUtils.isBlank(tableNames)) {
			return Collections.emptyList();
		}
		List<String> tableNameList = Arrays.asList(tableNames.split(Constants.COMMA));
		// 查询数据库表列表
		List<Object[]> list = genTableDao.selectDbTableList(tableNameList);
		return converter(list);
	}

	/**
	 * 数据库表信息转换成实体类
	 * @param list 数据库表四要素(表名，表描述，创建时间，更新时间)集合
	 * @return 实体类集合
	 * @author zmzhou
	 * date 2020/9/18 21:38
	 */
	private List<CodeGenTable> converter(List<Object[]> list) {
		List<CodeGenTable> res = new ArrayList<>();
		// 遍历结果集设置返回结果
		Optional.ofNullable(list).orElse(new ArrayList<>()).forEach(obj -> {
			CodeGenTable table = new CodeGenTable(obj[0].toString(), obj[1].toString(), (Date) obj[2]);
			// 表更新时间可能为空
			if (obj[Constants.INT_THREE] != null) {
				table.setUpdateTime((Date) obj[Constants.INT_THREE]);
			}
			res.add(table);
		});
		return res;
	}

	/**
	 * 导入生成代码的表结构保存
	 * Import Code gen table string.
	 * @param tableNames the table names
	 * @return the string
	 */
	public String importCodeGenTable(String tableNames) {
		// 根据表名查询数据库表信息
		List<CodeGenTable> tableList = this.selectDbTableList(tableNames);
		if (tableList.isEmpty()) {
			return "没有需要导入的数据";
		}
		String operName = SecurityUtils.getUsername();
		tableList.forEach(table -> {
			try {
				// 初始化表信息
				CodeGenUtils.initTable(table, operName);
				table = genTableDao.saveAndFlush(table);
				// 代码生成业务数据保存成功，再保存表字段信息
				if (table.getId() > 0) {
					String tableName = table.getTableName();
					// 根据表名查询表字段信息
					List<CodeGenTableColumn> tableColumns = tableColumnService.selectDbTableColumns(tableName);
					CodeGenTable finalTable = table;
					// 遍历初始化表字段信息
					tableColumns.forEach(column -> CodeGenUtils.initColumnField(column, finalTable));
					// 批量保存
					tableColumnService.saveAll(tableColumns);
				}
			} catch (Exception e) {
				log.error("表：{}导入失败", table.getTableName(), e);
				throw new BaseException("表[0]导入失败", table.getTableName());
			}
		});
		return "导入成功";
	}
}
