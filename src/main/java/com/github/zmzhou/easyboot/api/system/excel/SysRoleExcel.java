package com.github.zmzhou.easyboot.api.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.github.zmzhou.easyboot.common.excel.converters.StatusConverter;
import com.github.zmzhou.easyboot.common.excel.BaseExcel;

import lombok.Data;

/**
 * @title SysRoleExcel
 * @description 角色导出excel
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/3 21:03
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysRoleExcel extends BaseExcel {
    /** serialVersionUID */
    private static final long serialVersionUID = -3304319243957836923L;

    /**
     * The Role name.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "角色名称"})
    private String roleName;
    /**
     * The Role code.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "角色编码"})
    private String roleCode;
    /**
     * The Status.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "状态"}, converter = StatusConverter.class)
    private String status;

}
