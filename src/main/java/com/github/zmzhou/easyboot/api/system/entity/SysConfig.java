package com.github.zmzhou.easyboot.api.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

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
    private String configName;

    /** 参数键名 */
    private String configKey;

    /** 参数键值 */
    private String configValue;

    /** 系统内置（Y是 N否） */
    private String configType;

}
