package com.github.zmzhou.easyboot.common.excel;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.github.zmzhou.easyboot.common.excel.handler.StyleCellWriteHandler;

import lombok.Data;

/**
 * The type Base excel.
 *
 * @author zmzhou
 * @version 1.0
 * @title BaseExcel
 * @description excel导出vo父类
 * @date 2020 /9/3 20:58
 */
@Data
@MappedSuperclass
public class BaseExcel implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主要作用是占位子，实际标题从js传参在{@link StyleCellWriteHandler}中127行设置
     */
    public static final String EXCEL_NAME = "表头大标题";
    /** 日期格式化正则 */
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    /** 序号 */
    @ExcelProperty(value = {EXCEL_NAME, "", "序号"}, index = 0)
    @ColumnWidth(8)
    private Long rowNo;
}
