package com.github.zmzhou.easyboot.framework.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
import com.github.zmzhou.easyboot.framework.filter.AccessControlFilter;
import com.github.zmzhou.easyboot.framework.filter.MyCsrfFilter;
import com.github.zmzhou.easyboot.framework.security.filter.XssFilter;

/**
 * web配置
 * @author zmzhou
 * date 2020/07/02 19:34
 */
@Configuration
public class EasyWebMvcConfigurer implements WebMvcConfigurer {
	@Autowired
	private CorsConfig corsConfig;

    /**
     * 解决静态资源无法访问
     * @param registry ResourceHandlerRegistry
     * @author zmzhou
     * @date 2020/07/02 19:35
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
    }

    /**
     * 跨站资源共享配置
	 * Cross Origin Resourse-Sharing - 跨站资源共享
     * @param registry CorsRegistry
     * @author zmzhou
     * @date 2020/07/03 15:09
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
		// Add more mappings... 可以添加多个mapping
        registry.addMapping("/**")
				// 服务器支持的所有头信息字段
                .allowedHeaders(corsConfig.getAllowedHeaders())
				// 服务器支持的所有跨域请求的方法
                .allowedMethods(corsConfig.getAllowedMethods())
                // 是否允许发送Cookie
                .allowCredentials(corsConfig.isAllowCredentials())
				// 指定本次请求的有效期
                .maxAge(corsConfig.getMaxAge())
                // 设置允许跨域请求的域名
                .allowedOriginPatterns(corsConfig.getAllowedOriginPatterns());
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

    /**
     * 注册防御XSS(Cross Site Scripting)跨站脚本攻击过滤器
     * Xss filter registration filter registration bean.
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        // 拦截所有请求
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        // 最高等级注册
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    /**
     * 跨站请求伪造 过滤器
     * @author zmzhou
     * @since 2021/5/7 17:59
     */
    @Bean
    public FilterRegistrationBean<MyCsrfFilter> csrfFilterRegistration() {
        FilterRegistrationBean<MyCsrfFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new MyCsrfFilter(corsConfig));
        // 拦截所有请求
        registration.addUrlPatterns("/*");
        registration.setName("csrfFilter");
        // 最高等级注册
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

	/**
	 * 权限控制过滤器
	 * @author zmzhou
	 * @since 2021/5/7 17:59
	 */
	@Bean
	public FilterRegistrationBean<AccessControlFilter> accessControlFilterRegistration() {
		FilterRegistrationBean<AccessControlFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new AccessControlFilter());
		// 拦截所有请求
		registration.addUrlPatterns("/*");
		registration.setName("accessControlFilter");
		// 最高等级注册
		registration.setOrder(-1);
		return registration;
	}
}
