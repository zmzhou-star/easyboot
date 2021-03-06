package com.github.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.system.entity.SysMenu;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

/**
 * The type Sys menu vo.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysMenuVo
 * @description 菜单管理VO类
 * @date 2020 /8/30 17:17
 */
public class SysMenuVo extends SysMenu implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = 4602299726296062535L;
    /**
     * Entity sys user.
     *
     * @return the sys user
     */
    @Override
    public SysMenu toEntity(){
        SysMenu e = new SysMenu();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
