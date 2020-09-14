package com.github.zmzhou.easyboot.framework.aop;

import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.security.service.TokenService;

import lombok.extern.slf4j.Slf4j;

/**
 * @description Web log aspect.
 * @author zmzhou
 * @date 2020/07/03 11:12
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    @Resource
    private TokenService tokenService;

    /**
     * api controller包的切入点的名称
     * @author zmzhou
     * @date 2020/08/29 16:18
     */
    @Pointcut("execution(public * com.github.zmzhou.easyboot.api.*.controller..*.*(..))")
    public void controllerLog() {
        log.debug("api controller类切点");
    }

    /**
     * framework controller包的切入点.
     * @author zmzhou
     * @date 2020/08/29 16:18
     */
    @Pointcut("execution(public * com.github.zmzhou.easyboot.framework.*.controller..*.*(..))")
    public void frameworkControllerLog() {
        log.debug("framework controller类切点");
    }

    /**
     * 在切入点的方法run之前记录日志
     *
     * @param joinPoint the join point
     * @return 方法返回值
     * @author zmzhou
     * @date 2020/08/29 14:55
     */
    @Around("controllerLog() || frameworkControllerLog()")
    public Object around(ProceedingJoinPoint joinPoint) {
        //这个RequestContextHolder是Springmvc提供来获得请求的东西
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String method = joinPoint.getSignature().getName();
        String args = JSON.toJSONString(joinPoint.getArgs());
        long startTimeMillis = System.currentTimeMillis();
        // 获取用户身份信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        String ip = "";
        // 登录成功前为空
        if (loginUser != null) {
            ip = loginUser.getIpAddr();
        }
        try {
            // 调用 proceed() 方法才会真正的执行实际被代理的方法
            Object result = joinPoint.proceed();
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            StringBuilder sb = new StringBuilder(1000);
            sb.append(System.lineSeparator()).append("-----------------------")
                .append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE))
                .append("-----------------------").append(System.lineSeparator())
                .append("Class  : ").append(joinPoint.getSignature().getDeclaringTypeName())
                .append(System.lineSeparator())
                .append("Method : ").append(method).append("\t remote ip : ").append(ip)
                .append(System.lineSeparator())
                .append("Params : ").append(request.getMethod()).append(" ").append(request.getRequestURL())
                .append(" ").append(args).append(System.lineSeparator())
                .append("Return : ").append(JSON.toJSONString(result)).append(System.lineSeparator())
                .append("Cost   : ").append(execTimeMillis);
            log.info("{}", sb);
            // 返回方法执行结果
            return result;
        } catch (BaseException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            log.error("参数校验异常：{}", e.getConstraintViolations(), e);
            ConstraintViolationImpl impl = (ConstraintViolationImpl) e.getConstraintViolations().toArray()[0];
            return ApiResult.badRequest().error(impl.getMessageTemplate());
        } catch (Throwable throwable) {
            log.error("记录日志异常", throwable);
        }
        return ApiResult.badRequest();
    }
}
