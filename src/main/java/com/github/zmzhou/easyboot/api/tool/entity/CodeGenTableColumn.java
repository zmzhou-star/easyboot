package com.github.zmzhou.easyboot.api.tool.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CODE_GEN_TABLE_COLUMN")
public class CodeGenTableColumn extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 7589127122678497449L;
	/**
	 * 所属表id
	 */
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
	/**
	 * 是否主键（1是）
	 * @return 主键
	 * @author zmzhou
	 * date 2020/9/18 23:08
	 */
	public boolean isPk() {
		return StringUtils.isNotBlank(this.isPk) && StringUtils.equals(Constants.ONE, this.isPk);
	}

	/**
	 * 是否列表字段（1：是）
	 * Is list boolean.
	 * @return the boolean
	 */
	public boolean isList() {
		return this.isList != null && StringUtils.equals(Constants.ONE, this.isList);
	}

	/**
	 * JAVA字段名是不是超类字段
	 * Is super column boolean.
	 * @return the boolean
	 */
	public boolean isSuperColumn() {
		return isSuperColumn(this.javaField);
	}

	/**
	 * JAVA字段名是不是超类字段
	 * Is super column boolean.
	 * @param javaField the java field
	 * @return the boolean
	 */
	public boolean isSuperColumn(String javaField) {
		return StringUtils.equalsAnyIgnoreCase(javaField,
				// BaseEntity
				"id", "createBy", "createTime", "updateBy", "updateTime", "remark",
				// TreeEntity
				"parentName", "parentId", "orderNum", "ancestors");
	}
}
