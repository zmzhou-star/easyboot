package com.github.zmzhou.easyboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口
 * EasybootApplication
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/7 21:15
 */
@Slf4j
@EnableCaching
@SpringBootApplication
//@EnableDiscoveryClient
public class EasybootApplication {
    /**
     * @description 程序入口
     * @param args 参数
     * @author zmzhou
     * @date 2020/8/30 12:53
     */
    public static void main(String[] args) {
        // log4j2全局异步日志配置 http://logging.apache.org/log4j/2.x/manual/async.html#AllAsync
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(EasybootApplication.class, args);
    }

}
