package com.github.zmzhou.easyboot.api.monitor.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.github.zmzhou.easyboot.framework.entity.BaseEntity;

import lombok.Data;

/**
 * 定时任务实体类 sys_task
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-12-16 17:34:26
 */
@Data
@Entity
@Table(name = "SYS_TASK")
public class SysTask extends BaseEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = -7501705064269548754L;
    /** 任务名称 */
    @NotBlank(message = "任务名称不能为空")
    @Size(min = 2, max = 64, message = "任务名称长度不符")
    private String jobName;

    /** 任务分组 */
    @NotBlank(message = "任务分组不能为空")
    private String jobGroup;

    /** 任务执行时调用哪个类 */
    @NotBlank(message = "bean名字不能为空")
    @Pattern(regexp = "^[A-Za-z]{1,64}$", message = "bean名字格式不合法")
    private String beanName;

    /** 类的方法名 */
    @NotBlank(message = "类的方法名不能为空")
    @Pattern(regexp = "^[A-Za-z]{1,32}$", message = "类的方法名格式不合法")
    private String methodName;

    /** 类的方法参数 */
    private String methodParams;

    /** cron表达式 */
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    /** 计划执行错误策略（1立即执行 2执行一次 3放弃执行） */
    @NotBlank(message = "计划执行错误策略不能为空")
    @Pattern(regexp = "^[123]$", message = "计划执行错误策略格式不合法")
    private String misfirePolicy;

    /** 是否并发执行（1允许 0禁止） */
    @NotBlank(message = "是否并发执行不能为空")
    @Pattern(regexp = "^[01]$", message = "是否并发执行格式不合法")
    private String concurrent;

    /** 任务状态（1正常 0关闭） */
    @NotBlank(message = "任务状态不能为空")
    @Pattern(regexp = "^[01]$", message = "任务状态格式不合法")
    private String status;
}
