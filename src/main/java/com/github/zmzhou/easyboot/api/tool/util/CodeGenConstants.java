package com.github.zmzhou.easyboot.api.tool.util;

import java.util.Arrays;
import java.util.List;

/**
 * 代码生成通用常量
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/18 22:33
 */
public final class CodeGenConstants {
	/**
	 * 单表（增删改查）
	 */
	public static final String TPL_CRUD = "crud";

	/**
	 * 树表（增删改查）
	 */
	public static final String TPL_TREE = "tree";

	/**
	 * 树编码字段
	 */
	public static final String TREE_CODE = "treeCode";

	/**
	 * 树父编码字段
	 */
	public static final String TREE_PARENT_CODE = "treeParentCode";

	/**
	 * 树名称字段
	 */
	public static final String TREE_NAME = "treeName";

	/**
	 * 文本框
	 */
	public static final String HTML_INPUT = "input";

	/**
	 * 文本域
	 */
	public static final String HTML_TEXTAREA = "textarea";

	/**
	 * 下拉框
	 */
	public static final String HTML_SELECT = "select";

	/**
	 * 单选框
	 */
	public static final String HTML_RADIO = "radio";

	/**
	 * 复选框
	 */
	public static final String HTML_CHECKBOX = "checkbox";

	/**
	 * 日期控件
	 */
	public static final String HTML_DATETIME = "datetime";

	/**
	 * 字符串类型
	 */
	public static final String TYPE_STRING = "String";

	/**
	 * 整型
	 */
	public static final String TYPE_INTEGER = "Integer";

	/**
	 * 长整型
	 */
	public static final String TYPE_LONG = "Long";

	/**
	 * 浮点型
	 */
	public static final String TYPE_DOUBLE = "Double";

	/**
	 * 高精度计算类型
	 */
	public static final String TYPE_BIGDECIMAL = "BigDecimal";

	/**
	 * 时间类型
	 */
	public static final String TYPE_DATE = "Date";

	/**
	 * 模糊查询
	 */
	public static final String QUERY_LIKE = "LIKE";

	/**
	 * 需要
	 */
	public static final String REQUIRE = "1";
	/** create_by */
	public static final String CREATE_BY = "create_by";
	/** create_time */
	public static final String CREATE_TIME = "create_time";
	/** del_flag */
	public static final String DEL_FLAG = "del_flag";
	/**
	 * 数据库字符串类型
	 */
	protected static final List<String> COLUMNTYPE_STR = Arrays.asList("char", "varchar", "narchar", "varchar2",
			"tinytext", "text", "mediumtext", "longtext");

	/**
	 * 数据库时间类型
	 */
	protected static final List<String> COLUMNTYPE_TIME = Arrays.asList(HTML_DATETIME, "time", "date", "timestamp");

	/**
	 * 数据库数字类型
	 */
	protected static final List<String> COLUMNTYPE_NUMBER = Arrays.asList("tinyint", "smallint", "mediumint", "int",
			"number", "integer", "bigint", "float", "float", "double", "decimal");

	/**
	 * 页面不需要编辑字段
	 */
	protected static final List<String> COLUMNNAME_NOT_EDIT = Arrays.asList("id", CREATE_BY, CREATE_TIME, DEL_FLAG);

	/**
	 * 页面不需要显示的列表字段
	 */
	protected static final List<String> COLUMNNAME_NOT_LIST = Arrays.asList("id", CREATE_BY, CREATE_TIME, DEL_FLAG,
			"update_by", "update_time");

	/**
	 * 页面不需要查询字段
	 */
	protected static final List<String> COLUMNNAME_NOT_QUERY = Arrays.asList("id", CREATE_BY, CREATE_TIME, DEL_FLAG,
			"update_by", "update_time", "remark");

	/**
	 * Entity基类字段
	 */
	protected static final List<String> BASE_ENTITY = Arrays.asList("createBy", "createTime", "updateBy",
			"updateTime", "remark");

	/**
	 * Tree基类字段
	 */
	protected static final List<String> TREE_ENTITY = Arrays.asList("parentName", "parentId", "orderNum",
			"ancestors", "children");
	/**
	 * 构造器
	 * @author zmzhou
	 * date 2020/9/18 22:36
	 */
	private CodeGenConstants() {
		// 构造器
	}
}
