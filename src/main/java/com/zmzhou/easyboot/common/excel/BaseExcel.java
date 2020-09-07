package com.zmzhou.easyboot.common.excel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zmzhou.easyboot.common.excel.handler.StyleCellWriteHandler;

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

    /** 序号 */
    @ExcelProperty(value = {EXCEL_NAME, "", "序号"}, index = 0)
    @ColumnWidth(8)
    private Long rowNo;
    /**
     * 创建时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "创建时间"})
    @DateTimeFormat("yyyy-MM-dd HH:mm")
    private Date createTime;
    /**
     * 更新时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "更新时间"})
    @DateTimeFormat("yyyy-MM-dd HH:mm")
    private Date updateTime;
    /**
     * The Remark.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "备注"})
    @ColumnWidth(10)
    private String remark;
    /**
     * Gets create time.
     *
     * @return the create time
     */
    public Date getCreateTime() {
        if (null == this.createTime) {
            return null;
        }
        return (Date) (this.createTime).clone();
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(Date createTime) {
        if (null == createTime) {
            this.createTime = null;
        } else {
            this.createTime = (Date) (createTime).clone();
        }
    }

    /**
     * Gets update time.
     *
     * @return the update time
     */
    public Date getUpdateTime() {
        if (null == this.updateTime) {
            return null;
        }
        return (Date) (this.updateTime).clone();
    }

    /**
     * Sets update time.
     *
     * @param updateTime the update time
     */
    public void setUpdateTime(Date updateTime) {
        if (null == updateTime) {
            this.updateTime = null;
        } else {
            this.updateTime = (Date) (updateTime).clone();
        }
    }
}
