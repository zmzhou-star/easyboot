package com.github.zmzhou.easyboot.framework.page;

import java.io.Serializable;
import java.util.List;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * @description 表格分页数据对象
 * @author zmzhou
 * @date 2020/07/02 17:41
 */
@Data
public class TableDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<? extends BaseEntity> rows;

}
