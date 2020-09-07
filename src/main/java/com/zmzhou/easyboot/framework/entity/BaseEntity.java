package com.zmzhou.easyboot.framework.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

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
public class BaseEntity implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "persistenceGenerator", strategy = "increment")
    @Column(name = "ID")
    private Long id;
    /**
     * 创建者
     */
    @Column(name = "CREATE_BY")
    private String createBy;
    
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
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
    @JSONField(format = "yyyy-MM-dd HH:mm")
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
    
    /**
     * Gets update time.
     *
     * @return the update time
     */
    public Date getUpdateTime() {
        if (null == this.updateTime) {
            return null;
        }
        return (Date) (this.updateTime).clone();
    }
    
    /**
     * Sets update time.
     *
     * @param updateTime the update time
     */
    public void setUpdateTime(Date updateTime) {
        if (null == updateTime) {
            this.updateTime = null;
        } else {
            this.updateTime = (Date) (updateTime).clone();
        }
    }
}
