package com.github.zmzhou.easyboot.framework.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不打印切面日志注解（默认controller都会打印日志）
 * @author zmzhou
 * @version 1.0
 * date 2020/9/20 20:36
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoPrintLog {

}
