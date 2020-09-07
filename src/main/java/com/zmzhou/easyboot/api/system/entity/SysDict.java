package com.zmzhou.easyboot.api.system.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * The type Sys dict.
 *
 * @author zmzhou
 * @title SysDict
 * @Description 描述
 * @Date 2020 /08/27 11:23
 */
@Data
@Entity
@Table(name = "SYS_DICT")
public class SysDict extends BaseEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8406166378325512379L;
	/**
	 * The Dict sort.
	 */
	private long dictSort;
	/**
	 * The Dict label.
	 */
	private String dictLabel;
	/**
	 * The Dict value.
	 */
	private String dictValue;
	/**
	 * The Dict type.
	 */
	private String dictType;
	/**
	 * The Css class.
	 */
	private String cssClass;
	/**
	 * The List class.
	 */
	private String listClass;
	/**
	 * The Is default.
	 */
	private String isDefault;
	/**
	 * The Status.
	 */
	private String status;

}
