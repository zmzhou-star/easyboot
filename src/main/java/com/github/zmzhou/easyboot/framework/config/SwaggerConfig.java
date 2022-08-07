package com.github.zmzhou.easyboot.framework.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.zmzhou.easyboot.common.Constants;

import lombok.Data;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
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
@EnableOpenApi
@EnableWebMvc
@EnableSwagger2
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {
	/**
	 * swagger配置
	 */
	@Resource
	private SwaggerProperties properties;
	/** 项目名称 */
	@Value("${spring.application.name}")
	private String applicationName;

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

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
        ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
        EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
        WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(),
            new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties,
                                               Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath)
            || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
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
				.enable(properties.isEnabled())
				// 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.apiInfo(apiInfo())
				// 设置哪些接口暴露给Swagger展示
				.select()
				// 扫描所有有注解的api，用这种方式更灵活
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
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
				.title("《" + applicationName + "接口文档》")
				// 描述
				.description("spring boot、spring security、jwt token、redis、jpa + vue-admin-template" +
						"实现前后端分离简单入门框架接口文档</br>***请点击Authorize按钮输入token***</br>" +
						"官方注解文档：https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations")
				// 作者信息
				.termsOfServiceUrl(properties.getTermsOfServiceUrl())
				.contact(new Contact(properties.getContactName(), properties.getContactUrl(), properties.getContactEmail()))
				.license("Apache 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
				// 版本
				.version(properties.getVersion())
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
/**
 * swagger自定义配置
 * @author zmzhou
 * @version 1.0
 * @since 2021/5/18 11:25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
class SwaggerProperties {
	/**
	 * swagger开关，默认开启
	 */
	private boolean enabled = true;
	/** 服务条款网址 */
	private String termsOfServiceUrl = "https://github.com/zmzhou-star/easyboot";
	/** 联系人姓名 */
	private String contactName = "zmzhou-star";
	/** 联系网址 */
	private String contactUrl = "http://127.0.0.1:8089/eboot";
	/** 联系人邮箱 */
	private String contactEmail = "zmzhou8@qq.com";
	/** 版本 */
	private String version = "1.0";
}
