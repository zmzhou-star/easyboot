package com.zmzhou.easyboot.api.system.excel;

import org.springframework.beans.BeanUtils;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.common.excel.BaseExcel;
import com.zmzhou.easyboot.common.excel.converters.GenderConverter;
import com.zmzhou.easyboot.common.excel.converters.StatusConverter;

import lombok.Data;

/**
 * @title SysUserExcel
 * @description 用户管理导出excel
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/3 21:03
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(15)
public class SysUserExcel extends BaseExcel {
    /** serialVersionUID */
    private static final long serialVersionUID = -3304319243957836923L;

    /**
     * The Username.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "用户名称"})
    private String username;
    /**
     * The Nick name.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "用户昵称"})
    private String nickName;
    /**
     * The Tel.
     */
    @ExcelProperty(value = {EXCEL_NAME, "", "手机号码"})
    private String tel;
    /**
     * The Email.
     */
    @ColumnWidth(20)
    @ExcelProperty(value = {EXCEL_NAME, "", "邮箱"})
    private String email;
    /**
     * The Sex.
     */
    @ColumnWidth(10)
    @ExcelProperty(value = {EXCEL_NAME, "", "性别"}, converter = GenderConverter.class)
    private String sex;
    /**
     * The Status.
     */
    @ColumnWidth(10)
    @ExcelProperty(value = {EXCEL_NAME, "", "状态"}, converter = StatusConverter.class)
    private String status;
    /**
     * Entity sys user.
     *
     * @return the sys user
     */
    public SysUser toEntity(){
        SysUser e = new SysUser();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
