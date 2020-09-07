package com.zmzhou.easyboot.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 *  @title ServletUtils
 *  @Description 客户端工具类
 *  @author zmzhou
 *  @Date 2020/07/03 15:36
 */
@Slf4j
@Component
public final class ServletUtils implements ApplicationContextAware {
    /** spring上下文 */
    private static ApplicationContext context;

    /**
     * 私有构造器
     *
     * @author zmzhou
     * @date 2020/08/29 14:18
     */
    private ServletUtils() {
    }

    /**
     * @param applicationContext 上下文
     * @description 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
     * @author zmzhou
     * @date 2020/9/2 22:36
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        log.info("初始化上下文：{}", applicationContext);
        ServletUtils.context = applicationContext;
    }
    /**
     * @description  获取ApplicationContext.
     * @return  ApplicationContext
     * @author zmzhou
     * @date 2020/9/2 22:41
     */
    public static ApplicationContext getContext() {
        return context;
    }
    /**
     * @description 从ApplicationContext中取得Bean
     * @param  clazz bean
     * @return  对象
     * @author zmzhou
     * @date 2020/9/2 22:40
     */
    public static <T> T getBean(Class<T> clazz) {
        return getContext().getBean(clazz);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * @description 获取ServletRequestAttributes
     * @return ServletRequestAttributes
     * @author zmzhou
     * @date 2020/9/6 12:07
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
    /**
     * 使用response输出JSON
     * @param response
     * @param result 数据
     * @return
     * @author zmzhou
     * @date 2020/07/21 11:32
     */
    public static void response(HttpServletResponse response, Object result){
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()){
            out.println(JSON.toJSONString(result));
            out.flush();
        } catch (IOException e) {
            log.error("【JSON输出异常】", e);
        }
    }
    /**
     * 解析请求的Json数据
     * @return
     * @author zmzhou
     * @date 2020/07/08 15:51
     */
    public static String getRequestBody() {
        // 获取HttpServletRequest
        HttpServletRequest request = getRequest();
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        try {
            byte[] buffer = new byte[contentLength];
            request.getInputStream().read(buffer, 0, buffer.length);
            // 获取编码
            String charset = StandardCharsets.UTF_8.name();
            if (StringUtils.isNotBlank(request.getCharacterEncoding())) {
                charset = request.getCharacterEncoding();
            }
            return new String(buffer, charset);
        } catch (Exception e) {
            log.error("", e);
            return "";
        }
    }
}
