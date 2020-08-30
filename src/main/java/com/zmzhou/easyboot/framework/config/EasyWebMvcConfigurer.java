package com.zmzhou.easyboot.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description web配置
 * @author zmzhou
 * @date 2020/07/02 19:34
 */
@Configuration
public class EasyWebMvcConfigurer implements WebMvcConfigurer {
    /**
     * 方法描述
     * @param registry
     * @author zmzhou
     * @date 2020/07/02 19:35
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
    }

    /**
     * 允许所有跨站请求
     * @param registry CorsRegistry
     * @author zmzhou
     * @date 2020/07/03 15:09
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(1800)
                .allowedOrigins("*");
    }
}
