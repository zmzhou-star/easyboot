package com.github.zmzhou.easyboot.api.tool.util;

import java.util.Date;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.EasyBootUtils;
import com.github.zmzhou.easyboot.framework.config.CodeGenConfig;

/**
 * 代码生成器 工具类
 * @author zmzhou
 * @version 1.0
 * date 2020/9/18 23:18
 */
public final class CodeGenUtils {
	/**
	 * 构造器
	 * @author zmzhou
	 * date 2020/9/18 22:36
	 */
	private CodeGenUtils() {
		// 构造器
	}

	/**
	 * 初始化表信息
	 * Init table.
	 * @param genTable the gen table
	 * @param operName the oper name
	 */
	public static void initTable(CodeGenTable genTable, String operName) {
		// 读取代码生成相关配置
		CodeGenConfig codeGenConfig = CodeGenConfig.getInstance();
		genTable.setClassName(convertClassName(genTable.getTableName()));
		genTable.setPackageName(codeGenConfig.getPackageName());
		genTable.setModuleName(getModuleName(codeGenConfig.getPackageName()));
		genTable.setBusinessName(getBusinessName(genTable.getTableName()));
		genTable.setFunctionName(replaceText(genTable.getTableComment()));
		genTable.setFunctionAuthor(codeGenConfig.getAuthor());
		genTable.setCreateBy(operName);
		genTable.setTplCategory(CodeGenConstants.TPL_CRUD);
	}

	/**
	 * 初始化列属性字段
	 */
	public static void initColumnField(CodeGenTableColumn column, CodeGenTable table) {
		String dataType = getDbType(column.getColumnType());
		String columnName = column.getColumnName();
		column.setTableId(table.getId());
		column.setCreateBy(table.getCreateBy());
		column.setCreateTime(new Date());
		column.setDictType("");
		// 设置java字段名
		column.setJavaField(EasyBootUtils.toCamelCase(columnName));

		if (CodeGenConstants.COLUMNTYPE_STR.contains(dataType)) {
			column.setJavaType(CodeGenConstants.TYPE_STRING);
			// 字符串长度超过500设置为文本域
			Integer columnLength = getColumnLength(column.getColumnType());
			String htmlType = columnLength >= 500 ? CodeGenConstants.HTML_TEXTAREA : CodeGenConstants.HTML_INPUT;
			column.setHtmlType(htmlType);
		} else if (CodeGenConstants.COLUMNTYPE_TIME.contains(dataType)) {
			column.setJavaType(CodeGenConstants.TYPE_DATE);
			column.setHtmlType(CodeGenConstants.HTML_DATETIME);
		} else if (CodeGenConstants.COLUMNTYPE_NUMBER.contains(dataType)) {
			column.setHtmlType(CodeGenConstants.HTML_INPUT);

			// 如果是浮点型
			String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(),
					Constants.LEFT_PARENTHESIS, Constants.RIGHT_PARENTHESIS), Constants.COMMA);
			if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
				column.setJavaType(CodeGenConstants.TYPE_DOUBLE);
			}
			// 如果是整形
			else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
				column.setJavaType(CodeGenConstants.TYPE_INTEGER);
			}
			// 长整形
			else {
				column.setJavaType(CodeGenConstants.TYPE_LONG);
			}
		}

		// 插入字段（默认所有字段都需要插入）
		column.setIsInsert(CodeGenConstants.REQUIRE);

		// 编辑字段
		if (!CodeGenConstants.COLUMNNAME_NOT_EDIT.contains(columnName) && !column.isPk()) {
			column.setIsEdit(CodeGenConstants.REQUIRE);
		}
		// 列表字段
		if (!CodeGenConstants.COLUMNNAME_NOT_LIST.contains(columnName) && !column.isPk()) {
			column.setIsList(CodeGenConstants.REQUIRE);
		}
		// 查询字段
		if (!CodeGenConstants.COLUMNNAME_NOT_QUERY.contains(columnName) && !column.isPk()) {
			column.setIsQuery(CodeGenConstants.REQUIRE);
		}

		// 查询字段类型
		if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
			column.setQueryType(CodeGenConstants.QUERY_LIKE);
		}
		// 状态字段设置单选框
		if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
			column.setHtmlType(CodeGenConstants.HTML_RADIO);
		}
		// 类型&性别字段设置下拉框
		else if (StringUtils.endsWithIgnoreCase(columnName, "type")
				|| StringUtils.endsWithIgnoreCase(columnName, "sex")) {
			column.setHtmlType(CodeGenConstants.HTML_SELECT);
		}
	}

	/**
	 * 获取模块名
	 *
	 * @param packageName 包名
	 * @return 模块名
	 */
	private static String getModuleName(String packageName) {
		int lastIndex = packageName.lastIndexOf(Constants.DOT);
		int nameLength = packageName.length();
		return StringUtils.substring(packageName, lastIndex + 1, nameLength);
	}

	/**
	 * 获取业务名
	 *
	 * @param tableName 表名
	 * @return 业务名
	 */
	private static String getBusinessName(String tableName) {
		int lastIndex = tableName.lastIndexOf(Constants.UNDERLINE);
		int nameLength = tableName.length();
		return StringUtils.substring(tableName, lastIndex + 1, nameLength);
	}

	/**
	 * 表名转换成Java类名
	 *
	 * @param tableName 表名称
	 * @return 类名
	 */
	private static String convertClassName(String tableName) {
		// 读取代码生成相关配置
		CodeGenConfig genConfig = CodeGenConfig.getInstance();
		String tablePrefix = genConfig.getTablePrefix();
		if (genConfig.isAutoRemovePre() && StringUtils.isNotEmpty(tablePrefix)) {
			String[] searchList = StringUtils.split(tablePrefix, Constants.COMMA);
			tableName = replaceFirst(tableName, searchList);
		}
		return EasyBootUtils.convertToCamelCase(tableName);
	}

	/**
	 * 批量替换前缀
	 *
	 * @param replacement 替换值
	 * @param searchList   替换列表
	 * @return String
	 */
	private static String replaceFirst(String replacement, String[] searchList) {
		String text = replacement;
		for (String searchString : searchList) {
			if (replacement.startsWith(searchString)) {
				text = replacement.replaceFirst(searchString, "");
				break;
			}
		}
		return text;
	}

	/**
	 * 关键字替换
	 *
	 * @param text 需要被替换的名字
	 * @return 替换后的名字
	 */
	private static String replaceText(String text) {
		return RegExUtils.replaceAll(text, "(?:表)", "");
	}

	/**
	 * 获取数据库类型字段
	 *
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	private static String getDbType(String columnType) {
		if (StringUtils.indexOf(columnType, Constants.LEFT_PARENTHESIS) > 0) {
			return StringUtils.substringBefore(columnType, Constants.LEFT_PARENTHESIS);
		} else {
			return columnType;
		}
	}

	/**
	 * 获取字段长度
	 *
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	private static Integer getColumnLength(String columnType) {
		if (StringUtils.indexOf(columnType, Constants.LEFT_PARENTHESIS) > 0) {
			String length = StringUtils.substringBetween(columnType, Constants.LEFT_PARENTHESIS,
					Constants.RIGHT_PARENTHESIS);
			return Integer.valueOf(length);
		} else {
			return 0;
		}
	}

}
