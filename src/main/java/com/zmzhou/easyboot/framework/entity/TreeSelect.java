package com.zmzhou.easyboot.framework.entity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zmzhou.easyboot.api.system.entity.SysMenu;

import lombok.Data;

/**
 * The type Tree select.
 *
 * @author zmzhou
 * @title TreeSelect
 * @Description Treeselect树结构实体类
 * @Date 2020 /08/29 17:16
 */
@Data
public class TreeSelect implements Serializable {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 节点ID
	 */
	private Long id;
	
	/**
	 * 节点名称
	 */
	private String label;
	
	/**
	 * 子节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<TreeSelect> children;
	
	/**
	 * Instantiates a new Tree select.
	 */
	public TreeSelect() {
		super();
	}
	
	/**
	 * Instantiates a new Tree select.
	 *
	 * @param menu the menu
	 */
	public TreeSelect(SysMenu menu) {
		this.id = menu.getId();
		this.label = menu.getMenuName();
		this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}
}
