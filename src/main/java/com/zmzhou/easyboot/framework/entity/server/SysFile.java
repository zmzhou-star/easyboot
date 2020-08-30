package com.zmzhou.easyboot.framework.entity.server;

import lombok.Data;

/**
 *  @title SysFile
 *  @Description 系统文件相关信息
 *  @author zmzhou
 *  @Date 2020/08/29 15:01
 */
@Data
public class SysFile
{
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;
}
