package com.github.zmzhou.easyboot.framework.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * The type Base entity.
 *
 * @author zmzhou
 * @description 实体类父类
 * @date 2020 /07/02 17:25
 */
@Data
@MappedSuperclass
public class BaseEntity extends BaseIdEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -519908860784980193L;

    /**
     * 创建者
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * The Remark.
     */
    @Column(name = "REMARK")
    private String remark;

	/**
	 * Gets create time.
	 *
	 * @return the create time
	 */
	public Date getCreateTime() {
		return Optional.ofNullable(this.createTime).map(date -> (Date) (date).clone()).orElse(null);
	}

	/**
	 * Sets create time.
	 *
	 * @param createTime the create time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = Optional.ofNullable(createTime).map(date -> (Date) (date).clone()).orElse(null);
	}

	/**
	 * Gets update time.
	 *
	 * @return the update time
	 */
	public Date getUpdateTime() {
		return Optional.ofNullable(this.updateTime).map(date -> (Date) (date).clone()).orElse(null);
	}

	/**
	 * Sets update time.
	 *
	 * @param updateTime the update time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = Optional.ofNullable(updateTime).map(date -> (Date) (date).clone()).orElse(null);
	}
}
