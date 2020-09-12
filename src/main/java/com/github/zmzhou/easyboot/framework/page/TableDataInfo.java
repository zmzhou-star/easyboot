package com.github.zmzhou.easyboot.framework.page;

import java.io.Serializable;
import java.util.List;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 表格分页数据对象
 * @author zmzhou
 * @date 2020/07/02 17:41
 */
@Data
@ApiModel(description = "表格分页数据对象")
public class TableDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    @ApiModelProperty(value = "总记录数")
    private long total;

    /** 列表数据 */
    @ApiModelProperty(value = "列表数据")
    private List<? extends BaseEntity> rows;

}
