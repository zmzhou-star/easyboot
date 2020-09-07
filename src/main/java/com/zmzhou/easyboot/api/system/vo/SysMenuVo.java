package com.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.zmzhou.easyboot.api.system.entity.SysMenu;

/**
 * The type Sys menu vo.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysMenuVo
 * @description 菜单管理VO类
 * @date 2020 /8/30 17:17
 */
public class SysMenuVo extends SysMenu {
    /** serialVersionUID */
    private static final long serialVersionUID = 4602299726296062535L;
    /**
     * Entity sys user.
     *
     * @return the sys user
     */
    public SysMenu toEntity(){
        SysMenu e = new SysMenu();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
