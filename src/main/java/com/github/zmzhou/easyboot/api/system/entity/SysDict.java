package com.github.zmzhou.easyboot.api.system.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 数据字典实体类 sys_dict
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 16:47:04
 */
@Data
@Entity
@Table(name = "SYS_DICT")
public class SysDict extends BaseEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8406166378325512379L;
	/** 字典排序 */
	private Integer dictSort;
	/** 字典标签 */
	private String dictLabel;
	/** 字典键值 */
	private String dictValue;
	/** 字典名称 */
	private String dictName;
	/** 字典类型 */
	private String dictType;

	/** 样式属性（其他样式扩展） */
	private String cssClass;

	/** 表格回显样式 */
	private String listClass;

	/** 状态（1正常 0停用） */
	private String status;
}
