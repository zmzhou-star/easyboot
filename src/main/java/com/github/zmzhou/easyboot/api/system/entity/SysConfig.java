package com.github.zmzhou.easyboot.api.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 参数配置实体类 sys_config
 *
 * @author zmzhou
 * @version 1.0
 * date 2020-11-16 21:51:23
 */
@Data
@Entity
@Table(name = "SYS_CONFIG")
public class SysConfig extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 2995109388827324065L;
    /** 参数名称 */
    @Size(min = 1, max = 64, message = "参数名称不正确")
    private String configName;

    /** 参数键名 */
    @Size(min = 1, max = 64, message = "参数键名不正确")
    private String configKey;

    /** 参数键值 */
    @Size(min = 1, max = 500, message = "参数键值不正确")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @Size(min = 1, max = 1, message = "系统内置选项不能为空")
    private String configType;

}
