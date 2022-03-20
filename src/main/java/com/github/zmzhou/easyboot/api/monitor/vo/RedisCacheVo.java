/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.monitor.vo;

import java.util.List;
import java.util.Properties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * redis缓存信息vo
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022/3/20 13:21
 */
@Data
@ApiModel(description = "redis缓存信息vo")
public class RedisCacheVo {
    /** 基本信息 */
    @ApiModelProperty(value = "基本信息")
    private Properties baseInfo;

    /** Key数量 */
    @ApiModelProperty(value = "Key数量")
    private Long dbSize;

    /** Key数量 */
    @ApiModelProperty(value = "Key数量")
    private List<CommandStatVo> commandStats;

    /**
     * 命令统计信息VO
     *
     * @author zmzhou
     * @since 2022/3/20 13:35
     */
    @Data
    public static class CommandStatVo {
        /** 命令名称 */
        @ApiModelProperty(value = "命令名称")
        private String name;

        /** 调用次数 */
        @ApiModelProperty(value = "调用次数")
        private String calls;

        /** 调用总耗时 */
        @ApiModelProperty(value = "调用总耗时")
        private String usec;

        /** 调用平均耗时 */
        @ApiModelProperty(value = "调用平均耗时")
        private String usecPerCall;
    }
}
