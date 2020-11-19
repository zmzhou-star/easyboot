package com.github.zmzhou.easyboot.api.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 通知公告信息实体类 sys_notice
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-11-19 14:15:47
 */
@Data
@Entity
@Table(name = "SYS_NOTICE")
public class SysNotice extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 7016693290323699995L;
    /** 公告标题 */
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 50, message = "公告标题长度不符")
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    @NotBlank(message = "公告类型不能为空")
    private String noticeType;

    /** 公告内容 */
    @NotBlank(message = "公告内容不能为空")
    private String noticeContent;

    /** 公告状态（1正常 0关闭） */
    @NotBlank(message = "公告状态不能为空")
    private String status;
}
