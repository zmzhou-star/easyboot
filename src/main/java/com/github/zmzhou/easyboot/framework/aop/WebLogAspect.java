package com.github.zmzhou.easyboot.framework.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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
import com.github.zmzhou.easyboot.api.monitor.service.SysOperLogService;
import com.github.zmzhou.easyboot.common.Constants;
import com.github.zmzhou.easyboot.common.exception.BaseException;
import com.github.zmzhou.easyboot.common.utils.DateUtils;
import com.github.zmzhou.easyboot.framework.page.ApiResult;
import com.github.zmzhou.easyboot.framework.security.LoginUser;
import com.github.zmzhou.easyboot.framework.service.TokenService;

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
    @Resource
    private SysOperLogService operLogService;

    /**
     * api controller包的切入点的名称
     * <p>
     *     排除带有NoPrintLog注解的方法或类
     * </p>
     * @author zmzhou
     * @date 2020/08/29 16:18
     */
    @Pointcut("execution(public * com.github.zmzhou.easyboot.api.*.controller..*.*(..))" +
            " && !@annotation(com.github.zmzhou.easyboot.framework.annotations.NoPrintLog)")
    public void controllerLog() {
        // api controller类切点
    }

    /**
     * framework controller包的切入点.
     * @author zmzhou
     * @date 2020/08/29 16:18
     */
    @Pointcut("execution(public * com.github.zmzhou.easyboot.framework.*.controller..*.*(..))")
    public void frameworkControllerLog() {
        // framework controller类切点
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
        Object[] args = joinPoint.getArgs();
        long startTimeMillis = DateUtils.currentTimeMillis();
        // 获取用户身份信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        String ip = "";
        // 登录成功前为空
        if (loginUser != null) {
            ip = loginUser.getIpAddr();
        }
        Class<?> clazz = joinPoint.getSignature().getDeclaringType();
        Object result = null;
        try {
            // 调用 proceed() 方法才会真正的执行实际被代理的方法
            result = joinPoint.proceed();
            long execTimeMillis = DateUtils.currentTimeMillis() - startTimeMillis;
            StringBuilder sb = new StringBuilder(1000);
            sb.append(System.lineSeparator()).append("-----------------------")
                .append(DateUtils.getTime())
                .append("-----------------------").append(System.lineSeparator())
                .append("Class  : ").append(clazz.getName()).append(System.lineSeparator())
                .append("Method : ").append(method).append("\t remote ip : ").append(ip)
                .append(System.lineSeparator())
                .append("Params : ").append(request.getMethod()).append(" ").append(request.getRequestURL())
                .append(" ").append(JSON.toJSONString(args)).append(System.lineSeparator())
                .append("Return : ").append(JSON.toJSONString(result)).append(System.lineSeparator())
                .append("Cost   : ").append(execTimeMillis).append("ms");
            log.info("{}", sb);
            // 记录操作日志
            operLogService.saveOperLog(request, clazz, method, args, result, Constants.ONE, "");
            // 返回方法执行结果
            return result;
        } catch (BaseException e) {
            // 记录操作失败日志
            operLogService.saveOperLog(request, clazz, method, args, result, Constants.ZERO, e.getErrMsg());
            throw e;
        } catch (ConstraintViolationException e) {
            operLogService.saveOperLog(request, clazz, method, args, result, Constants.ZERO,
                    e.getConstraintViolations().toString());
            log.error("参数校验异常：{}", e.getConstraintViolations(), e);
            ConstraintViolationImpl<?> impl = (ConstraintViolationImpl<?>) e.getConstraintViolations().toArray()[0];
            return ApiResult.badRequest().error(impl.getMessageTemplate());
        } catch (Throwable throwable) {
            operLogService.saveOperLog(request, clazz, method, args, result, Constants.ZERO, throwable.getMessage());
            log.error("记录日志异常", throwable);
        }
        return ApiResult.badRequest();
    }
}
