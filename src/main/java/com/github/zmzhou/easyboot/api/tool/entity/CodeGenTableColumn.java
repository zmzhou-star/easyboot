package com.github.zmzhou.easyboot.api.tool.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;
import lombok.ToString;

/**
 * 代码生成业务表字段(CodeGenTableColumn)实体类
 * @title CodeGenTableColumn
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/16 23:38
 */
@Data
@ToString
@Entity
@Table(name = "CODE_GEN_TABLE_COLUMN")
public class CodeGenTableColumn extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 7589127122678497449L;
	/**
	 * 归属表编号
	 */
	@NotBlank(message = "所属表ID不能为空")
	private Long tableId;
	/**
	 * 列名称
	 */
	@NotBlank(message = "列名称不能为空")
	private String columnName;
	/**
	 * 列描述
	 */
	private String columnComment;
	/**
	 * 列类型
	 */
	@NotBlank(message = "列类型不能为空")
	private String columnType;
	/**
	 * JAVA类型
	 */
	private String javaType;
	/**
	 * JAVA字段名
	 */
	private String javaField;
	/**
	 * 是否主键（1是）
	 */
	private String isPk;
	/**
	 * 是否自增（1是）
	 */
	private String isIncrement;
	/**
	 * 是否必填（1是）
	 */
	private String isRequired;
	/**
	 * 是否为插入字段（1是）
	 */
	private String isInsert;
	/**
	 * 是否编辑字段（1是）
	 */
	private String isEdit;
	/**
	 * 是否列表字段（1是）
	 */
	private String isList;
	/**
	 * 是否查询字段（1是）
	 */
	private String isQuery;
	/**
	 * 查询方式（等于、不等于、大于、小于、范围）
	 */
	private String queryType;
	/**
	 * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
	 */
	private String htmlType;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 排序
	 */
	private Integer sortBy;
}
