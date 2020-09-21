package com.github.zmzhou.easyboot.api.tool.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.api.tool.dao.CodeGenTableColumnDao;
import com.github.zmzhou.easyboot.api.tool.dao.CodeGenTableDao;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;
import com.github.zmzhou.easyboot.api.tool.util.CodeGenConstants;
import com.github.zmzhou.easyboot.api.tool.util.CodeGenUtils;
import com.github.zmzhou.easyboot.api.tool.util.VelocityInitializer;
import com.github.zmzhou.easyboot.api.tool.util.VelocityUtils;
import com.github.zmzhou.easyboot.api.tool.vo.CodeGenTableParams;
import com.github.zmzhou.easyboot.api.tool.vo.CodeGenTableVo;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.SecurityUtils;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成业务表服务接口
 *
 * @author zmzhou
 * @version 1.0
 * @title CodeGenTableService
 * @date 2020 /9/16 23:28
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
	 * @param params   查询参数
	 * @param pageable the pageable
	 * @return Page<CodeGenTable> page
	 * @author zmzhou
	 * @date 2020 /9/16 23:47
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
	 *
	 * @param ids 代码生成ID
	 * @author zmzhou
	 * @date 2020 /9/17 0:03
	 */
	public void delete(Long[] ids) {
		for (Long id : ids) {
			// 删除表对应的字段数据
			columnDao.deleteByTableIds(ids);
			// 根据id删除代码生成数据
			genTableDao.deleteById(id);
		}
	}

	/**
	 * 查询数据库表列表
	 *
	 * @param tableName    表名称
	 * @param tableComment 表描述
	 * @return 数据库表列表 list
	 * @author zmzhou
	 * @date 2020 /9/17 20:31
	 */
	public List<CodeGenTable> selectDbTableList(String tableName, String tableComment) {
		// 查询数据库表列表
		List<Object[]> list = genTableDao.selectDbTableList(StringUtils.trimToEmpty(tableName),
				StringUtils.trimToEmpty(tableComment));
		return converter(list);
	}

	/**
	 * 根据表名查询数据库表信息
	 *
	 * @param tableNames 表名数组
	 * @return 实体类集合 list
	 * @author zmzhou  date 2020/9/18 21:40
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
	 *
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
	 *
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

	/**
	 * Gets one.
	 *
	 * @param id the id
	 * @return the one
	 */
	public CodeGenTable findById(Long id) {
		if (null == id) {
			return new CodeGenTable();
		}
		CodeGenTable table = genTableDao.findById(id).orElse(new CodeGenTable());
		if (table.getId() > 0) {
			// 根据id查询所有的列
			table.setColumns(tableColumnService.selectColumns(table.getId()));
		}
		return table;
	}

	/**
	 * 根据tableName查询数据生成表信息
	 *
	 * @param tableName the table name
	 * @return the code gen table
	 */
	public CodeGenTable findByName(String tableName) {
		// 构造查询条件
		Specification<CodeGenTable> spec = new SimpleSpecificationBuilder<CodeGenTable>()
				.and("tableName", Operator.EQUAL, tableName)
				.build();
		CodeGenTable table = genTableDao.findOne(spec).orElse(new CodeGenTable());
		if (table.getId() > 0) {
			// 根据id查询所有的列
			table.setColumns(tableColumnService.selectColumns(table.getId()));
		}
		return table;
	}
	/**
	 * 修改保存参数校验
	 * Validate edit.
	 *
	 * @param genTable the gen table
	 */
	private void validate(CodeGenTableVo genTable) {
		// 树表（增删改查）
		if (CodeGenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
			JSONObject params = (JSONObject) genTable.getParams();
			if (StringUtils.isEmpty(params.getString(CodeGenConstants.TREE_CODE))) {
				throw new BaseException("树编码字段不能为空");
			} else if (StringUtils.isEmpty(params.getString(CodeGenConstants.TREE_PARENT_CODE))) {
				throw new BaseException("树父编码字段不能为空");
			} else if (StringUtils.isEmpty(params.getString(CodeGenConstants.TREE_NAME))) {
				throw new BaseException("树名称字段不能为空");
			}
		}
	}

	/**
	 * 修改代码生成信息
	 * Update gen table code gen table.
	 *
	 * @param genTable the gen table
	 * @return the code gen table
	 */
	public CodeGenTable updateGenTable(CodeGenTableVo genTable) {
		// 修改保存参数校验
		this.validate(genTable);
		// 树表参数不为空
		if (!genTable.getParams().isEmpty()) {
			String options = JSON.toJSONString(genTable.getParams());
			genTable.setOthers(options);
		}
		genTable.setUpdateBy(SecurityUtils.getUsername());
		genTable.setUpdateTime(new Date());
		CodeGenTable table = genTableDao.saveAndFlush(genTable.toEntity());
		if (!genTable.getColumns().isEmpty()) {
			// 设置字段更新时间
			genTable.getColumns().forEach(column -> {
				column.setUpdateBy(SecurityUtils.getUsername());
				column.setUpdateTime(new Date());
				// 数据字段类型非空
				column.setDictType(StringUtils.trimToEmpty(column.getDictType()));
			});
			// 更新列信息
			table.setColumns(tableColumnService.saveAll(genTable.getColumns()));
		}
		return table;
	}

	/**
	 * 预览代码
	 *
	 * @param tableId 表编号
	 * @return 预览数据列表
	 */
	public Map<String, String> previewCode(Long tableId) {
		// 查询表信息
		CodeGenTable table = this.findById(tableId);
		// 查询列信息
		List<CodeGenTableColumn> columns = table.getColumns();
		setPkColumn(table, columns);
		// 初始化代码生成使用模板vm
		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);
		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		Map<String, String> dataMap = new LinkedHashMap<>();
		templates.forEach(temp -> {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template template = Velocity.getTemplate(temp, Constants.CHARSETS.displayName());
			template.merge(context, sw);
			dataMap.put(temp, sw.toString());
		});
		return dataMap;
	}

	/**
	 * 设置主键列信息
	 *
	 * @param table   业务表信息
	 * @param columns 业务字段列表
	 */
	public void setPkColumn(CodeGenTable table, List<CodeGenTableColumn> columns) {
		for (CodeGenTableColumn column : columns) {
			// 是否主键
			if (column.isPk()) {
				table.setPkColumn(column);
				break;
			}
		}
		// 为空就选第一个字段
		if (null == table.getPkColumn()) {
			table.setPkColumn(columns.get(0));
		}
	}

	/**
	 * 批量生成代码
	 *
	 * @param tableNames 表数组
	 * @return zip压缩包数据 byte [ ]
	 */
	public byte[] generatorCode(String tableNames) {
		if (StringUtils.isBlank(tableNames)) {
			throw new BaseException("请选择要生成代码的数据表");
		}
		String[] tables = tableNames.split(Constants.COMMA);
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
		     ZipOutputStream zipOs = new ZipOutputStream(bos)) {
			for (String tableName : tables) {
				generatorCode(tableName, zipOs);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			log.error("批量生成代码异常", e);
			throw new BaseException("批量生成代码异常");
		}
	}

	/**
	 * 查询表信息并生成代码
	 * @param tableName 表名
	 * @param zipOs ZipOutputStream
	 */
	private void generatorCode(String tableName, ZipOutputStream zipOs) {
		// 查询表信息
		CodeGenTable table = findByName(tableName);
		// 查询列信息
		List<CodeGenTableColumn> columns = table.getColumns();
		setPkColumn(table, columns);

		VelocityInitializer.initVelocity();
		VelocityContext context = VelocityUtils.prepareContext(table);
		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		templates.forEach(template -> {
			// 渲染模板
			try (StringWriter sw = new StringWriter()) {
				Template tpl = Velocity.getTemplate(template, Constants.CHARSETS.displayName());
				tpl.merge(context, sw);
				// 添加到zip
				zipOs.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
				IOUtils.write(sw.toString(), zipOs, Constants.CHARSETS.displayName());
				zipOs.flush();
				zipOs.closeEntry();
			} catch (IOException e) {
				log.error("渲染模板失败，表名：" + table.getTableName(), e);
				throw new BaseException("渲染模板失败，表名：[0]", table.getTableName());
			}
		});
	}
}
