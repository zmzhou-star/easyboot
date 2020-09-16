package com.github.zmzhou.easyboot.framework.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.zmzhou.easyboot.common.Constants;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 * SwaggerConfig
 *
 * @author zmzhou
 * @version 1.0
 * @date 2020/9/10 22:41
 */
@Configuration
@EnableSwagger2
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {
	/**
	 * swagger开关
	 */
	@Value("${server.swagger.enabled}")
	private boolean enabled;

	/**
	 * Add resource handlers.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 解决swagger无法访问
		registry.addResourceHandler("/swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		// 解决swagger的js文件无法访问
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * 创建API
	 *
	 * @return Docket
	 * @author zmzhou
	 * @date 2020/9/10 22:41
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				// 是否启用Swagger
				.enable(enabled)
				// 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.apiInfo(apiInfo())
				// 设置哪些接口暴露给Swagger展示
				.select()
				// 扫描所有有注解的api，用这种方式更灵活
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				// 扫描所有 .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				/* 设置安全模式，swagger可以设置访问token */
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	/**
	 * 添加摘要信息
	 *
	 * @return ApiInfo
	 * @author zmzhou
	 * @date 2020/9/10 22:42
	 */
	private ApiInfo apiInfo() {
		// 用ApiInfoBuilder进行定制
		return new ApiInfoBuilder()
				// 设置标题
				.title("《easy boot接口文档》")
				// 描述
				.description("spring boot、spring security、jwt token、redis、jpa + vue-admin-template" +
						"实现前后端分离简单入门框架接口文档</br>***请点击Authorize按钮输入token***</br>" +
						"官方注解文档：https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations")
				// 作者信息
				.termsOfServiceUrl("https://github.com/zmzhou-star/easyboot")
				.contact(new Contact("zmzhou", "http://127.0.0.1:8089/eboot", "zmzhou8@qq.com"))
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				// 版本
				.version("1.0")
				.build();
	}

	/**
	 * 安全模式，这里指定token通过Authorization头请求头传递
	 *
	 * @return List<SecurityScheme>
	 * @author zmzhou
	 * @date 2020/9/10 22:45
	 */
	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> apiKeyList = new ArrayList<>();
		apiKeyList.add(new ApiKey(Constants.AUTHORIZATION, Constants.AUTHORIZATION, "header"));
		return apiKeyList;
	}

	/**
	 * 安全上下文
	 *
	 * @return List<SecurityContext>
	 * @author zmzhou
	 * @date 2020/9/10 22:45
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(PathSelectors.regex("^(?!auth).*$"))
						.build());
		return securityContexts;
	}

	/**
	 * 默认的安全上引用
	 *
	 * @return List<SecurityReference>
	 * @author zmzhou
	 * @date 2020/9/10 22:46
	 */
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference(Constants.AUTHORIZATION, authorizationScopes));
		return securityReferences;
	}
}
