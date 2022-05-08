package com.github.zmzhou.easyboot;

import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.github.zmzhou.easyboot.common.utils.ThreadPoolUtils;

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
     * 程序入口
     *
     * @param args 参数
     * @author zmzhou
     * @date 2020/8/30 12:53
     */
    public static void main(String[] args) {
        // log4j2全局异步日志配置 http://logging.apache.org/log4j/2.x/manual/async.html#AllAsync
        System.setProperty("Log4jContextSelector", AsyncLoggerContextSelector.class.getName());
        SpringApplication.run(EasybootApplication.class, args);
		// 停止应用时，关闭线程池钩子，或者使用 @PreDestroy 注解执行一系列操作
		Runtime.getRuntime().addShutdownHook(new Thread(ThreadPoolUtils::shutdown, "ShutdownThreadPoolHook"));
    }
}
