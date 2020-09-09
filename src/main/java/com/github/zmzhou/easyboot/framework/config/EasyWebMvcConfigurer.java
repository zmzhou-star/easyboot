package com.github.zmzhou.easyboot.framework.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.zmzhou.easyboot.common.utils.DateUtils;

/**
 * @description web配置
 * @author zmzhou
 * @date 2020/07/02 19:34
 */
@Configuration
public class EasyWebMvcConfigurer implements WebMvcConfigurer {
    /**
     * 方法描述
     * @param registry ResourceHandlerRegistry
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
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .maxAge(1800)
                .allowedOrigins("*");
    }

    /**
     * Configure message converters.
     * json格式转换
     * @param converters the converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.DisableCircularReferenceDetect,
                //结果是否格式化,默认为false
                SerializerFeature.PrettyFormat
        );
	    //格式化日期
	    fastJsonConfig.setDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.asMediaType(MimeType.valueOf("application/json;charset=UTF-8")));
        mediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(fastJsonHttpMessageConverter);
    }
}
