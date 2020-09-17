package com.github.zmzhou.easyboot.api.tool.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;
import lombok.ToString;

/**
 * 代码生成业务表(CodeGenTable)实体类
 * @title CodeGenTable
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:38
 */
@Data
@ToString
@Entity
@Table(name = "CODE_GEN_TABLE")
public class CodeGenTable extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = -3106790670453646320L;
	/**
	 * 表名称
	 */
	@NotBlank(message = "表名称不能为空")
	private String tableName;
	/**
	 * 表描述
	 */
	@NotBlank(message = "表描述不能为空")
	private String tableComment;
	/**
	 * 实体类名称
	 */
	@NotBlank(message = "实体类名称不能为空")
	private String className;
	/**
	 * 使用的模板（crud单表操作 tree树表操作）
	 */
	private String tplCategory;
	/**
	 * 生成包路径
	 */
	@NotBlank(message = "生成包路径不能为空")
	private String packageName;
	/**
	 * 生成模块名
	 */
	@NotBlank(message = "生成模块名不能为空")
	private String moduleName;
	/**
	 * 生成业务名
	 */
	@NotBlank(message = "生成业务名不能为空")
	private String businessName;
	/**
	 * 生成功能名
	 */
	@NotBlank(message = "生成功能名不能为空")
	private String functionName;
	/**
	 * 生成功能作者
	 */
	@NotBlank(message = "作者不能为空")
	private String functionAuthor;
	/**
	 * 其它生成选项
	 */
	private String others;

	/** 主键信息 */
	@Transient
	private CodeGenTableColumn pkColumn;
	/** 表列信息 */
	@Valid
	@Transient
	private List<CodeGenTableColumn> columns;
}
