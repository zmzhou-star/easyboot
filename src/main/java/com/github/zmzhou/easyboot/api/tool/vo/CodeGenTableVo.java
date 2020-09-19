package com.github.zmzhou.easyboot.api.tool.vo;

import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.system.vo.BaseVo;
import com.github.zmzhou.easyboot.api.tool.entity.CodeGenTable;
import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成业务表信息
 *
 * @author zmzhou
 * @version 1.0
 * date 2020/9/19 22:58
 */
@Data
@ApiModel(description = "代码生成业务表信息vo类")
public class CodeGenTableVo extends CodeGenTable implements BaseVo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6623402198482673460L;
	/**
	 * 请求参数
	 */
	private transient Map<String, Object> params;
	/**
	 * 树编码字段
	 */
	@ApiModelProperty(value = "树编码字段")
	private String treeCode;
	/**
	 * 树父编码字段
	 */
	@ApiModelProperty(value = "树父编码字段")
	private String treeParentCode;
	/**
	 * 树名称字段
	 */
	@ApiModelProperty(value = "树名称字段")
	private String treeName;

	/**
	 * vo转实体类
	 *
	 * @return {@link BaseEntity}
	 * @author zmzhou
	 * date 2020/9/7 22:16
	 */
	@Override
	public CodeGenTable toEntity() {
		CodeGenTable e = new CodeGenTable();
		BeanUtils.copyProperties(this, e);
		return e;
	}
}
