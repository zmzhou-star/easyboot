/*
 * Copyright © 2020-present zmzhou-star. All Rights Reserved.
 */

package com.github.zmzhou.easyboot.api.monitor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zmzhou.easyboot.api.monitor.vo.RedisCacheVo;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.web.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * redis缓存监控
 *
 * @author zmzhou
 * @version 1.0
 * @since 2022/3/20 13:09
 */
@Api(tags = {"redis缓存监控"})
@RestController
@RequestMapping("/monitor/cache")
public class RedisCacheController extends BaseController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取redis缓存信息
     *
     * @return com.github.zmzhou.easyboot.framework.page.ApiResult<com.github.zmzhou.easyboot.api.monitor.vo.RedisCacheVo>
     * @author zmzhou
     * @since 2022/3/20 13:51
     */
    @PreAuthorize("@ebpe.hasPermission('monitor:cache:list')")
    @ApiOperation(value = "获取redis缓存信息")
    @GetMapping()
    public ApiResult<RedisCacheVo> getCacheInfo() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>)
            connection -> connection.info("commandstats"));
        Long dbSize = (Long) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

        RedisCacheVo cacheVo = new RedisCacheVo();
        cacheVo.setInfo(info);
        cacheVo.setDbSize(dbSize);
        List<RedisCacheVo.CommandStatVo> statVos = new ArrayList<>();
        Optional.ofNullable(commandStats).map(command -> commandStats.stringPropertyNames())
            .ifPresent(props -> props.forEach(key -> {
                RedisCacheVo.CommandStatVo commandStatVo = new RedisCacheVo.CommandStatVo();
                String value = commandStats.getProperty(key);
                commandStatVo.setName(StringUtils.removeStart(key, "cmdstat_"));
                commandStatVo.setValue(StringUtils.substringBetween(value, "calls=", ",usec"));
                commandStatVo.setUsec(StringUtils.substringBetween(value, "usec=", ",usec_per_call"));
                commandStatVo.setUsecPerCall(StringUtils.substringAfter(value, "usec_per_call="));
                statVos.add(commandStatVo);
            }));
        cacheVo.setCommandStats(statVos);
        return ok(cacheVo);
    }
}
