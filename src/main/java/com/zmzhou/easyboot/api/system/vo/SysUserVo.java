package com.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.zmzhou.easyboot.api.system.entity.SysUser;

/**
 * The type Sys user vo.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysUserVo
 * @description 用户管理vo类
 * @date 2020 /8/30 17:18
 */
public class SysUserVo extends SysUser {
    /** serialVersionUID */
    private static final long serialVersionUID = 5874469802059198167L;

    /**
     * Entity sys user.
     *
     * @return the sys user
     */
    public SysUser toEntity(){
        SysUser e = new SysUser();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
