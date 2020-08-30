package com.zmzhou.easyboot;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @description 程序入口
 * @author zmzhou
 * @date 2020/07/02 20:12
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
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

    /**
     * @description 定时任务
     * @author zmzhou
     * @date 2020/8/30 12:52
     */
    @Scheduled(cron = "0/20 * * * * ?")
    public void run20s() {
        log.info("定时任务:{}", LocalDateTime.now());
    }
}
