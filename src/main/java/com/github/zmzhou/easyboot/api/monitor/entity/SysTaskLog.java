package com.github.zmzhou.easyboot.api.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.zmzhou.easyboot.framework.entity.BaseIdEntity;

import lombok.Data;

/**
 * 定时任务日志实体类 sys_task_log
 * 
 * @author zmzhou
 * @version 1.0
 * date 2020-12-17 19:40:43
 */
@Data
@Entity
@Table(name = "SYS_TASK_LOG")
public class SysTaskLog extends BaseIdEntity {
    /** serialVersionUID */
    private static final long serialVersionUID = 4866808160559192684L;
    /** 任务名称 */
    private String jobName;

    /** 任务分组 */
    private String jobGroup;

    /** bean名字 */
    private String beanName;

    /** 类的方法名 */
    private String methodName;

    /** 类的方法参数 */
    private String methodParams;

    /** cron表达式 */
    private String cronExpression;

    /** 耗时 */
    private Long timeConsuming;

    /** 执行状态（1正常 0失败） */
    private String status;

    /** 异常信息 */
    private String exceptionInfo;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @Column(name = "CREATE_TIME")
    private Date createTime;
    /**
     * Gets create time.
     *
     * @return the create time
     */
    public Date getCreateTime() {
        if (null == this.createTime) {
            return null;
        }
        return (Date) (this.createTime).clone();
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(Date createTime) {
        if (null == createTime) {
            this.createTime = null;
        } else {
            this.createTime = (Date) (createTime).clone();
        }
    }
}
