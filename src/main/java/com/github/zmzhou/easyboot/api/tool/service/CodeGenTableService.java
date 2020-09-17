package com.github.zmzhou.easyboot.api.tool.service;

import java.util.ArrayList;
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
import com.github.zmzhou.easyboot.api.tool.vo.CodeGenTableParams;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.specification.Operator;
import com.github.zmzhou.easyboot.framework.specification.SimpleSpecificationBuilder;

/**
 * 代码生成业务表服务接口
 * @title CodeGenTableService
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CodeGenTableService {

	@Resource
	private CodeGenTableDao genTableDao;
	@Resource
	private CodeGenTableColumnDao columnDao;

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
		List<Object[]> list = genTableDao.selectDbTableList(StringUtils.trimToEmpty(tableName),
				StringUtils.trimToEmpty(tableComment));
		List<CodeGenTable> res = new ArrayList<>();
		// 遍历结果集设置返回结果
		Optional.ofNullable(list).orElse(new ArrayList<>()).forEach(obj -> {
			CodeGenTable table = new CodeGenTable();
			table.setTableName(obj[0].toString());
			table.setTableComment(obj[1].toString());
			table.setCreateTime((Date) obj[2]);
			if (obj[3] != null) {
				table.setUpdateTime((Date) obj[3]);
			}
			res.add(table);
		});
		return res;
	}
}
