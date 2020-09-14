package com.github.zmzhou.easyboot.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 只有id的实体类父类
 * @title BaseIdEntity
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/13 17:49
 */
@Data
@MappedSuperclass
public class BaseIdEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column(name = "ID")
	private Long id;
}
