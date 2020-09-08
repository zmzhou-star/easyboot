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
        SpringApplication.run(EasybootApplication.class, args);
    }

}
