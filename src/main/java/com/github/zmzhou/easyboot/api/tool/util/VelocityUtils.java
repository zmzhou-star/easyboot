package com.github.zmzhou.easyboot.api.tool.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTableColumn;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.utils.DateUtils;
import com.github.zmzhou.easyboot.common.utils.EasyBootUtils;

/**
 * velocity代码生成使用模板工具类
 *
 * @author zmzhou
 * @version 1.0  date 2020/9/20 17:38
 */
public final class VelocityUtils {
	/**
	 * 私有构造器
	 *
	 * @author zmzhou
	 * date 2020/9/20 17:36
	 */
	private VelocityUtils() {
		// 私有构造器
	}

	/**
	 * 项目空间路径
	 */
	private static final String PROJECT_PATH = "main/java";

	/**
	 * mybatis空间路径
	 */
	private static final String MYBATIS_PATH = "main/resources/mybatis";

	/**
	 * 设置模板变量信息
	 *
	 * @param genTable the gen table
	 * @return 模板列表 velocity context
	 */
	public static VelocityContext prepareContext(CodeGenTable genTable) {
		String moduleName = genTable.getModuleName();
		String businessName = genTable.getBusinessName();
		String packageName = genTable.getPackageName();
		String tplCategory = genTable.getTplCategory();
		String functionName = genTable.getFunctionName();

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("tplCategory", genTable.getTplCategory());
		velocityContext.put("tableName", genTable.getTableName());
		velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
		velocityContext.put("ClassName", genTable.getClassName());
		velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
		velocityContext.put("moduleName", genTable.getModuleName());
		velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName()));
		velocityContext.put("businessName", genTable.getBusinessName());
		velocityContext.put("basePackage", getPackagePrefix(packageName));
		velocityContext.put("packageName", packageName);
		velocityContext.put("author", genTable.getFunctionAuthor());
		velocityContext.put("datetime", DateUtils.getDate());
		velocityContext.put("pkColumn", genTable.getPkColumn());
		velocityContext.put("importList", getImportList(genTable.getColumns()));
		velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
		velocityContext.put("columns", genTable.getColumns());
		velocityContext.put("table", genTable);
		if (CodeGenConstants.TPL_TREE.equals(tplCategory)) {
			setTreeVelocityContext(velocityContext, genTable);
		}
		return velocityContext;
	}

	/**
	 * Sets tree velocity context.
	 *
	 * @param context  the context
	 * @param genTable the gen table
	 */
	public static void setTreeVelocityContext(VelocityContext context, CodeGenTable genTable) {
		String others = genTable.getOthers();
		JSONObject params = JSON.parseObject(others);
		String treeCode = getTreeCode(params);
		String treeParentCode = getTreeParentCode(params);
		String treeName = getTreeName(params);
		context.put(CodeGenConstants.TREE_CODE, treeCode);
		context.put(CodeGenConstants.TREE_PARENT_CODE, treeParentCode);
		context.put(CodeGenConstants.TREE_NAME, treeName);
		context.put("expandColumn", getExpandColumn(genTable));
		if (params.containsKey(CodeGenConstants.TREE_PARENT_CODE)) {
			context.put("tree_parent_code", params.getString(CodeGenConstants.TREE_PARENT_CODE));
		}
		if (params.containsKey(CodeGenConstants.TREE_NAME)) {
			context.put("tree_name", params.getString(CodeGenConstants.TREE_NAME));
		}
	}

	/**
	 * 获取模板信息
	 *
	 * @param tplCategory the tpl category
	 * @return 模板列表 template list
	 */
	public static List<String> getTemplateList(String tplCategory) {
		List<String> templates = new ArrayList<>();
		templates.add("templates/java/entity.java.vm");
		templates.add("templates/java/mapper.java.vm");
		templates.add("templates/java/service.java.vm");
		templates.add("templates/java/serviceImpl.java.vm");
		templates.add("templates/java/controller.java.vm");
		templates.add("templates/xml/mapper.xml.vm");
		templates.add("templates/sql/sql.vm");
		templates.add("templates/js/api.js.vm");
		if (CodeGenConstants.TPL_CRUD.equals(tplCategory)) {
			templates.add("templates/vue/index.vue.vm");
		} else if (CodeGenConstants.TPL_TREE.equals(tplCategory)) {
			templates.add("templates/vue/index-tree.vue.vm");
		}
		return templates;
	}

	/**
	 * 获取文件名
	 *
	 * @param template the template
	 * @param genTable the gen table
	 * @return the file name
	 */
	public static String getFileName(String template, CodeGenTable genTable) {
		// 文件名称
		String fileName = "";
		// 包路径
		String packageName = genTable.getPackageName();
		// 模块名
		String moduleName = genTable.getModuleName();
		// 大写类名
		String className = genTable.getClassName();
		// 业务名称
		String businessName = genTable.getBusinessName();

		String javaPath = PROJECT_PATH + Constants.SEPARATOR
				+ StringUtils.replace(packageName, ".", Constants.SEPARATOR);
		String mybatisPath = MYBATIS_PATH + Constants.SEPARATOR + moduleName;
		String vuePath = "vue";

		if (template.contains("entity.java.vm")) {
			fileName = String.format("%s/entity/%s.java", javaPath, className);
		} else if (template.contains("mapper.java.vm")) {
			fileName = String.format("%s/mapper/%sMapper.java", javaPath, className);
		} else if (template.contains("service.java.vm")) {
			fileName = String.format("%s/service/I%sService.java", javaPath, className);
		} else if (template.contains("serviceImpl.java.vm")) {
			fileName = String.format("%s/service/impl/%sServiceImpl.java", javaPath, className);
		} else if (template.contains("controller.java.vm")) {
			fileName = String.format("%s/controller/%sController.java", javaPath, className);
		} else if (template.contains("mapper.xml.vm")) {
			fileName = String.format("%s/%sMapper.xml", mybatisPath, className);
		} else if (template.contains("sql.vm")) {
			fileName = businessName + "Menu.sql";
		} else if (template.contains("api.js.vm")) {
			fileName = String.format("%s/api/%s/%s.js", vuePath, moduleName, businessName);
		} else if (template.contains("index.vue.vm")) {
			fileName = String.format("%s/views/%s/%s/index.vue", vuePath, moduleName, businessName);
		} else if (template.contains("index-tree.vue.vm")) {
			fileName = String.format("%s/views/%s/%s/index.vue", vuePath, moduleName, businessName);
		}
		return fileName;
	}

	/**
	 * 获取包前缀
	 *
	 * @param packageName 包名称
	 * @return 包前缀名称 package prefix
	 */
	public static String getPackagePrefix(String packageName) {
		int lastIndex = packageName.lastIndexOf(".");
		return StringUtils.substring(packageName, 0, lastIndex);
	}

	/**
	 * 根据列类型获取导入包
	 *
	 * @param columns 列集合
	 * @return 返回需要导入的包列表 import list
	 */
	public static Set<String> getImportList(List<CodeGenTableColumn> columns) {
		Set<String> importList = new HashSet<>();
		for (CodeGenTableColumn column : columns) {
			if (!column.isSuperColumn() && CodeGenConstants.TYPE_DATE.equals(column.getJavaType())) {
				importList.add("java.util.Date");
				importList.add("com.fasterxml.jackson.annotation.JsonFormat");
			} else if (!column.isSuperColumn() && CodeGenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
				importList.add("java.math.BigDecimal");
			}
		}
		return importList;
	}

	/**
	 * 获取权限前缀
	 *
	 * @param moduleName   模块名称
	 * @param businessName 业务名称
	 * @return 返回权限前缀 permission prefix
	 */
	public static String getPermissionPrefix(String moduleName, String businessName) {
		return String.format("%s:%s", moduleName, businessName);
	}

	/**
	 * 获取树编码
	 *
	 * @param others 生成其他选项
	 * @return 树编码 treecode
	 */
	public static String getTreeCode(JSONObject others) {
		if (others.containsKey(CodeGenConstants.TREE_CODE)) {
			return EasyBootUtils.toCamelCase(others.getString(CodeGenConstants.TREE_CODE));
		}
		return "";
	}

	/**
	 * 获取树父编码
	 *
	 * @param others 生成其他选项
	 * @return 树父编码 tree parent code
	 */
	public static String getTreeParentCode(JSONObject others) {
		if (others.containsKey(CodeGenConstants.TREE_PARENT_CODE)) {
			return EasyBootUtils.toCamelCase(others.getString(CodeGenConstants.TREE_PARENT_CODE));
		}
		return "";
	}

	/**
	 * 获取树名称
	 *
	 * @param others 生成其他选项
	 * @return 树名称 tree name
	 */
	public static String getTreeName(JSONObject others) {
		if (others.containsKey(CodeGenConstants.TREE_NAME)) {
			return EasyBootUtils.toCamelCase(others.getString(CodeGenConstants.TREE_NAME));
		}
		return "";
	}

	/**
	 * 获取需要在哪一列上面显示展开按钮
	 *
	 * @param genTable 业务表对象
	 * @return 展开按钮列序号 expand column
	 */
	public static int getExpandColumn(CodeGenTable genTable) {
		String others = genTable.getOthers();
		JSONObject paramsObj = JSON.parseObject(others);
		String treeName = paramsObj.getString(CodeGenConstants.TREE_NAME);
		int num = 0;
		for (CodeGenTableColumn column : genTable.getColumns()) {
			if (column.isList()) {
				num++;
				String columnName = column.getColumnName();
				if (columnName.equals(treeName)) {
					break;
				}
			}
		}
		return num;
	}
}
