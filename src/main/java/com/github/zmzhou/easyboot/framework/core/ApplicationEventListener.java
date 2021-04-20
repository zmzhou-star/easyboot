package com.github.zmzhou.easyboot.framework.core;

import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * springboot生命周期
 * @author zmzhou
 * @version 1.0
 * date 2021/4/20 13:54
 */
@Slf4j
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// 在这里可以监听到Spring Boot的生命周期
		if (event instanceof ApplicationStartingEvent) {
			log.info("应用程序启动中");
		} else if (event instanceof ApplicationEnvironmentPreparedEvent) {
			log.info("初始化环境变量");
		} else if (event instanceof ApplicationContextInitializedEvent) {
			log.info("ApplicationContext初始化完成");
		} else if (event instanceof ApplicationPreparedEvent) {
			log.info("Spring容器执行refresh前触发");
		} else if (event instanceof ContextRefreshedEvent) {
			log.info("应用刷新");
		} else if (event instanceof ApplicationReadyEvent) {
			log.info("应用已启动完成");
		} else if (event instanceof ContextStartedEvent) {
			// 应用启动，Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件。
			log.info("应用启动好了");
		} else if (event instanceof AvailabilityChangeEvent) {
			log.info("应用已处于活动状态");
		} else if (event instanceof ApplicationFailedEvent) {
			log.info("应用启动失败");
		} else if (event instanceof ContextStoppedEvent) {
			// 应用停止，Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。
			log.info("应用停止");
		} else if (event instanceof ContextClosedEvent) {
			// 应用关闭，当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。
			log.info("应用关闭");
		} else if (event instanceof DataSourceSchemaCreatedEvent) {
			log.info("数据源架构创建的事件");
		} else {
			log.info("其他事件:{}", event);
		}
	}
}
