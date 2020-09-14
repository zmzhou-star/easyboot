package com.github.zmzhou.easyboot.common.excel;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.Data;

/**
 * excel导出vo带有创建时间，更新时间和备注的父类
 * The type Base excel 3.
 *
 * @author zmzhou
 * @version 1.0
 * @title BaseExcel
 * @date 2020/9/3 20:58
 */
@Data
@MappedSuperclass
public class BaseExcel3 extends BaseExcel {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6324113722059171793L;
    /**
     * 创建时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "创建时间"})
    @DateTimeFormat(DATE_PATTERN)
    private Date createTime;
    /**
     * 更新时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "更新时间"})
    @DateTimeFormat(DATE_PATTERN)
    private Date updateTime;
    /**
     * The Remark.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "备注"})
    @ColumnWidth(15)
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
